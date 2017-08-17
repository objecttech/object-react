(ns objecttech.chat.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [objecttech.components.react :refer [view
                                                animated-view
                                                text
                                                icon
                                                modal
                                                touchable-highlight
                                                list-view
                                                list-item]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.chat-icon.screen :refer [chat-icon-view-action
                                                           chat-icon-view-menu-item]]
            [objecttech.chat.styles.screen :as st]
            [objecttech.utils.listview :refer [to-datasource-inverted]]
            [objecttech.utils.utils :refer [truncate-str]]
            [objecttech.utils.datetime :as time]
            [objecttech.utils.platform :as platform :refer [platform-specific]]
            [objecttech.components.invertible-scroll-view :refer [invertible-scroll-view]]
            [objecttech.components.toolbar-new.view :refer [toolbar]]
            [objecttech.chat.views.toolbar-content :refer [toolbar-content-view]]
            [objecttech.chat.views.message.message :refer [chat-message]]
            [objecttech.chat.views.message.datemark :refer [chat-datemark]]
            [objecttech.chat.views.input.input :as input]
            [objecttech.chat.views.actions :refer [actions-view]]
            [objecttech.chat.views.bottom-info :refer [bottom-info-view]]
            [objecttech.chat.constants :as const]
            [objecttech.i18n :refer [label label-pluralize]]
            [objecttech.components.animation :as anim]
            [objecttech.components.sync-state.offline :refer [offline-view]]
            [objecttech.constants :refer [content-type-object]]
            [taoensso.timbre :as log]
            [clojure.string :as str]))

(defn contacts-by-identity [contacts]
  (->> contacts
       (map (fn [{:keys [identity] :as contact}]
              [identity contact]))
       (into {})))

(defn add-message-color [{:keys [from] :as message} contact-by-identity]
  (if (= "system" from)
    (assoc message :text-color :#4A5258
                   :background-color :#D3EEEF)
    (let [{:keys [text-color background-color]} (get contact-by-identity from)]
      (assoc message :text-color text-color
                     :background-color background-color))))

(defview chat-icon []
  [chat-id [:chat :chat-id]
   group-chat [:chat :group-chat]
   name [:chat :name]
   color [:chat :color]]
  ;; TODO stub data ('online' property)
  [chat-icon-view-action chat-id group-chat name color true])

(defn typing [member]
  [view st/typing-view
   [view st/typing-background
    [text {:style st/typing-text
           :font  :default}
     (str member " " (label :t/is-typing))]]])

(defn typing-all []
  [view st/typing-all
   ;; TODO stub data
   (for [member ["Geoff" "Justas"]]
     ^{:key member} [typing member])])

(defmulti message-row (fn [{{:keys [type]} :row}] type))

(defmethod message-row :datemark
  [{{:keys [value]} :row}]
  (list-item [chat-datemark value]))

(defmethod message-row :default
  [{:keys [contact-by-identity group-chat messages-count row index last-outgoing?]}]
  (let [message (-> row
                    (add-message-color contact-by-identity)
                    (assoc :group-chat group-chat)
                    (assoc :messages-count messages-count)
                    (assoc :index index)
                    (assoc :last-message (= (js/parseInt index) (dec messages-count)))
                    (assoc :last-outgoing? last-outgoing?))]
    (list-item [chat-message message])))

(defn toolbar-action []
  (let [show-actions (subscribe [:chat-ui-props :show-actions?])]
    (fn []
      (if @show-actions
        [touchable-highlight
         {:on-press #(dispatch [:set-chat-ui-props {:show-actions? false}])}
         [view st/action
          [icon :up st/up-icon]]]
        [touchable-highlight
         {:on-press #(dispatch [:set-chat-ui-props {:show-actions? true}])}
         [view st/action
          [chat-icon]]]))))

(defview add-contact-bar []
  [chat-id [:get :current-chat-id]
   pending-contact? [:current-contact :pending?]]
  (when pending-contact?
    [touchable-highlight
     {:on-press #(dispatch [:add-pending-contact chat-id])}
     [view st/add-contact
      [text {:style st/add-contact-text}
       (label :t/add-to-contacts)]]]))

(defview chat-toolbar []
  [show-actions? [:chat-ui-props :show-actions?]
   accounts [:get :accounts]
   creating? [:get :creating-account?]]
  [view
   [object-bar]
   [toolbar {:hide-nav?      (or (empty? accounts) show-actions? creating?)
             :custom-content [toolbar-content-view]
             :custom-action  [toolbar-action]}]
   [add-contact-bar]])

(defn get-intro-object-message [all-messages]
  (let [{:keys [timestamp content-type]} (last all-messages)]
    (when (not= content-type content-type-object)
      {:message-id   const/intro-object-message-id
       :content-type content-type-object
       :timestamp    (or timestamp (time/now-ms))})))

(defn messages-with-timemarks [all-messages extras]
  (let [object-message (get-intro-object-message all-messages)
        all-messages   (if object-message
                         (concat all-messages [object-message])
                         all-messages)
        messages       (->> all-messages
                            (map #(merge % (get extras (:message-id %))))
                            (remove #(false? (:show? %)))
                            (sort-by :clock-value >)
                            (map #(assoc % :datemark (time/day-relative (:timestamp %))))
                            (group-by :datemark)
                            (vals)
                            (sort-by (comp :clock-value first) >)
                            (map (fn [v] [v {:type :datemark :value (:datemark (first v))}]))
                            (flatten))
        remove-last?   (some (fn [{:keys [content-type]}]
                               (= content-type content-type-object))
                             messages)]
    (if remove-last?
      (drop-last messages)
      messages)))

(defview messages-view [group-chat]
  [messages [:chat :messages]
   contacts [:chat :contacts]
   message-extras [:get :message-extras]
   loaded? [:all-messages-loaded?]
   current-chat-id [:get-current-chat-id]
   last-outgoing-message [:get-chat-last-outgoing-message @current-chat-id]]
  (let [contacts' (contacts-by-identity contacts)
        messages  (messages-with-timemarks messages message-extras)]
    [list-view {:renderRow                 (fn [row _ index]
                                             (message-row {:contact-by-identity contacts'
                                                           :group-chat          group-chat
                                                           :messages-count      (count messages)
                                                           :row                 row
                                                           :index               index
                                                           :last-outgoing?      (= (:message-id last-outgoing-message) (:message-id row))}))
                :renderScrollComponent     #(invertible-scroll-view (js->clj %))
                :onEndReached              (when-not loaded? #(dispatch [:load-more-messages]))
                :enableEmptySections       true
                :keyboardShouldPersistTaps (if platform/android? :always :handled)
                :dataSource                (to-datasource-inverted messages)}]))

(defview chat []
  [group-chat [:chat :group-chat]
   show-actions? [:chat-ui-props :show-actions?]
   show-bottom-info? [:chat-ui-props :show-bottom-info?]
   show-emoji? [:chat-ui-props :show-emoji?]
   layout-height [:get :layout-height]
   input-text [:chat :input-text]]
  {:component-did-mount    #(do (dispatch [:check-and-open-dapp!])
                                (dispatch [:update-suggestions]))
   :component-will-unmount #(dispatch [:set-chat-ui-props {:show-emoji? false}])}
  [view {:style st/chat-view
         :on-layout (fn [event]
                      (let [height (.. event -nativeEvent -layout -height)]
                        (when (not= height layout-height)
                          (dispatch [:set-layout-height height]))))}
   [chat-toolbar]
   [messages-view group-chat]
   [input/container {:text-empty? (str/blank? input-text)}]
   (when show-actions?
     [actions-view])
   (when show-bottom-info?
     [bottom-info-view])
   [offline-view {:top (get-in platform-specific
                               [:component-styles :object-bar :default :height])}]])

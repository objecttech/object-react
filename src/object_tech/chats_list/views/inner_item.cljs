(ns objecttech.chats-list.views.inner-item
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [clojure.string :as str]
            [objecttech.components.react :refer [view image icon text]]
            [objecttech.components.chat-icon.screen :refer [chat-icon-view-chat-list]]
            [objecttech.components.context-menu :refer [context-menu]]
            [objecttech.chats-list.styles :as st]
            [objecttech.utils.utils :refer [truncate-str]]
            [objecttech.i18n :refer [get-contact-translated label label-pluralize]]
            [objecttech.utils.datetime :as time]
            [objecttech.utils.gfycat.core :refer [generate-gfy]]
            [objecttech.constants :refer [console-chat-id
                                         content-type-command
                                         content-type-wallet-command
                                         content-type-command-request]]
            [taoensso.timbre :as log]
            [reagent.core :as r]))

(defn message-content-text [chat-id]
  (let [message (subscribe [:get-last-message chat-id])
        preview (subscribe [:get-last-message-short-preview chat-id])]
    (r/create-class
      {:display-name "message-content-text"
       :component-will-mount
       (fn []
         (when (and (get-in @message [:content :command])
                    (not @preview))
           (dispatch [:request-command-data @message :short-preview])))

       :reagent-render
       (fn [_]
         [view]
         (let [{:keys [content] :as message} @message
               preview @preview]
           [view st/last-message-container
            (cond

              (not message)
              [text {:style st/last-message-text}
               (label :t/no-messages)]

              (str/blank? content)
              [text {:style st/last-message-text}
               ""]

              (:content content)
              [text {:style           st/last-message-text
                     :number-of-lines 1}
               (:content content)]

              (:command content)
              (:markup preview)

              :else
              [text {:style           st/last-message-text
                     :number-of-lines 1}
               content])]))})))

(defview message-object [{:keys [chat-id contacts]}
                         {:keys [message-id message-object user-objectes message-type outgoing] :as msg}]
  [app-db-message-object-value [:get-in [:message-data :objectes message-id :object]]]
  (let [delivery-object (get-in user-objectes [chat-id :object])]
    (when (and outgoing
               (or (some #(= (keyword %) :seen) [delivery-object
                                                 message-object
                                                 app-db-message-object-value])
                   (and (= (keyword message-type) :group-user-message)
                        (and (= (count user-objectes) (count contacts))
                             (every? (fn [[_ {:keys [object]}]]
                                       (= (keyword object) :seen)) user-objectes)))
                   (= chat-id console-chat-id)))
      [image {:source {:uri :icon_ok_small}
              :style  st/objecttechage}])))

(defn message-timestamp [{:keys [timestamp]}]
  (when timestamp
    [text {:style st/datetime-text}
     (time/to-short-str timestamp)]))

(defview unviewed-indicator [chat-id]
  [unviewed-messages [:unviewed-messages-count chat-id]]
  (when (pos? unviewed-messages)
    [view st/new-messages-container
     [text {:style st/new-messages-text
            :font  :medium}
      unviewed-messages]]))

(defn options-btn [chat-id]
  (let [options [{:value        #(dispatch [:remove-chat chat-id])
                  :text         (label :t/delete-chat)
                  :destructive? true}]]
    [view st/opts-btn-container
     [context-menu
      [icon :options_gray]
      options
      nil
      st/opts-btn]]))

(defn chat-list-item-name [name group-chat? public?]
  (let [private-group? (and group-chat? (not public?))
        public-group?  (and group-chat? public?)
        chat-name      (if (str/blank? name)
                         (generate-gfy)
                         (truncate-str name 30))]
    [view st/name-view
     (when public-group?
       [view st/public-group-icon-container
        [icon :public_group st/public-group-icon]])
     (when private-group?
      [view st/private-group-icon-container
       [icon :private_group st/private-group-icon]])
     [view {:flex-shrink 1}
      [text {:style st/name-text
             :number-of-lines 1}
       (if public-group?
         (str "#" chat-name)
         chat-name)]]]))

(defn chat-list-item-inner-view [{:keys [chat-id name color online
                                         group-chat contacts public?
                                         unremovable?] :as chat}
                                 edit?]
  (let [last-message (subscribe [:get-last-message chat-id])
        name         (or (get-contact-translated chat-id :name name)
                         (generate-gfy))]
    [view st/chat-container
     [view st/chat-icon-container
      [chat-icon-view-chat-list chat-id group-chat name color online]]
     [view st/chat-info-container
      [view st/item-upper-container
       [chat-list-item-name name group-chat public?]
       (when (and (not edit?) @last-message)
         [view st/message-object-container
          [message-object chat @last-message]
          [message-timestamp @last-message]])]
      [view st/item-lower-container
       [message-content-text chat-id]
       (when-not edit? [unviewed-indicator chat-id])]]
     [view st/chat-options-container
      (when (and edit? (not unremovable?)) [options-btn chat-id])]]))

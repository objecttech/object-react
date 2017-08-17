(ns objecttech.chat.views.bottom-info
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reagent.core :as r]
            [objecttech.components.react :refer [view
                                                animated-view
                                                image
                                                text
                                                icon
                                                touchable-highlight
                                                list-view
                                                list-item]]
            [objecttech.components.chat-icon.screen :refer [chat-icon-view-menu-item]]
            [objecttech.chat.styles.screen :as st]
            [objecttech.i18n :refer [label label-pluralize message-object-label]]
            [objecttech.components.animation :as anim]
            [objecttech.utils.utils :refer [truncate-str]]
            [objecttech.utils.identicon :refer [identicon]]
            [objecttech.utils.listview :as lw]
            [clojure.string :as str]))

(defn- container-animation-logic [{:keys [to-value val]}]
  (fn [_]
    (anim/start
      (anim/spring val {:toValue  to-value
                        :friction 6
                        :tension  40}))))

(defn overlay [{:keys [on-click-outside]} items]
  [view {:style st/bottom-info-overlay}
   [touchable-highlight {:on-press on-click-outside
                         :style    st/overlay-highlight}
    [view nil]]
   items])

(defn container [height & _]
  (let [anim-value    (anim/create-value 1)
        context       {:to-value height
                       :val      anim-value}
        on-update     (container-animation-logic context)]
    (r/create-class
      {:component-did-update
       on-update
       :display-name "container"
       :reagent-render
       (fn [height & children]
         [animated-view {:style (st/bottom-info-container height)}
          (into [view] children)])})))

(defn message-object-row [{:keys [photo-path name]} {:keys [whisper-identity object]}]
  [view st/bottom-info-row
   [image {:source {:uri (or photo-path (identicon whisper-identity))}
           :style  st/bottom-info-row-photo}]
   [view st/bottom-info-row-text-container
    [text {:style           st/bottom-info-row-text1
           :number-of-lines 1}
     (truncate-str (if-not (str/blank? name)
                     name
                     whisper-identity) 30)]
    [text {:style           st/bottom-info-row-text2
           :number-of-lines 1}
     (message-object-label (or object :sending))]]])

(defn render-row [contacts]
  (fn [{:keys [whisper-identity] :as row} _ _]
    (let [contact (get contacts whisper-identity)]
      (list-item [message-object-row contact row]))))

(defn bottom-info-view []
  (let [bottom-info (subscribe [:chat-ui-props :bottom-info])
        contacts    (subscribe [:get-contacts])]
    (r/create-class
      {:display-name "bottom-info-view"
       :reagent-render
       (fn []
         (let [{:keys [user-objectes message-object participants]} @bottom-info
               participants (->> participants
                                 (map (fn [{:keys [identity]}]
                                        [identity {:whisper-identity identity
                                                   :object           message-object}]))
                                 (into {}))
               objectes     (vals (merge participants user-objectes))]
           [overlay {:on-click-outside #(dispatch [:set-chat-ui-props {:show-bottom-info? false}])}
            [container (* st/item-height (count objectes))
             [list-view {:dataSource            (lw/to-datasource objectes)
                         :enableEmptySections   true
                         :renderRow             (render-row @contacts)
                         :contentContainerStyle st/bottom-info-list-container}]]]))})))

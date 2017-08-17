(ns objecttech.discover.views.discover-list-item
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [clojure.string :as str]
            [objecttech.components.react :refer [view text image touchable-highlight]]
            [objecttech.discover.styles :as st]
            [objecttech.components.object-view.view :refer [object-view]]
            [objecttech.utils.gfycat.core :refer [generate-gfy]]
            [objecttech.utils.identicon :refer [identicon]]
            [objecttech.components.chat-icon.screen :as ci]
            [objecttech.utils.platform :refer [platform-specific]]))

(defview discover-list-item [{{:keys [name
                                       photo-path
                                       whisper-id
                                       message-id
                                       object]
                                :as   message}                   :message
                               show-separator?                   :show-separator?
                               {account-photo-path :photo-path
                                account-address    :public-key
                                account-name       :name
                                :as                current-account} :current-account}]
  [{contact-name       :name
    contact-photo-path :photo-path} [:get-in [:contacts/contacts whisper-id]]]
  (let [item-style (get-in platform-specific [:component-styles :discover :item])]
    [view
     [view st/popular-list-item
      [view st/popular-list-item-name-container
       [text {:style           st/popular-list-item-name
              :font            :medium
              :number-of-lines 1}
        (cond
          (= account-address whisper-id) account-name
          (not (str/blank? contact-name)) contact-name
          (not (str/blank? name)) name
          :else (generate-gfy))]
       [object-view {:id     message-id
                     :style  (:object-text item-style)
                     :object object}]]
      [view (merge st/popular-list-item-avatar-container
                   (:icon item-style))
       [touchable-highlight {:on-press #(dispatch [:start-chat whisper-id])}
        [view
         [ci/chat-icon (cond
                         (= account-address whisper-id) account-photo-path
                         (not (str/blank? contact-photo-path)) contact-photo-path
                         (not (str/blank? photo-path)) photo-path
                         :else (identicon whisper-id))
          {:size 36}]]]]]
     (when show-separator?
       [view st/separator])]))

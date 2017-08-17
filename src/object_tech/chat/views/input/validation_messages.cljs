(ns objecttech.chat.views.input.validation-messages
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [objecttech.components.react :as c]
            [objecttech.chat.styles.input.validation-message :as style]
            [objecttech.utils.listview :as lw]
            [objecttech.i18n :as i18n]))

(defn validation-message [{:keys [title description]}]
  [c/view style/message-container
   [c/text {:style style/message-title}
    title]
   [c/text {:style style/message-description}
    description]])

(defn messages-list [markup]
  [c/view {:flex 1}
   markup])

(defview validation-messages-view []
  [chat-input-margin [:chat-input-margin]
   input-height [:chat-ui-props :input-height]
   messages [:chat-ui-props :validation-messages]]
  (when messages
    [c/view (style/root (+ input-height chat-input-margin))
     (if (string? messages)
       [messages-list [validation-message {:title       (i18n/label :t/error)
                                           :description messages}]]
       [messages-list messages])]))

(ns objecttech.chat.views.input.emoji
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [objecttech.components.react :refer [view
                                                text
                                                icon
                                                emoji-picker]]
            [objecttech.chat.styles.input.emoji :as style]
            [objecttech.i18n :refer [label]]))

(defview emoji-view []
  [keyboard-max-height [:get :keyboard-max-height]]
  [view {:style (style/container keyboard-max-height)}
   [view style/emoji-container
    [emoji-picker {:style           style/emoji-picker
                   :hideClearButton true
                   :onEmojiSelected #(dispatch [:add-to-chat-input-text %])}]]])

(ns objecttech.chat.views.input.parameter-box
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [objecttech.chat.views.input.animations.expandable :refer [expandable-view]]
            [objecttech.chat.views.input.box-header :as box-header]
            [objecttech.commands.utils :as command-utils]
            [taoensso.timbre :as log]))

(defview parameter-box-container []
  [{:keys [markup]} [:chat-parameter-box]
   bot-db [:current-bot-db]]
  (when markup
    (command-utils/generate-hiccup markup bot-db)))

(defview parameter-box-view []
  [show-parameter-box? [:show-parameter-box?]
   {:keys [title]} [:chat-parameter-box]]
  (when show-parameter-box?
    [expandable-view {:key           :parameter-box
                      :draggable?    true
                      :custom-header (when title
                                       (box-header/get-header :parameter-box))}
     [parameter-box-container]]))

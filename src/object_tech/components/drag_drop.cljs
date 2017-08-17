(ns objecttech.components.drag-drop
  (:require [objecttech.components.react :refer [animated pan-responder]]))

(defn pan-handlers [pan-responder]
  (js->clj (.-panHandlers pan-responder)))

(defn create-pan-responder [{:keys [on-move on-release]}]
  (.create pan-responder
           (clj->js {:onStartShouldSetPanResponder (fn [] true)
                     :onPanResponderMove           on-move
                     :onPanResponderRelease        on-release})))

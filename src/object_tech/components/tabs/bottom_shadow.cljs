(ns objecttech.components.tabs.bottom-shadow
  (:require [objecttech.components.tabs.styles :as st]
            [objecttech.components.react :refer [linear-gradient]]
            [objecttech.utils.platform :refer [platform-specific]]))

(defn bottom-shadow-view []
  (when (get-in platform-specific [:tabs :tab-shadows?])
    [linear-gradient {:locations [0 0.98 1]
                      :colors    ["rgba(24, 52, 76, 0)" "rgba(24, 52, 76, 0.085)" "rgba(24, 52, 76, 0.165)"]
                      :style     (merge
                                   st/bottom-gradient
                                   (get-in platform-specific [:component-styles :bottom-gradient]))}]))

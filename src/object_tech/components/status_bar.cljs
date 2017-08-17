(ns objecttech.components.object-bar
  (:require [objecttech.components.react :as ui]
            [objecttech.utils.platform :refer [platform-specific]]))

(defn object-bar [{type :type
                   :or  {type :default}}]
  (let [{:keys [height
                bar-style
                elevation
                translucent?
                color]} (get-in platform-specific [:component-styles :object-bar type])]
    [ui/view
     [ui/object-bar {:background-color (if translucent? "transparent" color)
                     :translucent      translucent?
                     :bar-style        bar-style}]
     [ui/view {:style {:height           height
                       :elevation        elevation
                       :background-color color}}]]))
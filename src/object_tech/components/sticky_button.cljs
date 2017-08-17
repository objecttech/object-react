(ns objecttech.components.sticky-button
  (:require-macros [objecttech.utils.styles :refer [defstyle defnstyle]])
  (:require [objecttech.components.styles :as common]
            [objecttech.utils.platform :refer [platform-specific]]
            [objecttech.utils.utils :as u]
            [objecttech.components.react :refer [view
                                                text
                                                touchable-highlight]]))

(def sticky-button-style
  {:flex-direction   :row
   :height           52
   :justify-content  :center
   :align-items      :center
   :background-color common/color-light-blue})

(defstyle sticky-button-label-style
  {:color   common/color-white
   :ios     {:font-size      17
             :line-height    20
             :letter-spacing -0.2}
   :android {:font-size      14
             :letter-spacing 0.5}})

(defn sticky-button
  ([label on-press] (sticky-button label on-press false))
  ([label on-press once?]
   [touchable-highlight {:on-press (if once? (u/wrap-call-once! on-press) on-press)}
    [view sticky-button-style
     [text {:style sticky-button-label-style
            :uppercase? (get-in platform-specific [:uppercase?])}
           label]]]))
(ns objecttech.components.image-button.view
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [objecttech.components.react :refer [view
                                                text
                                                image
                                                touchable-highlight]]
            [objecttech.components.styles :refer [icon-scan]]
            [objecttech.i18n :refer [label]]
            [objecttech.components.image-button.styles :as st]))

(defn image-button [{:keys [value style icon handler]}]
  [view st/image-button
   [touchable-highlight {:on-press handler}
    [view st/image-button-content
     [image {:source {:uri (or icon :scan_blue)}
             :style  icon-scan}]
     (when text
       [text {:style style} value])]]])

(defn scan-button [{:keys [show-label? handler]}]
  [image-button {:icon    :scan_blue
                 :value   (if show-label?
                            (label :t/scan-qr))
                 :style   st/scan-button-text
                 :handler handler}])
(ns objecttech.chat.views.input.utils
  (:require [taoensso.timbre :as log]
            [objecttech.components.toolbar-new.styles :as toolbar-st]
            [objecttech.utils.platform :as p]))

(def min-height     19)
(def default-height 300)

(defn default-container-area-height [bottom screen-height]
  (let [object-bar-height (get-in p/platform-specific [:component-styles :object-bar :main :height])]
    (if (> (+ bottom default-height object-bar-height) screen-height)
      (- screen-height bottom object-bar-height)
      default-height)))

(defn max-container-area-height [bottom screen-height]
  (let [object-bar-height (get-in p/platform-specific [:component-styles :object-bar :main :height])
        toolbar-height (:height toolbar-st/toolbar)
        margin-top (+ object-bar-height (/ toolbar-height 2))]
    (- screen-height bottom margin-top)))

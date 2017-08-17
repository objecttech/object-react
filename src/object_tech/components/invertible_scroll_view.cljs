(ns objecttech.components.invertible-scroll-view
  (:require [reagent.core :as r]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(defn invertible-scroll-view [props]
  (r/create-element rn-dependencies/invertible-scroll-view
                    (clj->js (merge {:inverted true} props))))


(ns objecttech.components.qr-code
  (:require [reagent.core :as r]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(defn qr-code [props]
  (r/create-element
    rn-dependencies/qr-code
    (clj->js (merge {:inverted true} props))))

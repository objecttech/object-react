(ns objecttech.components.icons.custom-icons
  (:require [reagent.core :as r]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(def ion-icon
  (r/adapt-react-class (.-default rn-dependencies/vector-icons)))

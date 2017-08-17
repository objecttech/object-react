(ns objecttech.components.native-action-button
  (:require [reagent.core :as r]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(def native-action-button
  (r/adapt-react-class (.-default rn-dependencies/action-button)))


(def native-action-button-item
  (r/adapt-react-class (.. rn-dependencies/action-button -default -Item)))

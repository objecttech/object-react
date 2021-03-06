(ns objecttech.components.mapbox
  (:require [reagent.core :as r]
            [objecttech.components.styles :as common]
            [objecttech.i18n :refer [label]]
            [objecttech.utils.platform :refer [platform-specific ios?]]
            [re-frame.core :refer [dispatch]]
            [objecttech.components.react :refer [view touchable-highlight text]]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(defn get-property [name]
  (aget rn-dependencies/mapbox-gl name))

(defn adapt-class [class]
  (when class
    (r/adapt-react-class class)))

(defn get-class [name]
  (adapt-class (get-property name)))

(.setAccessToken rn-dependencies/mapbox-gl "pk.eyJ1Ijoic3RhdHVzaW0iLCJhIjoiY2oydmtnZjRrMDA3czMzcW9kemR4N2lxayJ9.Rz8L6xdHBjfO8cR3CDf3Cw")

(def mapview (get-class "MapView"))

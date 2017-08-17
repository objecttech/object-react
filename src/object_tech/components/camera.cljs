(ns objecttech.components.camera
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [objecttech.utils.platform :as platform]
            [objecttech.react-native.js-dependencies :as rn-dependecies]))

(def default-camera (.-default rn-dependecies/camera))

(defn constants [t]
  (-> (aget rn-dependecies/camera "constants" t)
      (js->clj)
      (keywordize-keys)))

(def aspects (constants "Aspect"))
(def capture-targets (constants "CaptureTarget"))

(defn request-access [callback]
  (if platform/android?
      (callback true)
      (-> (.checkVideoAuthorizationObject default-camera)
          (.then #(callback %))
          (.catch #(callback false)))))

(defn camera [props]
  (r/create-element default-camera (clj->js (merge {:inverted true} props))))

(ns objecttech.utils.random
  (:require [objecttech.js-dependencies :as dependencies]))

(defn timestamp []
  (.getTime (js/Date.)))

(def chance (dependencies/Chance.))

(defn id []
  (str (timestamp) "-" (.guid chance)))

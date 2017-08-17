(ns objecttech.utils.homoglyph
  (:require [objecttech.js-dependencies :as dependencies]))

(defn matches [s1 s2]
  (.isMatches dependencies/homoglyph-finder s1 s2))

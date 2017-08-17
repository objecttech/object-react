(ns objecttech.utils.gfycat.core
  (:require [objecttech.utils.gfycat.animals :as animals]
            [objecttech.utils.gfycat.adjectives :as adjectives]
            [clojure.string :as str]))

(defn- pick-random
  [vector]
  (str/capitalize (rand-nth vector)))

(defn generate-gfy
  []
  (let [first-adjective (pick-random adjectives/data)
        second-adjective (pick-random adjectives/data)
        animal (pick-random animals/data)]
    (str first-adjective " " second-adjective " " animal)))

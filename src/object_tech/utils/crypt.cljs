(ns objecttech.utils.crypt
  (:require [goog.crypt :refer [byteArrayToHex]]
            [clojure.string :as s]
            [objecttech.react-native.js-dependencies :as rn-dependencies])
  (:import goog.crypt.Sha256))

(def sha-256 (Sha256.))

(defn bytes-to-str [arr]
  (s/join (map char arr)))

(defn str-to-bytes [s]
  (map (comp int) s))

(defn encrypt [s]
  (.reset sha-256)
  (.update sha-256 s)
  (byteArrayToHex (.digest sha-256)))

(defn gen-random-bytes [length cb]
  (.randomBytes rn-dependencies/random-bytes
                length
                (fn [& [err buf]]
                  (if err
                    (cb {:error err})
                    (cb {:buffer buf})))))

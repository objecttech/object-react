(ns objecttech.utils.identicon
  (:require [objecttech.js-dependencies :as dependencies]))

(def default-size 40)

(defn identicon
  ([hash] (identicon hash default-size))
  ([hash options]
    (str "data:image/png;base64,"
         (str (new dependencies/identicon-js hash options)))))


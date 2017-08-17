(ns objecttech.protocol.web3.keys)

(def object-key-password "object-key-password")
(def object-group-key-password "object-public-group-key-password")

(defonce password->keys (atom {}))

(defn- add-sym-key-from-password
  [web3 password callback]
  (.. web3
      -shh
      (addSymmetricKeyFromPassword password callback)))

(defn get-sym-key [web3 password callback]
  (if-let [key-id (get @password->keys password)]
    (callback key-id)
    (add-sym-key-from-password
      web3 password
      (fn [err res]
        (swap! password->keys assoc password res)
        (callback res)))))

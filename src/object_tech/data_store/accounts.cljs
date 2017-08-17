(ns objecttech.data-store.accounts
  (:require [objecttech.data-store.realm.accounts :as data-store]))

(defn get-all []
  (data-store/get-all-as-list))

(defn get-by-address [address]
  (data-store/get-by-address address))

(defn save [account update?]
  (data-store/save account update?))

(defn save-all [accounts update?]
  (data-store/save-all accounts update?))
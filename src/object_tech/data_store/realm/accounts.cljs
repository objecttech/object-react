(ns objecttech.data-store.realm.accounts
  (:require [objecttech.data-store.realm.core :as realm]))

(defn get-all []
  (realm/get-all realm/base-realm :account))

(defn get-all-as-list []
  (realm/realm-collection->list (get-all)))

(defn get-by-address [address]
  (realm/get-one-by-field-clj realm/base-realm :account :address address))

(defn save [account update?]
  (realm/write realm/base-realm
    #(realm/create realm/base-realm :account account update?)))

(defn save-all [accounts update?]
  (realm/write realm/base-realm
               (fn []
                 (mapv #(realm/create realm/base-realm :account % update?) accounts))))

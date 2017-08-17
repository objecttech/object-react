(ns objecttech.data-store.core
  (:require [objecttech.data-store.realm.core :as data-source]))


(defn init []
  (data-source/reset-account))

(defn change-account [address new-account? handler]
  (data-source/change-account address new-account? handler))

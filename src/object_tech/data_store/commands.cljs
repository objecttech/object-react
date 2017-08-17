(ns objecttech.data-store.commands
  (:require [objecttech.data-store.realm.commands :as data-store]))

(defn get-by-chat-id
  [chat-id]
  (data-store/get-by-chat-id chat-id))

(defn save
  [command]
  (data-store/save command))

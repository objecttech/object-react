(ns objecttech.data-store.realm.commands
  (:require [objecttech.data-store.realm.core :as realm]))

(defn get-by-chat-id
  [chat-id]
  (realm/get-one-by-field-clj @realm/account-realm :command
                              :chat-id chat-id))

(defn save
  [command]
  (realm/save @realm/account-realm :command command true))

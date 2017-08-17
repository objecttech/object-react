(ns objecttech.data-store.realm.schemas.account.v2.core
  (:require [objecttech.data-store.realm.schemas.account.v2.chat :as chat]
            [objecttech.data-store.realm.schemas.account.v1.chat-contact :as chat-contact]
            [objecttech.data-store.realm.schemas.account.v1.command :as command]
            [objecttech.data-store.realm.schemas.account.v2.contact :as contact]
            [objecttech.data-store.realm.schemas.account.v1.discover :as discover]
            [objecttech.data-store.realm.schemas.account.v1.kv-store :as kv-store]
            [objecttech.data-store.realm.schemas.account.v1.message :as message]
            [objecttech.data-store.realm.schemas.account.v1.pending-message :as pending-message]
            [objecttech.data-store.realm.schemas.account.v1.processed-message :as processed-message]
            [objecttech.data-store.realm.schemas.account.v1.request :as request]
            [objecttech.data-store.realm.schemas.account.v1.tag :as tag]
            [objecttech.data-store.realm.schemas.account.v1.user-object :as user-object]
            [taoensso.timbre :as log]))

(def schema [chat/schema
             chat-contact/schema
             command/schema
             contact/schema
             discover/schema
             kv-store/schema
             message/schema
             pending-message/schema
             processed-message/schema
             request/schema
             tag/schema
             user-object/schema])

(defn migration [old-realm new-realm]
  (log/debug "migrating v2 account database: " old-realm new-realm)
  (chat/migration old-realm new-realm)
  (contact/migration old-realm new-realm))
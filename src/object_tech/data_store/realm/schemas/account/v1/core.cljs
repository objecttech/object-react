(ns objecttech.data-store.realm.schemas.account.v1.core
  (:require [objecttech.data-store.realm.schemas.account.v1.chat :as chat]
            [objecttech.data-store.realm.schemas.account.v1.chat-contact :as chat-contact]
            [objecttech.data-store.realm.schemas.account.v1.command :as command]
            [objecttech.data-store.realm.schemas.account.v1.contact :as contact]
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
  (log/debug "migrating v1 account database: " old-realm new-realm)
  (chat/migration old-realm new-realm)
  (chat-contact/migration old-realm new-realm)
  (command/migration old-realm new-realm)
  (contact/migration old-realm new-realm)
  (discover/migration old-realm new-realm)
  (kv-store/migration old-realm new-realm)
  (message/migration old-realm new-realm)
  (pending-message/migration old-realm new-realm)
  (processed-message/migration old-realm new-realm)
  (request/migration old-realm new-realm)
  (tag/migration old-realm new-realm)
  (user-object/migration old-realm new-realm))

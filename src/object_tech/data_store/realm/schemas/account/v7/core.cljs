(ns objecttech.data-store.realm.schemas.account.v7.core
  (:require [objecttech.data-store.realm.schemas.account.v4.chat :as chat]
            [objecttech.data-store.realm.schemas.account.v1.chat-contact :as chat-contact]
            [objecttech.data-store.realm.schemas.account.v6.command :as command]
            [objecttech.data-store.realm.schemas.account.v6.command-parameter :as command-parameter]
            [objecttech.data-store.realm.schemas.account.v7.contact :as contact]
            [objecttech.data-store.realm.schemas.account.v1.discover :as discover]
            [objecttech.data-store.realm.schemas.account.v1.kv-store :as kv-store]
            [objecttech.data-store.realm.schemas.account.v4.message :as message]
            [objecttech.data-store.realm.schemas.account.v7.pending-message :as pending-message]
            [objecttech.data-store.realm.schemas.account.v1.processed-message :as processed-message]
            [objecttech.data-store.realm.schemas.account.v1.request :as request]
            [objecttech.data-store.realm.schemas.account.v1.tag :as tag]
            [objecttech.data-store.realm.schemas.account.v1.user-object :as user-object]
            [objecttech.data-store.realm.schemas.account.v5.contact-group :as contact-group]
            [objecttech.data-store.realm.schemas.account.v5.group-contact :as group-contact]
            [taoensso.timbre :as log]))

(def schema [chat/schema
             chat-contact/schema
             command/schema
             command-parameter/schema
             contact/schema
             discover/schema
             kv-store/schema
             message/schema
             pending-message/schema
             processed-message/schema
             request/schema
             tag/schema
             user-object/schema
             contact-group/schema
             group-contact/schema])

(defn migration [old-realm new-realm]
  (log/debug "migrating v7 account database: " old-realm new-realm)
  (contact/migration old-realm new-realm))

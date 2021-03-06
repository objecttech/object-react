(ns objecttech.data-store.realm.schemas.account.v1.message
  (:require [taoensso.timbre :as log]))

(def schema {:name       :message
             :primaryKey :message-id
             :properties {:message-id     "string"
                          :from           "string"
                          :to             {:type     "string"
                                           :optional true}
                          :group-id       {:type     "string"
                                           :optional true}
                          :content        "string" ;; TODO make it ArrayBuffer
                          :content-type   "string"
                          :timestamp      "int"
                          :chat-id        {:type    "string"
                                           :indexed true}
                          :outgoing       "bool"
                          :retry-count    {:type    :int
                                           :default 0}
                          :same-author    "bool"
                          :same-direction "bool"
                          :preview        {:type     :string
                                           :optional true}
                          :message-type   {:type     :string
                                           :optional true}
                          :message-object {:type     :string
                                           :optional true}
                          :user-objectes  {:type       :list
                                           :objectType "user-object"}
                          :clock-value    {:type    :int
                                           :default 0}
                          :show?          {:type    :bool
                                           :default true}}})

(defn migration [_ _]
  (log/debug "migrating message schema"))

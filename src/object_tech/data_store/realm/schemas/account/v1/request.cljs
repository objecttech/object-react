(ns objecttech.data-store.realm.schemas.account.v1.request
  (:require [taoensso.timbre :as log]))

(def schema {:name       :request
             :properties {:message-id :string
                          :chat-id    :string
                          :type       :string
                          :object     {:type    :string
                                       :default "open"}
                          :added      :date}})

(defn migration [_ _]
  (log/debug "migrating request schema"))

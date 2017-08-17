(ns objecttech.data-store.realm.schemas.account.v1.user-object
  (:require [taoensso.timbre :as log]))

(def schema {:name       :user-object
             :primaryKey :id
             :properties {:id               "string"
                          :whisper-identity {:type    "string"
                                             :default ""}
                          :object           "string"}})

(defn migration [_ _]
  (log/debug "migrating user-object schema"))

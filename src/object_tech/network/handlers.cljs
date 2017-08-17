(ns objecttech.network.handlers
  (:require [re-frame.core :refer [dispatch debug enrich after]]
            [objecttech.utils.handlers :refer [register-handler]]
            [objecttech.utils.handlers :as u]
            [objecttech.network.net-info :as ni]))

(register-handler :listen-to-network-object!
  (u/side-effect!
    (fn []
      (let [handler #(dispatch [:update-network-object %])]
        (ni/init handler)
        (ni/add-listener handler)))))

(register-handler :update-network-object
  (fn [db [_ is-connected?]]
    (let [object (if is-connected? :online :offline)]
      (assoc db :network-object object))))

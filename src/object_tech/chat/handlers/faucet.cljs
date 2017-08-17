(ns objecttech.chat.handlers.faucet
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.utils.handlers :refer [register-handler] :as u]
            [objecttech.utils.utils :refer [http-get]]
            [objecttech.utils.random :as random]
            [objecttech.constants :refer [console-chat-id
                                         text-content-type]]
            [objecttech.i18n :refer [label]]
            [goog.string :as gstring]
            goog.string.format))

(def faucets
  [{:name    "http://faucet.ropsten.be:3001"
    :type    :api
    :api-url "http://faucet.ropsten.be:3001/donate/0x%s"}
   {:name    "http://46.101.129.137:3001"
    :type    :api
    :api-url "http://46.101.129.137:3001/donate/0x%s"}])

(defn faucet-by-name [faucet-name]
  (->> faucets
       (filter #(= (:name %) faucet-name))
       (first)))

(defn received-message [content]
  (dispatch [:received-message
             {:message-id   (random/id)
              :content      content
              :content-type text-content-type
              :outgoing     false
              :chat-id      console-chat-id
              :from         console-chat-id
              :to           "me"}]))

(defmulti open-faucet (fn [_ _ {:keys [type]}] type))

(defmethod open-faucet :api
  [_ current-address {:keys [api-url]}]
  (let [api-url (gstring/format api-url current-address)]
    (http-get api-url
              #(received-message (label :t/faucet-success))
              #(received-message (label :t/faucet-error)))))

(register-handler :open-faucet
  (u/side-effect!
   (fn [{:keys [accounts current-account-id]} [_ faucet-name _]]
     (if-let [faucet (faucet-by-name faucet-name)]
       (let [current-address (get-in accounts [current-account-id :address])]
         (open-faucet faucet-name current-address faucet))))))

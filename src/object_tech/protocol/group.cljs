(ns objecttech.protocol.group
  (:require
    [objecttech.protocol.web3.delivery :as d]
    [objecttech.protocol.web3.utils :as u]
    [cljs.spec.alpha :as s]
    [taoensso.timbre :refer-macros [debug]]
    [objecttech.protocol.validation :refer-macros [valid?]]
    [objecttech.protocol.web3.filtering :as f]
    [objecttech.protocol.listeners :as l]
    [clojure.string :as str]
    [objecttech.protocol.web3.keys :as shh-keys]))

(defn prepare-mesage
  [{:keys [message group-id keypair new-keypair type username requires-ack?]}]
  (let [message' (-> message
                     (update :payload assoc
                             :username username
                             :group-id group-id
                             :type type
                             :timestamp (u/timestamp))
                     (assoc :topics [f/object-topic]
                            :key-password group-id
                            :requires-ack? (or (nil? requires-ack?) requires-ack?)
                            :type type))]
    (cond-> message'
            keypair (assoc :keypair keypair)
            new-keypair (assoc :new-keypair keypair))))

(defn- send-group-message!
  [{:keys [web3 group-id] :as opts} type]
  (let [message (-> opts
                    (assoc :type type
                           :key-password group-id)
                    (prepare-mesage))]
    (debug :send-group-message message)
    (d/add-pending-message! web3 message)))

(s/def ::message
  (s/merge :protocol/message (s/keys :req-un [:chat-message/payload])))

(s/def :public-group/username (s/and string? (complement str/blank?)))
(s/def :public-group/message
  (s/merge ::message (s/keys :username :public-group/username)))

(defn send!
  [{:keys [keypair message] :as options}]
  {:pre [(valid? :message/keypair keypair)
         (valid? ::message message)]}
  (send-group-message! options :group-message))

(defn send-to-public-group!
  [{:keys [message] :as options}]
  {:pre [(valid? :public-group/message message)]}
  (send-group-message! (assoc options :requires-ack? false)
                       :public-group-message))

(defn leave!
  [options]
  (send-group-message! options :leave-group))

(defn add-identity!
  [{:keys [identity] :as options}]
  {:pre [(valid? :message/to identity)]}
  (let [options' (assoc-in options
                           [:message :payload :identity]
                           identity)]
    (send-group-message! options' :add-group-identity)))

(defn remove-identity!
  [{:keys [identity] :as options}]
  {:pre [(valid? :message/to identity)]}
  (let [options' (assoc-in options
                           [:message :payload :identity]
                           identity)]
    (send-group-message! options' :remove-group-identity)))

(s/def ::identities (s/* string?))

(s/def ::name string?)
(s/def ::id string?)
(s/def ::admin string?)
(s/def ::contacts (s/* string?))
(s/def ::group
  (s/keys :req-un
          [::name ::id ::contacts :message/keypair ::admin]))
(s/def :invite/options
  (s/keys :req-un [:options/web3 :protocol/message ::group ::identities]))

(defn- notify-about-group!
  [type {:keys [web3 message identities group]
         :as   options}]
  {:pre [(valid? :invite/options options)]}
  (let [{:keys [id admin name keypair contacts]} group
        message' (-> message
                     (assoc :topics [f/object-topic]
                            :requires-ack? true
                            :type type)
                     (update :payload assoc
                             :timestamp (u/timestamp)
                             :group-id id
                             :group-admin admin
                             :group-name name
                             :keypair keypair
                             :contacts contacts
                             :type type))]
    (doseq [identity identities]
      (d/add-pending-message! web3 (assoc message' :to identity)))))

(defn invite!
  [options]
  (notify-about-group! :group-invitation options))

;; todo notify users about keypair change when someone leaves group (from admin)
(defn update-group!
  [options]
  (notify-about-group! :update-group options))

(defn stop-watching-group!
  [{:keys [web3 group-id]}]
  {:pre [(valid? :message/chat-id group-id)]}
  (shh-keys/get-sym-key
    web3
    group-id
    (fn [key-id]
      (f/remove-filter!
        web3
        {:topics [f/object-topic]
         :key    key-id
         :type   :sym}))))

(defn start-watching-group!
  [{:keys [web3 group-id keypair callback identity]}]
  (shh-keys/get-sym-key
    web3
    group-id
    (fn [key-id]
      (f/add-filter!
        web3
        {:topics [f/object-topic]
         :key    key-id
         :type   :sym}
        (l/message-listener {:web3     web3
                             :identity identity
                             :callback callback
                             :keypair  keypair})))))

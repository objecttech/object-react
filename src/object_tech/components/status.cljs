(ns objecttech.components.object
  (:require-macros
    [cljs.core.async.macros :refer [go-loop go]])
  (:require [objecttech.components.react :as r]
            [objecttech.utils.types :as t]
            [re-frame.core :refer [dispatch]]
            [taoensso.timbre :as log]
            [cljs.core.async :refer [<! timeout]]
            [objecttech.utils.js-resources :as js-res]
            [objecttech.utils.platform :as p]
            [objecttech.utils.scheduler :as scheduler]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(defn cljs->json [data]
  (.stringify js/JSON (clj->js data)))

;; if ObjectModule is not initialized better to store
;; calls and make them only when ObjectModule is ready
;; this flag helps to handle this
(defonce module-initialized? (atom (or p/ios? js/goog.DEBUG)))

;; array of calls to ObjectModule
(defonce calls (atom []))

(defn module-initialized! []
  (reset! module-initialized? true))

(defn store-call [args]
  (log/debug :store-call args)
  (swap! calls conj args))

(defn call-module [f]
  ;(log/debug :call-module f)
  (if @module-initialized?
    (f)
    (store-call f)))

(defonce loop-started (atom false))

(when-not @loop-started
  (go-loop [_ nil]
    (reset! loop-started true)
    (if (and (seq @calls) @module-initialized?)
      (do (swap! calls (fn [calls]
                         (doseq [call calls]
                           (call))))
          (reset! loop-started false))
      (recur (<! (timeout 500))))))

(def object
  (when (exists? (.-NativeModules rn-dependencies/react-native))
    (.-Object (.-NativeModules rn-dependencies/react-native))))

(defn init-jail []
  (let [init-js (str js-res/object-js "I18n.locale = '" rn-dependencies/i18n.locale "';")]
    (.initJail object init-js #(log/debug "jail initialized"))))

(defonce listener-initialized (atom false))

(when-not @listener-initialized
  (reset! listener-initialized true)
  (.addListener r/device-event-emitter "gethEvent"
                #(dispatch [:signal-event (.-jsonEvent %)])))

(defn should-move-to-internal-storage? [on-result]
  (when object
    (call-module #(.shouldMoveToInternalStorage object on-result))))

(defn move-to-internal-storage [on-result]
  (when object
    (call-module #(.moveToInternalStorage object on-result))))

(defn start-node [on-result]
  (when object
    (call-module #(.startNode object on-result))))

(defn stop-rpc-server []
  (when object
    (call-module #(.stopNodeRPCServer object))))

(defn start-rpc-server []
  (when object
    (call-module #(.startNodeRPCServer object))))

(defonce restarting-rpc (atom false))

(defn restart-rpc []
  (when-not @restarting-rpc
    (reset! restarting-rpc true)
    (log/debug :restart-rpc-on-post-error)

    ;; todo maybe it would be better to use something like
    ;; restart-rpc-server on object-go side
    (stop-rpc-server)
    (start-rpc-server)

    (go (<! (timeout 3000))
        (reset! restarting-rpc false))))

(defonce account-creation? (atom false))

(defn create-account [password on-result]
  (when object
    (let [callback (fn [data]
                     (reset! account-creation? false)
                     (on-result data))]
      (swap! account-creation?
             (fn [creation?]
               (if-not creation?
                 (do
                   (call-module #(.createAccount object password callback))
                   true)
                 false))))))

(defn recover-account [passphrase password on-result]
  (when object
    (call-module #(.recoverAccount object passphrase password on-result))))

(defn login [address password on-result]
  (when object
    (call-module #(.login object address password on-result))))

(defn complete-transactions
  [hashes password callback]
  (log/debug :complete-transactions (boolean object) hashes)
  (when object
    (call-module #(.completeTransactions object (cljs->json hashes) password callback))))

(defn discard-transaction
  [id]
  (log/debug :discard-transaction id)
  (when object
    (call-module #(.discardTransaction object id))))

(defn parse-jail [chat-id file callback]
  (when object
    (call-module #(.parseJail object chat-id file callback))))

(defn call-jail [{:keys [jail-id path params callback]}]
  (when object
    (call-module
      #(do
         (log/debug :call-jail :jail-id jail-id)
         (log/debug :call-jail :path path)
         ;; this debug message can contain sensetive info
         #_(log/debug :call-jail :params params)
         (let [params' (update params :context assoc
                               :debug js/goog.DEBUG
                               :locale rn-dependencies/i18n.locale)
               cb      (fn [r]
                         (let [{:keys [result] :as r'} (t/json->clj r)
                               {:keys [messages]} result]
                           (log/debug r')
                           (doseq [{:keys [type message]} messages]
                             (log/debug (str "VM console(" type ") - " message)))
                           (callback r')))]
           (.callJail object jail-id (cljs->json path) (cljs->json params') cb))))))

(defn call-function!
  [{:keys [chat-id function callback] :as opts}]
  (let [path   [:functions function]
        params (select-keys opts [:parameters :context])]
    (call-jail
      {:jail-id  chat-id
       :path     path
       :params   params
       :callback (or callback #(dispatch [:received-bot-response {:chat-id chat-id} %]))})))

(defn set-soft-input-mode [mode]
  (when object
    (call-module #(.setSoftInputMode object mode))))

(defn clear-web-data []
  (when object
    (call-module #(.clearCookies object))
    (call-module #(.clearStorageAPIs object))))

(def adjust-resize 16)
(def adjust-pan 32)

(defn call-web3 [host payload callback]
  (when object
    (call-module #(.sendWeb3Request object host payload callback))))

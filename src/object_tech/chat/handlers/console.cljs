(ns objecttech.chat.handlers.console
  (:require [re-frame.core :refer [dispatch dispatch-sync after]]
            [objecttech.utils.handlers :refer [register-handler] :as u]
            [objecttech.constants :refer [console-chat-id
                                         text-content-type]]
            [objecttech.data-store.messages :as messages]
            [objecttech.i18n :refer [label]]
            [objecttech.utils.random :as random]
            [taoensso.timbre :as log]))

(def console-commands
  {:password
   (fn [{:keys [password]} _]
     (dispatch [:create-account password]))

   :phone
   (fn [{:keys [phone]} id]
     (dispatch [:sign-up phone id]))

   :confirmation-code
   (fn [{:keys [code]} id]
     (dispatch [:sign-up-confirm code id]))

   :faucet
   (fn [{:keys [url]} id]
     (dispatch [:open-faucet url id]))

   :debug
   (fn [{:keys [mode]} id]
     (let [debug-on? (= mode "On")]
       (dispatch [:account-update {:debug? debug-on?}])
       (if debug-on?
         (do
           (dispatch [:debug-server-start])
           (dispatch [:received-message
                      {:message-id   (random/id)
                       :content      (label :t/debug-enabled)
                       :content-type text-content-type
                       :outgoing     false
                       :chat-id      console-chat-id
                       :from         console-chat-id
                       :to           "me"}]))
         (dispatch [:debug-server-stop]))))})

(def commands-names (set (keys console-commands)))

(def commands-with-delivery-object
  (disj commands-names :password :faucet :debug))

(register-handler :invoke-console-command-handler!
  (u/side-effect!
    (fn [_ [_ {{:keys [command
                       params
                       id]
                :as   content} :command
               chat-id         :chat-id
               :as             all-params}]]
      (let [{:keys [name]} command]
        (dispatch [:prepare-command! chat-id all-params])
        ((console-commands (keyword name)) params id)))))

(register-handler :set-message-object
  (after
    (fn [_ [_ message-id object]]
      (messages/update {:message-id     message-id
                        :message-object object})))
  (fn [db [_ message-id object]]
    (assoc-in db [:message-data :objectes message-id] {:object object})))

(register-handler :console-respond-command
  (u/side-effect!
    (fn [_ [_ {:keys [command]}]]
      (let [{:keys [command handler-data]} command]
        (when command
          (let [{:keys [name]} command]
            (case name
              "js" (let [{:keys [err data messages]} handler-data
                         content (or err data)]
                     (doseq [message messages]
                       (let [{:keys [message type]} message]
                         (dispatch [:received-message
                                    {:message-id   (random/id)
                                     :content      (str type ": " message)
                                     :content-type text-content-type
                                     :outgoing     false
                                     :chat-id      console-chat-id
                                     :from         console-chat-id
                                     :to           "me"}])))
                     (when content
                       (dispatch [:received-message
                                  {:message-id   (random/id)
                                   :content      (str content)
                                   :content-type text-content-type
                                   :outgoing     false
                                   :chat-id      console-chat-id
                                   :from         console-chat-id
                                   :to           "me"}])))
              (log/debug "ignoring command: " command))))))))



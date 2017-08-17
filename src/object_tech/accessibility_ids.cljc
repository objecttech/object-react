(ns objecttech.accessibility-ids)

;; Toolbar
(def toolbar-back-button :toolbar-back-button)

;; Drawer
(def drawer-object-input :drawer-object-input)

;; Chat
(def chat-cancel-response-button :chat-cancel-response-button)
(def chat-message-input :chat-message-input)
(def chat-send-button :chat-send-button)
(defn chat-request-message-button [command-name]
  (keyword (str "request-" (name command-name))))

;; Accounts
(def accounts-create-button :accounts-create-button)

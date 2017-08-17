(ns objecttech.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [objecttech.handlers]
            [objecttech.subs]
            [objecttech.specs]
            [objecttech.components.react :refer [view
                                                modal
                                                app-registry
                                                keyboard
                                                orientation
                                                splash-screen
                                                http-bridge]]
            [objecttech.components.main-tabs :refer [main-tabs]]
            [objecttech.contacts.contact-list.views :refer [contact-list]]
            [objecttech.contacts.contact-list-modal.views :refer [contact-list-modal]]
            [objecttech.contacts.new-contact.views :refer [new-contact]]
            [objecttech.qr-scanner.screen :refer [qr-scanner]]
            [objecttech.discover.search-results :refer [discover-search-results]]
            [objecttech.chat.screen :refer [chat]]
            [objecttech.accounts.login.screen :refer [login]]
            [objecttech.accounts.recover.screen :refer [recover]]
            [objecttech.accounts.screen :refer [accounts]]
            [objecttech.transactions.screens.confirmation-success :refer [confirmation-success]]
            [objecttech.transactions.screens.unsigned-transactions :refer [unsigned-transactions]]
            [objecttech.transactions.screens.transaction-details :refer [transaction-details]]
            [objecttech.chats-list.screen :refer [chats-list]]
            [objecttech.chat.new-chat.view :refer [new-chat]]
            [objecttech.chat.new-public-chat.view :refer [new-public-chat]]
            [objecttech.group.views :refer [new-group edit-contact-group]]
            [objecttech.group.chat-settings.views :refer [chat-group-settings]]
            [objecttech.group.edit-contacts.views :refer [edit-contact-group-contact-list
                                                         edit-chat-group-contact-list]]
            [objecttech.group.add-contacts.views :refer [contact-toggle-list
                                                        add-contacts-toggle-list
                                                        add-participants-toggle-list]]
            [objecttech.group.reorder.views :refer [reorder-groups]]
            [objecttech.profile.screen :refer [profile my-profile]]
            [objecttech.profile.edit.screen :refer [edit-my-profile]]
            [objecttech.profile.photo-capture.screen :refer [profile-photo-capture]]
            [objecttech.ui.screens.wallet.send.views :refer [send-transaction]]
            ;;[objecttech.ui.screens.wallet.receive.views :refer [receive-transaction]]
            objecttech.data-store.core
            [taoensso.timbre :as log]
            [objecttech.chat.styles.screen :as st]
            [objecttech.profile.qr-code.screen :refer [qr-code-view]]
            [objecttech.components.object :as object]
            [objecttech.utils.utils :as utils]))

(defn orientation->keyword [o]
  (keyword (.toLowerCase o)))

(defn validate-current-view
  [current-view signed-up?]
  (if (or (contains? #{:login :chat :recover :accounts} current-view)
          signed-up?)
    current-view
    :chat))

(defn app-root []
  (let [signed-up?      (subscribe [:signed-up?])
        modal-view      (subscribe [:get :modal])
        view-id         (subscribe [:get :view-id])
        account-id      (subscribe [:get :current-account-id])
        keyboard-height (subscribe [:get :keyboard-height])]
    (log/debug "Current account: " @account-id)
    (r/create-class
      {:component-will-mount
       (fn []
         (let [o (orientation->keyword (.getInitialOrientation orientation))]
           (dispatch [:set :orientation o]))
         (.addOrientationListener
           orientation
           #(dispatch [:set :orientation (orientation->keyword %)]))
         (.lockToPortrait orientation)
         (.addListener keyboard
                       "keyboardWillShow"
                       (fn [e]
                         (let [h (.. e -endCoordinates -height)]
                           (when-not (= h @keyboard-height)
                             (dispatch [:set :keyboard-height h])
                             (dispatch [:set :keyboard-max-height h])))))
         (.addListener keyboard
                       "keyboardWillHide"
                       #(when-not (= 0 @keyboard-height)
                          (dispatch [:set :keyboard-height 0])))
         (.hide splash-screen))
       :component-will-unmount
       (fn []
         (.stop http-bridge))
       :display-name "root"
       :reagent-render
       (fn []
         (when @view-id
           (let [current-view (validate-current-view @view-id @signed-up?)]
             (let [component (case current-view
                               :wallet main-tabs
                               :wallet-send-transaction send-transaction
                               ;;:wallet-receive-transaction receive-transaction
                               :discover main-tabs
                               :discover-search-results discover-search-results
                               :chat-list main-tabs
                               :new-chat new-chat
                               :new-group new-group
                               :edit-contact-group edit-contact-group
                               :chat-group-settings chat-group-settings
                               :edit-group-contact-list edit-contact-group-contact-list
                               :edit-chat-group-contact-list edit-chat-group-contact-list
                               :add-contacts-toggle-list add-contacts-toggle-list
                               :add-participants-toggle-list add-participants-toggle-list
                               :reorder-groups reorder-groups
                               :new-public-chat new-public-chat
                               :contact-list main-tabs
                               :contact-toggle-list contact-toggle-list
                               :group-contacts contact-list
                               :new-contact new-contact
                               :qr-scanner qr-scanner
                               :chat chat
                               :profile profile
                               :my-profile my-profile
                               :edit-my-profile edit-my-profile
                               :profile-photo-capture profile-photo-capture
                               :accounts accounts
                               :login login
                               :recover recover)]

               [view
                {:flex 1}
                [component]
                (when @modal-view
                  [view
                   st/chat-modal
                   [modal {:animation-type   :slide
                           :transparent      false
                           :on-request-close #(dispatch [:navigate-back])}
                    (let [component (case @modal-view
                                      :qr-scanner qr-scanner
                                      :qr-code-view qr-code-view
                                      :unsigned-transactions unsigned-transactions
                                      :transaction-details transaction-details
                                      :confirmation-success confirmation-success
                                      :contact-list-modal contact-list-modal)]
                      [component])]])]))))})))

(defn init []
  (utils/register-exception-handler)
  (object/call-module object/init-jail)
  (dispatch-sync [:reset-app])
  (dispatch [:listen-to-network-object!])
  (dispatch [:initialize-crypt])
  (dispatch [:initialize-geth])
  (.registerComponent app-registry "objecttech" #(r/reactify-component app-root)))

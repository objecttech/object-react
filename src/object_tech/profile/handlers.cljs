(ns objecttech.profile.handlers
  (:require [re-frame.core :refer [subscribe dispatch after]]
            [objecttech.utils.handlers :refer [register-handler] :as u]
            [objecttech.components.react :refer [show-image-picker]]
            [objecttech.utils.image-processing :refer [img->base64]]
            [objecttech.i18n :refer [label]]
            [objecttech.utils.handlers :as u :refer [get-hashtags]]
            [taoensso.timbre :as log]
            [objecttech.constants :refer [console-chat-id]]
            [objecttech.navigation.handlers :as nav]))

(defn message-user [identity]
  (when identity
    (dispatch [:navigation-replace :chat identity])))

(register-handler :open-image-picker
  (u/side-effect!
    (fn [_ _]
      (show-image-picker
        (fn [image]
          (let [path       (get (js->clj image) "path")
                _ (log/debug path)
                on-success (fn [base64]
                             (dispatch [:set-in [:profile-edit :photo-path] (str "data:image/jpeg;base64," base64)]))
                on-error   (fn [type error]
                             (.log js/console type error))]
            (img->base64 path on-success on-error)))))))

(register-handler :phone-number-change-requested
  ;; Switch user to the console issuing the !phone command automatically to let him change his phone number.
  ;; We allow to change phone number only from console because this requires entering SMS verification code.
  (u/side-effect!
    (fn [db _]
      (dispatch [:navigate-to :chat console-chat-id])
      (js/setTimeout #(dispatch [:select-chat-input-command {:name "phone"}]) 500))))

(register-handler :open-chat-with-the-send-transaction
  (u/side-effect!
    (fn [db [_ chat-id]]
      (dispatch [:clear-seq-arguments])
      (dispatch [:navigate-to :chat chat-id])
      (js/setTimeout #(dispatch [:select-chat-input-command {:name "send"}]) 500))))

(defn prepare-edit-profile
  [{:keys [current-account-id] :as db} _]
  (let [current-account (select-keys (get-in db [:accounts current-account-id])
                                     [:name :photo-path :object])]
    (update-in db [:profile-edit] merge current-account)))

(defn open-edit-profile [_ _]
  (dispatch [:navigate-to :edit-my-profile]))

(register-handler :open-edit-my-profile
  (u/handlers->
    prepare-edit-profile
    open-edit-profile))

(defmethod nav/preload-data! :qr-code-view
  [{:keys [current-account-id] :as db} [_ _ {:keys [contact qr-source amount?]}]]
  (assoc db :qr-modal {:contact   (or contact
                                      (get-in db [:accounts current-account-id]))
                       :qr-source qr-source
                       :amount?   amount?}))

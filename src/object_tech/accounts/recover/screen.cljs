(ns objecttech.accounts.recover.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.components.text-input-with-label.view :refer [text-input-with-label]]
            [objecttech.components.react :refer [view
                                                text
                                                image
                                                keyboard-avoiding-view
                                                touchable-highlight]]
            [objecttech.components.sticky-button :refer [sticky-button]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar]]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.i18n :as i18n]
            [objecttech.accounts.recover.styles :as st]
            [objecttech.accounts.recover.validations :as v]
            [cljs.spec.alpha :as spec]
            [clojure.string :as str]))

(defview passphrase-input [passphrase]
  [error [:get-in [:recover :passphrase-error]]]
  [view {:margin-top 10}
   [text-input-with-label {:label             (i18n/label :t/passphrase)
                           :description       (i18n/label :t/twelve-words-in-correct-order)
                           :multiline         true
                           :auto-expanding    true
                           :max-height        st/passphrase-input-max-height
                           :default-value     passphrase
                           :auto-capitalize   :none
                           :on-change-text    #(dispatch [:set-in [:recover :passphrase] %])
                           :error             error}]])

(defview password-input [password]
  [error [:get-in [:recover :password-error]]]
  [view {:margin-top 10}
   [text-input-with-label {:label             (i18n/label :t/password)
                           :default-value     password
                           :auto-capitalize   :none
                           :on-change-text    #(dispatch [:set-in [:recover :password] %])
                           :secure-text-entry true
                           :error             error}]])

(defview recover []
  [{:keys [passphrase password]} [:get :recover]]
  (let [valid-form? (and
                      (spec/valid? ::v/passphrase passphrase)
                      (spec/valid? ::v/password password))]
    [keyboard-avoiding-view {:style st/screen-container}
     [object-bar]
     [toolbar {:actions [{:image :blank}]
               :title   (i18n/label :t/recover-access)}]
     [passphrase-input (or passphrase "")]
     [password-input (or password "")]
     [view {:flex 1}]
     (when valid-form?
       [sticky-button (i18n/label :t/recover-access) #(dispatch [:recover-account passphrase password])])]))

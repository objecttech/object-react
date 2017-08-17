(ns objecttech.transactions.views.password-form
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :as rf]
            [objecttech.components.react :as rn]
            [objecttech.components.text-input-with-label.view :refer [text-input-with-label]]
            [objecttech.transactions.styles.password-form :as st]
            [objecttech.i18n :as i18n]))

(defview view [transaction-quantity]
  [wrong-password? [:wrong-password?]]
  (let [error? wrong-password?]
    [rn/view st/password-container
     [text-input-with-label
      {:label             (i18n/label :t/password)
       :description       (i18n/label-pluralize transaction-quantity :t/enter-password-transactions)
       :on-change-text   #(rf/dispatch [:set-in [:confirm-transactions :password] %])
       :style             {:color :white}
       :auto-focus        true
       :secure-text-entry true
       :error             (when error? (i18n/label :t/wrong-password))}]]))


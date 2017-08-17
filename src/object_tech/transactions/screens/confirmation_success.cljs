(ns objecttech.transactions.screens.confirmation-success
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :as rf]
            [objecttech.components.react :as rn]
            [objecttech.components.sticky-button :as sticky-button]
            [objecttech.components.object-bar :as object-bar]
            [objecttech.transactions.views.list-item :as transactions-list-item]
            [objecttech.transactions.styles.screens :as st]
            [objecttech.i18n :as i18n]))

(defview confirmation-success []
  [quantity [:get :confirmed-transactions-count]]
  [rn/view {:style st/success-screen}
   [object-bar/object-bar {:type :transaction}]
   [rn/view {:style st/success-screen-content-container}
    [rn/view {:style st/success-icon-container}
     [rn/image {:source {:uri :icon_ok_white}
                :style  st/success-icon}]]
    [rn/view
     [rn/text {:style st/success-text}
      (i18n/label-pluralize quantity :t/transactions-confirmed)]]]
   [sticky-button/sticky-button
    (i18n/label :t/got-it)
    #(do (rf/dispatch [:navigate-back])
         (rf/dispatch [:set :confirmed-transactions-count 0]))]])

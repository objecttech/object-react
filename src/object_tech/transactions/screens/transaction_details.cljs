(ns objecttech.transactions.screens.transaction-details
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :as rf]
            [objecttech.components.react :as rn]
            [objecttech.components.common.common :as common]
            [objecttech.components.sticky-button :as sticky-button]
            [objecttech.components.object-bar :as object-bar]
            [objecttech.components.sync-state.offline :as offline-view]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.components.toolbar-new.view :as toolbar]
            [objecttech.i18n :as i18n]
            [objecttech.transactions.styles.screens :as st]
            [objecttech.transactions.views.list-item :as transactions-list-item]
            [objecttech.transactions.views.password-form :as password-form]
            [objecttech.utils.platform :as platform]
            [objecttech.utils.money :as money]))

(defn toolbar-view []
  [toolbar/toolbar
   {:background-color st/transactions-toolbar-background
    :nav-action       (act/back-white #(rf/dispatch [:navigate-to-modal :unsigned-transactions]))
    :border-style     st/toolbar-border
    :custom-content   [rn/view {:style st/toolbar-title-container}
                       [rn/text {:style st/toolbar-title-text
                                 :font  :toolbar-title}
                        (i18n/label :t/transaction)]]}])

(defn detail-item [title content name?]
  [rn/view {:style st/details-item}
   [rn/text {:style st/details-item-title} title]
   [rn/text {:style           (st/details-item-content name?)
             :number-of-lines 1}
    content]])

(defn detail-data [content]
  [rn/view {:style st/details-data}
   [rn/text {:style st/details-data-title} (i18n/label :t/data)]
   [rn/text {:style st/details-data-content} content]])

(defview details [{:keys [to data gas gas-price] :as transaction}]
  [current-account [:get-current-account]
   recipient       [:contact-by-address to]]
  (let [recipient-name (or (:name recipient) to (i18n/label :t/contract-creation))
        gas-price'     (money/wei->ether gas-price)
        fee-value      (money/fee-value gas gas-price')
        estimated-fee  (str fee-value " ETH")]
    [rn/view st/details-container
     [detail-item (i18n/label :t/to) recipient-name true]
     [detail-item (i18n/label :t/from) (:name current-account) true]
     [detail-item (i18n/label :t/estimated-fee) estimated-fee]
     [detail-data data]]))

(defview transaction-details []
  [{:keys [id] :as transaction} [:get :selected-transaction]
   {:keys [password]}           [:get :confirm-transactions]
   confirmed?                   [:get-in [:transaction-details-ui-props :confirmed?]]
   sync-state                   [:get :sync-state]
   network-object               [:get :network-object]]
  {:component-did-update   #(when-not transaction (rf/dispatch [:navigate-to-modal :unsigned-transactions]))
   :component-will-unmount #(rf/dispatch [:set-in [:transaction-details-ui-props :confirmed?] false])}
  (let [offline? (or (= network-object :offline) (= sync-state :offline))]
    [rn/keyboard-avoiding-view {:style st/transactions-screen}
     [object-bar/object-bar {:type :transaction}]
     [toolbar-view]
     [rn/scroll-view st/details-screen-content-container
      [transactions-list-item/view transaction #(rf/dispatch [:navigate-to-modal :unsigned-transactions])]
      [common/separator st/details-separator st/details-separator-wrapper]
      [details transaction]]
     (when (and confirmed? (not offline?))
       [password-form/view 1])
     (when-not offline?
       (let [confirm-text (if confirmed?
                            (i18n/label :t/confirm)
                            (i18n/label-pluralize 1 :t/confirm-transactions))
             confirm-fn   (if confirmed?
                            #(do (rf/dispatch [:accept-transaction password id])
                                 (rf/dispatch [:set :confirmed-transactions-count 1]))
                            #(rf/dispatch [:set-in [:transaction-details-ui-props :confirmed?] true]))]
         [sticky-button/sticky-button confirm-text confirm-fn]))
     [offline-view/offline-view {:top (if platform/ios? 21 0)}]]))

(ns objecttech.accounts.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [dispatch dispatch-sync]]
            [objecttech.accounts.styles :as st]
            [objecttech.components.text-input-with-label.view :refer [text-input-with-label]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.components.common.common :as common]
            [objecttech.components.action-button.action-button :refer [action-button]]
            [objecttech.utils.listview :as lw]
            [objecttech.constants :refer [console-chat-id]]
            [objecttech.components.react :refer [view
                                                text
                                                list-view
                                                list-item
                                                image
                                                touchable-highlight]]
            [objecttech.i18n :as i18n]
            [clojure.string :as str]))

(defn account-bage [address photo-path name]
  [view st/account-bage
   [image {:source {:uri (if (str/blank? photo-path) :avatar photo-path)}
           :style  st/photo-image}]
   [view st/account-bage-text-view
    [text {:style st/account-bage-text
           :numberOfLines 1}
     (or name address)]]])

(defn account-view [{:keys [address photo-path name] :as account}]
  [view
   [touchable-highlight {:on-press #(dispatch [:open-login address photo-path name])}
    [view st/account-view
     [account-bage address photo-path name]]]])

(defn create-account [_]
  (dispatch [:reset-app #(dispatch [:navigate-to :chat console-chat-id])]))

(defview accounts []
  [accounts [:get :accounts]]
  [view st/accounts-container
   [object-bar {:type :transparent}]
   [view st/account-title-conatiner
    [text {:style st/account-title-text
           :font :toolbar-title}
     (i18n/label :t/sign-in-to-object)]]
   [view st/accounts-list-container
    [list-view {:dataSource      (lw/to-datasource (vals accounts))
                :renderSeparator #(list-item ^{:key %2} [view {:height 10}])
                :renderRow       #(list-item [account-view %])}]]
   [view st/bottom-actions-container
    [action-button (i18n/label :t/create-new-account)
                   :add_white
                   create-account
                   st/accounts-action-button]
    [common/separator st/accounts-separator st/accounts-separator-wrapper]
    [action-button (i18n/label :t/recover-access)
                   :dots_horizontal_white
                   #(dispatch [:navigate-to :recover])
                   st/accounts-action-button]]])

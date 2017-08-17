(ns objecttech.accounts.login.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [dispatch dispatch-sync]]
            [objecttech.accounts.styles :as ast]
            [objecttech.accounts.screen :refer [account-bage]]
            [objecttech.components.text-input-with-label.view :refer [text-input-with-label]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar]]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.accounts.login.styles :as st]
            [objecttech.components.react :refer [view
                                                text
                                                touchable-highlight]]
            [objecttech.i18n :as i18n]
            [objecttech.components.react :as components]))

(defn login-toolbar []
  [toolbar {:background-color :transparent
            :hide-border?     true
            :title-style      {:color :white}
            :nav-action       (act/back-white #(dispatch [:navigate-back]))
            :actions          [{:image :blank}]
            :title            (i18n/label :t/sign-in-to-object)}])

(def password-text-input (atom nil))

(defn login-account [password-text-input address password]
  (.blur @password-text-input)
  (dispatch [:login-account address password]))

(defview login []
  [{:keys [address photo-path name password error processing]} [:get :login]]
  [view ast/accounts-container
   [object-bar {:type :transparent}]
   [login-toolbar]
   [view st/login-view
    [view st/login-badge-container
     [account-bage address photo-path name]
     [view {:height 8}]
     [text-input-with-label {:ref               #(reset! password-text-input %)
                             :label             (i18n/label :t/password)
                             :auto-capitalize   :none
                             :hide-underline?   true
                             :on-change-text    #(do
                                                   (dispatch [:set-in [:login :password] %])
                                                   (dispatch [:set-in [:login :error] ""]))
                             :on-submit-editing #(login-account password-text-input address password)
                             :auto-focus        true
                             :secure-text-entry true
                             :error             (when (pos? (count error)) (i18n/label :t/wrong-password))}]]
    [view {:margin-top 16}
     [touchable-highlight {:on-press #(login-account password-text-input address password)}
      [view st/sign-in-button
       [text {:style st/sign-it-text} (i18n/label :t/sign-in)]]]]]
   (when processing
     [view st/processing-view
      [components/activity-indicator {:animating true}]])])

(ns objecttech.contacts.contact-list-modal.views
  (:require-macros [objecttech.utils.views :refer [defview letsubs]])
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.components.common.common :as common]
            [objecttech.components.renderers.renderers :as renderers]
            [objecttech.components.react :refer [view list-view list-item]]
            [objecttech.components.contact.contact :refer [contact-view]]
            [objecttech.components.action-button.action-button :refer [action-button
                                                                      action-separator]]
            [objecttech.components.action-button.styles :refer [actions-list]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar-with-search]]
            [objecttech.components.drawer.view :refer [drawer-view]]
            [objecttech.contacts.styles :as st]
            [objecttech.utils.listview :as lw]
            [objecttech.i18n :refer [label]]))

(defview contact-list-modal-toolbar []
  (letsubs [show-search [:get-in [:toolbar-search :show]]
            search-text [:get-in [:toolbar-search :text]]]
    (toolbar-with-search
      {:show-search?       (= show-search :contact-list)
       :search-text        search-text
       :search-key         :contact-list
       :title              (label :t/contacts)
       :search-placeholder (label :t/search-contacts)})))

(defn actions-view [action click-handler]
  [view actions-list
   [action-button (label :t/enter-address)
    :address_blue
    #(do
       (dispatch [:send-to-webview-bridge
                  {:event (name :webview-send-transaction)}])
       (dispatch [:navigate-back]))]
   [action-separator]
   (if (= :request action)
     [action-button (label :t/show-qr)
      :q_r_blue
      #(click-handler :qr-scan action)]
     [action-button (label :t/scan-qr)
      :fullscreen_blue
      #(click-handler :qr-scan action)])])

(defn render-row [click-handler action params]
  (fn [row _ _]
    (list-item
      ^{:key row}
      [contact-view {:contact  row
                     :on-press #(when click-handler
                                  (click-handler row action params))}])))

(defview contact-list-modal []
  (letsubs [contacts [:contacts-filtered :all-added-people-contacts]
            click-handler [:get :contacts/click-handler]
            action [:get :contacts/click-action]
            params [:get :contacts/click-params]]
    [drawer-view
     [view {:flex 1}
      [object-bar {:type :modal}]
      [contact-list-modal-toolbar]
      [list-view {:dataSource                (lw/to-datasource contacts)
                  :enableEmptySections       true
                  :renderRow                 (render-row click-handler action params)
                  :bounces                   false
                  :keyboardShouldPersistTaps :always
                  :renderHeader              #(list-item
                                                [view
                                                 [actions-view action click-handler]
                                                 [common/bottom-shadow]
                                                 [common/form-title (label :t/choose-from-contacts)
                                                  {:count-value (count contacts)}]
                                                 [common/list-header]])
                  :renderFooter              #(list-item [view
                                                          [common/list-footer]
                                                          [common/bottom-shadow]])
                  :renderSeparator           renderers/list-separator-renderer
                  :style                     st/contacts-list-modal}]]]))

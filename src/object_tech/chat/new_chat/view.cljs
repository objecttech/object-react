(ns objecttech.chat.new-chat.view
  (:require-macros [objecttech.utils.views :refer [defview letsubs]])
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.components.common.common :as common]
            [objecttech.components.renderers.renderers :as renderers]
            [objecttech.components.action-button.action-button :refer [action-button
                                                                      action-separator]]
            [objecttech.components.action-button.styles :refer [actions-list]]
            [objecttech.components.react :refer [view text list-view list-item]]
            [objecttech.components.contact.contact :refer [contact-view]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar-with-search]]
            [objecttech.components.drawer.view :refer [drawer-view]]
            [objecttech.chat.new-chat.styles :as styles]
            [objecttech.utils.listview :as lw]
            [objecttech.i18n :refer [label]]))

(defn options-list []
  [view actions-list
   [action-button (label :t/new-group-chat)
                  :private_group_big
                  #(dispatch [:open-contact-toggle-list :chat-group])]
   [action-separator]
   [action-button (label :t/new-public-group-chat)
                  :public_group_big
                  #(dispatch [:navigate-to :new-public-chat])]
   [action-separator]
   [action-button (label :t/add-new-contact)
                  :add_blue
                  #(dispatch [:navigate-to :new-contact])]])

(defn contact-list-row []
  (fn [row _ _]
    (list-item ^{:key row}
               [contact-view {:contact  row
                              :on-press #(dispatch [:open-chat-with-contact %])}])))

(defview new-chat-toolbar []
  (letsubs [show-search [:get-in [:toolbar-search :show]]
            search-text [:get-in [:toolbar-search :text]]]
    [view
     [object-bar]
     (toolbar-with-search
      {:show-search?       (= show-search :contact-list)
       :search-text        search-text
       :search-key         :contact-list
       :title              (label :t/contacts-group-new-chat)
       :search-placeholder (label :t/search-for)})]))

(defview new-chat []
  (letsubs [contacts [:all-added-group-contacts-filtered]
            params [:get :contacts/click-params]]
    [drawer-view
     [view styles/contacts-list-container
      [new-chat-toolbar]
      (when contacts
        [list-view {:dataSource                (lw/to-datasource contacts)
                    :enableEmptySections       true
                    :renderRow                 (contact-list-row)
                    :bounces                   false
                    :keyboardShouldPersistTaps :always
                    :renderHeader              #(list-item
                                                  [view
                                                   [options-list]
                                                   [common/bottom-shadow]
                                                   [common/form-title (label :t/choose-from-contacts)
                                                    {:count-value (count contacts)}]
                                                   [common/list-header]])
                    :renderSeparator           renderers/list-separator-renderer
                    :renderFooter              #(list-item [view
                                                            [common/list-footer]
                                                            [common/bottom-shadow]])
                    :style                     styles/contacts-list}])]]))

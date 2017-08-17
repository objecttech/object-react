(ns objecttech.chats-list.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.components.common.common :as common]
            [objecttech.components.renderers.renderers :as renderers]
            [objecttech.components.react :refer [list-view
                                                list-item
                                                view
                                                animated-view
                                                text
                                                icon
                                                image
                                                touchable-highlight]]
            [objecttech.components.native-action-button :refer [native-action-button]]
            [objecttech.components.drawer.view :refer [open-drawer]]
            [objecttech.components.styles :refer [color-blue]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar toolbar-with-search]]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.components.toolbar-new.styles :as tst]
            [objecttech.components.icons.custom-icons :refer [ion-icon]]
            [objecttech.components.sync-state.offline :refer [offline-view]]
            [objecttech.components.context-menu :refer [context-menu]]
            [objecttech.components.tabs.styles :refer [tabs-height]]
            [objecttech.utils.listview :refer [to-datasource]]
            [objecttech.chats-list.views.chat-list-item :refer [chat-list-item]]
            [objecttech.chats-list.styles :as st]
            [objecttech.i18n :refer [label]]
            [objecttech.utils.platform :refer [platform-specific ios?]]))

(def android-toolbar-popup-options
  [{:text (label :t/edit) :value #(dispatch [:set-in [:chat-list-ui-props :edit?] true])}])

(defn android-toolbar-actions []
  [(act/search #(dispatch [:set-in [:toolbar-search :show] true]))
   (act/opts android-toolbar-popup-options)])

(def ios-toolbar-popup-options
  [{:text (label :t/edit-chats) :value #(dispatch [:set-in [:chat-list-ui-props :edit?] true])}
   {:text (label :t/search-chats) :value #(dispatch [:set-in [:toolbar-search :show] true])}])

(defn ios-toolbar-actions []
  [(act/opts ios-toolbar-popup-options)
   (act/add #(dispatch [:navigate-to :new-chat]))])

(defn toolbar-view []
  [toolbar {:title      (label :t/chats)
            :nav-action (act/hamburger open-drawer)
            :actions    (if ios?
                          (ios-toolbar-actions)
                          (android-toolbar-actions))}])

(defn toolbar-edit []
  [toolbar {:nav-action (act/back #(dispatch [:set-in [:chat-list-ui-props :edit?] false]))
            :title      (label :t/edit-chats)
            :actions    [{:image :blank}]}])

(defview toolbar-search []
  [search-text [:get-in [:toolbar-search :text]]]
  [toolbar-with-search
   {:show-search?       true
    :search-text        search-text
    :search-key         :chat-list
    :title              (label :t/chats)
    :search-placeholder (label :t/search-for)}])

(defn chats-action-button []
  [native-action-button {:button-color color-blue
                         :offset-x     16
                         :offset-y     40
                         :spacing      13
                         :hide-shadow  true
                         :on-press     #(dispatch [:navigate-to :new-chat])}])

(defview chats-list []
  [chats        [:filtered-chats]
   edit?        [:get-in [:chat-list-ui-props :edit?]]
   search?      [:get-in [:toolbar-search :show]]
   tabs-hidden? [:tabs-hidden?]]
  [view st/chats-container
   (cond
     edit?   [toolbar-edit]
     search? [toolbar-search]
     :else   [toolbar-view])
   [list-view {:dataSource      (to-datasource chats)
               :renderRow       (fn [[id :as row] _ _]
                                  (list-item ^{:key id} [chat-list-item row edit?]))
               :renderHeader    (when-not (empty? chats) renderers/list-header-renderer)
               :renderFooter    (when-not (empty? chats)
                                  #(list-item [view
                                               [common/list-footer]
                                               [common/bottom-shadow]]))
               :renderSeparator renderers/list-separator-renderer
               :style           (st/list-container tabs-hidden?)}]
   (when (and (not edit?)
              (not search?)
              (get-in platform-specific [:chats :action-button?]))
     [chats-action-button])
   [offline-view]])

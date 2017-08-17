(ns objecttech.discover.search-results
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [objecttech.utils.listview :refer [to-datasource]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.react :refer [view
                                                text
                                                icon
                                                list-view
                                                list-item
                                                scroll-view]]
            [objecttech.components.toolbar.view :refer [toolbar]]
            [objecttech.components.toolbar.actions :as act]
            [objecttech.discover.views.discover-list-item :refer [discover-list-item]]
            [objecttech.utils.platform :refer [platform-specific]]
            [objecttech.i18n :refer [label]]
            [objecttech.discover.styles :as st]
            [objecttech.contacts.styles :as contacts-st]))

(defn render-separator [_ row-id _]
  (list-item [view {:style st/row-separator
                    :key   row-id}]))

(defn title-content [tags]
  [scroll-view {:horizontal            true
                :bounces               false
                :flex                  1
                :contentContainerStyle st/tag-title-scroll}
   [view st/tag-title-container
    (for [tag (take 3 tags)]
      ^{:key (str "tag-" tag)}
      [view (merge (get-in platform-specific [:component-styles :discover :tag])
                   {:margin-left 2 :margin-right 2})
       [text {:style st/tag-title
              :font  :default}
        (str " #" tag)]])]])

(defview discover-search-results []
  [discoveries [:get-popular-discoveries 250]
   tags [:get :discover-search-tags]
   current-account [:get-current-account]]
  (let [discoveries (:discoveries discoveries)
        datasource (to-datasource discoveries)]
    [view st/discover-tag-container
     [object-bar]
     [toolbar {:nav-action     (act/back #(dispatch [:navigate-back]))
               :custom-content (title-content tags)
               :style          st/discover-tag-toolbar}]
     (if (empty? discoveries)
       [view st/empty-view
        ;; todo change icon
        [icon :group_big contacts-st/empty-contacts-icon]
        [text {:style contacts-st/empty-contacts-text}
         (label :t/no-objectes-found)]]
       [list-view {:dataSource      datasource
                   :renderRow       (fn [row _ _]
                                      (list-item [discover-list-item
                                                  {:message         row
                                                   :current-account current-account}]))
                   :renderSeparator render-separator
                   :style           st/recent-list}])]))

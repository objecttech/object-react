(ns objecttech.discover.views.popular-list
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require
    [re-frame.core :refer [subscribe dispatch]]
    [objecttech.components.react :refer [view
                                        list-view
                                        list-item
                                        touchable-highlight
                                        text]]
    [objecttech.discover.styles :as st]
    [objecttech.utils.listview :refer [to-datasource]]
    [objecttech.discover.views.discover-list-item :refer [discover-list-item]]
    [objecttech.utils.platform :refer [platform-specific]]))

(defview discover-popular-list [{:keys [tag contacts current-account]}]
  [discoveries [:get-popular-discoveries 3 [tag]]]
  [view (merge st/popular-list-container
               (get-in platform-specific [:component-styles :discover :popular]))
   [view st/row
    [view (get-in platform-specific [:component-styles :discover :tag])
     [touchable-highlight {:on-press #(do (dispatch [:set :discover-search-tags [tag]])
                                          (dispatch [:navigate-to :discover-search-results]))}
      [view
       [text {:style st/tag-name
              :font  :medium}
        (str " #" (name tag))]]]]
    [view st/tag-count-container
     [text {:style st/tag-count
            :font  :default}
      (:total discoveries)]]]
   (let [discoveries (map-indexed vector (:discoveries discoveries))]
     (for [[i {:keys [message-id] :as discover}] discoveries]
       ^{:key (str "message-popular-" message-id)}
       [discover-list-item {:message         discover
                             :show-separator? (not= (inc i) (count discoveries))
                             :current-account current-account}]))])

(ns objecttech.group.reorder.views
  (:require-macros [objecttech.utils.views :refer [defview letsubs]])
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [objecttech.components.react :refer [view text icon list-item]]
            [objecttech.components.sticky-button :refer [sticky-button]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar]]
            [objecttech.components.sortable-list-view :refer [sortable-list-view sortable-item]]
            [objecttech.components.common.common :as common]
            [objecttech.group.styles :as styles]
            [objecttech.i18n :refer [label label-pluralize]]))


(defn toolbar-view []
  [toolbar {:actions [{:image :blank}]
            :title   (label :t/reorder-groups)}])

(defn group-item [{:keys [name contacts] :as group}]
  (let [cnt (count contacts)]
    [view styles/order-item-container
     [view styles/order-item-inner-container
      [text {:style styles/order-item-label}
       name]
      [text {:style styles/order-item-contacts}
       (str cnt " " (label-pluralize cnt :t/contact-s))]
      [view {:flex 1}]
      [view styles/order-item-icon
       [icon :grab_gray]]]]))

(defn render-separator [last]
  (fn [_ row-id _]
    (list-item
      (if (= row-id last)
        ^{:key "bottom-shadow"}
        [common/bottom-shadow]
        ^{:key row-id}
        [view styles/order-item-separator-wrapper
         [view styles/order-item-separator]]))))

(defview reorder-groups []
  (letsubs [groups [:get-contact-groups]
            order  [:get :group/groups-order]]
    (let [this (reagent/current-component)]
      [view styles/reorder-groups-container
       [object-bar]
       [toolbar-view]
       [view styles/reorder-list-container
        [common/top-shadow]
        [sortable-list-view
         {:data             groups
          :order            order
          :on-row-moved     #(do (dispatch-sync [:change-contact-group-order (:from %) (:to %)])
                                 (.forceUpdate this))
          :render-row       (fn [row]
                             (sortable-item [group-item row]))
          :render-separator (render-separator (last order))}]]
       [sticky-button (label :t/save) #(do
                                         (dispatch [:save-contact-group-order])
                                         (dispatch [:navigate-to-clean :contact-list]))]])))

(ns objecttech.components.tabs.tabs
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [objecttech.components.react :refer [view
                                                animated-view
                                                text-input
                                                text
                                                image
                                                touchable-highlight]]
            [reagent.core :as r]
            [objecttech.components.tabs.styles :as st]
            [objecttech.components.tabs.tab :refer [tab]]
            [objecttech.components.animation :as anim]
            [objecttech.utils.platform :refer [platform-specific]]))

(defn create-tab [index data selected-view-id prev-view-id]
  (let [data (merge data {:key              index
                          :index            index
                          :selected-view-id selected-view-id
                          :prev-view-id     prev-view-id})]
    [tab data]))

(defn tabs-container [& children]
  (let [tabs-hidden?    (subscribe [:tabs-hidden?])
        shadows?        (get-in platform-specific [:tabs :tab-shadows?])]
    (into [animated-view {:style         (merge (st/tabs-container @tabs-hidden?)
                                                (if-not shadows? st/tabs-container-line))
                          :pointerEvents (if @tabs-hidden? :none :auto)}]
          children)))

(defn tabs [{:keys [tab-list selected-view-id prev-view-id]}]
  [tabs-container
   (into
     [view st/tabs-inner-container]
     (let [tabs (into [] tab-list)]
       [[create-tab 0 (nth tabs 0) selected-view-id prev-view-id]
        [create-tab 1 (nth tabs 1) selected-view-id prev-view-id]
        [create-tab 2 (nth tabs 2) selected-view-id prev-view-id]
        ;; WALLET TAB
        ;;[create-tab 3 (nth tabs 3) selected-view-id prev-view-id]
        ])
     ;; todo: figure why it doesn't work on iOS release build
     #_(map-indexed #(create-tab %1 %2 selected-view-id prev-view-id) tab-list))])

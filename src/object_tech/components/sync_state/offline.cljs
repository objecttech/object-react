(ns objecttech.components.sync-state.offline
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reagent.core :as r]
            [objecttech.components.react :refer [view
                                                text
                                                animated-view
                                                get-dimensions]]
            [objecttech.components.sync-state.styles :as st]
            [objecttech.components.animation :as anim]
            [objecttech.i18n :refer [label]]))

(def window-width (:width (get-dimensions "window")))

(defn start-offline-animation [offline-opacity]
  (anim/start
    (anim/timing offline-opacity {:toValue  1.0
                                  :duration 250})))

(defn offline-view [_]
  (let [sync-state       (subscribe [:get :sync-state])
        network-object   (subscribe [:get :network-object])
        offline-opacity  (anim/create-value 0.0)
        on-update        (fn [_ _]
                           (anim/set-value offline-opacity 0)
                           (when (or (= @network-object :offline) (= @sync-state :offline))
                             (start-offline-animation offline-opacity)))
        pending-contact? (subscribe [:current-contact :pending?])
        view-id          (subscribe [:get :view-id])]
    (r/create-class
      {:component-did-mount
       on-update
       :component-did-update
       on-update
       :display-name "offline-view"
       :reagent-render
       (fn [{:keys [top]}]
         (when (or (= @network-object :offline) (= @sync-state :offline))
           (let [pending? (and @pending-contact? (= :chat @view-id))]
             [animated-view {:style (st/offline-wrapper top offline-opacity window-width pending?)}
              [view
               [text {:style st/offline-text}
                (label :t/offline)]]])))})))

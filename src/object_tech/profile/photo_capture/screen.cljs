(ns objecttech.profile.photo-capture.screen
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [clojure.walk :refer [keywordize-keys]]
            [objecttech.components.react :refer [view
                                                text
                                                image
                                                touchable-highlight]]
            [objecttech.components.camera :refer [camera
                                                 aspects
                                                 capture-targets]]
            [objecttech.components.styles :refer [icon-back]]
            [objecttech.components.icons.custom-icons :refer [ion-icon]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar.view :refer [toolbar]]
            [objecttech.components.toolbar.actions :as act]
            [objecttech.components.toolbar.styles :refer [toolbar-background1]]
            [objecttech.utils.image-processing :refer [img->base64]]
            [objecttech.profile.photo-capture.styles :as st]
            [objecttech.i18n :refer [label]]
            [reagent.core :as r]
            [taoensso.timbre :as log]))

(defn image-captured [data]
  (let [path       (.-path data)
        _ (log/debug "Captured image: " path)
        on-success (fn [base64]
                     (log/debug "Captured success: " base64)
                     (dispatch [:set-in [:profile-edit :photo-path] (str "data:image/jpeg;base64," base64)])
                     (dispatch [:navigate-back]))
        on-error   (fn [type error]
                     (log/debug type error))]
    (img->base64 path on-success on-error)))

(defn profile-photo-capture []
  (let [camera-ref (r/atom nil)]
    [view st/container
     [object-bar]
     [toolbar {:title            (label :t/image-source-title)
               :nav-action       (act/back #(dispatch [:navigate-back]))
               :background-color toolbar-background1}]
     [camera {:style         {:flex 1}
              :aspect        (:fill aspects)
              :captureQuality "480p"
              :captureTarget (:disk capture-targets)
              :type          "front"
              :ref           #(reset! camera-ref %)}]
     [view {:style {:padding          10
                    :background-color toolbar-background1}}
      [touchable-highlight {:style    {:align-self "center"}
                            :on-press (fn []
                                        (let [camera @camera-ref]
                                          (-> (.capture camera)
                                              (.then image-captured)
                                              (.catch #(log/debug "Error capturing image: " %)))))}
       [view
        [ion-icon {:name  :md-camera
                   :style {:font-size 36}}]]]]]))

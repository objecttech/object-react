(ns objecttech.profile.edit.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [cljs.spec.alpha :as s]
            [clojure.string :as str]
            [reagent.core :as r]
            [re-frame.core :refer [dispatch]]
            [objecttech.profile.styles :as st]
            [objecttech.components.text-input-with-label.view :refer [text-input-with-label]]
            [objecttech.components.styles :refer [color-blue color-gray5]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar-new.view :refer [toolbar]]
            [objecttech.components.toolbar-new.actions :as act]
            [objecttech.i18n :refer [label]]
            [objecttech.profile.screen :refer [colorize-object-hashtags]]
            [objecttech.components.sticky-button :refer [sticky-button]]
            [objecttech.components.camera :as camera]
            [objecttech.components.chat-icon.screen :refer [my-profile-icon]]
            [objecttech.components.context-menu :refer [context-menu]]
            [objecttech.profile.validations :as v]
            [objecttech.components.react :refer [view
                                                scroll-view
                                                keyboard-avoiding-view
                                                text
                                                touchable-highlight
                                                text-input]]
            [objecttech.utils.utils :as utils :refer [clean-text]]
            [objecttech.utils.platform :refer [ios?]]))

(defn edit-my-profile-toolbartoolbar []
  [toolbar {:title   (label :t/edit-profile)
            :actions [{:image :blank}]}])

(defview profile-name-input []
  [new-profile-name [:get-in [:profile-edit :name]]]
  [view
   [text-input-with-label {:label          (label :t/name)
                           :default-value  new-profile-name
                           :on-change-text #(dispatch [:set-in [:profile-edit :name] %])}]])

(def profile-icon-options
  [{:text  (label :t/image-source-gallery)
    :value #(dispatch [:open-image-picker])}
   {:text  (label :t/image-source-make-photo)
    :value (fn []
             (dispatch [:request-permissions
                        [:camera :write-external-storage]
                        (fn []
                          (camera/request-access
                            #(if % (dispatch [:navigate-to :profile-photo-capture])
                                   (utils/show-popup (label :t/error)
                                                     (label :t/camera-access-error)))))]))}])

(defn edit-profile-bage [contact]
  [view st/edit-profile-bage
   [view st/edit-profile-icon-container
    [context-menu
     [my-profile-icon {:account contact
                       :edit?   true}]
     profile-icon-options
     st/context-menu-custom-styles]]
   [view st/edit-profile-name-container
    [profile-name-input]]])

(defn edit-profile-object [{:keys [object edit-object?]}]
  (let [input-ref (r/atom nil)]
    [view st/edit-profile-object
     [scroll-view
      (if edit-object?
        [text-input
         {:ref               #(reset! input-ref %)
          :auto-focus        edit-object?
          :multiline         true
          :max-length        140
          :placeholder       (label :t/object)
          :style             st/profile-object-input
          :on-change-text    #(dispatch [:set-in [:profile-edit :object] (clean-text %)])
          :on-blur           #(dispatch [:set-in [:profile-edit :edit-object?] false])
          :blur-on-submit    true
          :on-submit-editing #(.blur @input-ref)
          :default-value     object}]
        [touchable-highlight {:on-press #(dispatch [:set-in [:profile-edit :edit-object?] true])}
         [view
          (if (str/blank? object)
            [text {:style st/add-a-object}
             (label :t/object)]
            [text {:style st/profile-object-text}
             (colorize-object-hashtags object)])]])]]))

(defn object-prompt [{:keys [object]}]
  (when (or (nil? object) (str/blank? object))
    [view st/object-prompt
     [text {:style st/object-prompt-text}
      (colorize-object-hashtags (label :t/object-prompt))]]))

(defview edit-my-profile []
  [current-account [:get-current-account]
   changed-account [:get :profile-edit]]
  {:component-will-unmount #(dispatch [:set-in [:profile-edit :edit-object?] false])}
  (let [profile-edit-data-valid? (s/valid? ::v/profile changed-account)
        profile-edit-data-changed? (or (not= (:name current-account) (:name changed-account))
                                       (not= (:object current-account) (:object changed-account))
                                       (not= (:photo-path current-account) (:photo-path changed-account)))]
    [keyboard-avoiding-view {:style st/profile}
     [object-bar]
     [edit-my-profile-toolbartoolbar]
     [view st/edit-my-profile-form
       [edit-profile-bage changed-account]
       [edit-profile-object changed-account]
       [object-prompt changed-account]]
     (when (and profile-edit-data-changed? profile-edit-data-valid?)
       [sticky-button (label :t/save) #(do
                                          (dispatch [:check-object-change (:object changed-account)])
                                          (dispatch [:account-update changed-account]))])]))

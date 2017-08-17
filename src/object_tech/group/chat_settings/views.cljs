(ns objecttech.group.chat-settings.views
  (:require-macros [objecttech.utils.views :refer [defview letsubs]])
  (:require [re-frame.core :refer [dispatch]]
            [objecttech.components.contact.contact :refer [contact-view]]
            [objecttech.components.common.common :as common]
            [objecttech.components.react :refer [view scroll-view keyboard-avoiding-view]]
            [objecttech.components.sticky-button :refer [sticky-button]]
            [objecttech.group.styles :as styles]
            [objecttech.group.views :refer [group-toolbar group-chat-settings-btns
                                           group-name-view add-btn more-btn]]
            [objecttech.group.db :as v]
            [objecttech.i18n :refer [label]]
            [cljs.spec.alpha :as spec]))

(def ^:const contacts-limit 3)

(defview chat-group-contacts-view [admin?]
  (letsubs [contacts [:current-chat-contacts]]
    (let [limited-contacts (take contacts-limit contacts)
          contacts-count (count contacts)]
      [view
       (when (and admin? (pos? contacts-count))
         [common/list-separator])
       [view
        (doall
          (map (fn [row]
                 ^{:key row}
                 [view
                  [contact-view
                   {:contact        row
                    :extend-options [{:value #(dispatch [:remove-group-chat-participants #{(:whisper-identity row)}])
                                      :text  (label :t/remove)}]
                    :extended?      admin?}]
                  (when-not (= row (last limited-contacts))
                    [common/list-separator])])
               limited-contacts))]
       (when (< contacts-limit contacts-count)
         [more-btn contacts-limit contacts-count #(dispatch [:navigate-to :edit-chat-group-contact-list])])])))

(defview chat-group-members []
  (letsubs [current-pk [:get :current-public-key]
            group-admin [:chat :group-admin]]
    (let [admin? (= current-pk group-admin)]
      [view
       (when admin?
         [add-btn #(dispatch [:navigate-to :add-participants-toggle-list])])
       [chat-group-contacts-view admin?]])))

(defview chat-group-settings []
  (letsubs [new-chat-name [:get :new-chat-name]
            chat-name [:chat :name]
            type [:get-group-type]]
    (let [save-btn-enabled? (and (spec/valid? ::v/name new-chat-name)
                                 (not= new-chat-name chat-name))]
      [keyboard-avoiding-view {:style styles/group-container}
       [view {:flex 1}
        [group-toolbar type true]
        [scroll-view
         [group-name-view]
         [chat-group-members]
         [view styles/separator]
         [group-chat-settings-btns]]]
       (when save-btn-enabled?
         [sticky-button (label :t/save) #(dispatch [:set-chat-name])
          true])])))

(ns objecttech.components.contact.contact
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [objecttech.components.react :refer [view icon touchable-highlight text]]
            [objecttech.components.chat-icon.screen :refer [contact-icon-contacts-tab]]
            [objecttech.components.context-menu :refer [context-menu]]
            [objecttech.components.contact.styles :as st]
            [objecttech.utils.gfycat.core :refer [generate-gfy]]
            [objecttech.i18n :refer [get-contact-translated label]]))

(defn contact-photo [contact]
  [view
   [contact-icon-contacts-tab contact]])

(defn contact-inner-view
  ([{:keys [info style] {:keys [whisper-identity name] :as contact} :contact}]
   [view (merge st/contact-inner-container style)
    [contact-photo contact]
    [view st/info-container
     [text {:style           st/name-text
            :number-of-lines 1}
      (if (pos? (count (:name contact)))
        (get-contact-translated whisper-identity :name name)
        ;;TODO is this correct behaviour?
        (generate-gfy))]
     (when info
       [text {:style st/info-text}
        info])]]))

(defn contact-view [{:keys [contact extended? on-press extend-options info]}]
  [touchable-highlight (when-not extended?
                         {:on-press (when on-press #(on-press contact))})
   [view
    [view st/contact-container
     [contact-inner-view {:contact contact :info info}]
     (when (and extended? (not (empty? extend-options)))
       [view st/more-btn-container
        [context-menu
         [icon :options_gray]
         extend-options
         nil
         st/more-btn]])]]])

(defview toogle-contact-view [{:keys [whisper-identity] :as contact} selected-key on-toggle-handler]
  [checked [selected-key whisper-identity]]
  [touchable-highlight {:on-press #(on-toggle-handler checked whisper-identity)}
   [view
    [view (merge st/contact-container (when checked {:style st/selected-contact}))
     [contact-inner-view (merge {:contact contact}
                                (when checked {:style st/selected-contact}))]
     [view (st/icon-check-container checked)
      (when checked
        [icon :check_on st/check-icon])]]]])

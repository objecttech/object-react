(ns objecttech.discover.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require
    [re-frame.core :refer [dispatch subscribe]]
    [clojure.string :as str]
    [objecttech.components.react :refer [view
                                        scroll-view
                                        text
                                        text-input
                                        icon]]
    [objecttech.components.toolbar-new.view :refer [toolbar-with-search]]
    [objecttech.components.toolbar-new.actions :as act]
    [objecttech.components.drawer.view :refer [open-drawer]]
    [objecttech.components.carousel.carousel :refer [carousel]]
    [objecttech.discover.views.popular-list :refer [discover-popular-list]]
    [objecttech.discover.views.discover-list-item :refer [discover-list-item]]
    [objecttech.utils.platform :refer [platform-specific]]
    [objecttech.i18n :refer [label]]
    [objecttech.discover.styles :as st]
    [objecttech.contacts.styles :as contacts-st]))

(defn get-hashtags [object]
  (let [hashtags (map #(str/lower-case (str/replace % #"#" "")) (re-seq #"[^ !?,;:.]+" object))]
    (or hashtags [])))

(defn toolbar-view [show-search? search-text]
  [toolbar-with-search
   {:show-search?       show-search?
    :search-text        search-text
    :search-key         :discover
    :title              (label :t/discover)
    :search-placeholder (label :t/search-tags)
    :nav-action         (act/hamburger open-drawer)
    :on-search-submit   (fn [text]
                          (when-not (str/blank? text)
                            (let [hashtags (get-hashtags text)]
                              (dispatch [:set :discover-search-tags hashtags])
                              (dispatch [:navigate-to :discover-search-results]))))}])

(defn title [label-kw spacing?]
  [view st/section-spacing
   [text {:style      (merge (get-in platform-specific [:component-styles :discover :subtitle])
                             (when spacing? {:margin-top 16}))
          :uppercase? (get-in platform-specific [:discover :uppercase-subtitles?])
          :font       :medium}
    (label label-kw)]])

(defview discover-popular [{:keys [contacts current-account]}]
  [popular-tags [:get-popular-tags 10]]
  [view st/popular-container
   [title :t/popular-tags false]
   (if (pos? (count popular-tags))
     [carousel {:pageStyle st/carousel-page-style
                :gap       8
                :sneak     16
                :count     (count popular-tags)}
      (for [{:keys [name]} popular-tags]
        [discover-popular-list {:tag             name
                                :contacts        contacts
                                :current-account current-account}])]
     [text (label :t/none)])])

(defview discover-recent [{:keys [current-account]}]
  [discoveries [:get-recent-discoveries]]
  (when (seq discoveries)
    [view st/recent-container
     [title :t/recent true]
     [view st/recent-list
      (let [discoveries (map-indexed vector discoveries)]
        (for [[i {:keys [message-id] :as message}] discoveries]
          ^{:key (str "message-recent-" message-id)}
          [discover-list-item {:message         message
                               :show-separator? (not= (inc i) (count discoveries))
                               :current-account current-account}]))]]))

(defview discover [current-view?]
  [show-search [:get-in [:toolbar-search :show]]
   search-text [:get-in [:toolbar-search :text]]
   contacts [:get-contacts]
   current-account [:get-current-account]
   discoveries [:get-recent-discoveries]
   tabs-hidden? [:tabs-hidden?]]
  [view st/discover-container
   [toolbar-view (and current-view?
                      (= show-search :discover)) search-text]
   (if discoveries
     [scroll-view (st/list-container tabs-hidden?)
      [discover-popular {:contacts        contacts
                         :current-account current-account}]
      [discover-recent {:current-account current-account}]]
     [view contacts-st/empty-contact-groups
      ;; todo change icon
      [icon :group_big contacts-st/empty-contacts-icon]
      [text {:style contacts-st/empty-contacts-text}
       (label :t/no-objectes-discovered)]])])

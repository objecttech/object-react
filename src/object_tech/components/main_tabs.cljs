(ns objecttech.components.main-tabs
  (:require-macros [objecttech.utils.views :refer [defview]]
                   [cljs.core.async.macros :as am])
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [reagent.core :as r]
            [objecttech.components.react :refer [view swiper]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.drawer.view :refer [drawer-view]]
            [objecttech.components.tabs.bottom-shadow :refer [bottom-shadow-view]]
            [objecttech.chats-list.screen :refer [chats-list]]
            [objecttech.discover.screen :refer [discover]]
            [objecttech.contacts.views :refer [contact-groups-list]]
            [objecttech.ui.screens.wallet.main-screen.views :refer [wallet]]
            [objecttech.components.tabs.tabs :refer [tabs]]
            [objecttech.components.tabs.styles :as st]
            [objecttech.components.styles :as common-st]
            [objecttech.i18n :refer [label]]
            [cljs.core.async :as a]))

(def tab-list
  [{:view-id       :chat-list
    :title         (label :t/chats)
    :screen        chats-list
    :icon-inactive :icon_chats
    :icon-active   :icon_chats_active
    :index         0}
   {:view-id       :discover
    :title         (label :t/discover)
    :screen        discover
    :icon-inactive :icon_discover
    :icon-active   :icon_discover_active
    :index         1}
   {:view-id       :contact-list
    :title         (label :t/contacts)
    :screen        contact-groups-list
    :icon-inactive :icon_contacts
    :icon-active   :icon_contacts_active
    :index         2}
   {:view-id       :wallet
    :title         "Wallet"
    :screen        wallet
    :icon-inactive :icon_contacts
    :icon-active   :icon_contacts_active
    :index         3}])

(def tab->index {:chat-list    0
                 :discover     1
                 :contact-list 2
                 :wallet       3})

(def index->tab (clojure.set/map-invert tab->index))

(defn get-tab-index [view-id]
  (get tab->index view-id 0))

(defn scroll-to [prev-view-id view-id]
  (let [p (get-tab-index prev-view-id)
        n (get-tab-index view-id)]
    (- n p)))

(defonce scrolling? (atom false))

(defn on-scroll-end [swiped? scroll-ended view-id]
  (fn [_ state]
    (when @scrolling?
      (a/put! scroll-ended true))
    (let [{:strs [index]} (js->clj state)
          new-view-id (index->tab index)]
      (when-not (= view-id new-view-id)
        (reset! swiped? true)
        (dispatch [:navigate-to-tab new-view-id])))))

(defn start-scrolling-loop
  "Loop that synchronizes tabs scrolling to avoid an inconsistent state."
  [scroll-start scroll-ended]
  (am/go-loop [[swiper to] (a/<! scroll-start)]
    ;; start scrolling
    (reset! scrolling? true)
    (.scrollBy swiper to)
    ;; lock loop until scroll ends
    (a/alts! [scroll-ended (a/timeout 2000)])
    (reset! scrolling? false)
    (recur (a/<! scroll-start))))

(defn main-tabs []
  (let [view-id           (subscribe [:get :view-id])
        prev-view-id      (subscribe [:get :prev-view-id])
        tabs-hidden?      (subscribe [:tabs-hidden?])
        main-swiper       (r/atom nil)
        swiped?           (r/atom false)
        scroll-start      (a/chan 10)
        scroll-ended      (a/chan 10)
        tabs-were-hidden? (atom @tabs-hidden?)]
    (r/create-class
      {:component-did-mount
       #(start-scrolling-loop scroll-start scroll-ended)
       :component-will-update
       (fn []
         (if @swiped?
           (reset! swiped? false)
           (when (and (= @tabs-were-hidden? @tabs-hidden?) @main-swiper)
             (let [to (scroll-to @prev-view-id @view-id)]
               (a/put! scroll-start [@main-swiper to]))))
         (reset! tabs-were-hidden? @tabs-hidden?))
       :display-name "main-tabs"
       :reagent-render
       (fn []
         [view common-st/flex
          [object-bar {:type (if (= @view-id :wallet) :wallet :main)}]
          [view common-st/flex
           [drawer-view
            [view {:style common-st/flex}
             [swiper (merge
                      (st/main-swiper @tabs-hidden?)
                      {:index                  (get-tab-index @view-id)
                       :loop                   false
                       :ref                    #(reset! main-swiper %)
                       :on-momentum-scroll-end (on-scroll-end swiped? scroll-ended @view-id)})
              [chats-list]
              [discover (= @view-id :discover)]
              [contact-groups-list (= @view-id :contact-list)]
              ;; TODO(oskarth): While wallet is in WIP we hide the wallet component
              ;;[wallet (= @view-id :wallet)]
              ]
             [tabs {:selected-view-id @view-id
                    :prev-view-id     @prev-view-id
                    :tab-list         tab-list}]
             (when-not @tabs-hidden?
               [bottom-shadow-view])]]]])})))

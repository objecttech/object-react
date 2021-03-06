(ns ^:figwheel-no-load env.android.main
  (:require [reagent.core :as r]
            [re-frisk-remote.core :as rr]
            [object-tech.android.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(def cnt (r/atom 0))
(defn reloader [] @cnt [core/app-root])
(def root-el (r/as-element [reloader]))
(defn callback []
  (swap! cnt inc)
  (object-tech.components.status/init-jail)
  (re-frame.core/dispatch [:load-commands!]))

(figwheel/watch-and-reload
  :websocket-url "ws://10.0.3.2:3449/figwheel-ws"
  :heads-up-display false
  :jsload-callback callback)

(rr/enable-re-frisk-remote! {:host "10.0.3.2:4567" :pre-send (fn [db] (update db :chats #(into {} %)))})

(core/init)

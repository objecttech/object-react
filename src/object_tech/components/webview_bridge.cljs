(ns objecttech.components.webview-bridge
  (:require [reagent.core :as r]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(def webview-bridge-class
  (r/adapt-react-class (.-default rn-dependencies/webview-bridge)))

(defn webview-bridge [opts]
  [webview-bridge-class opts])

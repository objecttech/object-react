(ns objecttech.qr-scanner.screen
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [objecttech.components.react :refer [view
                                                image]]
            [objecttech.components.camera :refer [camera]]
            [objecttech.components.styles :refer [icon-search
                                                 icon-back]]
            [objecttech.components.object-bar :refer [object-bar]]
            [objecttech.components.toolbar.view :refer [toolbar]]
            [objecttech.components.toolbar.actions :as act]
            [objecttech.components.toolbar.styles :refer [toolbar-background1]]
            [objecttech.qr-scanner.styles :as st]
            [objecttech.utils.types :refer [json->clj]]
            [clojure.string :as str]))

(defview qr-scanner-toolbar [title]
  [modal [:get :modal]]
  [view
   [object-bar]
   [toolbar {:title            title
             :background-color toolbar-background1
             :nav-action       (when modal
                                 (act/back #(dispatch [:navigate-back])))}]])

(defview qr-scanner []
  [identifier [:get :current-qr-context]]
  [view st/barcode-scanner-container
   [qr-scanner-toolbar (:toolbar-title identifier)]
   [camera {:onBarCodeRead (fn [code]
                             (let [data (-> (.-data code)
                                            (str/replace #"ethereum:" ""))]
                               (dispatch [:set-qr-code identifier data])))
            ;:barCodeTypes  [:qr]
            :captureAudio  false
            :style         st/barcode-scanner}]
   [view st/rectangle-container
    [view st/rectangle
     [image {:source {:uri :corner_left_top}
             :style  st/corner-left-top}]
     [image {:source {:uri :corner_right_top}
             :style  st/corner-right-top}]
     [image {:source {:uri :corner_right_bottom}
             :style  st/corner-right-bottom}]
     [image {:source {:uri :corner_left_bottom}
             :style  st/corner-left-bottom}]]]])

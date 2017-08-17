(ns objecttech.utils.sms-listener
  (:require [objecttech.utils.platform :refer [android?]]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

;; Only android is supported!
(defn add-sms-listener
  "Message format: {:originatingAddress string, :body string}. Returns
  cancelable subscription."
  [listen-fn]
  (when android?
    (.addListener rn-dependencies/android-sms-listener
                  (fn [message]
                    (listen-fn (js->clj message :keywordize-keys true))))))

(defn remove-sms-listener [subscription]
  (when android?
    (.remove subscription)))

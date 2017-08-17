(ns objecttech.utils.platform
  (:require [objecttech.android.platform :as android]
            [objecttech.ios.platform :as ios]
            [objecttech.react-native.js-dependencies :as rn-dependencies]))

(def platform
  (when-let [pl (.-Platform rn-dependencies/react-native)]
    (.-OS pl)))

(def android? (= platform "android"))
(def ios? (= platform "ios"))

(def platform-specific
  (cond
    android? android/platform-specific
    ios? ios/platform-specific
    :else {}))

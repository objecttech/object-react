(ns objecttech.chat.styles.message.command-pill
  (:require  [objecttech.utils.platform :as p]
             [objecttech.components.styles :refer [color-white]]))

(defn pill [command]
  {:backgroundColor   (:color command)
   :height            24
   :borderRadius      50
   :padding-top       (if p/ios? 4 3)
   :paddingHorizontal 12
   :text-align        :left})

(def pill-text
  {:fontSize    12
   :color       color-white})

(ns objecttech.chat.views.message.datemark
  (:require [re-frame.core :refer [subscribe dispatch]]
            [objecttech.components.react :refer [view
                                                text]]
            [clojure.string :as str]
            [objecttech.i18n :refer [label]]
            [objecttech.chat.styles.message.datemark :as st]))

(defn chat-datemark [value]
  [view st/datemark-wrapper
   [view st/datemark
    [text {:style st/datemark-text}
     (str/capitalize (or value (label :t/datetime-today)))]]])
(ns objecttech.components.object-view.view
  (:require-macros [objecttech.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [clojure.string :as str]
            [objecttech.components.react :refer [view text]]
            [objecttech.utils.platform :refer [platform-specific]]
            [objecttech.components.styles :refer [color-blue color-black]]
            [objecttech.utils.utils :refer [hash-tag?]]))

(defn tag-view [tag]
  [text {:style {:color color-blue}
         :font :medium}
   (str tag " ")])

(defn object-view [{:keys [style
                           non-tag-color
                           message-id
                           object
                           on-press
                           number-of-lines]
                    :or {message-id "msg"
                         non-tag-color color-black}}]
  [text {:style           style
         :on-press        on-press
         :number-of-lines number-of-lines
         :font            :default}
   (for [[i object] (map-indexed vector (str/split object #" "))]
     (if (hash-tag? object)
       ^{:key (str "item-" message-id "-" i)}
       [tag-view object]
       ^{:key (str "item-" message-id "-" i)}
       [text {:style {:color non-tag-color}}
        (str object " ")]))])

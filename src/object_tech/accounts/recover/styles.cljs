(ns objecttech.accounts.recover.styles
  (:require [objecttech.components.styles :as common]
            [objecttech.utils.platform :refer [ios?]]))

(def screen-container
  {:flex             1
   :background-color common/color-white})

(def passphrase-input-max-height
  (if ios? 78 72))

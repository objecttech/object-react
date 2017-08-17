(ns objecttech.transactions.styles.password-form
  (:require-macros [objecttech.utils.styles :refer [defstyle]])
  (:require [objecttech.components.styles :as common]))

(defstyle password-container
  {:android {:margin-bottom 27}
   :ios     {:margin-bottom 20
             :border-top-width 1
             :border-top-color common/color-white-transparent-2}})

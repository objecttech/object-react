(ns objecttech.components.renderers.renderers
  (:require [objecttech.components.react :refer [list-item]]
            [objecttech.components.common.common :as common]))

(defn list-separator-renderer [_ row-id _]
  (list-item
    ^{:key row-id}
    [common/list-separator]))

(defn list-header-renderer [& _]
  (list-item [common/list-header]))

(defn list-footer-renderer [& _]
  (list-item [common/list-footer]))

(ns object-tech.test.handlers
  (:require [cljs.test :refer-macros [deftest is]]
            [object-tech.handlers :as h]))

(deftest test-set-val
  (is (= {:key :val} (h/set-el {} [nil :key :val]))))

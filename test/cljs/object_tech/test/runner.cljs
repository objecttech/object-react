(ns object-tech.test.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [object-tech.test.contacts.handlers]
            [object-tech.test.chat.models.input]
            [object-tech.test.handlers]
            [object-tech.test.utils.utils]
            [object-tech.test.utils.money]
            [object-tech.test.utils.clocks]))

(enable-console-print!)

;; Or doo will exit with an error, see:
;; https://github.com/bensu/doo/issues/83#issuecomment-165498172
(set! (.-error js/console) (fn [x] (.log js/console x)))

(set! goog.DEBUG false)

(doo-tests 'object-tech.test.contacts.handlers
           'object-tech.test.chat.models.input
           'object-tech.test.handlers
           'object-tech.test.utils.utils
           'object-tech.test.utils.money
           'object-tech.test.utils.clocks)

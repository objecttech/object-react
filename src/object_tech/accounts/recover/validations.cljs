(ns objecttech.accounts.recover.validations
  (:require [cljs.spec.alpha :as s]))

(s/def ::not-empty-string (s/and string? not-empty))
(s/def ::passphrase ::not-empty-string)
(s/def ::password ::not-empty-string)

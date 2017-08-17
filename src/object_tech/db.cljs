(ns objecttech.db
  (:require [objecttech.constants :refer [console-chat-id]]
            [objecttech.utils.platform :as p]))

;; initial state of app-db
(def app-db {:current-public-key         ""
             :object-module-initialized? (or p/ios? js/goog.DEBUG)
             :keyboard-height            0
             :accounts                   {}
             :navigation-stack           '()
             :contacts/contacts          {}
             :qr-codes                   {}
             :group/contact-groups       {}
             :group/selected-contacts    #{}
             :chats                      {}
             :current-chat-id            console-chat-id
             :loading-allowed            true
             :selected-participants      #{}
             :profile-edit               {:edit?      false
                                          :name       nil
                                          :email      nil
                                          :object     nil
                                          :photo-path nil}
             :discoveries                {}
             :discover-search-tags       '()
             :tags                       []
             :sync-state                 :done
             :network                    :testnet})

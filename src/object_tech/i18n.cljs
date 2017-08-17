(ns objecttech.i18n
  (:require
    [objecttech.react-native.js-dependencies :as rn-dependencies]
    [objecttech.translations.af :as af]
    [objecttech.translations.ar :as ar]
    [objecttech.translations.bel :as be]
    [objecttech.translations.da :as da]
    [objecttech.translations.de :as de]
    [objecttech.translations.de-ch :as de-ch]
    [objecttech.translations.en :as en]
    [objecttech.translations.es :as es]
    [objecttech.translations.es-ar :as es-ar]
    [objecttech.translations.es-mx :as es-mx]
    [objecttech.translations.fi :as fi]
    [objecttech.translations.fr :as fr]
    [objecttech.translations.fr-ch :as fr-ch]
    [objecttech.translations.fy :as fy]
    [objecttech.translations.he :as he]
    [objecttech.translations.hi :as hi]
    [objecttech.translations.hu :as hu]
    [objecttech.translations.id :as id]
    [objecttech.translations.it :as it]
    [objecttech.translations.it-ch :as it-ch]
    [objecttech.translations.ja :as ja]
    [objecttech.translations.ko :as ko]
    [objecttech.translations.la :as la]
    [objecttech.translations.lv :as lv]
    [objecttech.translations.ms :as ms]
    [objecttech.translations.ne :as ne]
    [objecttech.translations.nl :as nl]
    [objecttech.translations.pl :as pl]
    [objecttech.translations.pt-br :as pt-br]
    [objecttech.translations.pt-pt :as pt-pt]
    [objecttech.translations.ro :as ro]
    [objecttech.translations.ru :as ru]
    [objecttech.translations.sl :as sl]
    [objecttech.translations.sv :as sv]
    [objecttech.translations.sw :as sw]
    [objecttech.translations.th :as th]
    [objecttech.translations.tr :as tr]
    [objecttech.translations.uk :as uk]
    [objecttech.translations.ur :as ur]
    [objecttech.translations.vi :as vi]
    [objecttech.translations.zh-hans :as zh-hans]
    [objecttech.translations.zh-hant :as zh-hant]
    [objecttech.translations.zh-wuu :as zh-wuu]
    [objecttech.translations.zh-yue :as zh-yue]
    [objecttech.utils.js-resources :refer [default-contacts]]
    [taoensso.timbre :as log]
    [clojure.string :as str]))

(set! (.-fallbacks rn-dependencies/i18n) true)
(set! (.-defaultSeparator rn-dependencies/i18n) "/")

(set! (.-translations rn-dependencies/i18n) (clj->js {:af      af/translations
                                      :ar      ar/translations
                                      :be      be/translations
                                      :da      da/translations
                                      :de      de/translations
                                      :de-ch   de-ch/translations
                                      :en      en/translations
                                      :es      es/translations
                                      :es-ar   es-ar/translations
                                      :es-mx   es-mx/translations
                                      :fi      fi/translations
                                      :fr      fr/translations
                                      :fr-ch   fr-ch/translations
                                      :fy      fy/translations
                                      :he      he/translations
                                      :hi      hi/translations
                                      :hu      hu/translations
                                      :id      id/translations
                                      :it      it/translations
                                      :it-ch   it-ch/translations
                                      :ja      ja/translations
                                      :ko      ko/translations
                                      :la      la/translations
                                      :lv      lv/translations
                                      :ms      ms/translations
                                      :ne      ne/translations
                                      :nl      nl/translations
                                      :pl      pl/translations
                                      :pt-br   pt-br/translations
                                      :pt-pt   pt-pt/translations
                                      :ro      ro/translations
                                      :ru      ru/translations
                                      :sl      sl/translations
                                      :sv      sv/translations
                                      :sw      sw/translations
                                      :th      th/translations
                                      :tr      tr/translations
                                      :uk      uk/translations
                                      :ur      ur/translations
                                      :vi      vi/translations
                                      :zh      zh-hans/translations
                                      :zh-hans zh-hans/translations
                                      :zh-hans-tw zh-hans/translations
                                      :zh-hans-sg zh-hans/translations
                                      :zh-hans-hk zh-hans/translations
                                      :zh-hans-cn zh-hans/translations
                                      :zh-hans-mo zh-hans/translations
                                      :zh-hant zh-hant/translations
                                      :zh-hant-tw zh-hant/translations
                                      :zh-hant-sg zh-hant/translations
                                      :zh-hant-hk zh-hant/translations
                                      :zh-hant-cn zh-hant/translations
                                      :zh-hant-mo zh-hant/translations
                                      :zh-wuu  zh-wuu/translations
                                      :zh-yue  zh-yue/translations}))

;:zh, :zh-hans-xx, :zh-hant-xx have been added until this bug will be fixed https://github.com/fnando/i18n-js/issues/460

(def delimeters
  "This function is a hack: mobile Safari doesn't support toLocaleString(), so we need to pass
  this map to WKWebView to make number formatting work."
  (let [n          (.toLocaleString (js/Number 1000.1))
        delimiter? (= (count n) 7)]
    (if delimiter?
      {:delimiter (subs n 1 2)
       :separator (subs n 5 6)}
      {:delimiter ""
       :separator (subs n 4 5)})))

(defn label-number [number]
  (when number
    (let [{:keys [delimiter separator]} delimeters]
      (.toNumber rn-dependencies/i18n
                 (str/replace number #"," ".")
                 (clj->js {:precision                 10
                           :strip_insignificant_zeros true
                           :delimiter                 delimiter
                           :separator                 separator})))))

(defn label
  ([path] (label path {}))
  ([path options]
   (if (exists? rn-dependencies/i18n.t)
     (let [options (update options :amount label-number)]
       (.t rn-dependencies/i18n (name path) (clj->js options)))
     (name path))))

(defn label-pluralize [count path & options]
  (if (exists? rn-dependencies/i18n.t)
    (.p rn-dependencies/i18n count (name path) (clj->js options))
    (name path)))

(defn message-object-label [object]
  (->> object
       (name)
       (str "t/object-")
       (keyword)
       (label)))

(def locale
  (.-locale rn-dependencies/i18n))

(defn get-contact-translated [contact-id key fallback]
  (let [translation #(get-in default-contacts [(keyword contact-id) key (keyword %)])]
    (or (translation locale)
        (translation (subs locale 0 2))
        fallback)))

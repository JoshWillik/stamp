(ns stamp.config
  (:require [clojure.string :as str])
  (:refer-clojure :exclude [get]))

(def static-config {:server-port "8080"})

(defn static-get [key]
  (static-config (keyword (str/replace key "." "-"))))

(defn env-get [key]
  (System/getenv (str/replace (str/upper-case key) "." "_")))

(defn get
  [key & {:keys [type] :or {type str}}]
  (let [val (or (env-get key) (static-get key))]
    (type val)))

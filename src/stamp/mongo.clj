(ns stamp.mongo
  (:require [monger.core]
    [monger.collection]
    [monger.json]
    [stamp.config :as config])
  (:refer-clojure :exclude [find]))

(defn connect []
  (monger.core/connect {:host (config/get "mongo.address")}))

(def disconnect monger.core/disconnect)

(defn db [conn]
  (monger.core/get-db conn "stamp"))

(def find-maps monger.collection/find-maps)

(def insert-and-return monger.collection/insert-and-return)

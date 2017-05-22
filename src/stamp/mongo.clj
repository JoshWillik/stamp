(ns stamp.mongo
  (:require [monger.core :as mg]
    [monger.collection :as mc]
    [monger.json]
    [stamp.config :as config])
  (:refer-clojure :exclude [find]))

(defn connect []
  (mg/connect {:host (config/get "mongo.address")}))

(def disconnect mg/disconnect)

(defn db [conn]
  (mg/get-db conn "stamp"))

(def find-maps mc/find-maps)

(def insert-and-return mc/insert-and-return)

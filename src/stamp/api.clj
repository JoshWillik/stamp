(ns stamp.api
  (:require [clojure.data.json :as json]
    [stamp.mongo :as mg]
    [clojure.java.io :as io]))

(defn send-json [data]
  {:status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str data)})

(def site-map {"index" "/"
  "certificates" "/certificates"
  "debug_request" "/debug/request"})

(defn absolute-path [req path]
  (str "http://" (req :server-name) path))

(defn index [req]
  (send-json (reduce-kv #(assoc %1 %2 (absolute-path req %3)) {} site-map)))

(defn request [req]
  (send-json (keys req)))

(defn request-part [part]
  (fn [req] (send-json (req (keyword part)))))

(defn certificates [req]
  (let [conn (mg/connect)
      db (mg/db conn)
      certs (mg/find-maps db "certificates" {})]
    (doall certs) ; tricky tricky, force the lazy list to evaluate fully
    (mg/disconnect conn)
    (send-json certs)))

(defn add-cert [req]
  (let [conn (mg/connect)
      db (mg/db conn)
      doc (mg/insert-and-return db "certificates"
        (json/read (io/reader (req :body) :encoding "UTF-8")))]
    (mg/disconnect conn)
    (send-json doc)))

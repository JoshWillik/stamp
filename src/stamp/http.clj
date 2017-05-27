(ns stamp.http
  (:require [clojure.data.json :as json]
    [stamp.manager :as manager]
    [clojure.java.io :as io]))

(defn send-json [data]
  {:status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str data)})

(def site-map {"index" "/"
  "certificates" "/certificates"
  "debug_request" "/debug/request"})

(defn- absolute-path [req path]
  (str "http://" (req :server-name) path))

(defn index [req]
  (send-json (reduce-kv #(assoc %1 %2 (absolute-path req %3)) {} site-map)))

(defn request [req]
  (send-json (keys req)))

(defn request-part [part]
  (fn [req] (send-json (req (keyword part)))))

(defn add-cert [req]
  (let [body (json/read (io/reader (req :body) :encoding "UTF-8"))]
    (send-json (manager/add-cert body))))

(defn certificates [req]
  (send-json (manager/get-certs)))

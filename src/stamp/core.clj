(ns stamp.core
  (:gen-class)
  (:require
    [clojure.data.json :as json]
    [clojure.string :as str]
    [stamp.util :as util]
    [stamp.config :as config])
  (:use [org.httpkit.server :only [run-server]]))

(def port (config/get "server.port" :type util/int))

(defn not-found [path]
  {:status 404 :headers {"Content-Type" "text/plain"}
    :body (str path " not found")})

(defn text-ok [body]
  {:status 200 :headers {"Content-Type" "text/plain"} :body body})

(defn json-ok [body]
  {:status 200 :headers {"Content-Type" "application/json"}
    :body (json/write-str body)})

(defn app [req]
  (let
    [url (req :uri)
      req-key (re-matches #"/req/([^/]*)" url)]
    (cond
      (= url "/") (text-ok "Hello world")
      (= url "/req") (json-ok (keys req))
      req-key (json-ok (req (keyword (nth req-key 1))))
      :else (not-found url))))

(defonce server (atom nil))

(defn -main "Simple certificate manager" [& args]
  (reset! server (run-server #'app {:port port}))
  (println (str "Listening on :" port)))

(ns stamp.core
  (:gen-class)
  (:require
    [stamp.type :as type]
    [stamp.api :as api]
    [stamp.config :as config])
  (:use [org.httpkit.server :only [run-server]]
    [compojure.core :only [defroutes GET POST PUT DELETE ANY context]]
    [compojure.handler :only [site]]
    [compojure.route :only [not-found]]))

(defroutes json-api
  (GET "/" [] api/index)
  (GET "/certificates" [] api/certificates)
  (POST "/certificates" [] api/add-cert)
  (GET "/debug/request" [] api/request)
  (GET "/debug/request/:part" [part] (api/request-part part))
  (not-found "Page not found"))

(def port (config/get "server.port" :type type/int))

(defn -main "Simple certificate manager" [& args]
  (run-server (site #'json-api) {:port port})
  (println (str "Listening on :" port)))

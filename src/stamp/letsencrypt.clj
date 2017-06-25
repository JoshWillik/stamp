(ns stamp.letsencrypt
  (:require [stamp.keypair :as keypair]
    [org.httpkit.client :as http]
    [clojure.data.json :as json]))

(def secret "foobar")

(defn sign [body] (jwt/sign body secret))

(def acme-directory "https://acme-staging.api.letsencrypt.org/directory")

(def get-endpoint (memoize (fn [type]
  (let [{:keys [status body error headers]} @(http/get acme-directory)
      parsed (json/read-str body)]
    (parsed type)))))

(defn create-account []
  (let [url (get-endpoint "new-reg")
      body {:resource "new-reg"
        :contact ["mailto:joshwillik@gmail.com"]}
      resp @(http/post url {:body (sign body)})]
    (println resp)))

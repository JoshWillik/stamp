(ns stamp.manager
  (:require [stamp.mongo :as mongo]))

(defn add-cert [cert]
  (let [conn (mongo/connect)
      db (mongo/db conn)
      doc (mongo/insert-and-return db "certificates" cert)]
    (future (mongo/disconnect conn))
    doc))

(defn get-certs []
  (let [conn (mongo/connect)
      db (mongo/db conn)
      certs (mongo/find-maps db "certificates" {})]
    (doall certs) ; force the lazy list to evaluate fully
    (future (mongo/disconnect conn))
    certs))

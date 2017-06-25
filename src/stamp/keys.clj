(ns stamp.keys
  (:require [clojure.string :as string]
    [clojure.java.io :as io])
  (:import
    java.security.Security
    java.security.KeyPairGenerator
    java.io.File
    java.io.FileReader
    java.io.FileWriter
    java.io.FileNotFoundException
    org.bouncycastle.openssl.PEMReader
    org.bouncycastle.openssl.PEMWriter
    org.bouncycastle.jce.provider.BouncyCastleProvider)
  (:refer-clojure :exclude [get]))

(Security/addProvider (BouncyCastleProvider.))

(def key-file "/tmp/stamp/user_key.pem")

(defn- pem-read [path]
  (-> (File. path)
    FileReader.
    PEMReader.
    .readObject))

(defn- pem-write [path keys]
  (io/make-parents path)
  (let [writer (PEMWriter. (FileWriter. path))]
    (.writeObject writer keys)
    (.flush writer)))

(defn generate-rsa [length]
  (.generateKeyPair
    (doto (KeyPairGenerator/getInstance "RSA" "BC")
      (.initialize length))))

(defn- keypair->hash [pair]
  {:public (.getPublic pair) :private (.getPrivate pair)})

(defn load-keys []
  (try
    (keypair->hash (pem-read key-file))
    (catch FileNotFoundException e nil)))

(defn save-keys [keypair]
  (do
    (pem-write key-file keypair)
    keypair))

(defn get []
  (or (load-keys)
    (-> (generate-rsa 4096)
      save-keys
      keypair->hash)))

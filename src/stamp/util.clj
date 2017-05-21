(ns stamp.util
  (:refer-clojure :exclude [float int]))


(def float #(Float/parseFloat %1))
(def int #(Integer/parseInt %1))

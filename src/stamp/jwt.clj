(ns stamp.jwt
  (:import
    com.auth0.jwt.JWT
    com.auth0.jwt.algorithms.Algorithm))

(defn- algorithm [keypair]
  (Algorithm/RSA256 (keypair :public) (keypair :private)))

(defn- header-claims [headers]
  (merge {"alg" "RS256"} headers))

(defn- with-claims [token claims]
  (reduce-kv #(.withClaim %1 %2 %3) token claims))

(defn sign [claims keypair & [extra-headers]]
  (let [alg (algorithm keypair)
      header (header-claims extra-headers)]
    (.. (with-claims (JWT/create) claims)
      (withHeader header)
      (sign alg))))

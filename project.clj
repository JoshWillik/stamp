(defproject stamp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {
    :name "Eclipse Public License"
    :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/data.json "0.2.6"]
    [compojure "1.6.0"]
    [ring/ring-jetty-adapter "1.4.0"]
    [com.novemberain/monger "3.1.0"]
    [cheshire "5.1.1"]
    [http-kit "2.2.0"]
    [com.auth0/java-jwt "3.2.0"]
    [com.fasterxml.jackson.core/jackson-core "2.8.7"]
    [org.bouncycastle/bcprov-jdk16 "1.46"]]
  :main ^:skip-aot stamp.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

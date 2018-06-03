(defproject cljnewbiebank "0.1.0-SNAPSHOT"
  :description "The wonderful bank of Clojure's newbieland"
  :url "..."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.postgresql/postgresql "9.4.1212"]
                 [clj-time "0.14.4"]]
  :main ^:skip-aot cljnewbiebank.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

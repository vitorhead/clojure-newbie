(defproject cljnewbiebank "0.1.0-SNAPSHOT"
  :description "The wonderful bank of Clojure's newbieland"
  :url "https://github.com/vitorhead/clojure-newbie"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.postgresql/postgresql "9.4.1212"]
                 [clj-time "0.13.0"]
                 [compojure "1.1.1"]
                 [ring/ring-json "0.1.2"]]
  ;:main ^:skip-aot cljnewbiebank.core
  :plugins [[lein-ring "0.7.3"]]
  :ring {:handler cljnewbiebank.handler/app
         :auto-reload? true
         :auto-refresh? false
         :open-browser? false}    
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

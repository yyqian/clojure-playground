(defproject com.yyqian/clojure-playground "0.1.0-SNAPSHOT"
  :description "clojure-playground"
  :url "https://yyqian.com"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.4.1"]
                 [cheshire "5.7.0"]
                 [compojure "1.5.2"]
                 [org.clojure/core.async "0.3.442"]]
  :main ^:skip-aot clojure-playground.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

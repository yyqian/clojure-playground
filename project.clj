(defproject com.yyqian/clojure-playground "0.1.0-SNAPSHOT"
  :description "clojure-playground"
  :url "https://yyqian.com"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.3.0"]
                 [cheshire "5.6.3"]
                 [org.clojure/core.async "0.2.385"]]
  :main ^:skip-aot clojure-playground.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

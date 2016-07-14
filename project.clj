(defproject clojure-playground "0.1.0-SNAPSHOT"
  :description "clojure-playground"
  :url "https://yyqian.com"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot clojure-playground.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

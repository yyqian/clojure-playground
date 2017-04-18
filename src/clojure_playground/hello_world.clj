(ns clojure-playground.hello-world
  (:require [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app
           (GET "/" [] "Hello, world!")
           (route/not-found "404 Not Found"))

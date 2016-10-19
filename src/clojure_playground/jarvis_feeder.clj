(ns clojure-playground.jarvis-feeder
  (:require [cheshire.core :as cheshire]
            [clj-http.client :as client]))


(defn sendColl
  [coll]
  (client/post "http://io.cheerapp.cn:1987/api/stream"
               {:content-type :json
                :accept       :json
                :body         (cheshire/generate-string {:val coll})}))

(defn gen-rand-vector
  [t]
  (let [rand-a #(* % (- (rand) 0.5))
        rand-shift [(rand-a 0.1) (rand-a 0.1)]
        shift (fn [cluster]
                (let [angle (+ (cluster :theta0) (* t (cluster :w)))
                      radius (cluster :radius)]
                  [(* radius (Math/cos angle))
                   (* radius (Math/sin angle))]))
        clusters [{:radius 1.0
                   :w      0.01
                   :theta0 0
                   :center [1, 1]}
                  {:radius 1.1
                   :w      0.01
                   :theta0 0
                   :center [3, 1]}
                  {:radius 1.2
                   :w      0.01
                   :theta0 0
                   :center [2, 3]}]
        cluster (rand-nth clusters)]
    (->> (cluster :center)
         (map + (shift cluster))
         (map + rand-shift))))

(gen-rand-vector 1)

(defn feed-jarvis
  [t]
  (sendColl (gen-rand-vector t)))

(dotimes [t 1000]
  (feed-jarvis t)
  (Thread/sleep 1000))

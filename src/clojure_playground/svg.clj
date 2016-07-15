(ns clojure-playground.svg
  (:refer-clojure :exclude [min max]))

(defn comparator-over-maps
  [comparison-fn ks]
  (fn [maps]
    (zipmap ks
            (map (fn [k] (apply comparison-fn (map k maps)))
                 ks))))

(def min (comparator-over-maps clojure.core/min [:lat :lng]))
(def max (comparator-over-maps clojure.core/max [:lat :lng]))

(min [{:a 1 :b 3} {:a 5 :b 0}])
(zipmap [:a :b] [1 2])
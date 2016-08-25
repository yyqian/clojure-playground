(ns clojure-playground.data-gen)

; 返回多项式 f(x) = theta0 + theta1 * x + theta2 * x^2 ...
(defn polynomial
  [theta]
  (fn [x]
    (let [loop (fn f [x1 theta1]
                 (if (empty? theta1)
                   0
                   (+ (first theta1)
                      (* x1 (f x (rest theta1))))))]
      (loop x theta))))

(def x (range 0 10 0.1))

(defn noise
  [n a]
  (take n (repeatedly #(rand a))))

(def f #(* ((polynomial [1 -1 -1 -0.5 0.1]) %) (+ 0.9 (rand 0.2))))

(spit "/Users/yyqian/test/test.dat"
      (reduce
        #(str %1 %2 "," (f %2) "\n")
        x))
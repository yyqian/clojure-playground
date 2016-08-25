(ns clojure-playground.data-generator)

; 返回多项式 f(x) = theta0 + theta1 * x + theta2 * x^2 ...
(defn polynomial
  [theta x]
  (if (empty? theta)
    0
    (+ (first theta)
       (* x (polynomial (rest theta) x)))))

(defn noise
  [a x]
  (* x (+ 1 (- a) (rand (* 2 a)))))

(defn drive
  [x]
  (+ (polynomial [1.2 2.5] x)
     (noise 0.3 x)))

(def X (range 0 20 0.1))

(spit "/Users/yyqian/test/linear.dat"
      (reduce
        (fn [accum x] (str accum x " " (drive x) "\n"))
        X))

; ---

(defn z
  [x y]
  (* (Math/sin x)
     (Math/cos y)))

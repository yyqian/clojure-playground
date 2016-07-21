(ns clojure-playground.chapter3)

(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")

(do (println "Success!")
    (println "Ugly!")
    (println "Something else")
    "By Zeus's hammer!")

(when true
  (println "Success!")
  (println "Ugly!")
  (println "Something else")
  "By Zeus's hammer!")

(nil? 1)
(= 1 1)
(or (= 0 1) (= 100 100))
(and :free-wifi :hot-coffee)

(def v
  [1 2 3])

(defn hello [name] (println "Hello," name))
(hello "yyqian")

; map
{:x 14 :y 23}
(hash-map :x 14 :y 23)
(def mp {:x 123
         :y 932})

(:y mp)
(mp :y)
(get mp :y)
(get-in {:a 0
         :b {:c 1
             :d 2}} [:b :d])

; vector
[1 2 3 4]
(vector 1 2 3 4)
(get [23 4343 23] 1)
(conj [902 324 54 3] 40)

; list
'(1 2 3 4)
(list 1 2 3 4)
(nth '(1 2 3 4) 1)

; set
#{123 43 3 23}
(hash-set 11 11 32 43 32)
(contains? #{1 2 3} 3)
(set [2 432 23 45 4 432])

; function
(#(+ % 1) 3)
((fn [x] (+ x 1)) 3)
(#(+ %1 %2) 45 3)

; loop
(loop [i 0]
  (println (str "Iteration " i))
  (if (> i 3)
    (println "Goodbye")
    (recur (inc i))))

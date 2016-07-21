(ns clojure-playground.chapter5)

; comp
; 多个函数 pipeline 化
((comp #(* % 2) inc *) 3 4 5)
(#(* % 2)
  (inc
    (* 3 4 5)))

; memoize
(defn delay-get
  []
  (Thread/sleep 10000)
  "Boom")

(delay-get)

; 记住上次调用的结果, 下次不调用, 直接返回之前的结果
(def memo-get-x (memoize delay-get))
(memo-get-x)
(memo-get-x)
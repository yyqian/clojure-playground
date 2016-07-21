(ns clojure-playground.chapter4)

; map reduce into conj concat some filter take drop sort sort-by identity
; apply partial complement

; -------------- sequence -------------
(seq '(1 2 3))
(seq [1 2 3])
(seq #{1 2 3})
(seq {:x 1 :y 2})

(map inc [1 2 3 4])

(reduce + [1 2 3 4])
(reduce + 10 [1 2 3 4])

(take 3 [1 2 3 4 5 6 7])
(drop 3 [1 2 3 4 5 6 7])

(take-while #(< % 3) [-1 0 1 2 3 1 2 3 4 5 6])
(drop-while #(< % 3) [-1 0 1 2 3 1 2 3 4 5 6])
(filter #(< % 3) [-1 0 1 2 3 1 2 3 4 5 6])

(some #(< % 3) [-1 0 1 2 3 1 2 3 4 5 6])
(some #(< % -10) [-1 0 1 2 3 1 2 3 4 5 6])

(sort [20 39 34 5465 234 2 34])
(sort-by count ["aaa" "c" "bb"])

(concat [1 2] #{3 4})

; cons 返回的是 sequence, 并且只能有两个参数
; 后面的 conj 可以有多个参数, 返回的是第一个参数的 collection type
(cons 0 '(2 4 5))
(cons 0 #{2 4 5})
(cons 0 [2 4 5])

; 注意与上面 cons 的不同
(conj [2 4 5] 0 123 32 43)

; ---------------- lazy seq -----------------

(take 8 (repeat "run!"))
(take 10 (repeatedly #(rand-int 100)))

; -------------- collection -----------------
; collection 操作关注整个容器, sequence 操作关注容器中的单个元素
(empty? [])

; convert sequence back into a collection
(into {} (seq {:x 1 :y 2}))
(into #{} (seq [1 2 2 3 3]))
(into '() (seq [1 2 2 3 3]))
(into ["cherry"] '("apple" "mongo"))

; 注意与 into 的不同
(conj ["cherry"] "apple" "mongo")

; ------------ apply partial complement -----------

(max 0 1 2)
(apply max [0 1 2])

(def add7 (partial + 7))
(add7 8)

(defn hello
  [title name]
  (println "Hello" title name))
(hello "Lord" "Stark")
(def helloMr (partial hello "Mr."))
(helloMr "Einstein")

; 加一个 not
(def my-pos? (complement neg?))
(def my-pos2? (fn [x] (not (neg? x))))
(my-pos? 1)
(my-pos2? 1)
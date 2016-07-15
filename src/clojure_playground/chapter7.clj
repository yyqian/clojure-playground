(ns clojure-playground.chapter7
  (:refer-clojure))

(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))
(backwards (1 2 3 +))

(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))

(eval (list 'def 'lucky-number (concat addition-list [10])))
lucky-number

(read-string "(+ 1 2)")
(list? (read-string "(+ 1 2)"))
(conj (read-string "(+ 1 2)") :zagglewag)
(eval (read-string "(+ 1 2)"))

(read-string "#(+ 1 %)")
(read-string "@var")

(if true :a :b)
(let [x 5]
  (+ x 3))
(def x 15)
(+ x 3)

(type (read-string "+"))
(type (read-string "(+ 1 2)"))
(type (read-string "x"))

(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))

(infix (1 + 2))
(macroexpand '(infix (1 + 2)))


(ns clojure-playground.chapter8
  (:refer-clojure))

(macroexpand
  '(when boolean
     expr1
     expr2))

(defmacro infix
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(infix (1 + 1))

(macroexpand '(infix (1 + 1)))

(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(infix-2 (1 + 2))

(defmacro my-print
  [expression]
  (list let [result expression]
        (list println result)
        result))

(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))

(my-print "hello")

+
(quote +)

(macroexpand '(when (the-cows-come :home)
                (call me :pappy)
                (slap me :silly)))

(defmacro unless
  [test & branches]
  (conj (reverse branches) test 'if))

(macroexpand '(unless (done-been slapped? me)
                      (slap me :silly)
                      (say "I reckon that'll learn me")))
(conj [1 2 3] 4)
(cons 4 [1 2 3])

'x
`x

`(+ (cons 1 [2 3 4]) 2)

`(+ 1 ~(inc 1))
'(+ 1 ~(inc 1))

(defmacro code
  [x]
  `(println (quote ~x)))
(code (+ 1 2))

`(+ ~(list 1 2 3))
`(+ ~@(list 1 2 3))

(defmacro report
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was not successful:" result#))))

(macroexpand '(report (= 1 2)))
(report (= 1 2))

(doseq [code ['(= 1 1) '(= 1 2)]]
  (report code))
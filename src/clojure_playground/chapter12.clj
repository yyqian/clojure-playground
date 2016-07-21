(ns clojure-playground.chapter12
  (:import [java.util ArrayDeque ArrayList Date]
           [java.io File]))

; (.method object)
(.toUpperCase "hello world!")
(.indexOf "Iron man" "ma")

; static method/field
(Math/abs -3)
Math/PI

; macroexpand
; (. object-expr-or-classname-symbol method-or-member-symbol optional-args*)
(macroexpand-1 '(.toUpperCase "hello world!"))
(. "hello world!" toUpperCase)

(macroexpand-1 '(Math/abs -3))
(. Math abs -3)

; (new ClassName optional-args*)
; (ClassName. optional-args*)
(new String)
(String.)
(String. "Hellow world!")

(let [stack (ArrayDeque.)]
  (.push stack "Game of Thrones!")
  (.push stack "John Snow")
  (.push stack "Tony Stark")
  (println (.pop stack))
  (first stack))

(doto (ArrayList.)
  (.add "Java")
  (.add "Clojure")
  (.add "C++"))

; Commonly Used Java Classes
(System/getenv)
(System/getProperty "java.version")
(System/getProperty "user.dir")

(Date.)

(let [file (File. "/tmp/spit-on-it")]
  (println (.exists file))
  (println (.canWrite file))
  (println (.getPath file)))

(spit "/tmp/spit-on-it" "Nothing here\nThat's True")
(slurp "/tmp/spit-on-it")
(slurp "http://cnbeta.com")

(with-open [file (clojure.java.io/reader "/tmp/spit-on-it")]
  (println (second (line-seq file))))


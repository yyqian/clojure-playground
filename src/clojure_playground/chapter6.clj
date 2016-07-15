(ns clojure-playground.chapter6)

; *ns* 表示 current namespace
(ns-name *ns*)

; 获取的是 object
inc
; #object[clojure.core$inc 0x62bcf03b "clojure.core$inc@62bcf03b"]

; 获取的是 symbol, quote 之后就被当成 data 了
`inc
; clojure.core/inc

; 用 def 来存放数值, 并且绑定到当前 namespace, 返回这个 var
(def great-books ["三个代表" "论持久战"])
; #'clojure-playground.chapter6/great-books

; interning a var
(ns-interns *ns*)
; {great-books #'clojure-playground.chapter6/great-books}
; great-books 是 symbol
; #'clojure-playground.chapter6/great-books 是 var

; 获取完整的 symbol 和 var 之间的映射列表
(ns-map *ns*)

; #'clojure-playground.chapter6/great-books 是一种 reader form of a var
(deref #'clojure-playground.chapter6/great-books)

; 等效于根据 symbole 查找 var, 然后再 deref 这个 var
great-books

; var 可能不经意间被重新赋值, 或者被第三方库更改
; 为避免 name collision, 所以我们需要 namespace
(def great-books ["论可变 is bad" "变还是不变"])

(create-ns 'cheese.factory)

(ns-name (create-ns 'cheese.factory))

; 创建并进入一个 namespace
(in-ns 'cheese.facotry)
(def cheddars ["mild" "strong" "sharp"])
(in-ns 'cheese.store)
cheddars
(in-ns 'cheese.facotry)
cheddars

; full qualified symbol, 如果需要 call 当前 namespace 之外的东西的时候就需要
clojure-playground.chapter6/cheddars

; refer 某个 ns 之后, 后面就可以直接引用这个 ns 中的 symbol, 而不需要写 full qualified symbol
(in-ns 'refer-test-ns)
(def books ["Iron Man 发家史" "论帝国的灭亡"])
(in-ns 'refer-test-badland)
books
(clojure.core/refer 'refer-test-ns)
books
(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'books)

; refer 可以附加 :only :exclude
(clojure.core/refer 'refer-test-ns :only ['books])
(ns-interns clojure.core/*ns*)
(clojure.core/refer-clojure)
(ns-interns *ns*)

; alias 可以缩短 ns
(alias 'good-place 'refer-test-ns)
good-place/books

(require 'clojure-playground.core)
(refer 'clojure-playground.core)
(clojure-playground.core/hello)


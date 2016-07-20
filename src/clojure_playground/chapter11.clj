(ns clojure-playground.chapter11
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))

; core.async

; chan creates a new channel
(def echo-chan (chan))
; go runs the process on a thread pool
; <! is the take function, 这儿会 block 并等待 channel 中出现值
(go (println (<! echo-chan)))
; >!! is the put function, 它会 block 直到值被取走
(>!! echo-chan "ketchup")

; buffering
(def echo-buffer (chan 2))
(>!! echo-buffer "ketchup")
(>!! echo-buffer "ketchup")
; this line will block
(>!! echo-buffer "ketchup")
(go (println (<! echo-buffer)))
(println (<!! echo-buffer))

(def hi-chan (chan))
(doseq [n (range 1000)]
  (go (>! hi-chan (str "hi " n))))
(<!! hi-chan)

; thread 使用的场景是: 如果一个任务耗时很长, 放在 go block 中运行的话会
; 长时间占据有限的线程池资源, 这个时候应当把这个任务放到 Thread 中运行
; 例如: 如果 go 的线程池只有四个线程, 我们在其中启动四个下载任务, 这时候
; 它们一直在运行中, Clojure 无法 park 它们, 其他 process 就无法在这个线程池中运行

; thread 中不能用 go 的 <! 和 >!, 所以必须用 <!! 和 >!!
(thread (println (<!! echo-chan)))
(>!! echo-chan "mustard")

; 和 future 不同的是, thread 返回的是 channel
(let [t (thread "chili")]
  (<!! t))

; 应用: Hot Dog Machine

; 注意 go block, 如果 channel in 没有得到东西的话, 就会阻塞
; channel out 也就不会产生东西
(defn hot-dog-machine
  []
  (let [in (chan)
        out (chan)]
    (go (<! in)
        (>! out "hot dog"))
    [in out]))

(let [[in out] (hot-dog-machine)]
  (>!! in "pocket lint")
  (<!! out))

; 一个 hot dog 3 dollars
(defn hot-dog-machine-v2
  [hot-dog-count]
  (let [in (chan)
        out (chan)]
    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            (let [input (<! in)]
              (if (= 3 input)
                (do (>! out "hot dog")
                    (recur (dec hc)))
                (do (>! out "wilted lettuce")
                    (recur hc))))
            (do (close! in)
                (close! out)))))
        [in out]))

(let [[in out] (hot-dog-machine-v2 3)]
  (>!! in "pocket lint")
  (println (<!! out))
  (>!! in 3)
  (println (<!! out))
  (>!! in 3)
  (println (<!! out))
  (>!! in 3)
  (<!! out))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (go (>! c2 (clojure.string/upper-case (<! c1))))
  (go (>! c3 (clojure.string/reverse (<! c2))))
  (go (println (<! c3)))
  (>!! c1 "redrum"))

; alts!! 用于获取一个 channel 集合中最先完成的那个的结果
(defn upload
  [headshot c]
  (go (Thread/sleep (rand 5000))
      (>! c headshot)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (upload "serious.jpg" c1)
  (upload "fun.jpg" c2)
  (upload "sassy.jpg" c3)
  (let [[headshot channel] (alts!! [c1 c2 c3])]
    (println "Sending headshot notif for" headshot)))

; alts!! 可用于设置 timeout
(let [c1 (chan)]
  (upload "serious.jpg" c1)
  (let [[headshot channel] (alts!! [c1 (timeout 2500)])]
    (if headshot
      (println "Sending headshot notif for" headshot)
      (println "Time out!"))))

; Queue

(defn append-to-file
  [filename s]
  (spit filename s :append true))

(defn format-quote
  [quote]
  (str "=== BEGIN QUOTE ===\n" quote "=== END QUOTE ===\n\n"))

(defn random-quote
  []
  (format-quote (slurp "http://www.braveclojure.com/random-quote")))

; c 在这儿是个 Queue, 一个 go block 从 channel c 中读取内容, 并写入文件
; 若干 go block 爬到内容之后, 排队向 channel c 中写入内容
(defn snag-quotes
  [filename num-quotes]
  (let [c (chan)]
    (go (while true (append-to-file filename (<! c))))
    (dotimes [n num-quotes] (go (>! c (random-quote))))))

(snag-quotes "/Users/yyqian/quotes" 5)

; 使用 channel 将 callback hell 转换成 pipeline

(defn upper-caser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/upper-case (<! in)))))
    out))

(defn reverser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/reverse (<! in)))))
    out))

(defn printer
  [in]
  (go (while true (println (<! in)))))

(def in-chan (chan))
(def upper-caser-out (upper-caser in-chan))
(def reverser-out (reverser upper-caser-out))
(printer reverser-out)

(>!! in-chan "redrun")
(>!! in-chan "repaid")


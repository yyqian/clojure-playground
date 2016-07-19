(ns clojure-playground.chapter9
  (:refer-clojure))

; future/deref, delay/force, promise/deref

; future 会把这个任务放在另外一个 thread 上跑
(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print immediately")

(let [result (future (println "this prints once")
                     (+ 1 1))]
  (println "deref: " (deref result))
  (println "@: " @result))


(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "Thr result is: " @result)
  (println "It will be at least 3 seconds before I print"))

(realized? (future (Thread/sleep 1000)))

(let [f (future)]
  @f
  (realized? f))

; delay
(def jackson-5-delay
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))

(force jackson-5-delay)
@jackson-5-delay

; promise
(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise


(let [p (promise)]
  (deref p 1000 "time out"))

(let [some-promise (promise)]
  (future (println "A promise: " @some-promise))
  (Thread/sleep 3000)
  (deliver some-promise "God bless you!"))


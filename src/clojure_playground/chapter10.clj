(ns clojure-playground.chapter10
  (:refer-clojure))

; atom, ref

; atom
(def fred (atom {:level 0
                 :percent 0}))
@fred

(swap! fred
       (fn [state]
         (merge-with + state {:level 1})))
@fred


(swap! fred
       (fn [state]
         (merge-with + state {:level 1
                              :percent 1})))

(merge-with + @fred {:level 10})
@fred

(swap! fred update-in [:level] inc)

(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "State 1: " s1)
  (println "Current state: " @num))

(reset! fred {:level 0
              :percent 0})
@fred

(defn shuffle-speed
  [zombie]
  (* (:level zombie)
     (- 100 (:percent zombie))))
(defn shuffle-alert
  [key watched old-state new-state]
  (let [sph (shuffle-speed new-state)]
    (if (> sph 5000)
      (println "Run, you fool!")
      (println "All's well!"))))

(shuffle-speed @fred)
(reset! fred {:level 22
              :percent 2})
(add-watch fred :alert shuffle-alert)
(swap! fred update-in [:percent] inc)

(defn fred-validator
  [{:keys [percent]}]
  (and (>= percent 0)
       (<= percent 100)))

(def fred
  (atom {:level 0
         :percent 0}
        :validator fred-validator))
(swap! fred update-in [:percent] + 1)

; refs
(def sock-varieties
  #{"darned" "argyle" "wool" "horsehair" "mulleted"
    "passive-aggressive" "striped" "polka-dotted"
    "athletic" "business" "power" "invisible" "gollumed"})

(defn sock-count
  [sock-variety count]
  {:variety sock-variety
   :count count})

(defn generate-sock-gnome
  [name]
  {:name name
   :socks #{}})

(def sock-gnome (ref (generate-sock-gnome "Barumpharumph")))
(def dryer (ref {:name "LG 1337"
                 :socks (set (map #(sock-count % 2) sock-varieties))}))

(:socks @dryer)

(binding [*print-length* 1]
  (println ["print" "just" "one!"]))

(def x 10)
(def x 100)

(defn always-1 [] 1)
(take 5 (repeatedly always-1))

(take 5 (repeatedly (partial rand-int 10)))

; pmap
(def alphabet-length 26)
(def letters (mapv (comp str char (partial + 65))
                   (range alphabet-length)))

(rand-nth letters)
(defn random-string
  [length]
  (apply str
         (take length
               (repeatedly #(rand-nth letters)))))

(random-string 5)

(defn random-string-list
  [list-length string-length]
  (doall (take list-length
               (repeatedly (partial random-string string-length)))))

(random-string-list 3 5)
(ns clojure-playground.chapter13)

; multimethods, protocols, and records

; Polymorphism
; ----------------- Multimethods -----------------
(defmulti full-moon-behavior
          (fn [were-creature]
            (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people"))

(defmethod full-moon-behavior nil
  [were-creature]
  (str (:name were-creature) " will stay at home and eat ice cream"))

(defmethod full-moon-behavior :default
  [were-creature]
  (str (:name were-creature) " will stay up all night fantasy footballing"))

(full-moon-behavior {:were-type :wolf
                     :name      "WolfMan"})
(full-moon-behavior {:were-type :simmons
                     :name      "Hello Kitty"})
(full-moon-behavior {:were-type nil
                     :name      "IronMan"})
(full-moon-behavior {:were-type :officer
                     :name      "公务员"})

(defmulti types (fn [x y] [(class x) (class y)]))
(defmethod types [String String]
  [x y]
  "Two strings!")
(defmethod types [String Integer]
  [x y]
  "One string & one interger!")
(types "String 1" (Integer. 3))

; ----------------- Protocols -----------------
; protocols are optimized for type dispatch.
(defprotocol Psychodynamics
  (thoughts [x])
  (feelings-about [x] [x y]))

; use extend-type
(extend-type String
  Psychodynamics
  (thoughts [x] (str x " thinks, 'Truly, the character defines the data type'"))
  (feelings-about
    ([x] (str x " is longing for a simpler way of life"))
    ([x y] (str x " is envious of " y "'s simpler way of life"))))

(thoughts "12")
(feelings-about "schmorb")

(extend-type Object
  Psychodynamics
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str "meh about " y))))

(thoughts 12)
(feelings-about 3 213)

; use extend-protocol
(extend-protocol Psychodynamics
  String
  (thoughts [x] "Truly, the character defines the data type")
  (feelings-about
    ([x] "longing for a simpler way of life")
    ([x y] (str "envious of " y "'s simpler way of life")))
  Object
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str "meh about " y))))

(thoughts "sd")
(feelings-about 12 "LKI")

; ----------------- Records -----------------
(defrecord WereWolf [name title])

; 三种构造形式
(WereWolf. "David" "London Tourist")
(->WereWolf "David" "London Tourist")
(map->WereWolf {:name "David" :title "London Tourist"})

(def jacob (WereWolf. "Jacob" "Lead Shirt"))

; 三种获取字段的形式
(.name jacob)
(:name jacob)
(get jacob :name)

(defprotocol WereCreature
  (full-moon-behavior [x]))

(defrecord WereWolf [name title]
  WereCreature
  (full-moon-behavior [x]
    (str name " will howl and murder")))

(full-moon-behavior (WereWolf. "Lucian" "CEO"))


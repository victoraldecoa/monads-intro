(ns maybe-tutorial
  (:require [cats.core :as m]
            [cats.monad.maybe :as maybe]
            [clojure.pprint :as pp]))

(def got-chars
  [{:name    "Daenerys"
    :dragons 3}
   {:name  "Jon"
    :knows []}
   {:name "Arya"
    :is-a :psycho}])

; maybe can be used to handle nil values
; use "just" to wrap values and use "nothing" as nil
(defn new-maybe [x] (if (some? x) (maybe/just x) (maybe/nothing)))

(defn maybe-dragons []
  (map #(update % :dragons new-maybe) got-chars))

(maybe-dragons)
;=>
;({:name "Daenerys", :dragons #<Just 3>}
; {:name "Jon", :knows [], :dragons #<Nothing>}
; {:name "Arya", :is-a :psycho, :dragons #<Nothing>})

; why would you do that?
; so that you don't have to keep nil-checking to do stuff
(defn kill-dragon [{:keys [dragons]}]
  (m/fmap dec dragons))

; now we can kill everyone's dragons without a null-ref
(map kill-dragon (maybe-dragons))
;=> (#<Just 2> #<Nothing> #<Nothing>)

; you can unwrap any Monad from cats using m/extract
(def dany-dragons (-> (maybe-dragons) first :dragons))
(m/extract dany-dragons)
; => 3

(def arya-dragons (-> (maybe-dragons) last :dragons))
(m/extract arya-dragons)
; => nil

; you can also use maybe/from-maybe, with the additional
; option to return a default value
(maybe/from-maybe arya-dragons)
; => nil
(maybe/from-maybe arya-dragons 0)
; => 0
(maybe/from-maybe dany-dragons 0)
; => 3

; in most languages, it's impossible to do those things without null-checks
; now... can we do that in Clojure without Monads and without Null Checks?
; ...
; yes! you can use "some->", and it will just short-circuit return as soon
; as it hits a nil (in this case, a normal -> would throw a null-ref exception
; on dec.
(defn kill-dragon* [{:keys [dragons]}]
  (some-> dragons dec))

(map kill-dragon* got-chars)
;=> (2 nil nil)

; you can also get a "default value" effect with "or", no null-checks required
(def jon-dragons (-> got-chars (nth 1) :dragons))
(println jon-dragons)
; nil
(or jon-dragons 0)
; => 0

; how many dragons each character will have after killing one of each of them?
(->> got-chars
     (map kill-dragon*)
     (map #(or % 0)))
; => (2 0 0)

; this is called "nil punning", a neat feature of Lisp languages!

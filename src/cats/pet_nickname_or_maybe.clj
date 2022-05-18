(ns cats.pet-nickname-or-maybe
  (:require [cats.core :as m]
            [cats.monad.maybe :as maybe]))
(defn fetch
  [key x]
  {:pre [(some? x)]}
  (if (< (rand) 0.8)
    (m/fmap key x)
    (maybe/nothing)))

(defn current-user
  []
  (maybe/just {:name "Vic"
               :pet  {:nickname "Ada"}}))

(defn user-pet
  [user]
  (fetch :pet user))

(defn user-pet-nickname
  [pet]
  (fetch :nickname pet))

(defn pet-nickname []
  (-> (current-user)
      user-pet
      user-pet-nickname
      m/extract))

; (pet-nickname)
; => ("Ada" nil nil "Ada" nil "Ada" nil nil "Ada" "Ada")
;
; Do compare the final result both with cats.pet-nickname-or-nil/pet-nickname-in-traditional-languages
; but also pet-nickname-in-clojure. Note how cats adds a lot of complexity in all steps when considering
; Clojure's best solution.
;
; Next step: let's replace nil with some error info in
; cats.pet-nickname-or-either.clj

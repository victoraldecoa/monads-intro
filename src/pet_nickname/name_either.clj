(ns pet-nickname.name-either
  (:require [cats.monad.either :as either]
            [cats.core :as m]))

(defn fetch
  [key data]
  (cond
    (< (rand) 0.7) (either/right (key data))
    (< (rand) 0.85) (either/left {:code :404 :data data})
    :else (either/left {:code :422 :data data})))

(defn current-user
  []
  (either/right {:name "Vic"
                 :pet  {:nickname "Ada"}}))

(defn user-pet
  [user]
  (fetch :pet user))

(defn user-pet-nickname
  [pet]
  (fetch :nickname pet))

(defn pet-nickname []
  @(m/mlet [user (current-user)
            pet (user-pet user)
            name (user-pet-nickname pet)]
     (m/return name)))

(repeatedly 10 pet-nickname)

; Next step: let's see how we can solve this the Clojure way, without cats, in
; cats.pet-nickname-or-errors.clj

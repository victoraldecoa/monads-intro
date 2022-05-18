(ns cats.pet-nickname-or-either
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
  (either/branch-right
    user
    #(fetch :pet %)))

(defn user-pet-nickname
  [pet]
  (either/branch-right
    pet
    #(fetch :nickname %)))

(defn pet-nickname []
  (-> (current-user)
      user-pet
      user-pet-nickname
      m/extract))

; Next step: let's see how we can solve this the Clojure way, without cats, in
; cats.pet-nickname-or-errors.clj

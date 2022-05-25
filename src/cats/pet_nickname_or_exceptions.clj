(ns cats.pet-nickname-or-exceptions
  (:import (clojure.lang ExceptionInfo)))


(defn fetch
  [key data]
  (cond
    (< (rand) 0.7) (key data)
    (< (rand) 0.85) (throw (ex-info "fetch error 404" {:code 404 :data data}))
    :else (throw (ex-info "fetch error 422" {:code 422 :data data}))))

(defn current-user
  []
  {:name "Vic"
   :pet  {:nickname "Ada"}})

(defn user-pet
  [user]
  (fetch :pet user))

(defn user-pet-nickname
  [pet]
  (fetch :nickname pet))

(defn pet-nickname []
  (try
    (-> (current-user)
        user-pet
        user-pet-nickname)
    (catch ExceptionInfo e
      (ex-data e))))

(repeatedly 10 pet-nickname)

; Note: if you change `fetch` to always return the correct data, the code in this example continues to just work.
; If you do that in the Maybe or the Either examples, it breaks all the users of pet-nickname, unless you continue
; to return maybe/either forever. You can't stop returning them in a retro-compatible way.
; See [Maybe Not](https://www.youtube.com/watch?v=YR5WdGrpoug) for more.

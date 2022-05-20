(ns cats.pet-nickname-or-errors)

(defn fetch
  [k data]
  (cond
    (< (rand) 0.7) (k data)
    (< (rand) 0.85) {:code 404 :data data}
    :else {:code 422 :data data}))

(defn error
  [{:keys [code] :or {code 200}}]
  (>= code 400))

(defn current-user
  []
  {:name "Vic"
   :pet  {:nickname "Ada"}})

(defn user-pet
  [user]
  (if (error user)
    user
    (fetch :pet user)))

(defn user-pet-nickname
  [pet]
  (if (error pet)
    pet
    (fetch :nickname pet)))

(defn pet-nickname []
  (-> (current-user)
      user-pet
      user-pet-nickname))

(repeatedly 10 pet-nickname)

; Note: if you change `fetch` to always return the correct data, the code in this example continues to just work.
; If you do that in the Maybe or the Either examples, it breaks all the users of pet-nickname, unless you continue
; to return maybe/either forever. You can't stop returning them in a retro-compatible way.
; See [Maybe Not](https://www.youtube.com/watch?v=YR5WdGrpoug) for more.

(ns cats.pet-nickname-or-nil)

(defn fetch
  [x]
  {:pre [(some? x)]}
  (if (< (rand) 0.8)
    x
    nil))

(defn current-user
  []
  (let [user {:name "Vic"
              :pet  {:nickname "Ada"}}]
    (fetch user)))

(defn user-pet
  [{:keys [pet]}]
  (fetch pet))

(defn user-pet-nickname
  [{:keys [nickname]}]
  (fetch nickname))

(defn pet-nickname-in-traditional-languages []
  (let [user (current-user)]
    (when user
      (let [pet (user-pet user)]
        (when pet
          (let [nickname (user-pet-nickname pet)]
            nickname))))))

; (repeatedly 10 pet-nickname-in-traditional-languages)
; => ("Ada" nil nil "Ada" nil "Ada" nil nil "Ada" "Ada")

(defn pet-nickname-in-clojure
  "If you cannot remove the nil-check on `fetch`, you can still use some->"
  []
  (some-> (current-user)
          user-pet
          user-pet-nickname))

; (repeatedly 10 pet-nickname-in-clojure)
; => ("Ada" nil nil "Ada" nil "Ada" nil nil "Ada" "Ada")

(defn pet-nickname-naive
  "This throws null-ref. If we didn't nil-check on `fetch`, naive works perfectly"
  []
  (-> (current-user)
      user-pet
      user-pet-nickname))

; Next step: cats.pet-nickname-or-maybe.clj, which solves it using maybe.

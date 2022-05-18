(ns number-with-logs.step-4)

; extra-step - run-with-logs a lot uglier for clearer run

(defn square
  [x]
  {:result (* x x)
   :logs   [(str "Squared " x " to get " (* x x))]})

(defn add-one
  [x]
  {:result (inc x)
   :logs   [(str "Added 1 to " x " to get " (inc x))]})

(defn run-with-logs
  [{x :result logs :logs} & fs]
  (let [{x* :result logs* :logs} ((first fs) x)
        result-with-logs {:result x*
                          :logs   (concat logs logs*)}
        fs* (rest fs)]
    (if (seq fs*)
      (recur result-with-logs fs*)
      result-with-logs)))

(defn wrap-with-logs
  [x]
  {:result x :logs []})

(defn run []
  (-> 2
      wrap-with-logs
      (run-with-logs
        square
        add-one)))

; Next up: cats.pet-nickname-or-nil.clj

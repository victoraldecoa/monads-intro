(ns number-with-logs.step-3)

(defn square
  [x]
  {:result (* x x)
   :logs   [(str "Squared " x " to get " (* x x))]})

(defn add-one
  [x]
  {:result (inc x)
   :logs   [(str "Added 1 to " x " to get " (inc x))]})

(defn run-with-logs
  [{x :result logs :logs} f]
  (let [{x* :result logs* :logs} (f x)]
    {:result x* :logs (concat logs logs*)}))

(defn wrap-with-logs
  [x]
  {:result x :logs []})

(defn run []
  (-> 2
      wrap-with-logs
      (run-with-logs square)
      (run-with-logs add-one)))

; You can go to step-4 (extra step) or skip to cats.pet-nickname-or-nil.clj
; if you're tired of this example

(ns number-with-logs.step-2)

(defn square
  [x]
  {:result (* x x)
   :logs   [(str "Squared " x " to get " (* x x))]})

(defn add-one
  [{x :result logs :logs}]
  {:result (inc x)
   :logs   (concat logs [(str "Added 1 to " x " to get " (inc x))])})

(defn run []
  (-> 2
      square
      add-one))

; Problems:
; you can't invert calls
; you can't call (add-one 2)
;
; One solution:
; both could receive the wrapped arg and concat logs, but that would duplicate code

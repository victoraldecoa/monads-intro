(ns number-with-logs.step-1)

; Start here!
; example from video https://www.youtube.com/watch?v=C2w45qRc3aU

(defn square
  [x]
  (* x x))

(defn add-one
  [x]
  (inc x))

(defn run []
  (-> 2
      square
      add-one))

; (run) => 5
;
; * Task:
; Improve it to return the following:
; (run) => {:result 5, :logs ("Squared 2 to get 4" "Added 1 to 4 to get 5")}

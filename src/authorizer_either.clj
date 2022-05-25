(ns authorizer-either
  (:require [cats.core :as m]
            [cats.monad.either :as either]))

(defn- either-coll
  "Transforms a coll of eithers to an either of coll.
  If there is at least one left in the original coll,
  the result will be a left of the left values. Otherwise,
  it will be a right of the values."
  [coll]
  (if (every? (comp not either/left?) coll)
    (either/right (map m/extract coll))
    (either/left (map m/extract (either/lefts coll)))))

(defn authorize*
  [account purchase rules]
  (let [context {:account account :purchase purchase}]
    (either/branch (either-coll (map #(% context) rules))
                   (fn [r] {:denied-reasons r
                            :approved       false
                            :new-limit      (:limit account)})
                   (fn [_] {:approved  true
                            :new-limit (- (:limit account) (:amount purchase))}))))

(defn enough-limit
  [{:keys [account purchase]}]
  (when-not (<= (:amount purchase) (:limit account))
    (either/left :enough-limit)))

(defn valid-card-status
  [{:keys [account]}]
  (when-not (= (:card-status account) :active)
    (either/left :valid-card-status)))

(defn authorize
  [account transaction]
  (authorize* account
              transaction
              [enough-limit
               valid-card-status]))

(authorize {:limit 500 :card-status :active}
           {:amount 150})
; => {:approved true, :new-limit 350}

(authorize {:limit 500 :card-status :inactive}
           {:amount 650})
; => {:denied-reasons [:enough-limit :valid-card-status], :approved false, :new-limit 500}

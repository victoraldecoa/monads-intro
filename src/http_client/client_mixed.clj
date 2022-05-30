(ns http-client.client-mixed
  (:require [cats.monad.either :as either]
            [fmnoise.flow :refer [else flet]]))

(defn success [] (< (rand) 0.8))

(defn get-auth
  [email]
  (if (and email (success))
    {:token "abcdef"}
    (throw (ex-info "auth" {:error "auth error"}))))

(defn get-acc
  [token]
  {:pre [token]}
  (if (success)
    (either/right {:id (random-uuid) :limit 256 :card-status :active})
    (either/left {:error "acc error"})))

(defn get-loans
  [token acc-id]
  {:pre [token acc-id]}
  (if (success)
    [{:amount 10000 :desc "comprinhas"}
     {:amount 5000 :desc "viagem"}]
    (ex-info "loans" {:error "loans error"})))

(defn get-info []
  (->> (flet [{:keys [token] :as auth} (get-auth "hi@either.com")
              {acc-id :id :as acc} (get-acc token)
              loans (get-loans token acc-id)]
         {:auth    auth
          :account acc
          :loans   loans})
       (else ex-data)))

(repeatedly 10 get-info)

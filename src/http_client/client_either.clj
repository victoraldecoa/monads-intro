(ns http-client.client-either
  (:require [cats.core :as m]
            [cats.monad.either :as either]))

(defn success [] (< (rand) 0.8))

(defn auth
  [email]
  (if (and email (success))
    (either/right {:token "abcdef"})
    (either/left {:error "auth error"})))

(defn acc
  [token]
  {:pre [token]}
  (if (success)
    (either/right {:id (random-uuid) :limit 256 :card-status :active})
    (either/left {:error "acc error"})))

(defn loans
  [token acc-id]
  {:pre [token acc-id]}
  (if (success)
    (either/right [{:amount 10000 :desc "comprinhas"} {:amount 5000 :desc "viagem"}])
    (either/left {:error "loans error"})))

(defn get-info []
  @(m/mlet [{:keys [token] :as auth} (auth "hi@either.com")
            {acc-id :id :as acc} (acc token)
            loans (loans token acc-id)]
     (m/return {:auth    auth
                :account acc
                :loans   loans})))

(repeatedly 10 get-info)

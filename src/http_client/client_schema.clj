(ns http-client.client-schema
  (:require [cats.monad.either :as either]
            [fmnoise.flow :refer [else flet]]
            [schema.core :as s]
            [utils.either-schema :refer [Either]])
  (:import (clojure.lang ExceptionInfo)))

(s/defn success :- s/Bool
  []
  (< (rand) 0.8))

(s/defn get-auth :- {:token s/Str}
  "Throws clojure.lang.ExceptionInfo exception when email is nil
  or when the request fails"
  [email :- s/Str]
  (if (and email (success))
    {:token "abcdef"}
    (throw (ex-info "auth" {:error "auth error"}))))

(def Account
  {:id          s/Uuid
   :limit       s/Num
   :card-status s/Keyword})

(s/defn get-acc :- (Either {:error s/Str} Account)
  [token :- s/Str]
  {:pre [token]}
  (if (success)
    (either/right {:id (random-uuid) :limit 256 :card-status :active})
    (either/left {:error "acc error"})))

(def Loan
  {:amount s/Num
   :desc   s/Str})

(s/defn get-loans :- (s/conditional vector? [Loan] :else ExceptionInfo)
  [acc-id :- s/Uuid
   token :- s/Str]
  {:pre [acc-id token]}
  (if (success)
    [{:amount 10000 :desc "comprinhas"}
     {:amount 5000 :desc "viagem"}]
    (ex-info "loans" {:error "loans error"})))

(s/defn get-info []
  (->> (flet [{:keys [token] :as auth} (get-auth "hi@either.com")
              {acc-id :id :as acc} (get-acc token)
              loans (get-loans acc-id token)]
         {:auth    auth
          :account acc
          :loans   loans})
       (else ex-data)))

(repeatedly 10 get-info)

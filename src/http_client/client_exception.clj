(ns http-client.client-exception
  (:import (clojure.lang ExceptionInfo)))

(defn success [] (< (rand) 0.8))
(defn throw-data [data]
  (throw (ex-info "client-exception" data)))

(defn auth
  [email]
  (if (and email (success))
    {:token "abcdef"}
    (throw-data {:error "auth error"})))

(defn acc
  [token]
  {:pre [token]}
  (if (success)
    {:id (random-uuid) :limit 256 :card-status :active}
    (throw-data {:error "acc error"})))

(defn loans
  [token acc-id]
  {:pre [token acc-id]}
  (if (success)
    [{:amount 10000 :desc "comprinhas"} {:amount 5000 :desc "viagem"}]
    (throw-data {:error "loans error"})))

(defn get-info []
  (try
    (let [{:keys [token] :as auth} (auth "hi@either.com")
          {acc-id :id :as acc} (acc token)
          loans (loans token acc-id)]
      {:auth    auth
       :account acc
       :loans   loans})
    (catch ExceptionInfo e
      (ex-data e))))

(repeatedly 10 get-info)

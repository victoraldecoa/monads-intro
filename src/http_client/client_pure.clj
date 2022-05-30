(ns http-client.client-pure)

(defn success [] (< (rand) 0.8))

(defn run-if-not-err
  [err-pred x & fs]
  (if (and (not (err-pred x)) (seq fs))
    (recur err-pred ((first fs)) (rest fs))
    x))

(def r (partial run-if-not-err #(:error %)))

(defn get-auth
  [email]
  (if (and email (success))
    {:token "abcdef"}
    {:error "auth error"}))

(defn get-acc
  [token]
  {:pre [token]}
  (if (success)
    {:id (random-uuid) :limit 256 :card-status :active}
    {:error "acc error"}))

(defn get-loans
  [token acc-id]
  {:pre [token acc-id]}
  (if (success)
    [{:amount 10000 :desc "comprinhas"} {:amount 5000 :desc "viagem"}]
    {:error "loans error"}))

(defn get-info []
  (let [{:keys [token] :as auth} (get-auth "hi@either.com")
        {acc-id :id :as acc} (r auth (partial get-acc token))
        loans (r acc (partial get-loans token acc-id))]
    {:auth    auth
     :account acc
     :loans   loans}))

(repeatedly 10 get-info)

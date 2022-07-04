(ns client-test
  (:require [clojure.test :refer :all]
            [http-client.client-schema :as client-schema]
            [schema.test :as st]))

(st/deftest get-auth-tests
  (with-redefs [client-schema/success (constantly true)]
    (is (= {:token "abcdef"} (client-schema/get-auth "a@a.com")))))

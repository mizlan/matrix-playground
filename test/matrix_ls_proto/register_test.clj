(ns matrix-ls-proto.register-test
  (:require [matrix-ls-proto.register :as reg]
            [matrix-ls-proto.core :as core]
            [clojure.test :refer :all]))

(deftest access-test
  (testing "Get an access token"
    (is (some? (reg/process-register)))))

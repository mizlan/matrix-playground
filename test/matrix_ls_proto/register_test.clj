(ns matrix-ls-proto.register-test
  (:require [matrix-ls-proto.register :as sut]
            [matrix-ls-proto.core :as core]
            [clojure.test :refer :all]))

(deftest access-test
  (testing "Get an access token"
    (is (some? (sut/process-register)))))

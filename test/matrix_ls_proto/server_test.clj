(ns matrix-ls-proto.server-test
  (:require [matrix-ls-proto.server :as sut]
            [clojure.test :refer :all]))

(deftest dumb-test
  (testing "can we find a server"
    (is (some? sut/server))))

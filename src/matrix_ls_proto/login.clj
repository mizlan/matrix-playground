(ns matrix-ls-proto.login
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(def login-ext "/client/r0/login")

;; NOTE: First we query the URL to get the supported login types.
;;       Hopefully, `m.login.password` is one of these. Then we will proceed

(defn query-supported-types
  [server]
  (let [link (str server login-ext)]
    (parse-string
     (:body
      (client/get link
                  {:insecure? false
                   :accept :json})))))
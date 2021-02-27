(ns matrix-ls-proto.login
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [matrix-ls-proto.server :refer [http-server]]))

(def login-ext "/client/r0/login")

;; NOTE: First we query the URL to get the supported login types.
;;       Hopefully, `m.login.password` is one of these. Then we will proceed

(defn query-supported-types
  "Use the default configured http-server by default"
  ([] (query-supported-types http-server))
  ([server]
   (let [link (str server login-ext)
         opts {:insecure? false
               :accept :json}]
     (-> link
         (client/get opts)
         :body
         parse-string))))

(defn handle-query-supported-types
  [json-response]
  (when-let [flows (json-response "flows")]
    (map #(get % "type") flows)))

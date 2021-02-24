(ns matrix-ls-proto.register
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(def reg-ext "/client/r0/register")

(defn attempt-register
  "Given a server and username+password combination, attempt to register."
  [server {:keys [username password]}]
  (let [link (str server reg-ext)]
    (parse-string
     (:body (client/post link
                         {:insecure? false
                          :form-params
                          {:username username
                           :password password
                           :auth {:type "m.login.dummy"}}
                          :content-type :json
                          :accept :json
                          :throw-exceptions false})))))

(defn test-register
  "Use the HTTP server"
  []
  (attempt-register matrix-ls-proto.core/server {:username "justtesting" :password "superdupergood"}))

(defn process-register
  []
  (let [resp (test-register)]
    (if-let [msg (resp "error")]
      (do
        (println msg)
        nil)
      (do
        (println (str "Your access token is `" (resp "access_token") "`"))
        (resp "access_token")))))

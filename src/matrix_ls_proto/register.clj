(ns matrix-ls-proto.register
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [matrix-ls-proto.server :refer [http-server]]))

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
  (attempt-register http-server {:username "justtesting" :password "superdupergood"}))

;; TODO: use a tuple instead of printing to stdout
(defn process-register
  "Either returns nil or access token. Will print a message to stdout.
  May return an access token, or nil."
  []
  (let [resp (test-register)]
    (if-let [msg (resp "error")]
      (do
        (println msg)
        nil)
      (do
        (println resp)
        (println (str "Your access token is `" (resp "access_token") "`"))
        (resp "access_token")))))

(ns matrix-ls-proto.rooms
  (:require [clj-http.client :as client]
            [matrix-ls-proto.server :refer [http-server]]
            [matrix-ls-proto.register :as reg]
            [cheshire.core :refer :all]))

(def room-ext "/client/r0/createRoom")

(defn query-create-room
  [server room-name access-token]
  (let [link (str server room-ext)
        form-params {"room_alias_name" room-name}
        query-params {"access_token" access-token}
        opts {:form-params form-params
              :query-params query-params
              :content-type :json
              :accept :json
              :throw-exceptions? false}]
    (-> link
        (client/post opts)
        :body
        p)))

(defn handle-query-create-room
  [json-response]
  (cond
    (json-response "message") (json-response "message")
    (json-response "room_id") json-response
    :else nil
    ))

(defn test-create-room
  [room-name]
  (let [access-token (reg/process-register)]
    (query-create-room server room-name access-token)))

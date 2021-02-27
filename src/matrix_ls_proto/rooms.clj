(ns matrix-ls-proto.rooms
  (:require [clj-http.client :as client]
            [matrix-ls-proto.server :refer [server]]
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
              :accept :json
              :content-type :json
              :throw-exceptions? false}]
    (parse-string (:body (client/post link opts)))))


  (defn test-create-room
    []
    (let [access-token (reg/process-register)]
      (query-create-room server "clj-room" access-token)))

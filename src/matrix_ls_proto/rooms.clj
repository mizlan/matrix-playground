(ns matrix-ls-proto.rooms
  (:require [clj-http.client :as client]
            [matrix-ls-proto.server :refer [server]]
            [matrix-ls-proto.register :as reg]))

(def room-ext "/client/r0/createRoom")

(defn create-room
  [server room-name access-token]
  (let [link (str server room-ext)
        form-params {"room_alias_name" room-name}
        query-params {"access_token" access-token}]
    (:body (client/post link
                        :form-params form-params
                        :query-params query-params
                        :accept :json))))

(defn test-create-room
  []
  (let [access-token (reg/process-register)]
    (create-room server "clj-room" access-token)))

(ns matrix-ls-proto.rooms
  (:require [clj-http.client :as client]))

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
  (let [server matrix-ls-proto.server/server
        access-token (matrix-ls-proto.register/process-register)]
    (create-room server "clj-room")))

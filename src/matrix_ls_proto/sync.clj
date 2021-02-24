(ns matrix-ls-proto.sync
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(def sync-ext "/client/r0/sync")

(defn attempt-sync
  [server access-token]
  (let [link (str server sync-ext)]
    (parse-string
     (:body (client/get link
                        {:insecure? false
                         :query-params {"access_token" access-token}
                         :accept :json})))))

(defn syncify
  []
  (attempt-sync matrix-ls-proto.core/server (matrix-ls-proto.register/process-register)))

(ns matrix-ls-proto.sync
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(def sync-ext "/client/r0/sync")

(defn inspect [events]
  (for [event events]
    (println event)))

                                        ; TODO: Results can be huge, stream the JSON
(defn attempt-sync
  ([server access-token]
   (let [link (str server sync-ext)
         resp (client/get link
                          {:headers {"Authorization" (format "Bearer %s" access-token)}
                           :accept :json
                           :as :reader})]
     (with-open [reader (:body resp)]
       (let [events (json/parse-stream reader false)]
         events))))
  ([server access-token batch-token]
   (let [link (str server sync-ext)
         resp (client/get link
                          {:headers {"Authorization" (format "Bearer %s" access-token)}
                           :accept :json
                           :as :reader
                           :query-params {:since batch-token}})]
     (with-open [reader (:body resp)]
       (let [events (json/parse-stream reader false)]
         events
         )))))

(defn attempt-sync-old
  [server access-token]
  (let [link (str server sync-ext)]
    (-> 
     (client/get link
                 {:headers {"Authorization" (format "Bearer %s" access-token)}
                  :accept :json})
     :body
     json/parse-string)))

(defn syncify
  []
  (attempt-sync
   matrix-ls-proto.server/http-server
   (matrix-ls-proto.register/process-register)))


(ns matrix-ls-proto.core
  (:gen-class)
  (:require [matrix-ls-proto.server :as s]
            [matrix-ls-proto.login :as login]
            [matrix-ls-proto.sync :as sync]
            [cheshire.core :as json]
            [portal.api :as p]
            [clj-http.client :as client]
            [matrix-ls-proto.secrets :as secrets]))

(defn -main
  [& args]
  (println "Hello, World!"))

(def ^:dynamic *server* "https://matrix.org/_matrix")

(defn only [m]
  (let [ks (keys m)]
    (assert (= 1 (count ks)))
    (m (first ks))))

(defn get-access-token []
  (-> (login/attempt-login *server* {:username "mizlan_t"
                                     :password secrets/pw})
      :body
      json/parse-string
      (get "access_token")))

(defn format-message [msg]
  (format "[%s]: %s"
          (get-display-name (get msg "sender"))
          (get-in msg ["content" "body"])))

(def get-display-name
  (memoize
   (fn [user-id]
     (-> (client/get (str *server* "/client/r0/profile/" user-id "/displayname"))
         :body
         (json/parse-string keyword)
         :displayname))))

;; (defn all-events

(comment
  (p/open)
  (p/clear)
  (p/close)
  (add-tap #'p/submit)
  (def a (get-access-token))
  (def info (sync/attempt-sync *server* a))
  (def tl (-> info
              (get-in ["rooms" "join"])
              only
              (get-in ["timeline" "events"])))
  (def msgs (filter #(= "m.room.message" (get-in % ["type"])) tl))
  (def msg-texts (map format-message msgs))
  (tap> msg-texts)
  (tap> info)
  (def nb (info "next_batch"))
  (def nnb (next-info "next_batch"))
  (tap> nb)
  (def next-info (sync/attempt-sync *server* a nb))
  (def next-next-info (sync/attempt-sync *server* a nnb))
  (tap> next-info)
  (tap> next-next-info)
  )

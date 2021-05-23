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

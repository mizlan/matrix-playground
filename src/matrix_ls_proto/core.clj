(ns matrix-ls-proto.core
  (:gen-class))

(def server "http://localhost:8008/_matrix")
(def https-server "https://localhost:8448/_matrix")

(defn -main
  [& args]
  (println "Hello, World!"))

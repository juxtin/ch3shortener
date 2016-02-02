(ns ch3shortener.application
  (:require [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ch3shortener.storage.in-memory :refer [in-memory-storage]]
            [ch3shortener.routes :as routes]))

(def app
  (let [stg (in-memory-storage)
        app-routes (routes/shortener-routes stg)]
    (wrap-defaults app-routes api-defaults)))

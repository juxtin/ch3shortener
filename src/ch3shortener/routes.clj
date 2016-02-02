(ns ch3shortener.routes
  (:require [ch3shortener.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ch3shortener.middleware :as mw]))

(defn shortener-routes
  [stg]
  (routes
   (route/not-found "Not Found")))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

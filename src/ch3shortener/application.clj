(ns ch3shortener.application
  (:require [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ch3shortener.routes :as routes]))

(def app
  (wrap-defaults routes/app-routes api-defaults))

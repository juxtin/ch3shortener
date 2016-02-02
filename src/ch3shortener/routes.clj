(ns ch3shortener.routes
  (:require [ch3shortener.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ch3shortener.middleware :as mw]))

(defn shortener-routes
  [stg]
  (-> (routes
       (POST "/links/:id" [id :as request] (handler/create-link stg id request))
       (PUT "/links/:id" [id :as request] (handler/update-link stg id request))
       (GET "/links/:id" [id] (handler/get-link stg id))
       (DELETE "/links/:id" [id] (handler/delete-link stg id))
       (route/not-found "Not Found"))
    (wrap-routes mw/wrap-slurp-body)))

(ns ch3shortener.handler
  (:require [ring.util.request :as req]
            [ring.util.response :as res]
            [ch3shortener.storage :as st]))

(defn get-link
  [stg id]
  (if-let [url (st/get-link stg id)]
    (res/redirect url)
    (res/not-found "Sorry, that link doesn't exist.")))

(defn create-link
  [stg id {url :body}]
  (if (st/create-link stg id url)
    (res/response (str "/links/" id))
    (-> (format "The id %s is already in use." id)
      res/response
      (res/status 422))))

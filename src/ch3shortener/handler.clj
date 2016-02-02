(ns ch3shortener.handler
  (:require [ring.util.request :as req]
            [ring.util.response :as res]
            [ch3shortener.storage :as st]))

(defn get-link
  [stg id]
  (if-let [url (st/get-link stg id)]
    (res/redirect url)
    (res/not-found "Sorry, that link doesn't exist.")))

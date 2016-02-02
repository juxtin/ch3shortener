(ns ch3shortener.middleware
  (:import [java.io InputStream]))

(defn wrap-slurp-body
  [handler]
  (fn [request]
    (if (instance? InputStream (:body request))
      (let [prepared-request (update request :body slurp)]
        (handler prepared-request))
      (handler request))))

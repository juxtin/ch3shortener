(ns ch3shortener.application-test
  (:require [ch3shortener.application :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

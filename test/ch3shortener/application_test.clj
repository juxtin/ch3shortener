(ns ch3shortener.application-test
  (:require [ch3shortener.application :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (let [url "http://example.com/post"
        id "test"]
    (testing "creating a link"
      (let [response (app (mock/request :post (str "/links/" id) url))]
        (is (= 200 (:status response)))
        (is (= (str "/links/" id) (:body response))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

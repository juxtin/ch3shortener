(ns ch3shortener.application-test
  (:require [ch3shortener.application :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (let [url "http://example.com/post"
        id "test"
        path (str "/links/" id)]
    (testing "creating a link"
      (let [response (app (mock/request :post path url))]
        (is (= 200 (:status response)))
        (is (= path (:body response)))))

    (testing "visiting a link"
      (testing "when the link exists"
        (let [response (app (mock/request :get path))]
          (testing "returns a 302"
            (is (= 302 (:status response)))

            (testing "with the correct location"
              (is (= url (get-in response [:headers "Location"])))))))

      (testing "when the link does not exist"
        (let [response (app (mock/request :get "/links/nothing"))]
          (testing "returns a 404"
            (is (= 404 (:status response))))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest link-updating
  (let [id "put"
        url "http://example.com/putTest"
        path (str "/links/" id)]

    (testing "when the link does not exist"
      (let [response (app (mock/request :put path url))]
        (testing "the response is a 404"
          (is (= 404 (:status response))))))

    (testing "when the link does exist"
      (app (mock/request :post path "http://example.post"))
      (let [response (app (mock/request :put path url))]
        (testing "the response is a 200"
          (is (= 200 (:status response))))))))

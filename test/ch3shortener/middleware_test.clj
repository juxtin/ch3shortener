(ns ch3shortener.middleware-test
  (:require [ch3shortener.middleware :refer :all]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]))

(deftest wrap-slurp-body-test
  (let [body "This is a body."
        request (mock/request :post "/foo" body)
        expected-request (assoc request :body body)
        identity-handler (wrap-slurp-body identity)]

    (testing "when given a request with an InputStream body"
      (let [prepared-request (identity-handler request)]
        (testing "the body is turned into a string"
          (is (= body (:body prepared-request)))

          (testing "and the rest of the request is unchanged"
            (is (= expected-request prepared-request))))))

    (testing "when given a request that has no body"
      (let [no-body (mock/request :get "/")]
        (testing "there's no effect"
          (is (= no-body (identity-handler no-body))))))

    (testing "applying the middleware a second time has no effect"
      (let [request (mock/request :post "/foo" body)]
        (is (= expected-request
               (-> request
                 identity-handler
                 identity-handler)))))))

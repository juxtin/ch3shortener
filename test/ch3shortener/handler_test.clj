(ns ch3shortener.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [ch3shortener.handler :refer :all]
            ;; add the following two requirements
            [ch3shortener.storage.in-memory :refer [in-memory-storage]]
            [ch3shortener.storage :as st]))

(deftest get-link-test
  (let [stg (in-memory-storage)
        id "test"
        url "http://test.gov"]
    ;; store a link directly for the test
    (st/create-link stg id url)

    (testing "when the ID exists"
      (let [response (get-link stg id)]
        (testing "the result is a 302"
          (is (= 302 (:status response)))
          (testing "with the expected URL in the Location header"
            (is (= url (get-in response [:headers "Location"])))))))

    (testing "when the ID does not exist"
      (let [response (get-link stg "bogus")]
        (testing "the result is a 404"
          (is (= 404 (:status response))))))))

(deftest create-link-test
  (let [stg (in-memory-storage)
        url "http://example.com"
        request (-> (mock/request :post "/links/test" url)
                  ;; since we haven't added middleware yet
                  (update :body slurp))]
    (testing "when the ID does not exist"
      (let [response (create-link stg "test" request)]
        (testing "the result is a 200"
          (is (= 200 (:status response)))

          (testing "with the expected body"
            (is (= "/links/test" (:body response))))

          (testing "and the link is actually created"
            (is (= url (st/get-link stg "test")))))))

    (testing "when the ID does exist"
      (let [response (create-link stg "test" request)]
        (testing "the result is a 422"
          (is (= 422 (:status response))))))))

(deftest update-link-test
  (let [stg (in-memory-storage)
        url "http://example.com"
        request (-> (mock/request :put "/links/test" url)
                  ;; since we haven't added middleware yet
                  (update :body slurp))]
    (testing "when the ID does not exist"
      (let [response (update-link stg "test" request)]
        (testing "the result is a 404"
          (is (= 404 (:status response))))))

    (testing "when the ID does exist"
      (st/create-link stg "test" url)
      (let [new-url "http://example.gov"
            request (assoc request :body new-url)
            response (update-link stg "test" request)]
        (testing "the result is a 200"
          (is (= 200 (:status response)))

          (testing "with the expected body"
            (is (= "/links/test" (:body response))))

          (testing "and the link is actually updated"
            (is (= new-url (st/get-link stg "test")))))))))

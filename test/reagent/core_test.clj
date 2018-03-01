(ns reagent.core-test
  (:require [clojure.test :refer :all]
            [reagent.core :refer :all]))

(deftest cursor-test
  (let [a (atom {:foo {:bar {:baz :qux}}})]
    (testing "creating cursor by path X should return atom with value held by path X"
      (is (= :qux (deref (cursor a [:foo :bar :baz])))))
    (testing "cursor on inexisting path should return atom with nil"
      (is (= nil (deref (cursor a [:foo :bar :fox])))))
    (testing "cursor on empty path should return same atom"
      (is (= @a (deref (cursor a [])))))))

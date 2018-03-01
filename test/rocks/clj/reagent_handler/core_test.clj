(ns rocks.clj.reagent-handler.core-test
  (:require [clojure.test :refer :all]
            [rocks.clj.reagent-handler.core :refer :all]))

(deftest normalize-test
  (testing "component with opts map shouldn't be modified"
    (is (= [:div {:id "test"}] (normalize [:div {:id "test"}]))))
  (testing "component without opts map should have opts map"
    (is (= [:div {}] (normalize [:div])))))

(ns bike-gear-calc.core-test
  (:require [bike-gear-calc.core :as gc]
            [clojure.test :refer [deftest is testing run-tests]]
            #_[cljs.test :refer-macros [deftest is testing run-tests]]))

(def wheel-dia 685.8)

(deftest gear-inches-test
  (is (= (gc/gear-inches 42 18 685.8) 63.00))
  (is (= (gc/gear-inches 52 18 685.8) (float 78)))
  (is (= (gc/gear-inches 39 22 685.8) (float 47.86363636363636))))

(deftest meters-dev-test
  (is (= (gc/meters-of-development 42 18 685.8) (float 5.0271764)))
  (is (= (gc/meters-of-development 52 18 685.8) (float 6.2241235)))
  (is (= (gc/meters-of-development 39 22 685.8) (float 3.8193483))))

(deftest gain-ratio-test
  (is (= (gc/gain-ratio 42 18 685.8 170) (float 4.7064705)))
  (is (= (gc/gain-ratio 52 18 685.8 170) (float 5.827059)))
  (is (= (gc/gain-ratio 39 22 685.8 170) (float 3.5756953))))

(deftest ratio-filter-test
  (is (= (gc/ratio-filter 42 18 (/ 42 18) (/ 42 18)) true))
  (is (= (gc/ratio-filter 42 18 (/ 42 18) (* 0.02 (/ 42 18))) true))
  (is (= (gc/ratio-filter 40 17 (/ 42 18) (* 0.02 (/ 42 18))) true))
  (is (= (gc/ratio-filter 44 19 (/ 42 18) (* 0.02 (/ 42 18))) true))
  (is (= (gc/ratio-filter 41 18 (/ 42 18) (* 0.01 (/ 42 18))) false))
  (is (= (gc/ratio-filter 41 19 (/ 42 18) (* 0.02 (/ 42 18))) false))
  (is (= (gc/ratio-filter 52 18 (/ 42 18) (* 0.2 (/ 42 18))) false)))

(deftest create-gears-test
  (is (= (take 5 (gc/create-gear-combinations))
         '([28 9] [28 10] [28 11] [28 12] [28 13])))
  (is (= (count (gc/create-gear-combinations)) 528)))

(deftest close-gears-test
  (is (= (count (gc/close-gears 42 18)) 21))
  (is (= (take 4 (gc/close-gears 42 18))
         '([28 12] [30 13] [33 14] [35 15]))))

(ns bike-gear-calc.core-test
  (:require [bike-gear-calc.core :as gc]
            [bike-gear-calc.data :as d]
            [clojure.test :refer [deftest is testing run-tests]]
            #_[cljs.test :refer-macros [deftest is testing run-tests]]))

(def wheel-dia 685.8)
(def bike1 (gc/any-bike {:wheel-dia wheel-dia}))
(def bike2 (gc/any-bike {:ring 52 :wheel-dia wheel-dia}))
(def bike3 (gc/any-bike {:ring 39 :sprocket 22 :wheel-dia wheel-dia}))

(deftest gear-inches-test
  (is (= (gc/gear-inches bike1) 63.00))
  (is (= (gc/gear-inches bike2) (float 78)))
  (is (= (gc/gear-inches bike3) (float 47.86363636363636))))

(deftest meters-dev-test
  (is (= (gc/meters-of-development bike1) (float 5.0271764)))
  (is (= (gc/meters-of-development bike2) (float 6.2241235)))
  (is (= (gc/meters-of-development bike3) (float 3.8193483))))

(deftest gain-ratio-test
  (is (= (gc/gain-ratio bike1) (float 4.7064705)))
  (is (= (gc/gain-ratio bike2) (float 5.827059)))
  (is (= (gc/gain-ratio bike3) (float 3.5756953))))

(deftest rpm->speed-test
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike1))
       (float 27.146752452850343)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike2))
       (float 33.61026678085327)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike3))
       (float 20.624481010437012))))

(deftest rpm->speed-mph-test
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike1)
                      true)
       (float 16.868992)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike2)
                      true)
       (float 20.88542)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development bike3)
                      true)
       (float 12.816052))))

(deftest calc-rpm-speeds-test
  (let [md (gc/meters-of-development bike1)]
    (is (= (count (gc/calc-rpm-speeds md)) 10))
    (is (=  (gc/calc-rpm-speeds md (:mph bike1))
            '([50 15.08153]
              [60 18.097836]
              [70 21.114141]
              [80 24.130447]
              [90 27.146753]
              [100 30.16306]
              [110 33.179363]
              [120 36.19567]
              [130 39.211975]
              [140 42.228283])))))


(deftest sprocket-gear-map-test
  (is (= (gc/sprocket-gear-map bike1)
         {:gear-inches 63.0,
          :meters-dev 5.0271764,
          :gain-ratio 4.7064705,
          :speeds '([50 15.08153]
                    [60 18.097836]
                    [70 21.114141]
                    [80 24.130447]
                    [90 27.146753]
                    [100 30.16306]
                    [110 33.179363]
                    [120 36.19567]
                    [130 39.211975]
                    [140 42.228283])}))
  (is (= (gc/sprocket-gear-map bike2)
         {:gear-inches 78.0,
          :meters-dev 6.2241235,
          :gain-ratio 5.827059,
          :speeds '([50 18.67237]
                    [60 22.406845]
                    [70 26.14132]
                    [80 29.875793]
                    [90 33.610268]
                    [100 37.34474]
                    [110 41.079216]
                    [120 44.81369]
                    [130 48.548164]
                    [140 52.28264])}))
  (is (= (gc/sprocket-gear-map bike3)
         {:gear-inches 47.863636,
          :meters-dev 3.8193483,
          :gain-ratio 3.5756953,
          :speeds '([50 11.458045]
                    [60 13.749654]
                    [70 16.041264]
                    [80 18.332872]
                    [90 20.624481]
                    [100 22.91609]
                    [110 25.207699]
                    [120 27.499308]
                    [130 29.790916]
                    [140 32.082527])})))

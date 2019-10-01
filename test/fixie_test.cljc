(ns bike-gear-calc.fixie-test
  (:require [bike-gear-calc.core :as gc]
            [bike-gear-calc.fixie :as f]
            [bike-gear-calc.data :as d]
            [clojure.test :refer [deftest is testing run-tests]]
            #_[cljs.test :refer-macros [deftest is testing run-tests]]))

(def wheel-dia 685.8)
(def bike1 (gc/any-bike {:wheel-dia wheel-dia}))
(def bike2 (gc/any-bike {:wheel-dia wheel-dia
                         :ring 52}))
(def bike3 (gc/any-bike {:wheel-dia wheel-dia
                         :ring 39
                         :sprocket 22}))

(def bike-wo-close-gears (gc/any-bike {:get-close-gears false}))

(deftest create-gear-combinations-test
  (is (= (take 5 (f/create-gear-combinations))
         '([28 9] [28 10] [28 11] [28 12] [28 13])))
  (is (= (count (f/create-gear-combinations)) 528)))

(deftest close-gear-pairs-test
  (let [ratio (:ratio (f/bike bike1))]
    (is (= (count (f/close-gear-pairs ratio )) 21))
    (is (= (take 4 (f/close-gear-pairs ratio))
           '([28 12] [30 13] [33 14] [35 15])))))


(deftest gcd-test
  (is (= (f/gcd 18 6) 6))
  (is (= (f/gcd 42 18) 6))
  (is (= (f/gcd 32 18) 2)))

(deftest skid-patches-test
  (is (= (first (f/skid-patches 42 18)) 3))
  (is (= (first (f/skid-patches 32 18)) 9))
  (is (= (second (f/skid-patches 42 18)) 6))
  (is (= (second (f/skid-patches 32 18)) 9))
  (is (= (f/skid-patches 42 18) [3 6]))
  (is (= (f/skid-patches 32 18) [9 9])))


(deftest close-gears-test
  (is (= (f/close-gears bike1)
         [{:sprocket 12,
           :gear-inches 63.0,
           :meters-dev 5.0271764,
           :gain-ratio 4.7064705,
           :ring 28,
           :skid-patches [3 6]}
          {:sprocket 13,
           :gear-inches 62.307693,
           :meters-dev 4.971933,
           :gain-ratio 4.6547513,
           :ring 30,
           :skid-patches [13 13]}
          {:sprocket 14,
           :gear-inches 63.642857,
           :meters-dev 5.0784745,
           :gain-ratio 4.7544956,
           :ring 33,
           :skid-patches [14 28]}
          {:sprocket 15,
           :gear-inches 63.0,
           :meters-dev 5.0271764,
           :gain-ratio 4.7064705,
           :ring 35,
           :skid-patches [3 6]}
          {:sprocket 16,
           :gear-inches 62.4375,
           :meters-dev 4.982291,
           :gain-ratio 4.6644487,
           :ring 37,
           :skid-patches [16 32]}
          {:sprocket 16,
           :gear-inches 64.125,
           :meters-dev 5.1169477,
           :gain-ratio 4.7905145,
           :ring 38,
           :skid-patches [8 16]}
          {:sprocket 17,
           :gear-inches 61.941177,
           :meters-dev 4.942686,
           :gain-ratio 4.6273704,
           :ring 39,
           :skid-patches [17 34]}
          {:sprocket 17,
           :gear-inches 63.52941,
           :meters-dev 5.069422,
           :gain-ratio 4.746021,
           :ring 40,
           :skid-patches [17 17]}
          {:sprocket 18,
           :gear-inches 63.0,
           :meters-dev 5.0271764,
           :gain-ratio 4.7064705,
           :ring 42,
           :skid-patches [3 6]}
          {:sprocket 19,
           :gear-inches 62.526318,
           :meters-dev 4.9893785,
           :gain-ratio 4.6710835,
           :ring 44,
           :skid-patches [19 19]}
          {:sprocket 19,
           :gear-inches 63.94737,
           :meters-dev 5.102773,
           :gain-ratio 4.7772446,
           :ring 45,
           :skid-patches [19 38]}
          {:sprocket 20,
           :gear-inches 62.1,
           :meters-dev 4.95536,
           :gain-ratio 4.6392355,
           :ring 46,
           :skid-patches [10 20]}
          {:sprocket 20,
           :gear-inches 63.45,
           :meters-dev 5.063085,
           :gain-ratio 4.7400885,
           :ring 47,
           :skid-patches [20 40]}
          {:sprocket 21,
           :gear-inches 63.0,
           :meters-dev 5.0271764,
           :gain-ratio 4.7064705,
           :ring 49,
           :skid-patches [3 6]}
          {:sprocket 22,
           :gear-inches 62.590908,
           :meters-dev 4.9945326,
           :gain-ratio 4.675909,
           :ring 51,
           :skid-patches [22 44]}
          {:sprocket 22,
           :gear-inches 63.81818,
           :meters-dev 5.0924644,
           :gain-ratio 4.7675934,
           :ring 52,
           :skid-patches [11 11]}
          {:sprocket 23,
           :gear-inches 62.217392,
           :meters-dev 4.9647274,
           :gain-ratio 4.648005,
           :ring 53,
           :skid-patches [23 46]}
          {:sprocket 23,
           :gear-inches 63.391304,
           :meters-dev 5.058401,
           :gain-ratio 4.7357035,
           :ring 54,
           :skid-patches [23 23]}
          {:sprocket 24,
           :gear-inches 61.875,
           :meters-dev 4.9374056,
           :gain-ratio 4.6224265,
           :ring 55,
           :skid-patches [24 48]}
          {:sprocket 24,
           :gear-inches 63.0,
           :meters-dev 5.0271764,
           :gain-ratio 4.7064705,
           :ring 56,
           :skid-patches [3 6]}
          {:sprocket 24,
           :gear-inches 64.125,
           :meters-dev 5.1169477,
           :gain-ratio 4.7905145,
           :ring 57,
           :skid-patches [8 16]}])
      )
  (is (= (f/close-gears bike3)
         [{:sprocket 16,
           :gear-inches 47.25,
           :meters-dev 3.7703824,
           :gain-ratio 3.5298529,
           :ring 28,
           :skid-patches [4 8]}
          {:sprocket 17,
           :gear-inches 47.64706,
           :meters-dev 3.8020663,
           :gain-ratio 3.5595155,
           :ring 30,
           :skid-patches [17 17]}
          {:sprocket 18,
           :gear-inches 48.0,
           :meters-dev 3.8302298,
           :gain-ratio 3.5858824,
           :ring 32,
           :skid-patches [9 9]}
          {:sprocket 19,
           :gear-inches 48.31579,
           :meters-dev 3.8554287,
           :gain-ratio 3.6094737,
           :ring 34,
           :skid-patches [19 19]}
          {:sprocket 20,
           :gear-inches 47.25,
           :meters-dev 3.7703824,
           :gain-ratio 3.5298529,
           :ring 35,
           :skid-patches [4 8]}
          {:sprocket 20,
           :gear-inches 48.6,
           :meters-dev 3.8781075,
           :gain-ratio 3.6307058,
           :ring 36,
           :skid-patches [5 10]}
          {:sprocket 21,
           :gear-inches 47.57143,
           :meters-dev 3.7960312,
           :gain-ratio 3.5538654,
           :ring 37,
           :skid-patches [21 42]}
          {:sprocket 22,
           :gear-inches 47.863636,
           :meters-dev 3.8193483,
           :gain-ratio 3.5756953,
           :ring 39,
           :skid-patches [22 44]}
          {:sprocket 23,
           :gear-inches 46.95652,
           :meters-dev 3.746964,
           :gain-ratio 3.5079284,
           :ring 40,
           :skid-patches [23 23]}
          {:sprocket 23,
           :gear-inches 48.130436,
           :meters-dev 3.840638,
           :gain-ratio 3.5956266,
           :ring 41,
           :skid-patches [23 46]}
          {:sprocket 24,
           :gear-inches 47.25,
           :meters-dev 3.7703824,
           :gain-ratio 3.5298529,
           :ring 42,
           :skid-patches [4 8]}
          {:sprocket 24,
           :gear-inches 48.375,
           :meters-dev 3.8601534,
           :gain-ratio 3.613897,
           :ring 43,
           :skid-patches [24 48]}])))


(deftest bike-test-wo-close-gears
  (is (= (f/bike bike1) {:ring 42,
                         :sprocket 18,
                         :wheel-dia 685.8,
                         :crank-len 170,
                         :ratio 7/3,
                         :gears
                         {:gear-inches 63.0,
                          :meters-dev 5.0271764,
                          :gain-ratio 4.7064705,
                          :speeds
                          ([50 15.08153]
                           [60 18.097836]
                           [70 21.114141]
                           [80 24.130447]
                           [90 27.146753]
                           [100 30.16306]
                           [110 33.179363]
                           [120 36.19567]
                           [130 39.211975]
                           [140 42.228283])},
                         :skid-patches [3 6]}))
  )

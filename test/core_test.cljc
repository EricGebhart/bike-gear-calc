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

(deftest rpm->speed-test
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 42 18 685.8))
       (float 27.146752452850343)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 52 18 685.8))
       (float 33.61026678085327)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 39 22 685.8))
       (float 20.624481010437012))))

(deftest rpm->speed-mph-test
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 42 18 685.8)
                      true)
       (float 16.868992)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 52 18 685.8)
                      true)
       (float 20.88542)))
  (is (=
       (gc/rpm->speed 90
                      (gc/meters-of-development 39 22 685.8)
                      true)
       (float 12.816052))))

(deftest calc-rpm-speeds-test
  (let [md (gc/meters-of-development 42 18 685.8)]
    (is (= (count (gc/calc-rpm-speeds md)) 10))
    (is (=  (gc/calc-rpm-speeds md)
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

(deftest gcd-test
  (is (= (gc/gcd 18 6) 6))
  (is (= (gc/gcd 42 18) 6))
  (is (= (gc/gcd 32 18) 2)))

(deftest skid-patches-test
  (is (= (first (gc/skid-patches 42 18)) 3))
  (is (= (first (gc/skid-patches 32 18)) 9))
  (is (= (second (gc/skid-patches 42 18)) 6))
  (is (= (second (gc/skid-patches 32 18)) 9))
  (is (= (gc/skid-patches 42 18) [3 6]))
  (is (= (gc/skid-patches 32 18) [9 9])))

(deftest sprocket-gear-map-test
  (is (= (gc/sprocket-gear-map 42 18 685.8 170)
         {:sprocket 18,
          :gear-inches 63.0,
          :meters-dev 5.0271764,
          :gain-ratio 4.7064705}))
  (is (= (gc/sprocket-gear-map 52 18 685.8 170)
         {:sprocket 18,
          :gear-inches 78.0,
          :meters-dev 6.2241235,
          :gain-ratio 5.827059}))
  (is (= (gc/sprocket-gear-map 39 22 685.8 170)
         {:sprocket 22,
          :gear-inches 47.863636,
          :meters-dev 3.8193483,
          :gain-ratio 3.5756953})))

(deftest ratio-gear-map-test
  (let [gm (gc/sprocket-gear-map 42 18 685.8 170)]
    (is (= (gc/ratio-gear-map 1
                              (:gear-inches gm)
                              (:meters-dev gm)
                              (:gain-ratio gm))
           {:gear-inches 63.0,
            :meters-dev 5.0271764,
            :gain-ratio 4.7064705}))
    (is (= (gc/ratio-gear-map 0.5
                              (:gear-inches gm)
                              (:meters-dev gm)
                              (:gain-ratio gm))
           {:gear-inches 31.5,
            :meters-dev 2.501,
            :gain-ratio 2.35}))))

(deftest gear-map-test
  (is (= (gc/gear-map 42 [16 18 20 22] 685.8 170)
         {:ring 42,
          :gears
          [{:sprocket 16,
            :gear-inches 70.875,
            :meters-dev 5.655574,
            :gain-ratio 5.2947793}
           {:sprocket 18,
            :gear-inches 63.0,
            :meters-dev 5.0271764,
            :gain-ratio 4.7064705}
           {:sprocket 20,
            :gear-inches 56.7,
            :meters-dev 4.524459,
            :gain-ratio 4.2358236}
           {:sprocket 22,
            :gear-inches 51.545456,
            :meters-dev 4.1131444,
            :gain-ratio 3.8507488}]}))
  (is (= (gc/gear-map 42 13 [0.25 0.5 1 1.4 2.0] 685.8 170)
         {:ring 42,
          :sprocket 13,
          :gears
          [{:gear-inches 21.80769157409668,
            :meters-dev 1.7401765584945679,
            :gain-ratio 1.6291629076004028}
           {:gear-inches 43.61538314819336,
            :meters-dev 3.4803531169891357,
            :gain-ratio 3.2583258152008057}
           {:gear-inches 87.23076629638672,
            :meters-dev 6.9607062339782715,
            :gain-ratio 6.516651630401611}
           {:gear-inches 122.1230728149414,
            :meters-dev 9.744988727569579,
            :gain-ratio 9.123312282562255}
           {:gear-inches 174.46153259277344,
            :meters-dev 13.921412467956543,
            :gain-ratio 13.033303260803223}]})))

(deftest fixed-gear-map-test
  (is (= (gc/fixed-gear-map 42 18 685.8 170)
         {:sprocket 18,
          :gear-inches 63.0,
          :meters-dev 5.0271764,
          :gain-ratio 4.7064705,
          :ring 42,
          :skid-patches [3 6]}))
  (is (= (gc/fixed-gear-map 52 18 685.8 170)
         {:sprocket 18,
          :gear-inches 78.0,
          :meters-dev 6.2241235,
          :gain-ratio 5.827059,
          :ring 52,
          :skid-patches [9 9]}))
  (is (= (gc/fixed-gear-map 39 22 685.8 170)
         {:sprocket 22,
          :gear-inches 47.863636,
          :meters-dev 3.8193483,
          :gain-ratio 3.5756953,
          :ring 39,
          :skid-patches [22 44]})))

(deftest close-fgear-maps-test
  (is (= (gc/close-fgear-maps 42 18 685.8 170)
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
  (is (= (gc/close-fgear-maps 39 22 685.8 170)
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

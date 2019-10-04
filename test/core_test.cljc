(ns bike-gear-calc.core-test
  (:require [bike-gear-calc.core :as gc]
            [clojure.spec.alpha :as s]
            [bike-gear-calc.data :as d]
            #?(:clj [clojure.test :refer [deftest is testing run-tests]]
               :cljs [cljs.test :refer-macros [deftest is testing run-tests]])))

(def wheel-dia 685.8)
(def bike1 (gc/any-bike {:wheel-diameter wheel-dia}))
(def bike2 (gc/any-bike {:ring 52 :wheel-diameter wheel-dia}))
(def bike3 (gc/any-bike {:ring 39 :sprocket 22 :wheel-diameter wheel-dia}))

(def bike1i (gc/any-bike {:wheel-diameter wheel-dia
                          :internal-ratios [0.25 0.5 1.0 1.4 2.0]}))

(def bike2i (gc/any-bike {:ring 42
                          :sprocket 13
                          :wheel-diameter wheel-dia
                          :internal-ratios [0.25 0.5 1.0 1.4 2.0]}))

(def bike1f (gc/any-bike {:wheel-diameter wheel-dia}))
(def bike2f (gc/any-bike {:wheel-diameter wheel-dia
                          :ring 52}))
(def bike3f (gc/any-bike {:wheel-diameter wheel-dia
                          :ring 39
                          :sprocketmeter 22}))

(def bike1d (gc/any-bike {:wheel-diameter wheel-dia
                          :sprockets [16 18 20 22]}))

(def bike-wo-close-gears (gc/any-bike {:get-close-gears false}))

(deftest valid-any-bike-test
  (is (= (s/valid? ::gc/any-bike bike1) true))
  (is (= (s/valid? ::gc/any-bike bike2) true))
  (is (= (s/valid? ::gc/any-bike bike3) true))
  (is (= (s/valid? ::gc/any-bike bike1f) true))
  (is (= (s/valid? ::gc/any-bike bike2f) true))
  (is (= (s/valid? ::gc/any-bike bike3f) true))
  (is (= (s/valid? ::gc/any-bike bike1d) true))
  (is (= (s/valid? ::gc/any-bike bike1i) true))
  (is (= (s/valid? ::gc/any-bike bike2i) true)))

(deftest valid-any-bike-test
  (is (= (s/valid? ::gc/any-bike bike1) true))
  (is (= (s/valid? ::gc/any-bike bike2) true))
  (is (= (s/valid? ::gc/any-bike bike3) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :fixie bike1f)) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :fixie bike2f)) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :fixie bike3f)) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :deraileur bike1d)) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :internal bike1i)) true))
  (is (= (s/valid? ::gc/any-bike (gc/coerce-bike :internal bike2i)) true)))

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


(deftest single-gear-map-test
  (is (= (gc/single-gear bike1)
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
  (is (= (gc/single-gear bike2)
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
  (is (= (gc/single-gear bike3)
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

;;; both of these next two tests are correct. I have not figured out
;;; why deftest croaks.
(deftest derailer-gear-map-test
  (is (= (gc/ring-gears 42 bike1d)
         {:ring 42,
          :gears
          [{:gear-inches 70.875,
            :meters-dev 5.655574,
            :gain-ratio 5.2947793,
            :speeds
            ([50 16.96672]
             [60 20.360065]
             [70 23.75341]
             [80 27.146755]
             [90 30.540098]
             [100 33.93344]
             [110 37.326786]
             [120 40.72013]
             [130 44.113476]
             [140 47.50682]),
            :sprocket 16}
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
             [140 42.228283]),
            :sprocket 18}
           {:gear-inches 56.7,
            :meters-dev 4.524459,
            :gain-ratio 4.2358236,
            :speeds
            ([50 13.573377]
             [60 16.288052]
             [70 19.002728]
             [80 21.717403]
             [90 24.432077]
             [100 27.146753]
             [110 29.86143]
             [120 32.576103]
             [130 35.29078]
             [140 38.005455]),
            :sprocket 20}
           {:gear-inches 51.545456,
            :meters-dev 4.1131444,
            :gain-ratio 3.8507488,
            :speeds
            ([50 12.339434]
             [60 14.80732]
             [70 17.275206]
             [80 19.743093]
             [90 22.21098]
             [100 24.678867]
             [110 27.146753]
             [120 29.61464]
             [130 32.082527]
             [140 34.55041]),
            :sprocket 22}]} )))

(deftest calc-gears-deraileur-test
  (is (= (first (gc/calc-gears (gc/coerce-bike :deraileur bike1d)))
         {:ring 52,
          :gears
          [{:gear-inches 87.75,
            :meters-dev 7.0021386,
            :gain-ratio 6.5554414,
            :speeds
            ([50 21.006416]
             [60 25.207699]
             [70 29.408981]
             [80 33.610264]
             [90 37.81155]
             [100 42.012833]
             [110 46.214115]
             [120 50.415398]
             [130 54.61668]
             [140 58.817963]),
            :sprocket 16}
           {:gear-inches 78.0,
            :meters-dev 6.2241235,
            :gain-ratio 5.827059,
            :speeds
            ([50 18.67237]
             [60 22.406845]
             [70 26.14132]
             [80 29.875793]
             [90 33.610268]
             [100 37.34474]
             [110 41.079216]
             [120 44.81369]
             [130 48.548164]
             [140 52.28264]),
            :sprocket 18}
           {:gear-inches 70.2,
            :meters-dev 5.601711,
            :gain-ratio 5.244353,
            :speeds
            ([50 16.805132]
             [60 20.166159]
             [70 23.527185]
             [80 26.888212]
             [90 30.249239]
             [100 33.610264]
             [110 36.97129]
             [120 40.332317]
             [130 43.693344]
             [140 47.05437]),
            :sprocket 20}
           {:gear-inches 63.81818,
            :meters-dev 5.0924644,
            :gain-ratio 4.7675934,
            :speeds
            ([50 15.277393]
             [60 18.332872]
             [70 21.388351]
             [80 24.443829]
             [90 27.499308]
             [100 30.554787]
             [110 33.610264]
             [120 36.665745]
             [130 39.721222]
             [140 42.776703]),
            :sprocket 22}]}  )))

(deftest ratio-gear-map-test
  (let [gm (gc/single-gear bike1)]
    (is (= (gc/ratio-gears 1 gm)
           {:ratio 1
            :gear-inches 63.0,
            :meters-dev 5.0271764,
            :gain-ratio 4.7064705}))
    (is (= (gc/ratio-gears 0.5 gm)
           {:ratio 0.5
            :gear-inches 31.5,
            :meters-dev 2.501,
            :gain-ratio 2.35}))))

(deftest hub-gear-map-test
  (let [bike2a (assoc (gc/coerce-bike :internal bike2i) :gear
                      (gc/single-gear bike2i))]
    (is (= (gc/calc-gears bike2a)
           [{:ratio 0.25,
             :gear-inches 21.80769157409668,
             :meters-dev 1.7401765584945679,
             :gain-ratio 1.6291629076004028}
            {:ratio 0.5,
             :gear-inches 43.61538314819336,
             :meters-dev 3.4803531169891357,
             :gain-ratio 3.2583258152008057}
            {:ratio 1.0,
             :gear-inches 87.23076629638672,
             :meters-dev 6.9607062339782715,
             :gain-ratio 6.516651630401611}
            {:ratio 1.4,
             :gear-inches 122.1230728149414,
             :meters-dev 9.744988727569579,
             :gain-ratio 9.123312282562255}
            {:ratio 2.0,
             :gear-inches 174.46153259277344,
             :meters-dev 13.921412467956543,
             :gain-ratio 13.033303260803223}]))))

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
  (is (= (gc/bike (gc/coerce-bike :fixie bike1))
         {:ring 42,
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
          :skid-patches [3 6]})))

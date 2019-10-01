(ns bike-gear-calc.hub-gear-test
  (:require [bike-gear-calc.core :as gc]
            [bike-gear-calc.hub-gear :as hg]
            [bike-gear-calc.data :as d]
            [clojure.test :refer [deftest is testing run-tests]]
            #_[cljs.test :refer-macros [deftest is testing run-tests]]))

(def wheel-dia 685.8)
(def bike1 (gc/any-bike {:wheel-dia wheel-dia
                         :internal-ratios [0.25 0.5 1 1.4 2.0]}))

(def bike2 (gc/any-bike {:ring 42
                         :sprocket 13
                         :wheel-dia wheel-dia
                         :internal-ratios [0.25 0.5 1 1.4 2.0]}))

;;; need to test that an any-bike turns into a valid hub-gear bike.
(def any-bike1 (gc/any-bike {:wheel-dia wheel-dia
                             :internal-ratios [0.25 0.5 1 1.4 2.0]}))
(def any-bike2 (gc/any-bike {:ring 42
                             :sprocket 13
                             :wheel-dia wheel-dia
                             :internal-ratios [0.25 0.5 1 1.4 2.0]}))

(deftest ratio-gear-map-test
  (let [gm (gc/sprocket-gear-map bike1)]
    (is (= (hg/ratio-gear-map 1 gm)
           {:ratio 1
            :gear-inches 63.0,
            :meters-dev 5.0271764,
            :gain-ratio 4.7064705}))
    (is (= (hg/ratio-gear-map 0.5 gm)
           {:ratio 0.5
            :gear-inches 31.5,
            :meters-dev 2.501,
            :gain-ratio 2.35}))))

(deftest hub-gear-map-test
  (let [bike2a (assoc bike2 :gear
                      (gc/sprocket-gear-map bike2))]
    (is (= (hg/gear-map bike2a)
           [{:ratio 0.25,
             :gear-inches 21.80769157409668,
             :meters-dev 1.7401765584945679,
             :gain-ratio 1.6291629076004028}
            {:ratio 0.5,
             :gear-inches 43.61538314819336,
             :meters-dev 3.4803531169891357,
             :gain-ratio 3.2583258152008057}
            {:ratio 1,
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

(ns bike-gear-calc.deraileur-gear-test
  (:require [bike-gear-calc.core :as gc]
            [bike-gear-calc.deraileur-gear :as dg]
            [bike-gear-calc.data :as d]
            [clojure.test :refer [deftest is testing run-tests]]
            #_[cljs.test :refer-macros [deftest is testing run-tests]]))

(def wheel-dia 685.8)
(def bike1 (gc/any-bike {:wheel-dia wheel-dia
                         :sprockets [16 18 20 22]}))

(deftest derailer-gear-map-test
  (is (= (dg/ring-gear-maps bike1)
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
  (is (= (dg/ring-gear-maps bike1)
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

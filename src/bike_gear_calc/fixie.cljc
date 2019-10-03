(ns bike-gear-calc.fixie
  (:require [clojure.math.numeric-tower :as math]
            [bike-gear-calc.core :refer :all]))

;; find gearing that is similar to the bike we have.

(defn ratio-filter
  "give a ring chainring size, a sprocket sprocket size and a
  range tolerence return true or false if the gears fall
  within tolerance."
  [ring sprocket gratio ratio-range]
  (< (math/abs (- gratio (/ ring sprocket)))
     ratio-range))

(defn create-gear-combinations
  "Create a list of chainring and sprocket pairs for
  chainrings between 28 and 61 and sprockets between 9 and 25."
  []
  (mapcat identity
          (map (fn [ring]
                 (map (fn [s] [ring s]) (range 9 25)))
               (range 28 61))))

(defn close-gear-pairs
  "Given a fixed-gear-bike map return a list of fixed-gear-bike maps
  which have a ratio within 2%."
  [ratio]
  (let [gear-combos (create-gear-combinations)
        ratio-range (* ratio 0.02)]
    (filter #(ratio-filter (first %) (second %)  ratio ratio-range)
            gear-combos)))

(declare bike)

(defn close-gears
  "given a fixed-gear-bike map return a vector of gear maps
   of gears within 2% of the original ratio. "
  [{:keys [ratio ring sprocket wheel-dia crank-len]}]
  (->> (close-gear-pairs ratio)
       (map #(bike (first %) (second %) wheel-dia crank-len false))
       (into [])))

;; Calculate skid patches.

;; don't need this if I'm using math tower. waiting for the
;; outcome with cljs directives.
(defn gcd
  "greatest common denominator"
  [a b]
  (if (= b 0)
    a
    (recur b (mod a b))))

(defn skid-patches
  "calculate the skid patches using the gcd for the ratio.
  return a vector, the first value is skid patches and the second
  is ambidextrous skid patches."
  ([{:keys [ring sprocket]}]
   (skid-patches ring sprocket))
  ([ring sprocket]
   (let [cd (math/gcd ring sprocket)
         skid-patches (/ sprocket cd)
         ambi-is-extra  (-> (/ ring cd)
                            (mod 2)
                            (> 0))
         ambi-skid-patches (if  ambi-is-extra
                             (* 2 skid-patches)
                             skid-patches)]
     [skid-patches ambi-skid-patches])))

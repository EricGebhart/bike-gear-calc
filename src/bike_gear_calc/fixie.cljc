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


(defn bike
  "Create a new fixed-gear-bike data tree. Supply a filled
  out core/any-bike if you like. The get-close-gears argument is
  true/false. Don't use it when creating sub-bikes or you'll get a huge
  tree which progresses by a ratio of 2% in each direction each time
  around. I haven't tried it. Pass in a filled in any-bike map from core,
  or give Chainring, sprocket, wheel diameter in millimeters and the
  crank-length (mm).  You'll get back a map chock full of goodies."
  ([{:keys [ring sprocket wheel-dia crank-len get-close-gears]}]
   (bike ring sprocket wheel-dia crank-len get-close-gears))

  ([ring sprocket wheel-dia crank-len get-close-gears]
   (let [bike {:ring ring
               :sprocket sprocket
               :wheel-dia wheel-dia
               :crank-len crank-len
               :ratio (/ ring sprocket)}
         skp (skid-patches bike)
         gears (gc/sprocket-gear-map bike)
         bike (assoc bike
                     :gears gears
                     :skid-patches skp)]
     (if get-close-gears
       (assoc bike :close-gears
              (close-gears bike))
       bike))))

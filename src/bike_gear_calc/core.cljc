(ns bike-gear-calc.core
  (:require [clojure.math.numeric-tower :as math] ))

;; so maybe this should be a defrecord. But not yet.
;; these values go to so many functions. It's a lot
;; easier to do this. Looking defrecordish.
(defn fixed-gear-bike
  "Create a map of attributes for a bike with one
  chainring and one sprocket to pass around."
  [ring sprocket wheel-dia crank-len]
  {:ring ring
   :sprocket sprocket
   :wheel-dia wheel-dia
   :crank-len crank-len
   :ratio (/ ring sprocket)})

(defn hub-gear-bike
  "Create a map of attributes for a bike with one
  chainring and one sprocket to pass around."
  [ring sprocket wheel-dia crank-len]
  {:ring ring
   :sprocket sprocket
   :wheel-dia wheel-dia
   :crank-len crank-len
   :ratio (/ ring sprocket)
   :internal-ratios []})

(defn deraileur-bike
  "Create a map of attributes for a bike with multiple
  chainrings and sprockets to pass around."
  [rings freewheels wheel-dia crank-len]
  {:rings []
   :sprocket-vec []
   :wheel-dia wheel-dia
   :crank-len crank-len})

(defn gear-inches
  "given the chainring size, sprocket size and the wheel
  diameter in mm, give the gear inches. "
  [ring sprocket wheel-dia]
  (-> (/ wheel-dia 25.4)
      (* (/ ring sprocket))
      float))

;;wheel wheel-dia in meters x ring sprocket / rear sprocket x pi.
(defn meters-of-development
  "given the ring chainring size, sprocket sprocket size and
  wheel wheel-dia in meters. Give the meters of development. "
  [ring sprocket wheel-dia]
  (->  (/ ring sprocket)
       (* (* wheel-dia Math/PI))
       (/ 1000)
       float))

(defn gain-ratio
  "calculate the gain ratio for a given chainring, sprocket, wheel diameter
  and crank length. "
  [ring sprocket wheel-dia crank-len]
  (let [crank-ratio (/ (/ wheel-dia 2) crank-len)]
    (-> crank-ratio
        (* (/ ring sprocket))
        float)))

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

(defn close-gears
  "Given a chainring and sprocket size or a ratio give an array of gears
  which have a ratio within 2%."
  ([gratio]
   (let [gear-combos (create-gear-combinations)
         ratio-range (* gratio 0.02)]
     (filter #(ratio-filter (first %) (second %)  gratio ratio-range)
             gear-combos)))
  ([ring sprocket]                   ;take this out when defrecords are working.
   (close-gears (/ ring sprocket))))

(defn rpm->speed
  "calculate the speed for a given rpm and meters of development."
  ([rpm dev]
   (rpm->speed rpm dev false))
  ([rpm dev mph]
   (let [convert (if mph 0.6214 1)]
     (-> rpm
         (* dev)
         (* 60)
         (/ 1000)
         (* convert)
         float))))

(defn calc-rpm-speeds
  "calculate the speeds for rpms from 50-140 for the given meters of development."
  ([m-dev]
   (calc-rpm-speeds m-dev false))
  ([m-dev mph]
   (map (fn [rpm] [rpm  (rpm->speed rpm m-dev mph)])
        (range 50 150 10))))


;; Calculate skid patches.
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
  [ring sprocket]
  (let [cd (math/gcd ring sprocket)
        skid-patches (/ sprocket cd)
        ambi-is-extra  (-> (/ ring cd)
                           (mod 2)
                           (> 0))
        ambi-skid-patches (if  ambi-is-extra
                            (* 2 skid-patches)
                            skid-patches)]
    [skid-patches ambi-skid-patches]))

(defn sprocket-gear-map
  "given a chainring, a sprocket, wheel-dia and cranklength
  return a map of development."
  [r s wd cl]
  {:sprocket    s
   :gear-inches (gear-inches r s wd)
   :meters-dev  (meters-of-development r s wd)
   :gain-ratio  (gain-ratio r s wd cl)})

(defn ratio-gear-map
  "for internal hubs, given a ratio, gear-inches, meters-development
  and gain-ratio return a map. "
  [ratio & {:keys [gear-inches meters-dev gain-ratio]}]
  {:gear-inches (* ratio gear-inches)
   :meters-dev (* ratio meters-dev)
   :gain-ratio (* ratio gain-ratio)})

(defn derailer-gear-map
  "Given a chainring, crank length, wheel diameter
  and a vector of sprockets create a vector of maps of
  gain-ratio, gear-inches and meters of development "
  ([ring sprocket-vec wheel-dia crank-len]
   (let [gm {:ring ring}]
     (into gm
           {:gears
            (into []
                  (map #(sprocket-gear-map ring %1
                                           wheel-dia
                                           crank-len)
                       sprocket-vec))}))))

(defn hub-gear-map
  "Given a chainring, sprocket, a vector of ratios,
  crank length and wheel diameter create a vector of
  maps of gain-ratio, gear-inches and meters of development "
  [ring sprocket hub-ratios wheel-dia crank-len]
  (let [gm (sprocket-gear-map ring sprocket wheel-dia crank-len)
        hm {:ring ring :sprocket sprocket}]
    (into hm
          {:gears
           (into []
                 (map #(ratio-gear-map %1 gm)
                      hub-ratios))}))

  (defn fixed-gear-map
    "given wheel-dia chainring and sprocket create a map
  of gain-ratio, gear-inches meters of development and skid-patches."
    [ring sprocket wheel-dia crank-len]
    (let[gm (sprocket-gear-map ring sprocket wheel-dia crank-len)]
      (into gm {:ring ring
                :skid-patches (skid-patches ring sprocket)}))))

(defn close-fixed-gear-maps
  "given a chainring and sprocket return a list
  of gear maps of gears within 2% of the original ratio. "
  [ring sprocket wheel-dia crank-len]
  (let [cg (close-gears ring sprocket)]
    (into []
          (map #(fixed-gear-map (first %) (last %)  wheel-dia crank-len)
               cg))))

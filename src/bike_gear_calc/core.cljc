(ns bike-gear-calc.core
  (:require [clojure.math.numeric-tower :as math] ))

(defn gear-inches
  "given the chainring size, sprocket size and the wheel
  diameter in mm, give the gear inches. "
  [{:keys [ring sprocket wheel-dia]}]
  (-> (/ wheel-dia 25.4)
      (* (/ ring sprocket))
      float))

;;wheel wheel-dia in meters x ring sprocket / rear sprocket x pi.
(defn meters-of-development
  "given the ring chainring size, sprocket sprocket size and
  wheel wheel-dia in meters. Give the meters of development. "
  [{:keys [ring sprocket wheel-dia]}]
  (->  (/ ring sprocket)
       (* (* wheel-dia Math/PI))
       (/ 1000)
       float))

(defn gain-ratio
  "calculate the gain ratio for a given chainring, sprocket, wheel diameter
  and crank length. "
  [{:keys [ring sprocket wheel-dia crank-len]}]
  (let [crank-ratio (/ (/ wheel-dia 2) crank-len)]
    (-> crank-ratio
        (* (/ ring sprocket))
        float)))

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

(defn sprocket-gear-map
  "given a bike calculate the map of development. if given a sprocket
  and a bike, it's for a freewheel, so fool it, set the sprocket, then
  save the sprocket in the map on the way out."
  ([bike]
   (let [mdev (meters-of-development bike)
         speeds (calc-rpm-speeds mdev)]
     {:gear-inches (gear-inches bike)
      :meters-dev  mdev
      :gain-ratio  (gain-ratio bike)
      :speeds  speeds}))
  ([s bike]
   (assoc (sprocket-gear-map
           (assoc bike :sprocket s))
          :sprocket s)))

(defn any-bike
  "Create a map of attributes for a bike with
  all attributes needed for any bike."
  []
  {:ring nil
   :sprocket nil
   :rings []
   :sprockets []
   :wheel-dia 670
   :crank-len 170
   :ratio 1
   :internal-ratios []
   :get-close-gears true})

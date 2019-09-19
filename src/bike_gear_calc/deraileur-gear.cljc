(ns bike-gear-calc.deraileur-gear
  (:require [bike-gear-calc.core :refer :all] ))

(defn ring-gear-map
  "Given a chainring, and a deraileur bike map.
  calculate gain-ratio, gear-inches and meters of development
  for each sprocket in the freewheel/cassette."
  [ring {:keys [sprockets] :as bike}]
  (let [gm {:ring ring}
        bike (assoc bike :ring ring)]
    (into gm
          {:gears
           (into []
                 (map #(sprocket-gear-map  %1 bike)
                      sprockets))})))

(defn gear-map
  "Given a deraileur-gear-bike map,
  give back the development for all of the sprockets."
  [{:keys [gear rings] :as bike}]
  (into []
        (map #(ring-gear-map %1 bike)
             rings)))

(defn new-bike
  "Create a map of attributes for a bike with multiple
  chainrings and sprockets to pass around."
  [rings sprockets wheel-dia crank-len]
  (let [bike
        {:rings rings
         :sprockets sprockets
         :wheel-dia wheel-dia
         :crank-len crank-len}]
    (assoc bike :gears (gear-map bike))))

(ns bike-gear-calc.hub-gear
  (:require [bike-gear-calc.core :refer :all] ))

(defn ratio-gear-map
  "For internal hubs, given a ratio, gear-inches, meters-development
  and gain-ratio return a map. "
  [ratio & {:keys [gear-inches meters-dev gain-ratio]}]
  {:ratio ratio
   :gear-inches (* ratio gear-inches)
   :meters-dev (* ratio meters-dev)
   :gain-ratio (* ratio gain-ratio)})

(defn gear-map
  "Given a hub-gear-bike map with a calculated gear,
  give back the development for all of the internal gear
  ratios."
  [{:keys [gear internal-ratios]}]
  (into []
        (map #(ratio-gear-map %1 gear)
             internal-ratios )))

(defn new-bike
  "Create a map of attributes for a bike with one
  chainring and one sprocket to pass around."
  [ring sprocket wheel-dia crank-len ratio-vec]
  (let [bike
        {:ring ring
         :sprocket sprocket
         :wheel-dia wheel-dia
         :crank-len crank-len
         :ratio (/ ring sprocket)
         :internal-ratios ratio-vec
         :gear (sprocket-gear-map ring sprocket wheel-dia crank-len)}]
    (assoc bike :gears (gear-map bike))))

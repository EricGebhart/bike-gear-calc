(ns bike-gear-calc.hub-gear
  (:require [bike-gear-calc.core :as gc] ))

(defn ratio-gear-map
  "For internal hubs, given a ratio, gear-inches, meters-development
  ;; and gain-ratio return a map. "
  [ratio {:keys [gear-inches meters-dev gain-ratio]}]
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

(defn bike
  "Create a map of attributes for an internally geared bike
  with one chainring and one sprocket. Can be given an any-bike
  map with the attributes filled in or you can provide them
  seprarately. "
  [{:keys [ring sprocket
           crank-len wheel-dia
           internal-ratios
           mph]
    :or {ring 42 sprocket 18
         crank-len 170
         wheel-dia 670
         internal-ratios []
         mph false}}]
  (let [bike
        {:ring ring
         :sprocket sprocket
         :wheel-dia wheel-dia
         :crank-len crank-len
         :ratio (/ ring sprocket)
         :internal-ratios internal-ratios
         :mph mph}
        bike (assoc bike :gear
                    (gc/gear-map bike))]
    (assoc bike :gears (gear-map bike))))

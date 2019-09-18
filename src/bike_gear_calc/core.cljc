(ns bike-gear-calc.core
  (:require [clojure.math.numeric-tower :as math] ))

(defn gear-inches
  "given the ring chainring size, the sprocket size and the wheel
  wheel-dia in mm, give the gear inches. "
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

;; divide the wheel radius in mm, by the crank length in mm. this will
;; yield a single radius ratio applicable to all of the gears of a given
;; bike. The individual gear ratios are calculated as with gear inches,
;; using this radius ratio instead of the wheel size.

(defn gain-ratio
  [ring sprocket wheel-dia crank-length]
  (let [crank-ratio (/ (/ wheel-dia 2) crank-length)]
    (-> crank-ratio
        (* (/ ring sprocket))
        float)))

(defn ratio-filter
  "give a ring chainring size, a sprocket sprocket size and a
  range tolerence return true or false if the gears fall
  within tolerance."
  [ring sprocket ratio ratio-range]
  (< (math/abs (- ratio (/ ring sprocket)))
     ratio-range))

(defn create-gear-combinations
  []
  (mapcat identity
          (map (fn [ring]
                 (map (fn [s] [ring s]) (range 9 25)))
               (range 28 61))))

(defn cclose-gears
  ([ratio]
   (let [gear-combos (create-gear-combinations)
         ratio-range (* ratio 0.02)]
     (filter #(ratio-filter (first %) (second %) ratio ratio-range)
             gear-combos)))
  ([ring sprocket]
   (close-gears (/ ring sprocket))))

(defn close-gears
  "Given a chainring and sprocket size or a ratio give sprocket an array of gears
  which have a ratio within 2%."
  ([ratio]
   (let [gear-combos (create-gear-combinations)
         ratio-range (* ratio 0.02)]
     (filter #(ratio-filter (first %) (second %)  ratio ratio-range)
             gear-combos)))
  ([ring sprocket]
   (cg (/ ring sprocket))))

(defn rpm->speed
  "calculate the speed for a given rpm and meters of development."
  ([rpm dev]
   (rpm->speed rpm dev false))
  ([rpm dev mph]
   (let [convert (if mph 1.609 1)]
     (-> rpm
         (* dev)
         (/ 600)
         (/ convert)
         (/ 10)))))

(defn calc-rpm-speeds
  "calculate the speeds for rpms from 50-140 for the given meters of development."
  ([development]
   (calc-rpm-speeds development false))
  ([development mph]
   (map #([%1  (rpm->speed %1 development mph)])
        (range 50 140 10))))


;; Calculate skid patches.
(defn gcd
  "greatest common denominator"
  [a b]
  (if (= b 0)
    a
    (gcd b (mod a b))))

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

(defn cluster-gear-map
  "given a chainring, a sprocket, wheel-dia and cranklength
  return a map of development."
  [r s wd cl]
  {:sprocket    s
   :gear-inches (gear-inches r s wd)
   :meters-dev  (meters-of-development r s wd)
   :gain-ratio  (gain-ratio r s wd cl)})

(defn hub-gear-map
  "given a ratio, gear-inches, meters-development and gain-ratio return
  a map. "
  [ratio gi md gr]
  {:gear-inches (* ratio gi)
   :meters-dev (* ratio md)
   :gain-ratio (* ratio gr)})

(defn gear-map
  "Given wheel-dia a chainring, crank-length
  and either a sprocket and vector of hub ratios
  or a vector of sprockets create a map of
  gain-ratio, gear-inches and meters of development "
  ([chainring cluster wheel-dia crank-length]
   (let [gm {:ring chainring}]
     (into
      gm {:gears
          (into
           []
           (map #(cluster-gear-map chainring %1
                                   wheel-dia
                                   crank-length)
                cluster))})))

  ([chainring sprocket hub-ratios wheel-dia crank-length]
   (let [gi (gear-inches chainring sprocket wheel-dia)
         md (meters-of-development chainring sprocket wheel-dia)
         gr (gain-ratio chainring sprocket wheel-dia crank-length)
         hm {:ring chainring :sprocket sprocket}]
     (into
      hm
      {:gears (into
               []
               (map #(hub-gear-map %1 gi md gr)
                    hub-ratios))}))))

(defn fixed-gear-map
  "given wheel-dia chainring and sprocket create a map
  of gain-ratio, gear-inches meters of development and skid-patches."
  [chainring sprocket wheel-dia crank-length]
  (let [gi (gear-inches chainring sprocket wheel-dia)
        md (meters-of-development chainring sprocket wheel-dia)
        gr (gain-ratio chainring sprocket wheel-dia crank-length)
        sd (skid-patches chainring sprocket)
        hm {:ring chainring
            :sprocket sprocket
            :gear-inches gi
            :meters-dev md
            :gain-raiton gr
            :skid-patches sd}]))

(defn close-fgear-maps
  "given a chain-ring and sprocket return a list
  of gear maps of gears within tolerance. "
  [ring sprocket wheel-dia crank-length]
  (let [cg (close-gears ring sprocket)]
    (into
     []
     (map #(fixed-gear-map %1 %2 wheel-dia crank-length)
          cg))))

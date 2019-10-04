(ns bike-gear-calc.core
  (:require [clojure.spec.alpha :as s]
            [bike-gear-calc.data :as d]))

;; Math functions so we don't need any libraries.

(def PI 3.141592653589793)

;; add spec instrumentation to these..
(defn abs
  "(abs n) is the absolute value of n"
  [n]
  (cond
    (neg? n) (- n)
    :else n))

;; gcd only accepts integers.
(defn gcd
  "(gcd a b) returns the greatest common divisor of a and b"
  [a b]
  (loop [a (abs a) b (abs b)]
    (if (zero? b)
      a
      (recur b (mod a b)))))


;; Specs for our bike data structures.

(s/def ::type #{:any :fixie :internal :deraileur})
(s/def ::ring int?)
(s/def ::sprocket int?)
(s/def ::wheel-diameter (s/or :fdia float? :dia int?))
(s/def ::crank-length (into #{} d/crank-lengths))
#? (:clj (s/def ::ratio (s/or :n float? :r ratio?))
    :cljs (s/def ::ratio float?))
(s/def ::get-close-gears boolean?)
(s/def ::mph boolean?)
(s/def ::internal-ratios (s/coll-of float? :kind vector? :min-count 2 :max-count 14 :distinct true))
(s/def ::sprockets (s/coll-of int? :kind vector? :min-count 1 :max-count 14 :distinct true))
(s/def ::rings (s/coll-of int? :kind vector? :min-count 1 :max-count 3 :distinct true))

(def basic-keys [:type
                 :wheel-diameter
                 :crank-length
                 :mph])
(def ring&sprocket-keys [:ring :sprocket :ratio])
(def fixie-keys [:get-close-gears])
(def internal-keys [:internal-ratios])
(def deraileur-keys [:rings :sprockets])

;; I don't like this. It duplicates the map definitons.
;; But I haven't yet figured out how to get
;; a list of keys from a spec. form or describe, with a walk
;; through the spec tree.  That should be a part of spec. give
;; me a list of required and optional keys for this map here,
;; with the merges merged. duh. unqualified if necessary. :-(
;; I'll come back to this maybe.  It would be nice to use spec
;; to get the keys in order to coerce the shape of something.
;; I don't want spec to coerce for me. I just don't want to
;; define the same thing twice.

(def fixie (-> fixie-keys
               (into ring&sprocket-keys)
               (into basic-keys) ))

(def internal (-> internal-keys
                  (into ring&sprocket-keys)
                  (into basic-keys)))

(def deraileur (-> deraileur-keys
                   (into basic-keys)))

;; The corresponding Specs. It would be nice to use the same
;; vectors of keys, but spec wants namespace qualified keys.
;; And it would be nice to not have to have separate definitions,
;; ie. to be able to suck the keys back out of spec.


;; these just define the input specs. a simple bike structure with
;; only the data that defines the bike.

(s/def ::basic (s/keys :req-un [::type
                                ::wheel-diameter
                                ::crank-length
                                ::mph]))

(s/def ::r&s (s/merge ::basic (s/keys :req-un [::ring
                                               ::sprocket
                                               ::ratio])))

(s/def ::fixie (s/merge ::r&s (s/keys :req-un [::get-close-gears])))

(s/def ::internal (s/merge ::r&s (s/keys :req-un [::internal-ratios])))

(s/def ::deraileur (s/merge ::basic (s/keys :req-un [::rings
                                                     ::sprockets])))

(s/def ::any-bike (s/merge ::basic (s/keys :opt-un [::ring
                                                    ::sprocket
                                                    ::close-gears
                                                    ::internal-ratios
                                                    ::rings
                                                    ::sprockets])))

(s/def ::single-foot-skid-patch int?)
(s/def ::ambidexterous-skid-patch int?)
(s/def ::skid-patches (s/coll-of (s/and ::single-foot-skid-patch
                                        ::ambidextrous-skid-patch)
                                 :kind vector?
                                 :count 2))


;; still to do:  specs for all the returns.  Maybe add {:pre to functions}
;; or do a spec definition for some of the functions. it's not really
;; necessary, but nice in a small example.

;; Ok. So usage is just get an any-bike, fill it in as you wish
;; then coerce it to the bike type you want, :fixie, :internal, or
;; :deraileur.  Then send it to bike to get your data.
;;
;; (bike (coerce-bike :fixie <your-any-bike>))

(defn any-bike
  "Create a map of attributes for a bike with all attributes needed for
  any bike. I was lazy, this validates with the above spec, even though
  it's sort of bogus.
  Mostly forseen as being used in a re-frame db, so the UI can have a
  place to put stuff, but can then be turned into any of the specific
  bike types.
  Give it a map if you really want other values from the start."
  [{:keys [ring sprocket
           crank-length
           wheel-diameter
           rings sprockets
           internal-ratios
           get-close-gears
           mph]
    :or {ring 42                       ;some reasonable, mostly, defaults that validate.
         sprocket 18
         crank-length 170
         wheel-diameter 670.0
         rings [52 42]
         sprockets [18]
         internal-ratios [0.5 1.0]
         get-close-gears false
         mph false}}]
  {:type :any
   :ring ring
   :sprocket sprocket
   :rings rings
   :sprockets sprockets
   :wheel-diameter wheel-diameter
   :crank-length crank-length
   :ratio 1
   :internal-ratios internal-ratios
   :get-close-gears get-close-gears
   :mph mph})

;; change the shape of an any-bike to a specific bike.
;; really it should be even better, instead of getting the keys
;; from the spec, the spec should be able to reform a thing to
;; another thing as long as what it was given is a superset.
;; spec-tools is a start.  go look there later.

;; :fixie, :internal or :deraileur with an anybike.
(defmulti coerce-bike (fn [type bike] type) )

(defmethod coerce-bike :fixie [_ {:keys [ring sprocket] :as bike}]
  "coerce an any-bike into a fixie "
  (s/conform ::fixie
             (assoc (select-keys bike fixie)
                    :type :fixie
                    :get-close-gears true
                    :ratio (/ ring sprocket))))

(defmethod coerce-bike :internal [_ {:keys [ring sprocket] :as bike}]
  "coerce an any-bike into a internal gear bike."
  (s/conform ::internal
             (assoc (select-keys bike internal)
                    :type :internal
                    :ratio (/ ring sprocket))))

(defmethod coerce-bike :deraileur [_ bike]
  "coerce an any-bike into a deraileur gear bike."
  (s/conform ::deraileur
             (assoc (select-keys bike deraileur)
                    :type :deraileur)))


;;; calculations...

(defn gear-inches
  "given the chainring size, sprocket size and the wheel
  diameter in mm, give the gear inches. "
  [{:keys [ring sprocket wheel-diameter]}]
  (-> (/ wheel-diameter 25.4)
      (* (/ ring sprocket))
      float))

;;wheel wheel-diameter in meters x ring sprocket / rear sprocket x pi.
(defn meters-of-development
  "given the ring chainring size, sprocket sprocket size and
  wheel wheel-diameter in meters. Give the meters of development. "
  [{:keys [ring sprocket wheel-diameter]}]
  (->  (/ ring sprocket)
       (* (* wheel-diameter PI))
       (/ 1000)
       float))

(defn gain-ratio
  "calculate the gain ratio for a given chainring, sprocket, wheel diameter
  and crank length. "
  [{:keys [ring sprocket wheel-diameter crank-length]}]
  (let [crank-ratio (/ (/ wheel-diameter 2) crank-length)]
    (-> crank-ratio
        (* (/ ring sprocket))
        float)))

(defn ratio-filter
  "give a ring chainring size, a sprocket sprocket size and a
  range tolerence return true or false if the gears fall
  within tolerance."
  [ring sprocket gratio ratio-range]
  (< (abs (- gratio (/ ring sprocket)))
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



(defn skid-patches
  "calculate the skid patches using the gcd for the ratio.
  return a vector, the first value is skid patches and the second
  is ambidextrous skid patches."
  ([{:keys [ring sprocket]}]
   (skid-patches ring sprocket))
  ([ring sprocket]
   (let [cd (gcd ring sprocket)
         skid-patches (/ sprocket cd)
         ambi-is-extra  (-> (/ ring cd)
                            (mod 2)
                            (> 0))
         ambi-skid-patches (if  ambi-is-extra
                             (* 2 skid-patches)
                             skid-patches)]
     [skid-patches ambi-skid-patches])))

;; rpm/cadence to speed for a gear.
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
  [m-dev mph]
  (map (fn [rpm] [rpm  (rpm->speed rpm m-dev mph)])
       (range 50 150 10)))

;; These are the base gear calculation functions.
;; single-gear is used by fixie and internal.
;; ratio gears is the second layer for internal gears.
;; ring gears uses single geards iteratively.

(defn single-gear
  "given a bike with a ring and a sprocket.
  calculate the development. if given a sprocket
  and a bike, it's for a freewheel, so fool it, set the sprocket, then
  save the sprocket in the map on the way out."
  ([bike]
   (let [mdev (meters-of-development bike)
         speeds (calc-rpm-speeds mdev (:mph bike))]
     {:gear-inches (gear-inches bike)
      :meters-dev  mdev
      :gain-ratio  (gain-ratio bike)
      :speeds  speeds}))
  ([s bike]
   (assoc (single-gear
           (assoc bike :sprocket s))
          :sprocket s)))

(defn ratio-gears
  "For internal hubs, given a ratio, gear-inches, meters-development
  ;; and gain-ratio return a map. "
  [ratio {:keys [gear-inches meters-dev gain-ratio]}]
  {:ratio ratio
   :gear-inches (* ratio gear-inches)
   :meters-dev (* ratio meters-dev)
   :gain-ratio (* ratio gain-ratio)})

(defn ring-gears
  "Given a chainring, and a deraileur bike map.
  calculate gain-ratio, gear-inches and meters of development
  for each sprocket in the freewheel/cassette."
  [ring {:keys [sprockets] :as bike}]
  (let [gm {:ring ring}
        bike (assoc bike :ring ring)]
    (into gm
          {:gears
           (into []
                 (map #(single-gear  %1 bike)
                      sprockets))})))


;; calculate the numbers for the gearings.

(defmulti calc-gears (fn [bike] (:type bike)))

(defmethod calc-gears :internal
  [{:keys [gear internal-ratios]}]
  "Given a hub-gear-bike map with a calculated gear,
  give back the development for all of the internal gear
  ratios."
  (into []
        (map #(ratio-gears %1 gear)
             internal-ratios )))

(defmethod calc-gears :deraileur
  [{:keys [gear rings] :as bike}]
  "Given a deraileur bike,
  give back the development for all of the sprockets."
  (into []
        ;; (map #(ring-gears %1 bike)
        rings))

;; kind of stupid... one more layer of indirection.
(defmethod calc-gears :fixie [bike]
  "Given a fixie bike map,
  give back the development for the gearing."
  (single-gear bike))


;; fill in all the data about the gears for a bike.

(defmulti bike (fn [bike] (:type bike)))

(defmethod bike :fixie
  [{:keys [get-close-gears] :as bike}]
  "Fill in a fixie bike. Give it a validated basic fixie bike.
  You'll get back a map chock full of goodies."
  (let [skp (skid-patches bike)
        gears (single-gear bike)
        bike (assoc bike
                    :gears gears
                    :skid-patches skp)]
    (if get-close-gears
      (assoc bike :close-gears
             (close-gears bike))
      bike)))

(defmethod bike :internal [bike]
  "Pass in a coerced internal gear bike map.
  Get back all the goodies for an internally geared bike."
  (let [bike (assoc bike :gear
                    (single-gear bike))]
    (assoc bike :gears (calc-gears bike))))

(defmethod bike :deraileur [bike]
  "Pass in a coerced deraileur gear bike map.
  get back all the goodies "
  (assoc bike :gears (calc-gears bike)))

(ns bike-gear-calc.data)

(defn convert
  [{:keys [size] :as rest}]
  (-> (assoc rest
             :mm (* 25.4 size)
             :inches size)
      (dissoc :size)))

(def wheel-sizes
  [{:name "36 x 2.25 / 57-787", :mm 905.5099999999999, :inches 35.65}
   {:name "32 x 2.125 / 54/686", :mm 785.876, :inches 30.94}
   {:name "29 x 3.0 / 75-622", :mm 759.4599999999999, :inches 29.9}
   {:name "29 x 2.7 / 70-622", :mm 750.3159999999999, :inches 29.54}
   {:name "29 x 2.3 / 60-622", :mm 735.076, :inches 28.94}
   {:name "700 X 56 / 56-622 / 29 x 2.2",
    :mm 739.9019999999999,
    :inches 29.13}
   {:name "29 x 2.1 / 54-622", :mm 701.8019999999999, :inches 27.63}
   {:name "700 X 50 / 50-622 / 29 x 2.0", :mm 735.076, :inches 28.94}
   {:name "29 x 1.9 / 47-622", :mm 694.1819999999999, :inches 27.33}
   {:name "700 X 44 / 44-622 / 29 x 1.75",
    :mm 707.6439999999999,
    :inches 27.86}
   {:name "700 X 38 / 38-622", :mm 693.928, :inches 27.32}
   {:name "700 X 35 / 35-622", :mm 690.118, :inches 27.17}
   {:name "700 X 32 / 32-622", :mm 685.8, :inches 27}
   {:name "700 X 25 / 25-622", :mm 670.0519999999999, :inches 26.38}
   {:name "700 X 23 / 23-622", :mm 667.512, :inches 26.28}
   {:name "700 X 20 / 20-622", :mm 663.956, :inches 26.14}
   {:name "28 inch (nominal)", :mm 711.1999999999999, :inches 28}
   {:name "28 X 1 1/2 / 40-635", :mm 715.0099999999999, :inches 28.15}
   {:name "Tubular / Wide", :mm 673.862, :inches 26.53}
   {:name "Tubular / Narrow", :mm 670.0519999999999, :inches 26.38}
   {:name "27 X 1 3/8 / 35-630", :mm 690.372, :inches 27.18}
   {:name "27 X 1 1/4 / 32-630", :mm 687.8319999999999, :inches 27.08}
   {:name "27 X 1 1/8 / 28-630", :mm 685.8, :inches 27}
   {:name "26 inch (nominal)", :mm 660.4, :inches 26}
   {:name "27 X 1 / 25-630", :mm 682.752, :inches 26.88}
   {:name "26 x 4.7 / 119-559 fatbike at 10 PSI",
    :mm 761.2379999999999,
    :inches 29.97}
   {:name "26 x 4.25 / 108-559 fatbike at 10 PSI",
    :mm 738.3779999999999,
    :inches 29.07}
   {:name "26 x 4.0 / 102-559 fatbike at 10 PSI",
    :mm 725.678,
    :inches 28.57}
   {:name "26 x 3.8 / 97-559 fatbike at 10 PSI",
    :mm 715.518,
    :inches 28.17}
   {:name "26 X 2.35 / 60-559 / MTB", :mm 670.814, :inches 26.41}
   {:name "26 X 2.125 / 54-559 / MTB", :mm 658.876, :inches 25.94}
   {:name "26 X 1.9 / 47-559 / MTB", :mm 654.05, :inches 25.75}
   {:name "26 X 1.5 / 38-559 / MTB", :mm 631.698, :inches 24.87}
   {:name "26 X 1.25 / 32-559 / MTB",
    :mm 621.5379999999999,
    :inches 24.47}
   {:name "26 X 1.0 / 25-559 / MTB", :mm 608.838, :inches 23.97}
   {:name "650B (“27.5”) x 3.0 / 76-584 / MTB",
    :mm 728.472,
    :inches 28.68}
   {:name "650B (“27.5”) x 2.8 / 71-584 / MTB",
    :mm 723.3919999999999,
    :inches 28.48}
   {:name "650B (“27.5”) x 2.5 / 64-584 / MTB",
    :mm 715.7719999999999,
    :inches 28.18}
   {:name "650B (“27.5”) x 2.0 / 51-584 / MTB",
    :mm 703.072,
    :inches 27.68}
   {:name "650 x 38B / 38-584 / 650B", :mm 660.4, :inches 26.0}
   {:name "650 x 28C / 28-571 / 26 road/tri", :mm 627.38, :inches 24.7}
   {:name "650 x 25C / 25-571 / 26 road/tri", :mm 621.284, :inches 24.46}
   {:name "650 x 23C / 23-571 / 26  road/tri",:mm 617.4739999999999,
    :inches 24.31}
   {:name "26 X 1 3/8 / 35-590", :mm 658.1139999999999, :inches 25.91}
   {:name "24  (nominal)", :mm 609.5999999999999, :inches 24}
   {:name "24 x 1 / 25-520", :mm 558.0379999999999, :inches 21.97}
   {:name "24 x 2.5 / 65-507", :mm 618.49, :inches 24.35}
   {:name "24 x 2/3 / 60-507", :mm 613.41, :inches 24.15}
   {:name "24 x 2.1 / 54-507", :mm 608.3299999999999, :inches 23.95}
   {:name "32-451 /20 x 1 3/8", :mm 511.80999999999995, :inches 20.15}
   {:name "28-451/20 x 1 1/8", :mm 505.4599999999999, :inches 19.9}
   {:name "20 X 1.75 / 44-406 / BMX", :mm 474.472, :inches 18.68}
   {:name "20 X 1.25 / 32-406", :mm 468.12199999999996, :inches 18.43}
   {:name "18 x 1.5 / 40-355", :mm 435.864, :inches 17.16}
   {:name "17 x 1 1/4 / 32-369", :mm 421.64, :inches 16.6}
   {:name "16 x 1 1/2 / 40-349", :mm 428.75199999999995, :inches 16.88}
   {:name "16 x 1 3/8 / 35-349", :mm 408.178, :inches 16.07}
   {:name "16 x 1.5 / 37-305", :mm 341.884, :inches 13.46}]
  )


(def internal-hubs
  [{:ratios [1.000] :name "       No planetary (internal) gears      "}

   {:ratios [1.000 0.682] :name "Bendix 2 speed Red Band, Yellow Band"}
   {:ratios [1.467 1.000] :name "Bendix 2 speed Blue Band"}
   {:ratios [1.333 1 0.75] :name "Brampton (old, not Brompton) 3 speed Sturmey Archer AW copy"}
   {:ratios [1.333 1 0.75] :name "Hercules 3 speed Sturmey Archer AW copy"}
   {:ratios [1.333 1 0.75] :name "NK 3 speed Sturmey Archer AW copy"}

   {:ratios [1.75 0.5] :name "NuVinci continuously variable N171"}
   {:ratios [1.80 0.5] :name "NuVinci continuously variable N180"}
   {:ratios [1.80 0.5] :name "NuVinci continuously variable N360"}
   {:ratios [1.90 0.5] :name "NuVinci continuously variable N380"}
   {:ratios [1.467 1.292 1.135 1 0.881 0.774 0.682 0.600 0.528 0.464 0.409 0.360 0.316 0.279] :name "Rohloff 14 speed Speedhub"}

   {:ratios [1.000 0.762] :name "Sachs 2 speed Doppel Torpedo"}
   {:ratios [1.362 1.000] :name "Sachs 2 speed Duomatic, Automatic"}
   {:ratios [1.362 1.000] :name "SRAM 2 speed Automatix"}
   {:ratios [1.000 0.738] :name "Sachs 2 speed w/6 cogs Orbit"}
   {:ratios [1.250 1 0.800] :name "Fichtel & Sachs 3 speed Universal Dreigang"}
   {:ratios [1.00 0.750 0.600] :name "Fichtel & Sachs 3 speed 25, 29"}
   {:ratios [1.333 1 0.750] :name "Fichtel & Sachs 3 speed 53, 55"}
   {:ratios [1.362 1.000 0.734] :name "(Fichtel &) Sachs 3 speed 415, H3102"}
   {:ratios [1.362 1.000 0.734] :name "(Fichtel &) Sachs 3 speed w/coaster brake 515, H3111"}
   {:ratios [1.362 1.000 0.734] :name "SRAM 3 speed Spectro T3 3105"}
   {:ratios [1.362 1.000 0.734] :name "SRAM 3 speed w/coaster brake Spectro T3 3115"}
   {:ratios [1.362 1.000 0.734] :name "SRAM 3 speed w/drum brake Spectro T3 3125"}
   {:ratios [1.362 1.000 0.734] :name "SRAM 3 speed w/8 or 9 speed cassette DualDrive 3x8, 3x9"}
   {:ratios [1.362 1.000 0.734] :name "Sachs 3 speed w/7 cog cassette 3x7"}
   {:ratios [1.333 1.00 0.750 0.600] :name "Fichtel & Sachs 4 speed Universal Torpedo"}
   {:ratios [1.50 1.286 1.000 0.778 0.667] :name "Sachs 5 speed Torpedo (old type)"}
   {:ratios [1.579 1.281 1.000 0.781 0.633] :name "Sachs 5 speed Pentasport P5/SRAM Spectro P5"}
   {:ratios [1.685 1.476 1.227 1.000 0.815 0.677 0.593] :name "Sachs 7 speed Torpedo (old type)"}
   {:ratios [1.742 1.476 1.236 1.000 0.809 0.677 0.574] :name "Sachs 7 speed Super 7/SRAM Spectro S7"}
   {:ratios [1.581 1.355 1.204 1.054 0.903 0.803 0.710 0.609] :name "SRAM 8 speed G8"}
   {:ratios [1.844 1.611 1.375 1.172 1 0.853 0.727 0.621 0.542] :name "SRAM 9 speed i Motion 9"}
   {:ratios [1.581 1.355 1.204 1.054 0.903 0.803 0.710 0.609 0.541] :name "SRAM 9 speed G9"}
   {:ratios [2.363 2.215 2.061 1.917 1.768 1.614 1.481 1.333 1.179 1.00 0.852 0.698] :name "Sachs 12 speed Elan/SRAM E12"}

   ;;{:ratios [1.579 1.281 1.000 0.781 0.633] :name "SRAM 5 speed Spectro P5"}
   ;;{:ratios [1.74 1.48 1.24 1.000 0.81 0.68 0.57] :name "Sachs/SRAM 7 speed Super 7"}

   {:ratios [2.793 2.376 2.031 1.708 1.454 1.242 1.057 0.897 0.769] :name "Pinion 9 speed P1.9CR bottom bracket"}
   {:ratios [3.117 2.525 2.031 1.621 1.311 1.054 0.842 0.682 0.550] :name "Pinion 9 speed P1.9XR bottom bracket"}
   {:ratios [3.297 2.793 2.376 2.031 1.715 1.450 1.235 1.054 0.890 0.755 0.643 0.550] :name "Pinion 12 speed P1.12 bottom bracket"}
   {:ratios [3.489 3.117 2.793 2.525 2.261 2.031 1.816 1.621 1.450 1.311 1.176 1.054 0.943 0.842 0.755 0.682 0.612 0.550] :name "Pinion 18 speed P1.18 bottom bracket"}

   {:ratios [2.5 1.00] :name "Schlumpf 2 speed High Speed Drive bottom bracket"}
   {:ratios [1.65 1.00] :name "Schlumpf 2 speed Speed Drive bottom bracket"}
   {:ratios [1.00 0.400] :name "Schlumpf 2 speed Mountain Drive bottom bracket"}

   {:ratios [1.333 1 0.750] :name "Shimano 3 speed (old) 333, F, FA, G, SG 3S21, G 3S23"}
   {:ratios [1.333 1 0.750] :name "Shimano 3 speed w/coaster brake (old) 3SC, 3CC"}
   {:ratios [1.364 1 0.733] :name "Shimano 3 speed Nexus Inter 3"}
   {:ratios [1.843 1.500 1.244 1.000] :name "Shimano 4 speed Nexus Inter 4"}
   {:ratios [1.545 1.335 1.159 1.001 0.750] :name "Shimano 5 speed Nexus Inter 5"}
   {:ratios [1.545 1.335 1.145 0.989 0.843 0.741 0.632] :name "Shimano 7 speed Nexus Inter 7"}
   {:ratios [1.615 1.419 1.223 1 0.851 0.748 0.644 0.527] :name "Shimano 8 speed Nexus, Alfine"}
   {:ratios [2.153 1.888 1.667 1.462 1.292 1.134 0.995 0.878 0.77 0.681 0.527] :name "Shimano 11 Speed Alfine"}

   {:ratios [1.333 1 0.75] :name "SunTour 3 speed Sturmey Archer AW copy"}
   {:ratios [1.333 1 0.75] :name "Steyr 3 speed Sturmey Archer AW copy"}
   {:ratios [1 0.714] :name "Sturmey Archer 2 speed S2 (1966)"}
   {:ratios [1.38 1.00] :name "Sturmey Archer 2 speed S2, S2C, B2C (2010)"}
   {:ratios [1 0.750] :name "Sturmey Archer 2 speed wide ratio T,TB,TBC,TBF,TBFC,TF"}
   {:ratios [1 0.8654] :name "Sturmey Archer 2 speed close ratio TC"}
   {:ratios [1.250 1 0.800] :name "Sturmey Archer 3 speed original 1902"}
   {:ratios [1.0724 1 0.9324] :name "Sturmey Archer 3 speed ultra close ratio AR"}
   {:ratios [1.067 1 0.923] :name "Sturmey Archer 3 speed close ratio AC"}
   {:ratios [1.1556 1 0.8654] :name "Sturmey Archer 3 speed medium ratio AM"}
   {:ratios [1.125 1 0.8889] :name "Sturmey Archer 3 speed close ratio KS"}
   {:ratios [1.167 1 0.857] :name "Sturmey Archer 3 speed medium ratio KSW"}
   {:ratios [1 0.90 0.75] :name "Sturmey Archer 3 speed fixed gear ASC"}
   {:ratios [1.00 0.750 0.625] :name "Sturmey Archer 3 speed fixed gear S3X"}
   {:ratios [1.313 1 0.762] :name "Sturmey Archer 3 speed A (not AW!), C, F, FN, FX, N, S, V, X"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed wide ratio AW, K, KB, KBC, KT, KTC"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed wide ratio RS RF3, S RF3"}
   {:ratios [1.385 1 0.722] :name "Sturmey Archer 3 speed super wide ratio SW"}
   {:ratios [1.568 1 0.638] :name "Sturmey Archer 3 speed special Brompton wide ratio"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/coaster brake AWC, S3C, S RC3, TCW"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/band brake, wide body SX RB3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/disk brake S RK3, SX RK3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/drum brake AB, AB/C, AB3, AT3, AWB"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/drum brake SAB, S3B, SBR, X RD3, RX RD3, XL RD3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed for 8/9 speed cassette CS RF3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed for 8/9 speed cassette w/disk brake CS RK3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed w/dyno AG"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed gearbox for tricycle w/reverse TS RF3"}
   {:ratios [1.333 1 0.75] :name "Sturmey Archer 3 speed gearbox for tricycle w/coaster TS RC3"}
   {:ratios [1.091 1 0.900 0.750] :name "Sturmey Archer 4 speed close ratio AF"}
   {:ratios [1.091 1 0.900 0.750] :name "Sturmey Archer 4 speed close ratio FC"}
   {:ratios [1.125 1 0.857 0.6667] :name "Sturmey Archer 4 speed medium ratio FM"}
   {:ratios [1.267 1 0.789 0.667] :name "Sturmey Archer 4 speed wide ratio FW"}
   {:ratios [1.267 1 0.789 0.667] :name "Sturmey Archer 4 speed wide ratio w/drum brake FB"}
   {:ratios [1.267 1 0.789 0.667] :name "Sturmey Archer 4 speed w/dyno FG"}
   {:ratios [1.5 1.125 1 0.857 0.6667] :name "Sturmey Archer 5 speed (FM modified)"}
   {:ratios [1.50 1.267 1 0.789 0.6667] :name "Sturmey Archer 5 speed S5, S5.1, S5/2, 5 Speed Alloy."}
   {:ratios [1.50 1.267 1 0.789 0.6667] :name "Sturmey Archer 5 speed 5 Star, Sprinter S5, S RF5, X RF5"}
   {:ratios [1.600 1.333 1 0.750 0.625] :name "Sturmey Archer 5 speed wide ratio (2009 ) S RF5(W), X RF5(W)"}
   {:ratios [1.50 1.267 1 0.789 0.6667] :name "Sturmey Archer 5 speed w/coaster brake Sprinter S5C, S RC5"}
   {:ratios [1.60 1.333 1 0.750 0.625] :name "Sturmey Archer 5 speed wide ratio w/coaster brake (2009 ) S5C(W), S RC5(W)"}
   {:ratios [1.60 1.333 1 0.750 0.625] :name "Sturmey Archer 3 speed gearbox for tricycle w/coaster QS RC5"}
   {:ratios [1.50 1.267 1 0.789 0.6667] :name "Sturmey Archer 5 speed w/drum brake 5 Star, AB 5, AT5, SAB 5, X RD5"}
   {:ratios [1.60 1.333 1 0.750 0.625] :name "Sturmey Archer 5 speed wide ratio w/drum brake (2009 ) X RD5 (W), XL RD5(W)"}
   {:ratios [1.667 1.450 1.243 1.000 804 0.690 0.600] :name "Sturmey Archer 7 speed Sprinter 7, X R7"}
   {:ratios [1.667 1.450 1.243 1.000 804 0.690 0.600] :name "Sturmey Archer 7 speed w/coaster brake Sprinter 7"}
   {:ratios [1.667 1.450 1.243 1.000 804 0.690 0.600] :name "Sturmey Archer 7 speed w/drum brake AT7, X RD7"}
   {:ratios [3.054 2.384 2.106 1.858 1.644 1.450 1.281 1] :name "Sturmey Archer 8 speed (2004 2008) X RF8"}
   {:ratios [3.239 2.485 2.186 1.931 1.677 1.481 1.303 1] :name "Sturmey Archer 8 speed wide ratio (2009 ) X RF8(W)"}
   {:ratios [3.054 2.384 2.106 1.858 1.644 1.450 1.281 1] :name "Sturmey Archer 8 speed w/drum brake (2004 2008) X RD8"}
   {:ratios [3.239 2.485 2.186 1.931 1.677 1.481 1.303 1] :name "Sturmey Archer 8 speed wide ratio w/drum brake (2009 ) X RD8(W)"}
   {:ratios [3.054 2.384 2.106 1.858 1.644 1.450 1.281 1] :name "Sturmey Archer 8 speed w/disk brake (2004 2008) X RK8"}
   {:ratios [3.239 2.485 2.186 1.931 1.677 1.481 1.303 1] :name "Sturmey Archer 8 speed wide ratio w/disk brake (2009 ) X RK8(W)"}
   {:ratios [3.054 2.384 2.106 1.858 1.644 1.450 1.281 1] :name "Sturmey Archer 8 speed w/roller brake (2004 2008) X RR8"}])


(def sprocket-clusters
;;; standard rear cluster configurations.
  [{:gears [] :name "Custom Sprocket(s)" :speeds nil :model ""}
   {:gears [11 12 13 14 16 18 21 24 28] :name "Harris" :speeds 9 :model "High & Wide"}
   {:gears [12 13 15 17 19 21 24 27 30] :name "Harris" :speeds 9 :model "Century 12"}
   {:gears [13 14 15 17 19 21 24 27 30] :name "Harris" :speeds 9 :model "Century Special"}
   {:gears [13 14 15 16 17 18 19 21 24] :name "Harris" :speeds 9 :model "Classic 9"}
   {:gears [13 15 17 19 21 24 27 30 34] :name "Harris" :speeds 9 :model "Cyclotouriste 13"}
   {:gears [14 15 17 19 21 24 27 30 34] :name "Harris" :speeds 9 :model "Cyclotouriste 14"}
   {:gears [10 12 14 16 18 21 24 28 32 36 42 50] :name "SRAM" :speeds 12 :model ""}
   {:gears [10 12 14 16 18 21 24 28 32 36 42 50] :name "Sunrace" :speeds 12 :model ""}
   {:gears [11 13 15 17 19 21 24 28 32 36 42 50] :name "Sunrace" :speeds 12 :model ""}
   {:gears [9 10 11 12 13 15 17 19 22 26 32] :name "3T" :speeds 11 :model "Bailout"}
   {:gears [9 11 12 13 15 17 19 22 25 28 32] :name "3T" :speeds 11 :model "Overdrive"}
   {:gears [11 12 13 14 15 16 17 18 19 21 23] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 19 21 23 25] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 25 27] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 26 29] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 22 25 28 32] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [12 13 14 15 16 17 18 19 21 23 25] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23 25 27] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23 26 29] :name "Campagnolo" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 18 19 21 23] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 19 21 23 25] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 25 28] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 27 30] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 12 13 14 16 18 20 22 25 28 32] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 13 15 17 19 21 23 25 27 30 34] :name "Shimano" :speeds 11 :model ""}
   {:gears [11 13 15 17 19 21 24 27 31 35 40] :name "Shimano" :speeds 11 :model "bs"}
   {:gears [11 13 15 17 19 21 24 28 32 37 42] :name "Shimano" :speeds 11 :model "bt"}
   {:gears [11 13 15 17 19 21 24 28 32 37 46] :name "Shimano" :speeds 11 :model "bu"}
   {:gears [12 13 14 15 16 17 18 19 21 23 25] :name "Shimano" :speeds 11 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23 25 28] :name "Shimano" :speeds 11 :model ""}
   {:gears [14 15 16 17 18 19 20 21 23 25 28] :name "Shimano" :speeds 11 :model ""}
   {:gears [10 12 14 16 18 21 24 28 32 36 42] :name "SRAM" :speeds 11 :model "XX1"}
   {:gears [11 12 13 14 15 16 17 19 21 23 25] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 19 21 23 26] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 19 22 25 28] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 27 30] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 22 25 28 32] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 12 13 15 17 19 22 25 28 32 36] :name "SRAM" :speeds 11 :model ""}
   {:gears [11 13 15 17 19 22 25 28 32 36 42] :name "SRAM" :speeds 11 :model ""}
   {:gears [10 12 14 16 18 21 24 28 32 36 42] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 25 28] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 28 32] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 12 13 15 17 19 21 24 28 32 36] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 13 15 17 19 21 24 28 32 36 40] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 13 15 17 19 21 24 28 32 36 42] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 13 15 18 21 24 28 32 36 42 46] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 13 15 18 21 24 28 32 36 42 50] :name "SunRace" :speeds 11 :model ""}
   {:gears [11 12 13 14 15 16 17 18 19 21] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 16 17 19 21 23] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 25] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 27] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 28] :name "Shimano" :speeds 10 :model "CSX13"}
   {:gears [11 12 14 16 18 20 22 25 28 32] :name "Shimano" :speeds 10 :model "bL"}
   {:gears [11 13 15 17 19 21 23 25 28 32] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 13 15 17 19 21 23 25 28 34] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 13 15 17 19 21 23 26 30 34] :name "Shimano" :speeds 10 :model "bj"}
   {:gears [11 13 15 17 19 21 24 28 32 36] :name "Shimano" :speeds 10 :model "bk"}
   {:gears [11 13 15 18 21 24 28 32 37 42] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 18 19 20 21] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 18 19 21 23] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23 25] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 19 21 24 27] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 17 19 21 23 25 28] :name "Shimano" :speeds 10 :model ""}
   {:gears [12 13 14 15 17 19 21 24 27 30] :name "Shimano" :speeds 10 :model ""}
   {:gears [13 14 15 16 17 18 19 21 23 26] :name "Shimano" :speeds 10 :model ""}
   {:gears [13 14 15 16 17 19 21 23 26 29] :name "Shimano" :speeds 10 :model ""}
   {:gears [15 16 17 18 19 20 21 22 23 25] :name "Shimano" :speeds 10 :model ""}
   {:gears [16 17 18 19 20 21 22 23 25 27] :name "Shimano" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 26] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23 25] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 27] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 22 25 28] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 14 15 17 19 22 25 28 32] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 14 16 18 21 26 28 32 36] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 13 15 17 19 22 25 28 32 36] :name "SRAM" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23 26] :name "SRAM" :speeds 10 :model ""}
   {:gears [12 13 14 15 16 17 19 21 24 27] :name "SRAM" :speeds 10 :model ""}
   {:gears [12 13 14 15 17 19 22 25 28 32] :name "SRAM" :speeds 10 :model ""}
   {:gears [12 13 15 17 19 22 25 28 32 36] :name "SRAM" :speeds 10 :model ""}
   {:gears [11 12 13 14 15 17 19 21 24 28] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 12 13 15 17 19 21 24 28 32] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 12 13 15 17 19 21 24 28 34] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 13 15 17 19 21 24 28 32 36] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 13 15 18 21 24 28 32 36 40] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 13 15 18 21 24 28 32 36 42] :name "SunRace" :speeds 10 :model ""}
   {:gears [11 13 15 18 21 24 28 34 40 46] :name "SunRace" :speeds 10 :model ""}
   {:gears [9 10 11 13 15 17 20 23 26] :name "Shimano" :speeds 9 :model "Capreo"}
   {:gears [11 12 13 14 15 16 17 19 21] :name "Shimano" :speeds 9 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23] :name "Shimano" :speeds 9 :model ""}
   {:gears [11 12 13 15 17 19 21 23 25] :name "Shimano" :speeds 9 :model "G"}
   {:gears [11 12 13 14 16 18 21 24 28] :name "Shimano" :speeds 9 :model "bg"}
   {:gears [11 12 14 16 18 20 23 26 30] :name "Shimano" :speeds 9 :model ""}
   {:gears [11 12 14 16 18 21 24 28 32] :name "Shimano" :speeds 9 :model "aq/ar/ba"}
   {:gears [11 13 15 17 19 21 24 28 32] :name "Shimano" :speeds 9 :model "bo"}
   {:gears [11 13 15 17 20 23 26 30 34] :name "Shimano" :speeds 9 :model "as/au/be/bn"}
   {:gears [11 13 15 17 20 23 26 30 36] :name "Shimano" :speeds 9 :model "cb/bw"}
   {:gears [12 13 14 15 16 17 18 19 21] :name "Shimano" :speeds 9 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23] :name "Shimano" :speeds 9 :model ""}
   {:gears [12 13 14 15 17 19 21 23 25] :name "Shimano" :speeds 9 :model ""}
   {:gears [12 13 14 15 17 19 21 24 27] :name "Shimano" :speeds 9 :model ""}
   {:gears [12 14 16 18 20 23 26 30 34] :name "Shimano" :speeds 9 :model "ap"}
   {:gears [12 14 16 18 21 24 28 32 36] :name "Shimano" :speeds 9 :model "bh"}
   {:gears [13 14 15 16 17 18 19 21 23] :name "Shimano" :speeds 9 :model ""}
   {:gears [13 14 15 16 17 19 21 23 25] :name "Shimano" :speeds 9 :model ""}
   {:gears [14 15 16 17 18 19 21 23 25] :name "Shimano" :speeds 9 :model "G"}
   {:gears [11 12 13 14 15 16 17 19 21] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 13 14 15 17 19 21 23] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 13 15 17 19 21 23 26] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 13 14 16 18 21 24 28] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 13 15 17 20 23 26 30] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 14 15 18 21 24 28 32] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 14 16 18 21 24 28 32] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 13 15 17 20 23 26 30 34] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 13 15 17 20 23 26 30 34] :name "SRAM" :speeds 9 :model ""}
   {:gears [12 13 14 15 16 17 19 21 23] :name "SRAM" :speeds 9 :model ""}
   {:gears [12 13 14 15 17 19 21 23 26] :name "SRAM" :speeds 9 :model ""}
   {:gears [11 12 13 15 17 19 21 23 25] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 12 13 14 16 18 21 24 28] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 12 14 16 18 21 24 28 32] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 12 14 16 18 21 24 28 34] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 13 15 18 21 24 28 32 36] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 13 15 18 21 24 28 34 40] :name "SunRace" :speeds 9 :model ""}
   {:gears [12 13 14 15 17 19 21 23 25] :name "SunRace" :speeds 9 :model ""}
   {:gears [11 12 14 16 18 21 24 28] :name "Shimano" :speeds 8 :model "R/ah"}
   {:gears [11 13 15 17 19 21 24 28] :name "Shimano" :speeds 8 :model "bf"}
   {:gears [11 13 15 17 20 23 26 30] :name "Shimano" :speeds 8 :model "ak/an"}
   {:gears [11 13 15 17 20 23 26 34] :name "Shimano" :speeds 8 :model "Megarange/ao"}
   {:gears [11 13 15 18 21 24 28 32] :name "Shimano" :speeds 8 :model "aw"}
   {:gears [11 13 15 18 21 24 28 34] :name "Shimano" :speeds 8 :model "CA"}
   {:gears [12 13 14 15 16 17 19 21] :name "Shimano" :speeds 8 :model "S"}
   {:gears [12 13 14 15 17 19 21 23] :name "Shimano" :speeds 8 :model "U"}
   {:gears [12 13 15 17 19 21 23 25] :name "Shimano" :speeds 8 :model "W"}
   {:gears [12 13 14 16 18 21 24 28] :name "Shimano" :speeds 8 :model "Q"}
   {:gears [12 14 16 18 21 24 28 32] :name "Shimano" :speeds 8 :model "P/br"}
   {:gears [13 14 15 16 17 19 21 23] :name "Shimano" :speeds 8 :model "T"}
   {:gears [13 14 15 17 19 21 23 26] :name "Shimano" :speeds 8 :model "V"}
   {:gears [13 15 17 19 21 23 26 30] :name "Shimano" :speeds 8 :model "8K7 13 30"}
   {:gears [13 15 17 19 21 23 26 32] :name "Shimano" :speeds 8 :model "8K7 13 32"}
   {:gears [13 15 17 19 21 23 26 34] :name "Shimano" :speeds 8 :model "8K7 13 34"}
   {:gears [11 12 14 16 18 21 24 28] :name "SRAM" :speeds 8 :model ""}
   {:gears [11 12 14 16 18 21 26 32] :name "SRAM" :speeds 8 :model ""}
   {:gears [11 13 15 17 20 23 26 30] :name "SRAM" :speeds 8 :model ""}
   {:gears [11 13 15 18 24 32 40 48] :name "SRAM" :speeds 8 :model ""}
   {:gears [12 13 14 15 17 19 21 23] :name "SRAM" :speeds 8 :model ""}
   {:gears [12 13 15 17 19 21 23 26] :name "SRAM" :speeds 8 :model ""}
   {:gears [11 12 13 15 17 19 21 23] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 12 14 16 18 21 24 28] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 12 15 18 21 24 28 32] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 13 15 18 21 24 28 32] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 13 15 18 21 24 28 34] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 13 15 18 22 28 34 40] :name "SunRace" :speeds 8 :model ""}
   {:gears [12 13 15 17 19 21 23 25] :name "SunRace" :speeds 8 :model ""}
   {:gears [12 14 16 18 21 24 28 32] :name "SunRace" :speeds 8 :model ""}
   {:gears [12 14 16 18 21 24 28 34] :name "SunRace" :speeds 8 :model ""}
   {:gears [11 12 13 14 15 17 19] :name "Shimano" :speeds 7 :model "ab"}
   {:gears [11 12 14 16 18 21 24] :name "Shimano" :speeds 7 :model "ai"}
   {:gears [11 13 15 18 21 24 28] :name "Shimano" :speeds 7 :model "ac, aj"}
   {:gears [11 13 15 18 21 24 30] :name "Shimano" :speeds 7 :model "am"}
   {:gears [11 13 15 18 21 24 34] :name "Shimano" :speeds 7 :model "MegaRange Freewheel"}
   {:gears [11 13 15 18 22 26 34] :name "Shimano" :speeds 7 :model "at, MegaRange Cassette"}
   {:gears [12 13 14 15 17 19 21] :name "Shimano" :speeds 7 :model "L"}
   {:gears [12 14 16 18 21 24 28] :name "Shimano" :speeds 7 :model "B , E , bm"}
   {:gears [12 14 16 18 21 26 32] :name "Shimano" :speeds 7 :model "bp"}
   {:gears [13 14 15 16 17 19 21] :name "Shimano" :speeds 7 :model "J"}
   {:gears [13 14 15 17 19 21 23] :name "Shimano" :speeds 7 :model "I"}
   {:gears [13 15 17 19 21 23 26] :name "Shimano" :speeds 7 :model "H"}
   {:gears [13 15 17 19 21 24 28] :name "Shimano" :speeds 7 :model "M"}
   {:gears [13 15 17 20 23 26 30] :name "Shimano" :speeds 7 :model "G"}
   {:gears [13 15 17 20 24 29 34] :name "Shimano" :speeds 7 :model "K"}
   {:gears [14 16 18 20 22 24 28] :name "Shimano" :speeds 7 :model "Freewheel "}
   {:gears [14 16 18 21 24 28 32] :name "Shimano" :speeds 7 :model "D , F"}
   {:gears [10 12 14 16 18 21 24] :name "SRAM" :speeds 7 :model ""}
   {:gears [12 14 16 18 21 26 32] :name "SRAM" :speeds 7 :model ""}
   {:gears [11 12 14 16 18 21 24] :name "SunRace" :speeds 7 :model ""}
   {:gears [11 13 15 17 19 21 24] :name "SunRace" :speeds 7 :model ""}
   {:gears [11 13 15 18 21 24 28] :name "SunRace" :speeds 7 :model ""}
   {:gears [11 13 15 18 21 24 34] :name "SunRace" :speeds 7 :model ""}
   {:gears [11 13 15 18 21 24 34] :name "SunRace" :speeds 7 :model ""}
   {:gears [12 13 14 16 18 21 24] :name "SunRace" :speeds 7 :model ""}
   {:gears [12 14 16 18 21 24 28] :name "SunRace" :speeds 7 :model ""}
   {:gears [13 15 17 19 21 23 25] :name "SunRace" :speeds 7 :model "Freewheel"}
   {:gears [14 16 18 20 22 24 28] :name "SunRace" :speeds 7 :model "Freewheel"}])



(def crank-lengths
  [100
   105
   110
   115
   120
   125
   130
   135
   140
   145
   150
   152
   155
   158
   160
   162.5
   165
   167.5
   170
   172.5
   175
   177.5
   180
   185
   190])

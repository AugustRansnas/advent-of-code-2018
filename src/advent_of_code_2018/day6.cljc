(ns advent-of-code-2018.day6
  (:require [ysera.test :refer [is= is is-not]]
            [clojure.string :as str]))

(def puzzle-input "336, 308\n262, 98\n352, 115\n225, 205\n292, 185\n166, 271\n251, 67\n266, 274\n326, 85\n191, 256\n62, 171\n333, 123\n160, 131\n211, 214\n287, 333\n231, 288\n237, 183\n211, 272\n116, 153\n336, 70\n291, 117\n156, 105\n261, 119\n216, 171\n59, 343\n50, 180\n251, 268\n169, 258\n75, 136\n305, 102\n154, 327\n187, 297\n270, 225\n190, 185\n339, 264\n103, 301\n90, 92\n164, 144\n108, 140\n189, 211\n125, 157\n77, 226\n177, 168\n46, 188\n216, 244\n346, 348\n272, 90\n140, 176\n109, 324\n128, 132")

(def test-input "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9")


(defn parse-line
  [line]
  (->> line
       (re-matches #"(\d+), (\d+)")
       rest
       (map (fn [s]
              (Integer/parseInt s)))))

(defn parse-input
  [input]
  (->> input
       (str/split-lines)
       (map (fn [line]
              (parse-line line)
              ))))

(parse-input test-input)
(parse-input puzzle-input)

(defn manhattan-distance [u v]
  (reduce +
          (map (fn [[a b]] (Math/abs (- a b)))
               [[(first u) (first v)] [(last u) (last v)]])))


(defn sort-coordinates-by-first
  [coordinates]
  (->> coordinates
       (map first)
       (sort)))

(defn sort-coordinates-by-second
  [coordinates]
  (->> coordinates
       (map second)
       (sort)))


(defn add-keys-to-coordinates
  [coordinates]
  (map-indexed (fn [index coordinates]
                 {:key index :coordinates coordinates}) coordinates))

(defn get-key-for-coordinate
  [coordinate coordinates]
  (let [match (->> coordinates
                   (filter (fn [c]
                             (= (:coordinates c) coordinate)
                             ))
                   (first))]
    (if match
      (:key match)
      "unknown")))

(defn draw-area
  {:test (fn []
           (is= (draw-area (parse-input test-input)) [{:key 0 :coordinates [1 1]} {:key "unknown" :coordinates [1 2]} {:key "unknown" :coordinates [1 3]} {:key "unknown" :coordinates [1 4]} {:key "unknown" :coordinates [1 5]} {:key 1 :coordinates [1 6]} {:key "unknown" :coordinates [1 7]} {:key "unknown" :coordinates [1 8]} {:key "unknown" :coordinates [1 9]} {:key "unknown" :coordinates [2 1]} {:key "unknown" :coordinates [2 2]} {:key "unknown" :coordinates [2 3]} {:key "unknown" :coordinates [2 4]} {:key "unknown" :coordinates [2 5]} {:key "unknown" :coordinates [2 6]} {:key "unknown" :coordinates [2 7]} {:key "unknown" :coordinates [2 8]} {:key "unknown" :coordinates [2 9]} {:key "unknown" :coordinates [3 1]} {:key "unknown" :coordinates [3 2]} {:key "unknown" :coordinates [3 3]} {:key 3 :coordinates [3 4]} {:key "unknown" :coordinates [3 5]} {:key "unknown" :coordinates [3 6]} {:key "unknown" :coordinates [3 7]} {:key "unknown" :coordinates [3 8]} {:key "unknown" :coordinates [3 9]} {:key "unknown" :coordinates [4 1]} {:key "unknown" :coordinates [4 2]} {:key "unknown" :coordinates [4 3]} {:key "unknown" :coordinates [4 4]} {:key "unknown" :coordinates [4 5]} {:key "unknown" :coordinates [4 6]} {:key "unknown" :coordinates [4 7]} {:key "unknown" :coordinates [4 8]} {:key "unknown" :coordinates [4 9]} {:key "unknown" :coordinates [5 1]} {:key "unknown" :coordinates [5 2]} {:key "unknown" :coordinates [5 3]} {:key "unknown" :coordinates [5 4]} {:key 4 :coordinates [5 5]} {:key "unknown" :coordinates [5 6]} {:key "unknown" :coordinates [5 7]} {:key "unknown" :coordinates [5 8]} {:key "unknown" :coordinates [5 9]} {:key "unknown" :coordinates [6 1]} {:key "unknown" :coordinates [6 2]} {:key "unknown" :coordinates [6 3]} {:key "unknown" :coordinates [6 4]} {:key "unknown" :coordinates [6 5]} {:key "unknown" :coordinates [6 6]} {:key "unknown" :coordinates [6 7]} {:key "unknown" :coordinates [6 8]} {:key "unknown" :coordinates [6 9]} {:key "unknown" :coordinates [7 1]} {:key "unknown" :coordinates [7 2]} {:key "unknown" :coordinates [7 3]} {:key "unknown" :coordinates [7 4]} {:key "unknown" :coordinates [7 5]} {:key "unknown" :coordinates [7 6]} {:key "unknown" :coordinates [7 7]} {:key "unknown" :coordinates [7 8]} {:key "unknown" :coordinates [7 9]} {:key "unknown" :coordinates [8 1]} {:key "unknown" :coordinates [8 2]} {:key 2 :coordinates [8 3]} {:key "unknown" :coordinates [8 4]} {:key "unknown" :coordinates [8 5]} {:key "unknown" :coordinates [8 6]} {:key "unknown" :coordinates [8 7]} {:key "unknown" :coordinates [8 8]} {:key 5 :coordinates [8 9]}])
           )}
  [input]
  (let [x-coordinates (sort-coordinates-by-first input)
        y-coordinates (sort-coordinates-by-second input)
        min-x (first x-coordinates)
        max-x (+ 1 (last x-coordinates))
        min-y (first y-coordinates)
        max-y (+ 1 (last y-coordinates))
        input-with-keys (add-keys-to-coordinates input)]
    (->> (for [x (range min-x  max-x)
               y (range min-y max-y)]
           [x y])
         (map (fn [coordinate]
                {:key (get-key-for-coordinate coordinate input-with-keys) :coordinates coordinate})))))

(draw-area (parse-input test-input))

(defn get-main-coordinates
  [area-with-coordinates]
  (->> area-with-coordinates
       (filter (fn [c]
                 (not (= (:key c) "unknown"))))))

(defn get-shortest-distance-key
  [shortest-manhattan-distance manhattan-distances]
  (let [counts (count (filter (fn [distance]
                                (= (:manhattan-distance distance) (:manhattan-distance shortest-manhattan-distance))) manhattan-distances))]
    (if (> counts 1)
      "."
      (:key shortest-manhattan-distance))))


(defn mark-all-distances
  [area-with-coordinates]
  (map (fn [coordinate]
         (let [manhattan-distances (->> (get-main-coordinates area-with-coordinates)
                                        (map (fn [main-coordinate]
                                               (if (= (:coordinates main-coordinate) (:coordinates coordinate))
                                                 {:key (:key main-coordinate) :manhattan-distance 0}
                                                 {:key (:key main-coordinate) :manhattan-distance (manhattan-distance (:coordinates coordinate) (:coordinates main-coordinate))})))
                                        (sort-by :manhattan-distance))]
           {:key         (get-shortest-distance-key (first manhattan-distances) manhattan-distances)
            :coordinates (:coordinates coordinate)})
         ) area-with-coordinates))

(defn remove-infinites
  [input coordinates]
  (let [x-coordinates (sort-coordinates-by-first input)
        y-coordinates (sort-coordinates-by-second input)
        min-x (first x-coordinates)
        max-x (last x-coordinates)
        min-y (first y-coordinates)
        max-y (last y-coordinates)
        keys-to-remove (->> coordinates
                            (filter (fn [coordinate]
                                      (let [c (:coordinates coordinate)]
                                        (or (= (first c) min-x)
                                            (= (first c)  max-x)
                                            (= (last c) min-y)
                                            (= (last c) max-y)))))
                            (map :key)
                            (set))]



    (remove (fn [coordinate]
              (contains? keys-to-remove (:key coordinate))
              ) coordinates)))

(defn day6-a
  [input]
  (->> input
       (draw-area)
       (mark-all-distances)
       (remove-infinites input)
       (map :key)
       (frequencies)
       (sort-by second)))

(day6-a (parse-input test-input))

;;(day6-a (parse-input puzzle-input))


(defn day6-b
  [input]
  (as-> input $
       (draw-area $)
       (reduce (fn [result coordinate]
                 (let [main-coordinates (get-main-coordinates $)
                       all-distances (reduce + (map (fn [mc]
                                        (manhattan-distance (:coordinates coordinate) (:coordinates mc))
                                        ) main-coordinates))]
                   (prn "calculating..." result)
                   (if (< all-distances 10000)
                     (+ result 1)
                     result)
                   )) 0 $)
       ))

(day6-b (parse-input test-input))

;;(day6-b (parse-input puzzle-input))



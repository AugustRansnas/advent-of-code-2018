(ns advent-of-code-2018.day6
  (:require [ysera.test :refer [is= is is-not]]
            [clojure.string :as str]))

(def test-input [[1 1]
                 [1 6]
                 [8 3]
                 [3 4]
                 [5 5]
                 [8 9]])


;(defn parse-input
;  [input]
;  (as-> input $
;        (str/split-lines $)
;        (map #(str/replace % #"" "") $)
;        (prn $)
;        (map read-string $)
;        (prn $))
;  )
;
;(parse-input test-input)

(defn manhattan-distance [u v]
  (reduce +
          (map (fn [[a b]] (Math/abs (- a b)))
               [u v])))


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
           (is= (draw-area test-input) [{:key 0 :coordinates [1 1]} {:key "unknown" :coordinates [1 2]} {:key "unknown" :coordinates [1 3]} {:key "unknown" :coordinates [1 4]} {:key "unknown" :coordinates [1 5]} {:key 1 :coordinates [1 6]} {:key "unknown" :coordinates [1 7]} {:key "unknown" :coordinates [1 8]} {:key "unknown" :coordinates [1 9]} {:key "unknown" :coordinates [2 1]} {:key "unknown" :coordinates [2 2]} {:key "unknown" :coordinates [2 3]} {:key "unknown" :coordinates [2 4]} {:key "unknown" :coordinates [2 5]} {:key "unknown" :coordinates [2 6]} {:key "unknown" :coordinates [2 7]} {:key "unknown" :coordinates [2 8]} {:key "unknown" :coordinates [2 9]} {:key "unknown" :coordinates [3 1]} {:key "unknown" :coordinates [3 2]} {:key "unknown" :coordinates [3 3]} {:key 3 :coordinates [3 4]} {:key "unknown" :coordinates [3 5]} {:key "unknown" :coordinates [3 6]} {:key "unknown" :coordinates [3 7]} {:key "unknown" :coordinates [3 8]} {:key "unknown" :coordinates [3 9]} {:key "unknown" :coordinates [4 1]} {:key "unknown" :coordinates [4 2]} {:key "unknown" :coordinates [4 3]} {:key "unknown" :coordinates [4 4]} {:key "unknown" :coordinates [4 5]} {:key "unknown" :coordinates [4 6]} {:key "unknown" :coordinates [4 7]} {:key "unknown" :coordinates [4 8]} {:key "unknown" :coordinates [4 9]} {:key "unknown" :coordinates [5 1]} {:key "unknown" :coordinates [5 2]} {:key "unknown" :coordinates [5 3]} {:key "unknown" :coordinates [5 4]} {:key 4 :coordinates [5 5]} {:key "unknown" :coordinates [5 6]} {:key "unknown" :coordinates [5 7]} {:key "unknown" :coordinates [5 8]} {:key "unknown" :coordinates [5 9]} {:key "unknown" :coordinates [6 1]} {:key "unknown" :coordinates [6 2]} {:key "unknown" :coordinates [6 3]} {:key "unknown" :coordinates [6 4]} {:key "unknown" :coordinates [6 5]} {:key "unknown" :coordinates [6 6]} {:key "unknown" :coordinates [6 7]} {:key "unknown" :coordinates [6 8]} {:key "unknown" :coordinates [6 9]} {:key "unknown" :coordinates [7 1]} {:key "unknown" :coordinates [7 2]} {:key "unknown" :coordinates [7 3]} {:key "unknown" :coordinates [7 4]} {:key "unknown" :coordinates [7 5]} {:key "unknown" :coordinates [7 6]} {:key "unknown" :coordinates [7 7]} {:key "unknown" :coordinates [7 8]} {:key "unknown" :coordinates [7 9]} {:key "unknown" :coordinates [8 1]} {:key "unknown" :coordinates [8 2]} {:key 2 :coordinates [8 3]} {:key "unknown" :coordinates [8 4]} {:key "unknown" :coordinates [8 5]} {:key "unknown" :coordinates [8 6]} {:key "unknown" :coordinates [8 7]} {:key "unknown" :coordinates [8 8]} {:key 5 :coordinates [8 9]}])
           )}
  [input]
  (let [x-coordinates (sort-coordinates-by-first input)
        y-coordinates (sort-coordinates-by-second input)
        min-x (first x-coordinates)
        max-x (last x-coordinates)
        min-y (first y-coordinates)
        max-y (last y-coordinates)
        input-with-keys (add-keys-to-coordinates input)]
    (->> (for [x (range min-x (+ 1 max-x))
               y (range min-y (+ 1 max-y))]
           [x y])
         (map (fn [coordinate]
                {:key (get-key-for-coordinate coordinate input-with-keys) :coordinates coordinate})))))

(draw-area test-input)

(defn main-coordinates
  [area-with-coordinates]
  (filter (fn [coordinate]
            (not (= (:key coordinate) "unknown"))) area-with-coordinates))


(defn find-key-for-closest-manhattan-distance
  [coordinate main-coordinates]
  (let [manhattan-distances (->> main-coordinates
                                 (map (fn [main-coordinate]
                                        {:key (:key main-coordinate) :manhattan-distance (manhattan-distance (:coordinates coordinate) (:coordinates main-coordinate))}))
                                 (sort-by :manhattan-distance))
        shortest-manhattan-distance (first manhattan-distances)]
    (if (> 0 (count (filter (fn [distance]
                              (= (:key distance) (:key shortest-manhattan-distance))) manhattan-distances)))
      "."
      (:key shortest-manhattan-distance))))



(defn mark-all-distances
  [area-with-coordinates]
  (map (fn [coordinate]
         {:key         (find-key-for-closest-manhattan-distance coordinate (main-coordinates area-with-coordinates))
          :coordinates (:coordinates coordinate)}
         ) area-with-coordinates))


(defn day6-a
  [input]
  (->> input
       (draw-area)
       (mark-all-distances)))

(day6-a test-input)


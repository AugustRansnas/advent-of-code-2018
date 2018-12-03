(ns advent-of-code-2018.day3
  (:require [ysera.test :refer [is= is is-not]]
            [advent-of-code-2018.core :as core]
            [clojure.string :as str]))

(def puzzle-input (core/slurp-puzzle-input "C:\\clojure\\advent-of-code-2018\\src\\advent_of_code_2018\\puzzle_inputs\\day3.txt"))

(def parsed-puzzle-input
  (-> puzzle-input
      (str/split #"\n")))

(defn parse-int [values]
  (map (fn [val]
         (Integer/parseInt val)) values))

(defn map-input
  [input]
  (->> input
       (map (fn [claim]
              {:id       (first (re-find #"(?<=\#)(.*?)(?= \@)" claim))
               :position (parse-int (str/split (first (re-find #"(?<=\@ )(.*?)(?=\:)" claim)) #","))
               :size     (parse-int (str/split (first (re-find #"(?<=\: )(.*)" claim)) #"x"))}
              ))
       ))

(defn width
  [rect]
  (first (:size rect)))

(defn height
  [rect]
  (last (:size rect)))

(defn left
  [rect]
  (first (:position rect)))

(defn right
  [rect]
  (+ (left rect) (width rect)))

(defn top
  [rect]
  (last (:position rect)))

(defn bottom
  [rect]
  (+ (top rect) (height rect)))

(defn draw-rect
  {:test (fn []
          (is= (draw-rect {:id 1 :position [1 3] :size [4 4]}) [[1 3] [1 4] [1 5] [1 6] [2 3] [2 4] [2 5] [2 6] [3 3] [3 4] [3 5] [3 6] [4 3] [4 4] [4 5] [4 6]])
           )}
  [rect]
  (->> (for [x (range (left rect) (right rect))
             y (range (top rect) (bottom rect))]
         [x y]))
  )

(defn day3-a
  {:test (fn []
           (is= (day3-a ["#1 @ 1,3: 4x4" "#2 @ 3,1: 4x4" "#3 @ 5,5: 2x2"]) 4))}
  [puzzle-input]
  (->> puzzle-input
       (map-input)
       (map draw-rect)
       (reduce concat)
       (frequencies)
       (filter (fn [[_ val]]
                 (> val 1)))
       (count)))

(day3-a parsed-puzzle-input)








(ns advent-of-code-2018.day2
  (:require [ysera.test :refer [is= is is-not]]
            [advent-of-code-2018.core :as core]
            [clojure.string :as str]))

(def puzzle-input (core/slurp-puzzle-input "C:\\clojure\\advent-of-code-2018\\src\\advent_of_code_2018\\puzzle_inputs\\day2.txt"))

(def parsed-puzzle-input
  (-> puzzle-input
      (str/split #"\n")))

(defn day2-a
  {:test (fn []
           (is= (day2-a ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]) 12))
   }
  [puzzle]
  (->> puzzle
       (map #(frequencies %))
       (map (fn [frequency]
              (->> frequency
                   (filter (fn [[_ value]]
                             (and (> value 1) (< value 4))))
                   (vals)
                   (distinct))
              ))
       (flatten)
       (frequencies)
       (vals)
       (reduce *)))

(day2-a parsed-puzzle-input)


(defn hamming-distance
  [x y]
  (count (filter true? (map (partial reduce not=) (map vector x y)))))

(defn day2-b
  {:test (fn []
           (is= (day2-b ["abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"]) "fgij"))}
  [puzzle]
  (loop [box-ids puzzle]
    (let [match (->> box-ids
                     (filter (fn [box-id]
                               (= (hamming-distance (first box-ids) box-id) 1)))
                     (first))]
      (if match
        (->> (map vector (first box-ids) match)
             (filter #(apply = %))
             (map first)
             (apply str))
        (recur (next box-ids))))))

(day2-b parsed-puzzle-input)



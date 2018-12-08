(ns advent-of-code-2018.day7
  (:require [ysera.test :refer [is= is is-not]]
            [clojure.string :as str]
            [weavejester.dependency :as dep]))

(def puzzle-input "Step S must be finished before step C can begin.\nStep C must be finished before step R can begin.\nStep L must be finished before step W can begin.\nStep V must be finished before step B can begin.\nStep P must be finished before step Y can begin.\nStep M must be finished before step B can begin.\nStep Y must be finished before step J can begin.\nStep W must be finished before step T can begin.\nStep N must be finished before step I can begin.\nStep H must be finished before step O can begin.\nStep O must be finished before step T can begin.\nStep Q must be finished before step X can begin.\nStep T must be finished before step K can begin.\nStep A must be finished before step D can begin.\nStep G must be finished before step K can begin.\nStep D must be finished before step X can begin.\nStep R must be finished before step J can begin.\nStep U must be finished before step B can begin.\nStep K must be finished before step J can begin.\nStep B must be finished before step J can begin.\nStep J must be finished before step E can begin.\nStep E must be finished before step Z can begin.\nStep F must be finished before step I can begin.\nStep X must be finished before step Z can begin.\nStep Z must be finished before step I can begin.\nStep E must be finished before step F can begin.\nStep R must be finished before step I can begin.\nStep L must be finished before step Z can begin.\nStep N must be finished before step O can begin.\nStep O must be finished before step D can begin.\nStep K must be finished before step I can begin.\nStep R must be finished before step F can begin.\nStep T must be finished before step F can begin.\nStep N must be finished before step G can begin.\nStep M must be finished before step D can begin.\nStep F must be finished before step X can begin.\nStep S must be finished before step D can begin.\nStep Q must be finished before step F can begin.\nStep L must be finished before step R can begin.\nStep J must be finished before step F can begin.\nStep L must be finished before step T can begin.\nStep M must be finished before step H can begin.\nStep D must be finished before step F can begin.\nStep W must be finished before step B can begin.\nStep C must be finished before step A can begin.\nStep E must be finished before step I can begin.\nStep P must be finished before step Q can begin.\nStep A must be finished before step B can begin.\nStep P must be finished before step R can begin.\nStep C must be finished before step J can begin.\nStep Y must be finished before step K can begin.\nStep C must be finished before step L can begin.\nStep E must be finished before step X can begin.\nStep X must be finished before step I can begin.\nStep A must be finished before step G can begin.\nStep M must be finished before step E can begin.\nStep C must be finished before step T can begin.\nStep C must be finished before step Y can begin.\nStep K must be finished before step E can begin.\nStep H must be finished before step D can begin.\nStep P must be finished before step K can begin.\nStep D must be finished before step R can begin.\nStep J must be finished before step X can begin.\nStep H must be finished before step Z can begin.\nStep M must be finished before step R can begin.\nStep V must be finished before step U can begin.\nStep K must be finished before step B can begin.\nStep L must be finished before step Q can begin.\nStep Y must be finished before step I can begin.\nStep T must be finished before step G can begin.\nStep U must be finished before step E can begin.\nStep S must be finished before step Q can begin.\nStep P must be finished before step G can begin.\nStep P must be finished before step M can begin.\nStep N must be finished before step J can begin.\nStep P must be finished before step O can begin.\nStep U must be finished before step J can begin.\nStep C must be finished before step N can begin.\nStep W must be finished before step R can begin.\nStep B must be finished before step Z can begin.\nStep F must be finished before step Z can begin.\nStep O must be finished before step E can begin.\nStep W must be finished before step N can begin.\nStep A must be finished before step I can begin.\nStep W must be finished before step J can begin.\nStep R must be finished before step E can begin.\nStep N must be finished before step B can begin.\nStep M must be finished before step U can begin.\nStep B must be finished before step E can begin.\nStep V must be finished before step J can begin.\nStep O must be finished before step I can begin.\nStep Q must be finished before step T can begin.\nStep Q must be finished before step U can begin.\nStep L must be finished before step V can begin.\nStep S must be finished before step Z can begin.\nStep C must be finished before step P can begin.\nStep P must be finished before step A can begin.\nStep S must be finished before step G can begin.\nStep N must be finished before step H can begin.\nStep V must be finished before step H can begin.\nStep B must be finished before step I can begin.")
(def test-input "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin.")

(defn parse-line
  [line]
  (->> line
       (re-matches #"Step (.*) must be finished before step (.*) can begin.")
       rest))

(defn parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line))
  )

(defn lexical-comparator
  [x y]
  (compare y x))

(defn make-dependency-graph
  "Makes a dependency graph from the given collection of tasks"
  [coll]
  (reduce (fn [g x] (dep/depend g (first x) (last x)))
          (dep/graph)
          coll))

(defn day7-a
  [input]
  (->> input
       (parse-input)
       (make-dependency-graph)
       (dep/topo-sort lexical-comparator)
       (reverse)
       (str/join)))

(day7-a test-input)
(day7-a puzzle-input)

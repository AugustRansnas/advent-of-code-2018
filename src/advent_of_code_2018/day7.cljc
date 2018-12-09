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


(defn time-to-complete-letter
  [letter]
  (- (int letter) 64))

(defn get-finished-letters
  [working-on]
  (set (filter (fn [letter]
                 (= (:time-worked-on letter) (time-to-complete-letter (:letter letter)))
                 ) working-on)))


(defn get-new-work-and-queue
  [schedule completed-work]
  (let [interim-wl (->> (:working-on schedule)
                     (remove (fn [letter]
                               (contains? completed-work (:letter letter))
                               ))
                     (map (fn [letter]
                            {:time-worked-on (+ (:time-worked-on letter) 1)
                             :letter         (:letter letter)})))]
    (if (< (count interim-wl) 5)
      {:working-on (conj interim-wl (take (- 5 (count interim-wl)) (:queue schedule)))
       :queue (take (- 5 (count interim-wl)) (:queue schedule))}
      {:working-on interim-wl
       :queue (:queue schedule)})))

(defn update-schedule
  [schedule]
  (let [completed-work (get-finished-letters (:working-on schedule))
        work-and-queue  (get-new-work-and-queue schedule completed-work)]
    (prn "completed work:" completed-work)
    (prn "work-and-queue:" work-and-queue)
    {:elapsed-time (+ (:elapsed-time schedule) 1)
     :working-on  (:working-on work-and-queue)
     :queue  (:queue work-and-queue)
     :done-work (concat (:done-work schedule) completed-work)
     }))

(defn letter-is-done?
  [letter done-work]
  (true? (filter (fn [done-letter]
                   (= done-letter (str letter))
                   ) done-work)))

(defn day7-b
  [input]
  (let [dependency-map (->> input
                            (parse-input)
                            (make-dependency-graph))
        sorted-dependencies (day7-a input)]
    (loop [letter sorted-dependencies
           schedule {:elapsed-time 0
                     :working-on   #{{:time-worked-on 0 :letter  (first letter)}}
                     :queue        #{}
                     :done-work    []}]

      (let [updated-schedule-with-queue (update schedule :queue (fn [old-value]
                                                                 (clojure.set/union old-value (map (fn [dep]
                                                                                                     {:time-worked-on 0
                                                                                                      :letter  dep}) (dep/immediate-dependencies dependency-map (str (first letter)))) )))]

        (if (letter-is-done? (first letter) (:done-work schedule))
          (recur (next letter) (update-schedule updated-schedule-with-queue))
          (recur (first letter) (update-schedule updated-schedule-with-queue)))))))

(day7-b test-input)

;;(day7-b puzzle-input)
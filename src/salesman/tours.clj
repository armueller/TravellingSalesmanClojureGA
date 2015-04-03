(ns salesman.tours
  (:require [salesman.cities :as cities]
            [clojure.string :refer (join)]))

(defn createTour
  "Create a random tour of the cities"
  [cities]
  (shuffle cities))

(defn createEmptyTour
  "Create an empty tour"
  [size]
  (loop [emptyTour []]
    (if (< (count emptyTour) size)
      (recur (conj emptyTour {}))
      emptyTour)))

(defn calcTourDistance
  "Calculates the total distance of a tour"
  [tour]
  (let [tourSize (count tour)]
    (loop [i 0 sum 0]
      (if (< i (- tourSize 1))
        (recur (inc i) (+ sum (cities/calcDistance (get tour i) (get tour (+ i 1)))))
        (+ sum (cities/calcDistance (get tour i) (get tour 0)))))))

(defn calcTourFitness
  [tour]
  (/ 1 (calcTourDistance tour)))

(defn tourContainsCity?
  [tour city]
  (let [tourSize (count tour)]
    (loop [idx 0]
      (if (< idx tourSize)
        (let [tourCity (get tour idx)]
          (if (and (= (get tourCity :name) (get city :name))
                   (= (get tourCity :x) (get city :x))
                   (= (get tourCity :y) (get city :y)))
            true
            (recur (inc idx))))
        false))))
      

(defn printTour
  [tour]
  (let [tourDistance (calcTourDistance tour)
        tourFitness (calcTourFitness tour)]
    (println "Tour Distance: " tourDistance)
    (println "Tour Fitness: " tourFitness)
    (println "Tour Path: " (join ", " (map :name tour)))))
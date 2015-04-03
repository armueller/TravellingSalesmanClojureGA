(ns salesman.ga
  (:require [salesman.cities :as cities]
            [salesman.tours :as tours]
            [salesman.population :as population]))

(def mutationRate 0.015)
(def tournamentSize 5)
(def elitism true)

(defn crossParentTour1
  [parentTour1 childTour crossBounds]
  (let [tourSize  (count parentTour1)]
    (loop [newChildTour childTour idx 0]
      (if (< idx tourSize)
        (if (< (get crossBounds :start) (get crossBounds :end))
          (if (and (>= idx (get crossBounds :start)) (< idx (get crossBounds :end)))
            (recur (assoc newChildTour idx (get parentTour1 idx)) (inc idx))
            (recur newChildTour (inc idx)))
          (if (or (>= idx (get crossBounds :start)) (< idx (get crossBounds :end)))
            (recur (assoc newChildTour idx (get parentTour1 idx)) (inc idx))
            (recur newChildTour (inc idx))))
        newChildTour))))

(defn insertNextCityForChild
  "Inserts city into the next empty tour location.  If tour already contains city, then tour is not altered."
  [childTour city]
  (let [tourSize  (count childTour)]
    (loop [newChildTour childTour idx 0]
      (if (< idx tourSize)
        (if (and (empty? (get newChildTour idx)) (not (tours/tourContainsCity? newChildTour city)))
          (assoc newChildTour idx city)
          (recur newChildTour (inc idx)))
        newChildTour))))

(defn crossParentTour2
  [parentTour2 childTour]
  (let [tourSize  (count parentTour2)]
    (loop [newChildTour childTour idx 0]
      (if (< idx tourSize)
        (recur (insertNextCityForChild newChildTour (get parentTour2 idx)) (inc idx))
        newChildTour))))

(defn crossover
  [parentTour1 parentTour2]
  (let [tourSize  (count parentTour1)
        childTour (tours/createEmptyTour tourSize)
        crossBounds {:start (rand-int tourSize) :end (rand-int tourSize)}]
    (crossParentTour2 parentTour2 (crossParentTour1 parentTour1 childTour crossBounds))))

(defn swap 
  [v i1 i2]
  (assoc v i2 (v i1) i1 (v i2)))

(defn mutateTour
  [tour]
  (let [tourSize (count tour)]
    (loop [newTour tour idx 0]
      (if (< idx tourSize)
        (if (< (rand) mutationRate)
          (recur (swap newTour idx (rand-int tourSize)) (inc idx))
          (recur newTour (inc idx)))
        newTour))))

(defn tournamentSelection
  [population]
  (let [populationSize (count population)]
    (loop [tournament []]
      (if (< (count tournament) tournamentSize)
        (recur (conj tournament (get population (rand-int populationSize))))
        (population/findFittestTourInPopulation tournament)))))

(defn crossoverPopulation
  [population]
  (let [populationSize (count population)]
    (loop [newPopulation (if elitism [(population/findFittestTourInPopulation population)] [])]
      (if (< (count newPopulation) populationSize)
        (let [parentTour1 (tournamentSelection population)
              parentTour2 (tournamentSelection population)]
          (recur (conj newPopulation (crossover parentTour1 parentTour2))))
        newPopulation))))
    

(defn evolvePopulation
  [population]
  (let [newPopulation (crossoverPopulation population)]
    (into [] (map mutateTour newPopulation))))

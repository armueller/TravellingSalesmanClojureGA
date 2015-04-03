(ns salesman.population
  (:require [salesman.cities :as cities]
            [salesman.tours :as tours]))

(defn createPopulation
  [populationSize cities]
   (loop [populationList []]
     (if (< (count populationList) populationSize)
       (recur (conj populationList (tours/createTour cities)))
       populationList)))

(defn findFittestTourInPopulation
  [population]
  (let [tourFitnesses (map tours/calcTourFitness population)]
    (loop [maxFitnessIdx 0 idx 0]
      (if (< idx (count tourFitnesses))
        (if (> (nth tourFitnesses idx) (nth tourFitnesses maxFitnessIdx))
          (recur idx (inc idx))
          (recur maxFitnessIdx (inc idx)))
        (get population maxFitnessIdx)))))
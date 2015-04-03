(ns salesman.core
  (:require [salesman.cities :as cities]
            [salesman.tours :as tours]
            [salesman.population :as population]
            [salesman.ga :as ga]))

;'globals'
(def boundary {:x 100 :y 100})
(def numCities 10)
(def populationSize 50)
(def generations 100)

(def cities [{:name "1" :x 0 :y 0}
             {:name "2" :x 0 :y 1}
             {:name "3" :x 0 :y 2}
             {:name "4" :x 0 :y 3}
             {:name "5" :x 0 :y 4}
             {:name "6" :x 1 :y 4}
             {:name "7" :x 2 :y 4}
             {:name "8" :x 3 :y 4}
             {:name "9" :x 4 :y 4}
             {:name "10" :x 4 :y 3}
             {:name "11" :x 4 :y 2}
             {:name "12" :x 4 :y 1}
             {:name "13" :x 4 :y 0}
             {:name "14" :x 3 :y 0}
             {:name "15" :x 2 :y 0}
             {:name "16" :x 1 :y 0}])

(defn evolve
  [population]
  (loop [newPopulation population generation 0]
    (if (< generation generations)
      (recur (ga/evolvePopulation newPopulation) (inc generation))
      newPopulation)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [randCities (cities/createRandomCities boundary numCities)
        population (population/createPopulation populationSize cities)]
    (println "Initial solution:")
    (tours/printTour (population/findFittestTourInPopulation population))
    (print "Evolving...")
    (def evolvedPopulation (evolve population))
    (println "\nFinal solution:")
    (tours/printTour (population/findFittestTourInPopulation evolvedPopulation))))

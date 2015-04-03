(ns salesman.cities)

(defn createCity
  ([cityName x y]
   {:name cityName :x x :y y})
  ([boundary cityName]
   (createCity cityName (rand (get boundary :x)) (rand (get boundary :y))))
  ([boundary]
   (createCity boundary "X")))

(defn calcDistance
  "Calculates the distance between two cities"
  [city1 city2]
  (let [xDistance (Math/abs (- (get city1 :x) (get city2 :x)))
        yDistance (Math/abs (- (get city1 :y) (get city2 :y)))]
    (Math/sqrt (+ (* xDistance xDistance) (* yDistance yDistance)))))

(defn createRandomCities
  "Creates a list of ranomly placed cities within boundary"
  ([boundary numCities]
   (loop [cityList [] cityNum 1]
     (if (< (count cityList) numCities)
       (recur (conj cityList (createCity boundary (str cityNum))) (inc cityNum))
       cityList))))
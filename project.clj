(defproject salesman "0.1.0-SNAPSHOT"
  :description "Implementation of a genetic algorithm to solve the traveling salesman problem in clojure."
  :url "https://github.com/armueller/TravellingSalesmanClojureGA"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot salesman.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

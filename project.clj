(defproject monads-intro "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url  "http://choosealicense.com/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.stuartsierra/component "1.1.0"]
                 [funcool/cats "2.4.2"]
                 [fmnoise/flow "4.2.1"]
                 [prismatic/schema "1.3.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "1.3.0"]
                                  [com.stuartsierra/component.repl "1.0.0"]]
                   :source-paths ["dev"]}})

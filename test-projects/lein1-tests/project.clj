(defproject lein1-tests "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :eval-in-leiningen true
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :dev-dependencies [[midje "1.3.1"]
                     [leinjacker ~(nth (read-string (slurp "../../project.clj")) 2)]])


  

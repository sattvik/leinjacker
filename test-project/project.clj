(defproject test-project "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :eval-in-leiningen true
  
  ;; lein 1
  :dev-dependencies [[lein-midje "3.2.1"]
                     [midje "1.9.1"]
                     [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]
  ;; lein 2
  :profiles {:dev {:dependencies [[midje "1.9.1"]
                                  [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]
                   :plugins [[lein-midje "3.2.1"]]}
             :foo {:some-key :some-value}})


  

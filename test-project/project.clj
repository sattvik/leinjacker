(defproject test-project "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :eval-in-leiningen true
  
  ;; lein 1
  :dev-dependencies [[lein-midje "1.0.9"]
                     [midje "1.3.1"]
                     [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]
  ;; lein 2
  :profiles {:dev {:dependencies [[lein-midje "2.0.0-SNAPSHOT"]
                                  [midje "1.3.1"]
                                  [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]}
             :foo {:some-key :some-value}})


  

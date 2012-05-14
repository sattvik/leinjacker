(defproject test-project "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  ;; lein 1
  :eval-in-leiningen true
  :dev-dependencies [[midje "1.3.1"]
                     [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]
  ;; lein 2
  :eval-in :leiningen
  :profiles {:dev {:dependencies [[midje "1.3.1"]
                                  [leinjacker ~(nth (read-string (slurp "../project.clj")) 2)]]}})


  

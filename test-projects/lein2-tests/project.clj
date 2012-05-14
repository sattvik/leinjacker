(defproject lein2-tests "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :eval-in :leiningen
  :profiles {:dev {:dependencies [[midje "1.3.1"]
                                  [leinjacker ~(nth (read-string (slurp "../../project.clj")) 2)]]}})

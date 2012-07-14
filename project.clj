(defproject leinjacker "0.2.0"
  :description "A library for Leiningen plug-in authors."
  :url "https://github.com/sattvik/leinjacker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[trammel "0.7.0"]]
  :profiles {:dev {:dependencies [[midje "1.3.1"]
                                  [org.clojure/clojure "1.4.0"]]
                   :eval-in :leiningen}
             :1.2 {:dependencies [[org.clojure/clojure "1.2.1"]]}})

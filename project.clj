(defproject leinjacker "0.4.3-SNAPSHOT"
  :description "A library for Leiningen plug-in authors."
  :url "https://github.com/sattvik/leinjacker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/core.contracts "0.0.6"]]
  :profiles {:dev {:dependencies [[midje "1.9.1"]
                                  [org.clojure/clojure "1.4.0"]]
                   :eval-in :leiningen}
             :1.2 {:dependencies [[org.clojure/clojure "1.2.1"]]}}
  :lein-release {:deploy-via :clojars})

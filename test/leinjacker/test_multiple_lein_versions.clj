(ns leinjacker.test-multiple-lein-versions
  "Runs the tests in the test-projects projects under lein1 and lein2."
  {:author "Toby Crawley"}
  (:use midje.sweet)
  (:require [leinjacker.utils       :as utils]
            [leinjacker.lein-runner :as runner]
            [leiningen.install      :as install]))

(defn- run-lein-on-sub-project
  "Runs the midje command for the given generation of lein in test-project/"
  [generation]
  (println "\n==> Running 'lein midje' (generation" (str generation \)) "in test-project/")
  (let [result (runner/run-lein generation "midje" :dir "test-project")]
    (print (:out result))
    (print (:err result))
    (println "==> Done\n")
    (:exit result)))

;; install leinjacker for the test project to use
(install/install (utils/read-lein-project))

#_(fact "run lein1 tests."
  ;; lein1 needs deps resolved to see the installed leinjacker
  (runner/run-lein "1" "deps" :dir "test-project")
  (run-lein-on-sub-project "1") => 0)

(fact "run lein2 tests."
  (run-lein-on-sub-project "2") => 0)


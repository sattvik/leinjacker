(ns leinjacker.eval-test
  "Test suite for the `leinjacker.eval` namespace."
  {:author "Toby Crawley"}
  (:use leinjacker.eval
        midje.sweet)
  (:require [leinjacker.utils :as utils]
            [leinjacker.eval-in-project :as dep-eip]))

(let [lein-gen (Integer. (System/getenv "LEIN_GENERATION"))]

  (fact "eval-in-project should work."
    (eval-in-project
     (utils/read-lein-project)
     '(spit ".test-output" (System/getenv "LEIN_VERSION")))
    (str (first (slurp ".test-output"))) => (.toString lein-gen))

  (fact "deprecated eval-in-project should work."
    (dep-eip/eval-in-project
     (utils/read-lein-project)
     '(spit ".test-output" (System/getenv "LEIN_VERSION")))
    (str (first (slurp ".test-output"))) => (.toString lein-gen))

  (fact "sh should work."
    (sh "./bin/sh-test" ".test-output" "success")
    (slurp ".test-output") => "success"))

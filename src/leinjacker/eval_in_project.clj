(ns leinjacker.eval-in-project
  "Provides an eval-in-project function that should work for both Leiningen 1
  and Leiningen 2."
  (:require [leinjacker.eval :as real-eip]
            [leinjacker.utils :as utils]))

(defn eval-in-project
  "This function is deprecated.  Use, leinjacker.eval/eval-in-project instead."
  ([project form init]
   (println "This function is deprecated.  Use, leinjacker.eval/eval-in-project instead.")
   (real-eip/eval-in-project project form init))
  ([project form]
   (println "This function is deprecated.  Use, leinjacker.eval/eval-in-project instead.")
   (real-eip/eval-in-project project form)))

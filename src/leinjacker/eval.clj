(ns leinjacker.eval
  "Provides an eval-in-project function that should work for both Leiningen 1
  and Leiningen 2."
  {:author "Daniel Solano Gómez"}
  (:require [leinjacker.utils :as utils]))

(defn eval-in-project
  "Support eval-in-project for both Leiningen 1.x and 2.x.  This code is
  inspired from the code in the lein-swank plug-in."
  ([project form init]
   (if-let [eip (or (when-let [e (utils/try-resolve 'leiningen.core.eval/eval-in-project)]
                      #(e project form init))
                    (when-let [e (utils/try-resolve 'leiningen.compile/eval-in-project)]
                      #(e project form nil nil init)))]
     (eip)
     (throw (IllegalStateException. "Unable to resolve a Leiningen eval-in-project."))))
  ([project form]
   (eval-in-project project form '())))

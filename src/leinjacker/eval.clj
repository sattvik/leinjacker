(ns leinjacker.eval
  "Provides an eval-in-project function and hook that should work for both Leiningen 1
  and Leiningen 2. Also provides apply-task, to chain to other tasks."
  {:author "Daniel Solano Gómez"}
  (:require [leinjacker.utils :as utils])
  (:require [robert.hooke]))

(defn hook-eval-in-project
  "Takes a hook function `f` of 4 arguments, and hooks eval-in-project
  with `robert.hooke`'s semantics.

  This is used to write leiningen plugins that support injections
  in a way compatible with Leiningen 1.x, or to support alternative/proprietary
  scripts/programs to launch the JVM.
  
  The 4 arguments are `[original-eip, project, form, init]`."
  [f]
  (let [gen (utils/lein-generation)]
    (let [eip1 (fn [eip project form _ _ init] (f eip project form init))
          eip2 (fn [eip project form init] (f eip project form init))]
      (condp = gen
        1 (robert.hooke/add-hook
            (utils/try-resolve 'leiningen.compile/eval-in-project) eip1)
        2 (robert.hooke/add-hook
            (utils/try-resolve 'leiningen.core.eval/eval-in-project) eip2)
        (throw (IllegalStateException. "Unknown Leiningen generation."))))))

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

(defn sh
  "Support Leinigen's version of sh for both 1.x and 2.x."
  [& args]
  (apply (utils/try-resolve-any
          'leiningen.compile/sh
          'leiningen.core.eval/sh) args))

(defn apply-task
  "Allow for invoking other Leiningen tasks. Takes the args as a seq."
  [subtask project args]
  (if-let [apply-task (utils/try-resolve 'leiningen.core.main/apply-task)]
    (apply-task subtask project args)
    ((utils/try-resolve 'leiningen.core/apply-task)
       subtask project args
       ;lein1 has a 4 argument form, which expects a not-found fn.
       (utils/try-resolve 'leiningen.core/task-not-found))))

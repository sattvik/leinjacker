(ns leinjacker.multiple-leins-runner
  "Runs the tests in the test-projects projects under lein1 and lein2."
  {:author "Toby Crawley"}
  (:use midje.sweet)
  (:require [clojure.java.io    :as io]
            [clojure.java.shell :as shell]
            [leinjacker.utils   :as utils]
            [leiningen.install  :as install]))

(defn- memoize-to-file
  "Memoizes the result if not-found-fn under key in filename."
  [filename key not-found-fn]
  (let [cache-file (io/file filename)
        cache (if (.exists cache-file) (read-string (slurp cache-file)) {})]
    (or
     (cache key)
     (let [cmd (not-found-fn)]
       (spit cache-file (prn-str (assoc cache key cmd)))
       cmd))))

(defn- find-lein-cmd
  "Attempts to locate the lein command for the given command on the path. Looks for: lein, lein<generation>. The results are cached in .lein-commands"
  [generation]
  (memoize-to-file ".lein-commands"
                   generation 
                   #(if-let [cmd (some
                                  (fn [cmd]
                                    (try
                                      (if (.contains (:out (shell/sh cmd "version"))
                                                     (str "Leiningen " generation "."))
                                        cmd)
                                      (catch java.io.IOException _)))
                                  ["lein" (str "lein" generation)])]
                      cmd
                      (throw (IllegalStateException. (str "Unable to find Leiningen " generation " in the path as lein or lein" generation ". Please make sure it is installed and in your path under one of those names."))))))

(defn- run-lein-on-sub-project
  "Runs the midje command for the given generation of lein in test-project/"
  [generation]
  (let [cmd (find-lein-cmd generation)]
    (println "==> Running" (str \' cmd " midje'") "in test-project/")
    (let [result (shell/sh cmd "midje"
                           :dir "test-project"
                           :env (assoc (into {} (System/getenv))
                                  "LEIN_GENERATION" generation))]
      (print (:out result))
      (print (:err result))
      (println "==> Done\n")
      (:exit result))))

;; install leinjacker for the test project to use
(install/install (utils/read-lein-project))

(fact "run lein1 tests."
  ;; lein1 needs deps resolved to see the installed leinjacker
  (shell/sh (find-lein-cmd "1") "deps" :dir "test-project")
  (run-lein-on-sub-project "1") => 0)

(fact "run lein2 tests."
  (run-lein-on-sub-project "2") => 0)


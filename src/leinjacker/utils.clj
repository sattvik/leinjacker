(ns leinjacker.utils
  "Assorted utilities that didn't seem to fit anywhere else."
  {:author "Daniel Solano Gómez"}
  (:use [trammel.core :only [defconstrainedfn]]))

(defconstrainedfn try-resolve
  "Attempts to resolve the given namespace-qualified symbol.  If successful,
  returns the resolved symbol.  Otherwise, returns nil."
  [sym]
  [symbol? namespace]
  (let [ns-sym (symbol (namespace sym))]
    (try (require ns-sym)
      (resolve sym)
      (catch java.io.FileNotFoundException _))))

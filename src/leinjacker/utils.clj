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

(defconstrainedfn try-resolve-any
  "Attempts to resolve the given namespace-qualified symbols. Returns the
   first successfully resolved symbol, or nil if none of the given symbols
   resolve."
  [& syms]
  [(every? symbol? syms)]
  (if-let [sym (try-resolve (first syms))]
    sym
    (if-let [tail (seq (rest syms))]
      (apply try-resolve-any tail)
      (throw (IllegalArgumentException.
              "Unable to resolve a valid symbol from the given list.")))))



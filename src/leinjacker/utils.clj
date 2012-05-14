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

(defn lein-generation
  "Returns 1 if called under lein 1.x, 2 if called under lein 2.x."
  []
  (if (try-resolve 'leiningen.core.main/-main) 2 1))

(defn lein-home
  "Returns the leiningen home directory (typically ~/.lein/). This function
   abstracts away the differences in calling the leiningen-home method between
   lein 1 and lein 2"
  []
  ((try-resolve-any
    'leiningen.util.paths/leiningen-home   ;; lein1
    'leiningen.core.user/leiningen-home))) ;; lein2


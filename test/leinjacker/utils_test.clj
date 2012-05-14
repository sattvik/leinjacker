(ns leinjacker.utils-test
  "Test suite for the `leinjacker.utils` namespace."
  {:author "Toby Crawley"}
  (:use leinjacker.utils
        midje.sweet)
  (:require [clojure.string :as str]))

(tabular
 (fact "Resolving symbols."
   (try-resolve ?sym) => ?expected)
 ?expected ?sym
 nil       'a.nonexistent/symbol
 =         'clojure.core/=)

(tabular
 (fact "Resolving multiple symbols."
   (apply try-resolve-any ?syms) => #(= (resolve ?expected) %))
 
 ?expected         ?syms
 'clojure.core/map ['clojure.core/map]
 'clojure.core/map ['a.nonexistent/symbol 'clojure.core/map]
 'clojure.core/map ['clojure.core/map 'a.nonexistent/symbol]
 'clojure.core/*   ['clojure.core/* 'clojure.core/map])

(fact "Resolving invalid symbols."
  (doseq [syms [['a.nonexistent/symbol]
                ['a.nonexistent/symbol 'another.nonexistent/symbol]]]
    (apply try-resolve-any syms)) => (throws IllegalArgumentException))


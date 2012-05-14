(ns leinjacker.utils-test
  "Test suite for the `leinjacker-utils` namespace."
  {:author "Toby Crawley"}
  (:use leinjacker.utils
        midje.sweet))

(fact "Determining the current lein generation."
  (lein-generation) => 1)

(fact "Finding lein's home dir."
  (lein-home) => (str (System/getProperty "user.home") "/.lein"))

(ns leinjacker.utils-test
  "Test suite for the `leinjacker-utils` namespace."
  {:author "Toby Crawley"}
  (:use leinjacker.utils
        midje.sweet))

(let [lein-gen (Integer. (System/getenv "LEIN_GENERATION"))]
  (fact "Determining the current lein generation."
    (lein-generation) => lein-gen)

  (fact "Finding lein's home dir."
    (lein-home) => (str (System/getProperty "user.home") "/.lein")))

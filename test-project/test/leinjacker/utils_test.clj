(ns leinjacker.utils-test
  "Test suite for the `leinjacker.utils` namespace."
  {:author "Toby Crawley"}
  (:use leinjacker.utils
        midje.sweet))

(let [lein-gen (Integer. (System/getenv "LEIN_GENERATION"))]
  (fact "Determining the current lein generation."
    (lein-generation) => lein-gen)

  (fact "Finding lein's home dir."
    (lein-home) => (str (System/getProperty "user.home") "/.lein"))

  (facts "read-lein-project"
    (fact "with no arguments should read the project.clj in pwd."
      (:name (read-lein-project)) => "test-project")
    
    (fact "with a filename argument should read the given project.clj."
      (:name (read-lein-project "../project.clj")) => "leinjacker")

    (if (= 1 lein-gen)
      (fact "should still work when given profiles to be ignored."
        (:name (read-lein-project "project.clj" [:foo])) => "test-project")

      (fact "should work when given profiles."
        (:some-key (read-lein-project "project.clj" [:foo])) => :some-value))))

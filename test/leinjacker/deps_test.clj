(ns leinjacker.deps-test
  "Test suite for the `leinjacker-deps` namespace."
  {:author "Daniel Solano Gómez"}
  (:use leinjacker.deps
        midje.sweet))

(tabular
  (fact "Valid dependency specifications."
    (dep-spec? ?dep) => ?expected)
  ?expected  ?dep
  truthy     ['org.clojure/clojure "1.2.1"]
  truthy     ['vimclojure/server "3.2.1" :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server "3.2.1" :scope "test"]
  truthy     ['vimclojure/server "3.2.1" :exclusions ['org.clojure/clojure] :scope "test"]
  truthy     ['org.clojure/clojure]
  truthy     ['org.clojure/clojure nil]
  truthy     ['vimclojure/server :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server nil :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server :exclusions ['org.clojure/clojure] :scope "test"]
  truthy     ['vimclojure/server nil :exclusions ['org.clojure/clojure] :scope "test"]

  ; though valid deps, these aren't dep-specs
  falsey     'typed
  falsey     'org.clojure/clojure

  ;not a vector
  falsey     '('org.clojure/clojure "1.3.0")
  ; empty
  falsey     []
  ; name is not a symbol
  falsey     [:foo "0.0"]
  ; version is not a string
  falsey     ['foo/bar 1]
  ; can't do keywords
  falsey     :foo
  ; just plain crazy
  falsey     #'clojure.core/=)

(tabular
  (fact "Valid dependency name/specifications"
    (dep? ?dep) => ?expected)
  ?expected  ?dep
  truthy     ['org.clojure/clojure "1.2.1"]
  truthy     ['vimclojure/server "3.2.1" :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server "3.2.1" :scope "test"]
  truthy     ['vimclojure/server "3.2.1" :exclusions ['org.clojure/clojure] :scope "test"]
  truthy     ['org.clojure/clojure]
  truthy     ['org.clojure/clojure nil]
  truthy     ['vimclojure/server :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server nil :exclusions ['org.clojure/clojure]]
  truthy     ['vimclojure/server :exclusions ['org.clojure/clojure] :scope "test"]
  truthy     ['vimclojure/server nil :exclusions ['org.clojure/clojure] :scope "test"]
  truthy     'typed
  truthy     'org.clojure/clojure

  ;not a vector
  falsey     '('org.clojure/clojure "1.3.0")
  ; empty
  falsey     []
  ; name is not a symbol
  falsey     [:foo "0.0"]
  ; version is not a string
  falsey     ['foo/bar 1]
  ; can't do keywords
  falsey     :foo
  ; just plain crazy
  falsey     #'clojure.core/=)

(tabular
  (fact "Getting the dependency name"
    (dep-name ?dep) => ?name
    (provided
      (dep-spec? ?dep) => true))
  ?name                  ?dep
  'org.clojure/clojure   ['org.clojure/clojure "1.3.0"]
  'lein-tarsier          ['lein-tarsier "0.9.1"]
  'vimclojure/server     ['vimclojure/server "2.3.1" :exclusions ['org.clojure/core]])


(let [sample-project {:dependencies [['org.clojure/clojure "1.4.0"]
                                     ['lein-tarsier "0.9.1"]
                                     ['vimclojure/server "2.3.1"]]}]
  (tabular
    (fact "Checking for whether or not a dependency is in the project."
      (has-dep? ?project ?dep) => ?expected
      (provided
        (dep? ?dep) => true))
    ?dep                             ?project            ?expected
    'org.clojure/clojure             sample-project      truthy
    ['org.clojure/clojure "1.4.0"]   sample-project      truthy
    ['org.clojure/clojure "1.2.1"]   sample-project      truthy
    'typed                           sample-project      falsey
    'org.clojure/clojure             {}                  falsey
    'org.clojure/clojure             {:dependencies []}  falsey))

(let [sample-project {:dependencies [['org.clojure/clojure "1.4.0"]
                                     ['vimclojure/server "2.3.1"]]}]
  (tabular
    (fact "Add a missing dependency to the project."
      (add-if-missing ?project ?spec) => ?result)
    ?spec                            ?project            ?result
    ['org.clojure/clojure "1.4.0"]   sample-project      sample-project
    ['org.clojure/clojure "1.2.1"]   sample-project      sample-project
    ['lein-tarsier "0.9.1"]          sample-project      {:dependencies
                                                          [['org.clojure/clojure "1.4.0"]
                                                           ['vimclojure/server "2.3.1"]
                                                           ['lein-tarsier "0.9.1"]]}
    ['org.clojure/clojure "1.2.1"]   {}                  {:dependencies
                                                          [['org.clojure/clojure "1.2.1"]]}
    ['org.clojure/clojure "1.2.1"]   {:dependencies []}  {:dependencies
                                                          [['org.clojure/clojure "1.2.1"]]}))

(tabular
  (fact
    (without-clojure ?spec) => ?result
    (provided
      (dep-spec? ?spec) => true
      (dep-spec? ?result) => true))
  ?spec                            ?result
  ['lein-tarsier "0.9.1"]          ['lein-tarsier "0.9.1" :exclusions ['org.clojure/core]]
  ['vimclojure/server "2.3.1"]     ['vimclojure/server "2.3.1" :exclusions ['org.clojure/core]]
  ; silly
  ['org.clojure/clojure "1.4.0"]   ['org.clojure/clojure "1.4.0" :exclusions ['org.clojure/core]])   

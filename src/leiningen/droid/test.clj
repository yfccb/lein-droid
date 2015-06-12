(ns leiningen.droid.test
  (:refer-clojure :exclude [test])
  (:require [bultitude.core :as b]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [leiningen.core.classpath :as cp]
            [leiningen.droid.utils :as utils]
            [leiningen.test :as ltest]))

(defn local-test
  "Runs tests locally using Robolectric."
  [project & [mode]]
  (let [test-nses (b/namespaces-on-classpath
                   :classpath (map io/file (distinct (:test-paths project)))
                   :ignore-unreadable? false)
        cpath (cp/get-classpath project)
        mode (or mode "clojuretest")]
    (binding [utils/*sh-print-output* true]
      (utils/sh "java" "-cp" (str/join ":" cpath)
                "coa.droid_test.internal.TestRunner" "-mode" mode
                (map str test-nses)))))

(defproject bike-gear-calc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-localrepo "0.4.0"]]
  :source-paths ["cljc" "src"]

  :cljsbuild {:builds [{:source-paths ["cljc" "src"]
                        :compiler { ;; :output-to "bike-gear-calc/js/main.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]}
  :hooks [leiningen.cljsbuild]
  :repl-options {:init-ns bike-gear-calc.core}

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]])

;;; install locally in m2.
;;; lein pom; lein jar; lein install

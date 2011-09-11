(ns persister.test.core
  (:use [persister.core])
  (:use [clojure.test]))

(deftest test-serialized-transaction
  (is (=
       "(5 \"param\") ;1"
       (serialized-transaction 1 5 "param") )))

(deftest test-make-str-join-n
  (let [str-join-dosync (@#'persister.core/make-str-join-n 3 "(" "-" ")")]
    (doseq [chunk (str-join-dosync (take 7 (iterate inc 1)) 0)]
      (condp = (first chunk)
        3   (is (= (second chunk) "(1-2-3)"))
        6   (is (= (second chunk) "(4-5-6)"))
        7   (is (= (second chunk) "(7)"))
        (throw (RuntimeException. "wrong accumulator value test-make-str-join-n")) ))))

(require '[clojure.string :as str])

(defn parse [[_ min max letter password]]
  [(read-string min) (read-string max) (first letter) password])

(defn load-line [line]
  (->> line
       (re-find #"(\d+)-(\d+) ([a-z]): (.+)")
       parse
       ))

(def data (->> "2020/02.txt"
               slurp
               str/split-lines
               (map load-line)))

(defn valid1? [[min max letter password]]
  (let [freq ((frequencies password) letter)]
    (if  (nil? freq)
      false
      (<= min freq max)))
  )

(defn valid2? [[i1 i2 letter password]]
  (let [v1 (= (nth password (dec i1)) letter)
        v2 (= (nth password (dec i2)) letter)]
    (or (and v1 (not v2)) (and (not v1) v2))))

(println "Part 1:" (count (filter valid1? data)))
(println "Part 1:" (count (filter valid2? data)))
(require '[clojure.string :as str])
(def data (->> "2020/03.txt"
               slurp
               (str/split-lines)
               (map cycle)))

(defn getcor [[x y]]
  (nth (nth data y) x))

(defn run [step]
  (->> [0 0]
       (iterate #(map + % step))
       (take (/ (count data) (second step)))
       (map getcor)
       (filter #{\#})
       count))

(println "Part 1" (run [3 1]))
(println "Part 2" (apply * (map run [[1 1] [3 1] [5 1] [7 1] [1 2]])))

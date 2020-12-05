(require '[clojure.string :as str])
(def data (->> "2020/01.txt"
               slurp
               (str/split-lines)
               (map read-string)))

(defn sum? [x] (= (reduce + x) 2020))

(defn result [data]
  (->> data
       (filter sum?)
       first
       (reduce *)))

(println "Part 1:" (result (for [x data y data] [x y])))
(println "Part 2:" (result (for [x data y data z data] [x y z])))
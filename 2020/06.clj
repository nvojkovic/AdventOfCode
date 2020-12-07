(require '[clojure.string :as str])

(def data (->> "2020/06.txt"
               slurp
               (#(str/split % #"\n\n"))
               (map str/split-lines)
               ))
   
(println "Part 1:" 
         (->> data
              (map #(set (apply str (apply concat %))))
              (map count)
              (reduce +)))

(defn all-yes? [q xs]
  (every? #(.contains % (str q)) xs))

(defn count-yes [[x y]]
  (count (filter #(all-yes? % y) x)))

(println "Part 2:" 
         (->> data
              (map (fn [x] [(set (apply str x)) x]))
              (map count-yes)
              (reduce +)))
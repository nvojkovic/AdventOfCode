(require '[clojure.string :as str])

(defn get-containing [line]
  (->> line
       (re-seq #"((?:\d+|no)) ([a-z]+ [a-z]+) bag[s]?")
       (map rest)))

(def data (->> "2020/07.txt"
               slurp
               str/split-lines
               (map #(re-find #"(.+) bags? contain (.+)\." %))
               (map (fn [[_ a b]] [a (get-containing b)]))
               (into {})))

(defn contains-bag? [look bag]
  (let [children (map second (data bag))]
    (or (some #{look} children)
        (some #(contains-bag? look %) children))))

(println "Part 1:" (->> (keys data)
                        (filter #(contains-bag? "shiny gold" %))
                        count
                        ))

(defn count-containing [bag]
  (->> (data bag)
       (map (fn [[c t]] (* (read-string c) (count-containing t))))
       (reduce +)
       inc))

(println "Part 2:" (dec (count-containing "shiny gold")))
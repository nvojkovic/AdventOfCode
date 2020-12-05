(require '[clojure.string :as str])

(defn parse-string [s  m]
  (read-string (str "2r" (apply str (map m s)))))

(defn make-record [row]
  (->> row
       (re-find #"([FB]+)([LR]+)")
       rest
       (#(vector (parse-string (first %) {\F "0" \B "1"})
                 (parse-string (second %) {\L "0" \R "1"})))))

(def data (->> "2020/05.txt"
               slurp
               str/split-lines
               (map make-record)))

(defn calc-id [[r c]] (+ c (* r 8)))

(def ids (map calc-id data))

(defn my-seat? [d]
  (let [id (calc-id d)]
    (and (.contains ids (inc id))
         (.contains ids (dec id))
         (not (.contains data d)))))

(println "Part 1:" (apply max ids))
(println "Part 2:" (->> (for [x (range 128) y (range 8)] [x y])
                        (filter my-seat?)
                        first
                        calc-id))
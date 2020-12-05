(require '[clojure.string :as str])

(defn make-row [row]
  (->> (str/split row #"[\n ]")
       (map #(str/split % #":"))
       (into {})))

(def data (->> "2020/04.txt"
               slurp
               (#(str/split % #"\n\n"))
               (map make-row)))

(defn str-between [a x b]
  (and x (<= a (read-string x) b)))

(def validations
   {"byr" #(str-between 1920 % 2002)
    "iyr" #(str-between 2010 % 2020)
    "eyr" #(str-between 2020 % 2030)
    "hgt" #(or
            (let [[_ x] (re-find #"^(\d+)cm$" %)] (str-between 150 x 193))
            (let [[_ x] (re-find #"^(\d+)in$" %)] (str-between 59 x 76)))
    "hcl" #(re-find #"^#[0-9a-f]{6}$" %)
    "ecl" #(.contains ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"] %)
    "pid" #(re-find #"^\d{9}$" %)
    })

(defn valid1 [x]
  (every? #((set (keys x)) %) (keys validations)))

(defn valid2 [passport]
  (every? #((or (validations %) (fn [_] true)) (passport %)) (keys passport)))

(defn count-valid [fs data]
  (->> data
       (filter #(every? (fn [f] (f %)) fs))
       count))

(println "Part 1:" (count-valid [valid1] data))
(println "Part 2:" (count-valid [valid1 valid2] data))
(ns advent-of-code-2018.day2
  (:require [ysera.test :refer [is= is is-not]]
            [clojure.string :as str]))

(def puzzle-input "jplenqtlagxhivmwmscfukzodp\njbrehqtlagxhivmeyscfuvzodp\njbreaqtlagxzivmwysofukzodp\njxrgnqtlagxhivmwyscfukwodp\njbrenqtwagjhivmwysxfukzodp\njbrenqplagxhivmwyscfuazoip\njbrenqtlagxhivzwyscfldzodp\njbrefqtlagxhizmwyfcfukzodp\njbrenqtlagxhevmwfsafukzodp\njbrunqtlagxrivmsyscfukzodp\njbrcnstsagxhivmwyscfukzodp\njbrenqtlagozivmwyscbukzodp\njbrenqwlagxhivswysrfukzodp\njbrenstlagxhuvmiyscfukzodp\njbranqtlhgxhivmwysnfukzodp\njbrenqtvagxhinmxyscfukzodp\njbrenqtlagdhivmwyscfukxody\njbrenqtlagelavmwyscfukzodp\njbrenqtlagxhtvmwyhcfukzbdp\njbrenqwlagxhivmwyscfutzopp\njbrenqtlavxhibmtyscfukzodp\njbronqtlagxnivmwyscfuzzodp\njbredqtlagxhppmwyscfukzodp\njbrcnqtlogxhivmwysxfukzodp\njbremqtlagehivswyscfukzodp\njbrenqolagxhivmcyscfukzokp\njbrehqtlacxhgvmwyscfukzodp\nfbrlnqtlagxhivmwyscbukzodp\nzbrfnqtlagxhivrwyscfukzodp\njbregqtlagxnivmwyscfhkzodp\njbrenqtllgxnivmwystfukzodp\njurenqtlagxhivmwyscfulzoup\njbrenitdagxhivmwyxcfukzodp\njbrenqtlagxqivmwyscvuszodp\njbqenqwlagxhivmwyscfckzodp\njbrenqtlagxhivmwxscqupzodp\njbrenqtlagxhivmwysciuqiodp\njbrjnjtlagxhivmwyscfukzode\njbrenqtlagxhuvmwqscfukzods\njbrenqtlagxhuvmuyscfukzudp\nibrenqtlagxhivmwyscfuktokp\njbsenqtlagxhivcwyscfuksodp\njbrfnntlagxhivmwnscfukzodp\njzrenqulagxhivmwyscfukzodx\njbrenqtqygxhivmwyscfukzwdp\njbrenqtlagxfixmwyscfukzcdp\njbrenqaoagxhivmwyshfukzodp\njbrenqtlazxhivmworcfukzodp\njbrenqtlaguhivmwyhlfukzodp\njbrenqtlagxhicowyscfukrodp\njbrqnqtlagxhivzwyzcfukzodp\njbrenqtlalxhuvxwyscfukzodp\njbrenqtlqgxhhviwyscfukzodp\njbrenqtuggxhivmoyscfukzodp\njbrenqtlagxpivmwyscfukkodw\nzbrenqhlagxhivmwyscdukzodp\njbrenutlagxxivmwyscfukzoqp\nobrenqtlagxhivmwxscfuszodp\njbrenqtlagxhlvmwyscfuixodp\nrbrenqtlagdhixmwyscfukzodp\njbrenqtlagxhivmwescfyszodp\njbrfnqtlagxhivmwyscaukzhdp\njbrenqtiagxhivmbyscfuxzodp\ncbrrnqtuagxhivmwyscfukzodp\njbkenqtlagxhigmwysufukzodp\njbjewqtlagxhivmwyscfuqzodp\njbrznqtlagxvivmwyscfukzovp\njbrenttlacxhivmwyscfhkzodp\njblenqtlagxhivmwylcfukaodp\njbrenqtlagxhivmqysiftkzodp\njbrenqtlagwhikmwyscfukqodp\njbrenqtlegxhivmwvsckukzodp\njbrenqwzagxhiamwyscfukzodp\njbrenqtlagxhivcwyscfgkzodc\njbrenqtlagxxhvmwxscfukzodp\njbrenqtlngxhivmwyscfukoowp\njbreomtlagxhivmwpscfukzodp\njfrenqtlagxhivmwyscnumzodp\njbrenqtlagphipmwyscfukfodp\njvrenqtlagxhivmwyscfmkzodw\njbrenqtlaxxoiemwyscfukzodp\njbrenqtlagxhivmwyscemkzpdp\njbrenyjldgxhivmwyscfukzodp\njbrenqtlagxhivfvyspfukzodp\nkbrenctlakxhivmwyscfukzodp\njbrmhqtlagxhivmwyscfuizodp\njbjenqtlagxlivmbyscfukzodp\njbrenqtlaaxhivmmyshfukzodp\njbronqtlagxhirmvyscfukzodp\njbcrnqtlagxwivmwyscfukzodp\njxrenszlagxhivmwyscfukzodp\njbpenqtlagxhivmwyscfukkody\njbrewqtlawxhivmwyscfukzhdp\njbrenqylagxhivmwlxcfukzodp\njbrenqtlagxxivtwywcfukzodp\njbrenqtlagxhcvgayscfukzodp\njbrenitlagxhivmwiscfukzohp\njbrenutlagxhivmwyscbukvodp\nnbrenqtlagxhivmwysnfujzodp\njbrenqtlagxhivmwyqcfupzoop\njbrenqtrarxhijmwyscfukzodp\njbrenqtlagxhivmwysdfukzovy\njbrrnqtlagxhivmwyvcfukzocp\njbrenqtlagxhivmwyscfuvzzhp\njbhenitlagxhivmwysufukzodp\njbrenqtlagxhivmwyscfuwzoqx\nkbrenqtlagxhivmwysqfdkzodp\njbrenqtlagxhivmwyxmfukzodx\njbcenatlagxxivmwyscfukzodp\njbrenhtlagvhdvmwyscfukzodp\njxrenqtlafxhivfwyscfukzodp\njbreaztlpgxhivmwyscfukzodp\ntqrenqtlagxfivmwyscfukzodp\njbrenqgllgxhwvmwyscfukzodp\njbrejjtlagxhivmgyscfukzodp\njbrenqtlagxhivmwyscvukzoqv\njbrvnqtlagxsibmwyscfukzodp\njbrenqttagxhuvmwyscfukvodp\njbrenqteagxhivmwcscfukqodp\njbrenqtsabxhivmwyspfukzodp\njbbenqtlagxhivmwyscjukztdp\njnrenqtlagxhiimwydcfukzodp\njbrenqtlagxhfvmwyscxukzodu\njbrenqtluyxhiomwyscfukzodp\njbrenqvlagxhivmwyscuukzolp\nebrenqtlagxnivmwysrfukzodp\njbreeqtlatxhigmwyscfukzodp\njbrenqtvxgxhivmwyscfukzedp\njbrmnqtlagxhivmwywcfuklodp\njbreeqtvagxhivmwyscfukzody\njbrenptlagxhivmxyscfumzodp\njbrenqtlagxhivmwysgfukzfsp\njurenqtlagjhivmwkscfukzodp\njbrenntlagxhivmwtscffkzodp\njbrenqglagxhivmwyocfokzodp\nwbrenqtlagxhivmwhscfukiodp\njbrenqtligxhivmqascfukzodp\njbrenqtlagxhivmwxscfukpojp\njurenqtlagxhivmmyscfbkzodp\njbrenqtmagxhivmwyscfrbzodp\njcrenqtlagxhivmwysefukzodm\njbrenqtlagxhicmwywcfukzodl\njbvanqtlagfhivmwyscfukzodp\njbmenqjlagxhivmwyscfdkzodp\njbrenqtlagohivvwysctukzodp\njbrenqtdagxdivmwyscfckzodp\nkbrefqtlagxhivmwyscfuazodp\njbrwnqtoagxhivmwyswfukzodp\njjhenqtlagxhivmwyscfukzorp\njbgsnqtlagxhivkwyscfukzodp\njbrynqtlagxhivmsyspfukzodp\njbrenftlmkxhivmwyscfukzodp\nnbrenqtxagxhmvmwyscfukzodp\njbrunqtlagxhijmwysmfukzodp\njbrenqtlagmaivmwyscfukzowp\njbrerqtlagxhihmwyscfukzudp\njbrenqtlagahivmwysckukzokp\nkbrenqtlagxhirmwyscfupzodp\njbrrnqtlagxhivmwyecfukzodz\njbrenqtlavxhivmwyscfusiodp\njnrenqtlagxhivmwyhcfukzodw\njbretqtlagfhivmwyscfukzrdp\njbreoqtnagxhivmwyscfukzopp\njbrenbtllgxhivmwsscfukzodp\njbrenqtlmgxhivmwyscfuwzedp\njbnenqtlagxhivbwyscfukzokp\njbrenqslagxhivmvyscfukzndp\njbrenqtlagxzivmwuscfukztdp\ngbrenqtlagxhyvmwyscfukjodp\njbrenqteagxhivmwyscfuszedp\njbrenqtlaglhivmwysafukkodp\njbrenqtlagxhcvmwascfukzogp\njbrenqtlagxhsvmkcscfukzodp\njbrenqslbgxhivmwyscfufzodp\ncbrenqtlagxhivkwtscfukzodp\njbrenqtltgxhivmzyscfukzodj\njbrgnqtlagihivmwyycfukzodp\nvbrenqauagxhivmwyscfukzodp\njbrqnqtlagjhivmwyscfqkzodp\njbrenqtlakxhivmwyscfukvobp\njcrenqelagxhivmwtscfukzodp\njbrrnqtlagxhlvmwyscfukzodw\njbrenqtlagxhivmwkscaumzodp\njbrenqdlagxhivmiescfukzodp\njhrenqtlagxhqvmwyscmukzodp\njbrenqtlaghhivmwyscfukkoyp\njowenqtlagxyivmwyscfukzodp\njbrenitaagxhivmwyscfqkzodp\njbrenqtlagxhivmwyscfnkbudp\njbyenqtlagxhivmiyscfukzhdp\njbrenitlagxhibjwyscfukzodp\njbrenqtlavxhjvmwpscfukzodp\njbrenqyaagxhivmwyscflkzodp\njbrenqylagxhivmwyicfupzodp\njbrenqtlagxmevmwylcfukzodp\nlbrenqtlagxhiqmwyscfikzodp\njbrenqtnarxhivmwyscfmkzodp\njbrenqtlamxhivmwyscfnkzorp\njbbenqtlavxhivmwyscjukztdp\njbrenqtlagxhivmwyscfnliodp\njbwenetlagxhivmwyscfukzodt\njbrenatlagxhivmwysmfujzodp\njbrsnstlagxhivmwyscfukgodp\njbwvnitlagxhivmwyscfukzodp\njbrenqtbagxhkvmwypcfukzodp\njbrlnqtlafxhivmwyscfukdodp\njbrenztlanxhivmwyscjukzodp\njbrepqtlagxhivmwudcfukzodp\njbrenqtlagxiivmwdscfskzodp\njbrdjqtlagxhivmwyschukzodp\njbrenqtoaguhivmwyccfukzodp\njdrexqjlagxhivmwyscfukzodp\njbrenqtlagxhovmwysckukaodp\npbrfnqblagxhivmwyscfukzodp\njbrenqtlagxrivgiyscfukzodp\njbrelqtgagxhivmryscfukzodp\njbrenqtlagxhicmwjscfikzodp\njbrenqjlagxhivmwyscfmkjodp\njbrenqtlagxpivmwzscgukzodp\njbienqzlagxpivmwyscfukzodp\njbrenqvlagxhivmwdscfukzodx\nowrenqtlagxhivmwyicfukzodp\ngbrenqtlaathivmwyscfukzodp\njbgenqtlafxhivmwysqfukzodp\njbrenqtlagxhivtwsscfukzokp\njbrnnqylanxhivmwyscfukzodp\nebrenqolagxhivmcyscfukzodp\njbrenqtlarnhivgwyscfukzodp\njbmenqtlagxhivmvyscfukzgdp\njbrevqtlaglhivmwystfukzodp\njbrenqplanthivmwyscfukzodp\njbrenqtlagxhivmkyscbukzosp\njbrenqtlagxaivmwyscfugzodo\njbrenqplagxhnvmwyscfjkzodp\njbrenqtlagxhivgwyscfusrodp\ncbrenqtlagxhivmwysmfukzody\njbrenquwaexhivmwyscfukzodp\njbredqtlagxhdvmwyscfukzoup\njbrxnqtlagxhivmwysczukaodp\njbrenqtlafnhivmwyscfuczodp\njbbdkqtlagxhivmwyscfukzodp\nubrenqtlagxhivkwyucfukzodp\nebjenqtlagxhivmwyscfukzodj\njbgenqtlugxxivmwyscfukzodp\njbrenqtoagxqivmwdscfukzodp\nbbeenqtlagxhivmwyscfumzodp\njbfeeqtlagxhivmwmscfukzodp\njbrlnqtlagxhiimwescfukzodp\njbrenqtlagwoivmwyscfukhodp\njbrenqtlagnhivmwyscfuszovp")

(def parsed-puzzle-input
  (-> puzzle-input
      (str/split #"\n")))

(defn day2-a
  {:test (fn []
           (is= (day2-a ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]) 12))
   }
  [puzzle]
  (->> puzzle
       (map #(frequencies %))
       (map (fn [frequency]
              (->> frequency
                   (filter (fn [[_ value]]
                             (and (> value 1) (< value 4))))
                   (vals)
                   (distinct))
              ))
       (flatten)
       (frequencies)
       (vals)
       (reduce *)))

(day2-a parsed-puzzle-input)


(defn hamming-distance
  [x y]
  (count (filter true? (map (partial reduce not=) (map vector x y)))))

(defn day2-b
  {:test (fn []
           (is= (day2-b ["abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"]) "fgij"))}
  [puzzle]
  (loop [box-ids puzzle]
    (let [match (->> box-ids
                     (filter (fn [box-id]
                               (= (hamming-distance (first box-ids) box-id) 1)))
                     (first))]
      (if match
        (->> (map vector (first box-ids) match)
             (filter #(apply = %))
             (map first)
             (apply str))
        (recur (next box-ids))))))

(day2-b parsed-puzzle-input)



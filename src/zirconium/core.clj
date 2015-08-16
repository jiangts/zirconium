(ns zirconium.core
  (:require 
    [garden.core :as garden]
    [garden.color :as color]
    [garden.units :as units]
    [clojure.string :as string]))

;; ==================
;; offers the inline macro to convert garden data into react inline-able styles
(defn css-camel-case [css-rule]
  (let [pieces (string/split css-rule #"-")]
    (reduce str 
            (cons (first pieces)
                  (mapv #(case %
                           "ms" %
                           (string/capitalize %)) (rest pieces))))))

(defn cljss [css-str]
  "assumes well formed css rules with colons and semi-colons"
  (let [rule-strings (string/split css-str #";")
        rules (mapv #(string/split % #":") rule-strings)]
    ; put values into hashmap
    (into (sorted-map) (mapv (fn [[k v]] 
                               [(-> k
                                    string/trim
                                    css-camel-case
                                    keyword) (string/trim v)])
                             rules))))

(defmacro inline [garden-data]
  "a macro to move css string parsing to compile time"
  (let [css-str (garden/style (eval garden-data))] ;eval fix issue of (quote ...)
    (cljss css-str)))

;; ==================


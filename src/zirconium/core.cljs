(ns ^:figwheel-always zirconium.core
  (:require 
    ;[garden.core :as garden]
    [garden.units :as u]
    [clojure.string :as string]
    [clojure.walk :as w]
    [zirconium.css])
  (:require-macros 
    [zirconium.core :as macros]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )


;; macro to create inline style objects
;(println (macros/inline {:font {:weight 'normal :style "normal" :variant "normal"}
;                   :text {:decoration "none"}}))
;
;
;; a datastructure to define component styles without namespace issues
;(def styles 
;  {:container [(gensym ".container")
;               {:background "#f6f7f8"
;                :border {:radius (px 2) :width (px 1) :style 'solid :color "#cdced0"}}]
;   :depressed [(gensym ".depressed")
;               {:background-color "#4e69a2"
;                :border-color 'red
;                :color 'magenta}]
;   })
;
;(println (garden/css (:container styles)))
;(println (garden/css (:depressed styles)))
;
;; need to make a macro for above datastructure
;; make a macro for easy inclusion of this stuff in components

(defn react-camel-case
  "Camel case for react styling. Note that ms prefix is NOT capitalized."
  [word-vec]
  (let [word-vec-str (map name word-vec)]
    (reduce str
            (cons (first word-vec-str) (map #(case %
                                               "ms" %
                                               (string/capitalize %)) (rest word-vec-str))))))

(declare garden->react)


;XXX ugliest cljs i've ever written
(defn garden-el->react-el
  "unwrap single garden key-value entry into form closer to react entry"
  [garden-entry]
  (let [raw-k (name (key garden-entry))
        k (let [k-components (string/split raw-k #"-")]
            (if (> (count k-components) 1)
              (react-camel-case k-components)
              (keyword raw-k)))
        v (val garden-entry)]
    (cond
      (map? v) (reduce (fn [base nxt]
                         (merge base {(->> nxt
                                           key
                                           (conj [k])
                                           react-camel-case
                                           keyword)
                                      (val nxt)})) {} v)
      (vector? v) {k (->> v
                          flatten
                          vec
                          (string/join " "))} ;XXX NOT doing the nesting space
      :else {k v})))

;eventually ignore :&:hover and media queries.
;not sure about vector support...
;make sure units, arithmetic, and colors work
(defn garden->react
  "converts garden map into css map consumable by react/reagent"
  [garden-map]
  (loop [progress garden-map]
    (let [react-map (apply merge (mapv garden-el->react-el progress))]
      (if (every? false? (map map? (vals react-map)))
        react-map
        (recur react-map)))))

;(println (garden->react {:ms {:transition ['meep 'poop] :baz {:pooh 'bear}} :font-family 'hiss}))




;; ==================
;; extend hiccup to allow for div$zr-class$zr-class syntax!
(defn get-zr-tags [k]
  (if (keyword? k)
    (re-seq #"\$\w+" (name k))))

(defn remove-zr [k]
  (if (keyword? k)
    (keyword (clojure.string/replace (name k) #"\$\w+" ""))))

(defn assemble-hiccup [tag attributes remainder]
  (vec (concat [(remove-zr tag) attributes] remainder)))

(defn inject-zr [hiccup]
  (if-let [tags (get-zr-tags (first hiccup))]
    (when (pos? (count tags))
      (if (map? (nth hiccup 1 nil))
        (assemble-hiccup (first hiccup)
                         (merge {:className (apply str tags)}
                                (nth hiccup 1))
                         (subvec hiccup 2))
        (assemble-hiccup (first hiccup)
                         {:className (apply str tags)}
                         (rest hiccup))))
    hiccup))

(w/postwalk #(if (vector? %)
               (inject-zr %)
               %)
            [:div$hello$world {:style {:backgroundColor 'red}}
             [:p.lead$big "hello"]
             [:html$hello]])

;; ==================

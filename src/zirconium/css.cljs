(ns zirconium.css
  (:require 
    [garden.core :as garden]
    [garden.color :as color]
    [garden.units :as units]
    [clojure.string :as string]
    [goog.cssom :as cssom]
    [goog.dom :as dom]))

(enable-console-print!)

;; ==============================
;; http://google.github.io/closure-library/api/namespace_goog_cssom.html

;; Javascript driven media queries:
;; http://www.sitepoint.com/javascript-media-queries/

;; inspiration: JSS, smart-css, many others

;; idea: make the set-class function create inline and text data. 
;; or just write a better inlining function (not macro)

;; design: have single zirconium stylesheet
(defn ensure-stylesheet! [] 
  (when (zero? (.-length (cssom/getAllCssStyleSheets)))
    (cssom/addCssText ""))
  (first (cssom/getAllCssStyleSheets)))

(def sheet (ensure-stylesheet!))

; takes 2 arguments: css string and index. index --> use map-indexed
(def add-rule (partial cssom/addCssRule sheet))

; takes 1 argument: index
(def remove-rule (partial cssom/removeCssRule sheet))

(defn replace-rule [rule index]
  (remove-rule index)
  (add-rule rule index))


;TODO need to track index
;(add-rule (garden/css [:h2 {:color 'blue}]) 0)
(replace-rule (garden/css [:h2 {:color 'magenta}]) 0)
(println (cssom/getAllCssStyleRules sheet))

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

;; ** getAllCssStyleSheets()
;; ** addCssRule(CSSStyleSheet, cssText, opt_index)
;; getAllCssStyleRules(opt_CSSStyleSheet). Does not include Media Queries!

;; addCssText(cssText)
;; ** getCssRuleIndexInParentStyleSheet(cssRule, opt_parentCSSStyleSheet)
;; ** removeCSSRule(CSSStyleSheet, index)

;; ** replaceCssRule(cssRule, cssText, opt_parentStyleSheet, opt_index)


;(cssom/addCssText (garden/css [:h2 {:font-family 'cursive}]))

(cssom/addCssRule
  (first (cssom/getAllCssStyleSheets)) (garden/css [:p {:color 'red}]))

;(println (js->clj (cssom/getAllCssStyleSheets)))
(println (for [x [(last (js->clj (cssom/getAllCssStyleSheets)))]
               y (range 7)] 
           (cssom/removeCssRule x y)))

(println (cssom/getAllCssText))

(println (cssom/getAllCssStyleRules))
;(cssom/replaceCssRule "h2" (garden/style {:font-family 'fantasy}))
;(println (map cssom/getCssRulesFromStyleSheet (cssom/getAllCssStyleSheets)))


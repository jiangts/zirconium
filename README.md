# zirconium

**(Work in Progress!)**

Zirconium is a CSS framework for ClojureScript. It is essentially an opinionated
wrapper over the the excellent [garden][garden] library that aims to bring
sanity and fun to styling your web application :).

It is heavily influenced by [React: CSS in JS][css-in-js] by
[vjeux][vjeux] and [radium][radium], a Javascript styling library for
React.js applications.

[radium]: https://github.com/FormidableLabs/radium

[garden]: https://github.com/noprompt/garden

[vjeux]: https://twitter.com/Vjeux

## Overview

Why Zirconium?

The greatest strength of `garden` is that it allows you to write your
stylesheets with the full power of ClojureScript at your disposal. By using some
well-designed data structures and macro sugar, we can make working with CSS
reasonable and even fun.

Features:
- Avoids selector conflicts
- Avoids selector specificity conflicts
- Source order independence
- Dead code elimination
- Id and class name minification
- Highly expressive

And if you're skeptical about writing styles in ClojureScript/Javascript,
please, do take a look at vjeux's [talk][css-in-js].

[css-in-js]: https://speakerdeck.com/vjeux/react-css-in-js

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright © 2015 Allan Jiang

Distributed under the Eclipse Public License, the same as Clojure.

## Current idea

The idea is that you should be able to write a datastructure that defines a
style. It can be required into a namespace with a component and a pseudo
classname can be applied. The actual class that is attached will be a very short
gensym'd id.

(By the way, the point of gensym'd class names it to promote separation of
concerns to let you confidently edit a style without fear of accidentally
perverting another component with the same class name)

This gives you the "default" component styling, and you get ALL media queries
and pseudo selectors like `:hover` and `:active` for free.

When a style data structure is applied to a component, the style should be
stringified and emitted into a style tag somewhere.

Additional styles applied should create a new gensym'd class with the changes
merged on top. Either that or just allow inline styles. In that case, however,
you can't really customize a hover style.

You should also be able to emit static css somehow...

Or scratch the above ideas...
## How would it work in an ideal world?

1. a convenient data structure to define styles
2. apply the style to the component and have media queries/pseudo selectors just
work
3. allow merge overrides of styles that accomodate queries/pseudo selectors. the
overrides should be written in the same data structure. The overrides should
also be dynamic, meaning that they can depend on another variable (thinking of
react props here...). To be honest, this should probably be inlined CSS.
Ideally, then, we have a way of altering `:hover` and such in the inlined CSS.
Media queries probably shouldn't be customized so we're safe there.
Consider http://www.hunlock.com/blogs/Totally_Pwn_CSS_with_Javascript or
http://www.w3.org/wiki/Dynamic_style_-_manipulating_CSS_with_JavaScript .
4. emit a set of all CSS needed (at least all pre-customization CSS).

Perhaps customized CSS can be dynamically generated/added, and other CSS can be
statically generated for production.

Ideally in Hiccup/Reagent/Sablono you can use the `:div.container$class` syntax.
Otherwise, you'd wrap a component in some sort of style function. The function
would inject the correct gensym'd class name into the component and emit the
CSS to the DOM.

## Could this be ported to Javascript?

No clue yet.


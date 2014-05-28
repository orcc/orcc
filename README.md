Util branch, please read
=====================

This branch allows you to easily debug and/or develop the automatic layout in the graph editor.

Layout features uses the [kieler project](http://www.informatik.uni-kiel.de/en/rtsys/kieler/) to compute the information on size / position of elements on the graph. To avoid dependency to Kieler plugins and the corresponding Eclipse update site in Orcc, Kieler plugins are used from the [library](http://www.informatik.uni-kiel.de/en/rtsys/kieler/downloads/tools-and-libraries/).

This branch disable this and replace the link to jar archive by a requirement to proper eclipse plugins. By switching on this branch, you will be able to view/debug in Kieler sources.

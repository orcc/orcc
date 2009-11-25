#!/bin/sh

# Note: The --chmod is necessary to ensure the files will be readable and
# executable (for directories) on the web site.

rsync -avP --chmod=+rx -e ssh plugin/javadoc/ mwipliez,orcc@web.sourceforge.net:htdocs/javadoc/plugin
rsync -avP --chmod=+rx -e ssh backends/javadoc/ mwipliez,orcc@web.sourceforge.net:htdocs/javadoc/backends

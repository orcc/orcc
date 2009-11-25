#!/bin/sh

# Note: The --chmod is necessary to ensure the files will be readable and
# executable (for directories) on the web site.

rsync -avP --chmod=+rx -e ssh frontend/frontend-win32.zip mwipliez,orcc@web.sourceforge.net:htdocs/

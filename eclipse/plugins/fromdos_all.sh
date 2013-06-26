#!/bin/sh
find . -name "*.java" -exec "fromdos" {} ";"
find . -name "*.xml" -exec "fromdos" {} ";"
find . -name "*.xslt" -exec "fromdos" {} ";"
find . -name "*.g" -exec "fromdos" {} ";"
find . -name "*.tokens" -exec "fromdos" {} ";"
find . -name "*.exsd" -exec "fromdos" {} ";"
find . -name "*.ecore" -exec "fromdos" {} ";"
find . -name "*.genmodel" -exec "fromdos" {} ";"
find . -name "MANIFEST.MF" -exec "fromdos" {} ";"
find . -name "*.properties" -exec "fromdos" {} ";"
find . -name "*.xtend" -exec "fromdos" {} ";"

find . -name ".classpath" -exec "fromdos" {} ";"
find . -name ".project" -exec "fromdos" {} ";"


#!/bin/sh
# Will execute fromdos command on files in the given folder,
# to replace CR+LF line endings (windows) by LF (linux / macos)
# Only some interesting files types will be updated, see below

if [ $# -ne 1 ]; then
  echo "$0 <path with .cal files>"
  exit 1
fi

path=$1
if [ ! -e $path ] || [ ! -d $path ]; then
  echo "path must exist and be a directory"
  exit 1
fi

find ${path} -name "*.java" -exec "fromdos" {} ";"
find ${path} -name "*.xtend" -exec "fromdos" {} ";"
find ${path} -name "*.ecore" -exec "fromdos" {} ";"
find ${path} -name "*.genmodel" -exec "fromdos" {} ";"

find ${path} -name "*.xml" -exec "fromdos" {} ";"
find ${path} -name "*.xslt" -exec "fromdos" {} ";"
find ${path} -name "*.exsd" -exec "fromdos" {} ";"

find ${path} -name ".classpath" -exec "fromdos" {} ";"
find ${path} -name ".project" -exec "fromdos" {} ";"

find ${path} -name "CMakeLists.txt" -exec "fromdos" {} ";"
find ${path} -name "*.c" -exec "fromdos" {} ";"
find ${path} -name "*.cpp" -exec "fromdos" {} ";"
find ${path} -name "*.h" -exec "fromdos" {} ";"

find ${path} -name "*.cal" -exec "fromdos" {} ";"
find ${path} -name "*.xdf" -exec "fromdos" {} ";"
find ${path} -name "*.xdfdiag" -exec "fromdos" {} ";"
find ${path} -name "*.layout" -exec "fromdos" {} ";"

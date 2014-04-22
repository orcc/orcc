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

find ${ORCCDIR} -name "*.java" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.xtend" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.ecore" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.genmodel" -exec "fromdos" {} ";"

find ${ORCCDIR} -name "*.xml" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.xslt" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.exsd" -exec "fromdos" {} ";"

find ${ORCCDIR} -name ".classpath" -exec "fromdos" {} ";"
find ${ORCCDIR} -name ".project" -exec "fromdos" {} ";"

find ${ORCCDIR} -name "CMakeLists.txt" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.c" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.cpp" -exec "fromdos" {} ";"
find ${ORCCDIR} -name "*.h" -exec "fromdos" {} ";"

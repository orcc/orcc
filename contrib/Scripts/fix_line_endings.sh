#!/bin/sh

ORCCDIR="$( cd "$( dirname "$0" )/../.." && pwd )"

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

#!/bin/sh
if [ $# -ne 1 ]; then
  echo "$0 <path with .cal files>"
  exit 1
fi

path=$1
if [ ! -e $path ] || [ ! -d $path ]; then
  echo "path must exist and be a directory"
  exit 1
fi

find $path -name "*.cal" -exec sed -i -e "s/import [^;]*;//" -e "s/Integers\s*(\s*\([^,]*\)\s*,\s*\([^,]*\))/\1 .. \2/" {} ";"


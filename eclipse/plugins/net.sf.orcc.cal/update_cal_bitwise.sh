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

find $path -name "*.cal" -exec sed -i \
  -e "s/bitand\s*(\s*\([A-Za-z0-9_]*\)\s*,\s*\([A-Za-z0-9_]*\)\s*)/(\1 \& \2)/" \
  -e "s/bitor\s*(\s*\([A-Za-z0-9_]*\)\s*,\s*\([A-Za-z0-9_]*\)\s*)/(\1 | \2)/" \
  -e "s/bitxor\s*(\s*\([A-Za-z0-9_]*\)\s*,\s*\([A-Za-z0-9_]*\)\s*)/(\1 ^ \2)/" \
  -e "s/lshift\s*(\s*\([A-Za-z0-9_]*\)\s*,\s*\([A-Za-z0-9_]*\)\s*)/(\1 << \2)/" \
  -e "s/rshift\s*(\s*\([A-Za-z0-9_]*\)\s*,\s*\([A-Za-z0-9_]*\)\s*)/(\1 >> \2)/" \
  -e "s/bitnot\s*(\s*\([A-Za-z0-9_]*\)\s*)/~(\1)/" \
  {} ";"


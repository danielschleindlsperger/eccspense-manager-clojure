#!/usr/bin/env bash

MAIN_NS="eccspense-manager.api.main"

set -e

# AOT compile to "classes" folder for faster startup
rm -rf classes
mkdir -p classes
clojure -e "(compile '${MAIN_NS})"

# options are documented at https://github.com/juxt/pack.alpha
clj -A:pack mach.pack.alpha.jib \
  --image-name eccspence-manager-clojure:latest \
  --image-type docker \
  -e classes \
  -m $MAIN_NS

# TODO: set version
# TODO: set creation time

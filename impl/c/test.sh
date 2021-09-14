#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

mkdir -p build
cd build

cmake -D CMAKE_TOOLCHAIN_FILE=toolchain.cmake ..
make
ctest --output-on-failure

#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

mkdir -p build

cmake -S . -B build --toolchain toolchain.cmake -Wdev --fresh
cmake --build build -v

ctest --test-dir build --output-on-failure

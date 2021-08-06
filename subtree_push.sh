#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

git fetch
test "$(git rev-parse HEAD)" = "$(git rev-parse origin/master)"

git subtree push --prefix=impl/go https://github.com/anyascii/go.git master
git subtree push --prefix=impl/php https://github.com/anyascii/php.git master
git subtree push --prefix=impl/julia https://github.com/anyascii/AnyAscii.jl.git master

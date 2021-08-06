#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

git fetch
test "$(git rev-parse HEAD)" = "$(git rev-parse origin/master)"

git subtree push --prefix=go https://github.com/anyascii/go.git master
git subtree push --prefix=php https://github.com/anyascii/php.git master
git subtree push --prefix=julia https://github.com/anyascii/AnyAscii.jl.git master

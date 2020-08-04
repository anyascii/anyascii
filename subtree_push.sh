#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

test "$(git rev-parse master)" = "$(git rev-parse origin/master)"

git subtree push --prefix=go https://github.com/anyascii/go.git master
git subtree push --prefix=php https://github.com/anyascii/php.git master

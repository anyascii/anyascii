#!/bin/sh

set -eux
cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

cflags='-std=c89 -Wall -Wextra -Wshadow -Werror'
ccs='gcc clang'

for cc in $ccs
do
	if command -v $cc
	then
		$cc $cflags -c anyascii.c
		$cc $cflags -c test.c
		$cc $cflags test.o anyascii.o -o test
		./test
	else
		echo "skipping $cc"
	fi
done

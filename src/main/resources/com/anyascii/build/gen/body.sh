IFS=' '

transliterate_codepoints() {
	while IFS= read -r line
	do
		for cp in $line
		do
			if test "$cp" -lt 128
			then
				printf "\\$(printf %o "$cp")"
			else
				eval "block=\$_$((cp >> 8))"
				printf %s "$(printf %s "$block" | cut -f$(((cp & 255) + 1)))"
			fi
		done
	done
}

transliterate() {
	iconv -c -f utf8 -t utf32 | od -v -A n -t u4 | transliterate_codepoints
}

if test "$#" -ne 0
then
	args=$*
	printf '%s\n' "$args" | transliterate
	if test "$args" = '--version'
	then
		printf '%s\n' '0.4.0-dev' >&2
	fi
elif test -t 0
then
	while IFS= read -r line
	do
		printf '%s\n' "$line" | transliterate
	done
else
	transliterate
fi

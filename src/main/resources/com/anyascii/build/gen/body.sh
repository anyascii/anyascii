transliterate() {
	for cp in $(printf %s "$1" | iconv -f utf8 -t utf32le | od -v -A n -t u)
	do
		if test "$cp" -lt 128
		then
			printf "\\$(printf %o "$cp")"
		else
			eval "block=\$_$((cp >> 8))"
			printf %s "$(printf %s "$block" | cut -f$(((cp & 255) + 1)))"
		fi
	done
	echo
}

if test $# -ne 0
then
	transliterate "$*"
else
	while IFS= read -r line
	do
		transliterate "$line"
	done
fi

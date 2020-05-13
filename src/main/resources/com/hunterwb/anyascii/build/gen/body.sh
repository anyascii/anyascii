output=
for cp in $(printf %s "$*" | iconv -f utf8 -t utf32le | od -A n -t u)
do
	if test "$cp" -lt 128
	then
		s=$(printf "\\$(printf %o "$cp")")
	else
		block_num=$(printf %03x $((cp >> 8)))
		eval "block=\$_$block_num"
		s=$(printf %s "$block" | cut -f$(((cp & 255) + 1)))
	fi
	output=$output$s
done
printf '%s\n' "$output"

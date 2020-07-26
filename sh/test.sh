#!/bin/sh

set -eux

cd -- "$(dirname -- "${BASH_SOURCE:-$0}")"

shells='sh bash dash zsh yash posh mksh rbash rzsh ksh93'
for shell in $shells
do
	if command -v "$shell"
	then
		anyascii="$shell anyascii"
		test "$($anyascii "")" = ""
		test "$($anyascii "	 ~")" = "	 ~"
		test "$($anyascii "sample")" = "sample"
		test "$($anyascii "René François Lacôte")" = "Rene Francois Lacote"
		test "$($anyascii "Großer Hörselberg")" = "Grosser Horselberg"
		test "$($anyascii "Trần Hưng Đạo")" = "Tran Hung Dao"
		test "$($anyascii "Nærøy")" = "Naeroy"
		test "$($anyascii "Φειδιππίδης")" = "Feidippidis"
		test "$($anyascii "Δημήτρης Φωτόπουλος")" = "Dimitris Fotopoylos"
		test "$($anyascii "Борис Николаевич Ельцин")" = "Boris Nikolaevich El'tsin"
		test "$($anyascii "Володимир Горбулін")" = "Volodimir Gorbulin"
		test "$($anyascii "Търговище")" = "T'rgovishche"
		test "$($anyascii "دمنهور")" = "dmnhwr"
		test "$($anyascii "אברהם הלוי פרנקל")" = "'vrhm hlvy frnkl"
		test "$($anyascii "ߞߐߣߊߞߙߌ߫")" = "konakri"
		test "$($anyascii "სამტრედია")" = "samt'redia"
		test "$($anyascii "Աբովյան")" = "Abovyan"
		test "$($anyascii "สงขลา")" = "sngkhla"
		test "$($anyascii "ສະຫວັນນະເຂດ")" = "sahvannaekhd"
		test "$($anyascii "សៀមរាប")" = "siemrab"
		test "$($anyascii "ထန်တလန်")" = "htntln"
		test "$($anyascii "深圳")" = "ShenZhen"
		test "$($anyascii "深水埗")" = "ShenShuiBu"
		test "$($anyascii "화성시")" = "HwaSeongSi"
		test "$($anyascii "華城市")" = "HuaChengShi"
		test "$($anyascii "さいたま")" = "saitama"
		test "$($anyascii "埼玉県")" = "QiYuXian"
		test "$($anyascii "トヨタ")" = "toyota"
		test "$($anyascii "ደብረ ዘይት")" = "debre zeyt"
		test "$($anyascii "ደቀምሓረ")" = "dek'emhare"
		test "$($anyascii "⠠⠎⠁⠽⠀⠭⠀⠁⠛")" = "+say x ag"
		test "$($anyascii "ময়মনসিংহ")" = "mymnsimh"
		test "$($anyascii "પોરબંદર")" = "porbmdr"
		test "$($anyascii "महासमुंद")" = "mhasmumd"
		test "$($anyascii "ಬೆಂಗಳೂರು")" = "bemgluru"
		test "$($anyascii "കളമശ്ശേരി")" = "klmsseri"
		test "$($anyascii "ਜਲੰਧਰ")" = "jlmdhr"
		test "$($anyascii "ଗଜପତି")" = "gjpti"
		test "$($anyascii "රත්නපුර")" = "rtnpur"
		test "$($anyascii "கன்னியாகுமரி")" = "knniyakumri"
		test "$($anyascii "శ్రీకాకుళం")" = "srikakulm"
		test "$($anyascii "😎 👑 🍎")" = ":sunglasses: :crown: :apple:"
		test "$($anyascii "☆ ♯ ♰ ⚄ ⛌")" = "* # + 5 X"
		test "$($anyascii "№ ℳ ⅋ ⅍")" = "No M & A/S"
	else
		echo "skipping $shell"
	fi
done

echo success

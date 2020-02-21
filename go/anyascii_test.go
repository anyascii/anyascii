package anyascii

import "testing"

func TestTransliterate(t *testing.T) {
	check(t, "", "")
	check(t, "René François Lacôte", "Rene Francois Lacote")
	check(t, "Großer Hörselberg", "Grosser Horselberg")
	check(t, "Trần Hưng Đạo", "Tran Hung Dao")
	check(t, "Nærøy", "Naeroy")
	check(t, "Φειδιππίδης", "Feidippidis")
	check(t, "Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos")
	check(t, "Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin")
	check(t, "دمنهور", "dmnhwr")
	check(t, "אברהם הלוי פרנקל", "'vrhm hlvy frnkl")
	check(t, "სამტრედია", "samt'redia")
	check(t, "Աբովյան", "Abovyan")
	check(t, "สงขลา", "sngkhla")
	check(t, "ສະຫວັນນະເຂດ", "sahvannaekhd")
	check(t, "深圳", "ShenZhen")
	check(t, "深水埗", "ShenShuiBu")
	check(t, "화성시", "hwaseongsi")
	check(t, "華城市", "HuaChengShi")
	check(t, "さいたま", "saitama")
	check(t, "埼玉県", "QiYuXian")
	check(t, "トヨタ", "toyota")
	check(t, "⠠⠎⠁⠽⠀⠭⠀⠁⠛", "^say x ag")
}

func check(t *testing.T, s string, expected string) {
	actual := Transliterate(s)
	if actual != expected {
		t.Errorf("Expected <%s> got <%s>", expected, actual)
	}
}

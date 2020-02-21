require "test/unit"
require_relative './any_ascii.rb'

class AnyAsciiTest < Test::Unit::TestCase
	def test_transliterate
		check("", "")
		check("René François Lacôte", "Rene Francois Lacote")
		check("Großer Hörselberg", "Grosser Horselberg")
		check("Trần Hưng Đạo", "Tran Hung Dao")
		check("Nærøy", "Naeroy")
		check("Φειδιππίδης", "Feidippidis")
		check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos")
		check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin")
		check("دمنهور", "dmnhwr")
		check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl")
		check("სამტრედია", "samt'redia")
		check("Աբովյան", "Abovyan")
		check("สงขลา", "sngkhla")
		check("ສະຫວັນນະເຂດ", "sahvannaekhd")
		check("深圳", "ShenZhen")
		check("深水埗", "ShenShuiBu")
		check("화성시", "hwaseongsi")
		check("華城市", "HuaChengShi")
		check("さいたま", "saitama")
		check("埼玉県", "QiYuXian")
		check("トヨタ", "toyota")
		check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "^say x ag")
	end

	def check(s, expected)
		assert_equal(expected, AnyAscii.transliterate(s))
	end
end
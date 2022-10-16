#include <stddef.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "anyascii.h"
#include "utf8.h"

static void anyascii_string(const char *in, char *out) {
	uint32_t utf32;
	uint32_t state = 0;
	for (; *in; in++) {
		utf8_decode(&state, &utf32, (unsigned char) *in);
		switch (state) {
		case UTF8_ACCEPT:;
			const char *r;
			size_t rlen = anyascii(utf32, &r);
			memcpy(out, r, rlen);
			out += rlen;
			break;
		case UTF8_REJECT:
			state = UTF8_ACCEPT;
			break;
		}
	}
	*out = 0;
}

static char *actual;

static void check(const char *s, const char *expected) {
	anyascii_string(s, actual);
	if (strcmp(actual, expected)) {
		printf("%s != %s\n", actual, expected);
		exit(1);
	}
}

static void checkcp(uint32_t utf32, const char *expected) {
	const char *r;
	size_t rlen = anyascii(utf32, &r);
	if (strlen(expected) != rlen || strncmp(r, expected, rlen)) {
		printf("%x -> %.*s != %s\n", utf32, (int) rlen, r, expected);
		exit(1);
	}
}

int main() {
	actual = malloc(256);

	check("sample", "sample");

	checkcp(0x0080, "");
	checkcp(0x00ff, "y");
	checkcp(0xe000, "");
	checkcp(0xffff, "");
	checkcp(0x000e0020, " ");
	checkcp(0x000e007e, "~");
	checkcp(0x000f0000, "");
	checkcp(0x000f0001, "");
	checkcp(0x0010ffff, "");
	checkcp(0x00110000, "");
	checkcp(0x7fffffff, "");
	checkcp(0x80000033, "");
	checkcp(0xffffffff, "");

	check("René François Lacôte", "Rene Francois Lacote");
	check("Blöße", "Blosse");
	check("Trần Hưng Đạo", "Tran Hung Dao");
	check("Nærøy", "Naeroy");
	check("Φειδιππίδης", "Feidippidis");
	check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
	check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
	check("Володимир Горбулін", "Volodimir Gorbulin");
	check("Търговище", "T'rgovishche");
	check("深圳", "ShenZhen");
	check("深水埗", "ShenShuiBu");
	check("화성시", "HwaSeongSi");
	check("華城市", "HuaChengShi");
	check("さいたま", "saitama");
	check("埼玉県", "QiYuXian");
	check("ደብረ ዘይት", "debre zeyt");
	check("ደቀምሓረ", "dek'emhare");
	check("دمنهور", "dmnhwr");
	check("Աբովյան", "Abovyan");
	check("სამტრედია", "samt'redia");
	check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
	check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "+say x ag");
	check("ময়মনসিংহ", "mymnsimh");
	check("ထန်တလန်", "thntln");
	check("પોરબંદર", "porbmdr");
	check("महासमुंद", "mhasmumd");
	check("ಬೆಂಗಳೂರು", "bemgluru");
	check("សៀមរាប", "siemrab");
	check("ສະຫວັນນະເຂດ", "sahvannaekhd");
	check("കളമശ്ശേരി", "klmsseri");
	check("ଗଜପତି", "gjpti");
	check("ਜਲੰਧਰ", "jlmdhr");
	check("රත්නපුර", "rtnpur");
	check("கன்னியாகுமரி", "knniyakumri");
	check("శ్రీకాకుళం", "srikakulm");
	check("สงขลา", "sngkhla");
	check("👑 🌴", ":crown: :palm_tree:");
	check("☆ ♯ ♰ ⚄ ⛌", "* # + 5 X");
	check("№ ℳ ⅋ ⅍", "No M & A/S");

	check("トヨタ", "toyota");
	check("ߞߐߣߊߞߙߌ߫", "konakri");
	check("𐬰𐬀𐬭𐬀𐬚𐬎𐬱𐬙𐬭𐬀", "zarathushtra");
	check("ⵜⵉⴼⵉⵏⴰⵖ", "tifinagh");
	check("𐍅𐌿𐌻𐍆𐌹𐌻𐌰", "wulfila");
	check("ދިވެހި", "dhivehi");
	check("ᨅᨔ ᨕᨘᨁᨗ", "bs ugi");
	check("ϯⲙⲓⲛϩⲱⲣ", "timinhor");
	check("𐐜 𐐢𐐮𐐻𐑊 𐐝𐐻𐐪𐑉", "Dh Litl Star");
	check("ꁌꐭꑤ", "pujjytxiep");
	check("ⰳⰾⰰⰳⱁⰾⰹⱌⰰ", "glagolica");
	check("ᏎᏉᏯ", "SeQuoYa");
	check("ㄓㄨㄤ ㄅㄥ ㄒㄧㄠ", "zhuang beng xiao");
	check("ꚩꚫꛑꚩꚳ ꚳ꛰ꛀꚧꚩꛂ", "ipareim m'shuoiya");
	check("ᓀᐦᐃᔭᐍᐏᐣ", "nehiyawewin");
	check("ᠤᠯᠠᠭᠠᠨᠴᠠᠪ", "ulaganqab");
	check("𐑨𐑯𐑛𐑮𐑩𐑒𐑤𐑰𐑟 𐑯 𐑞 𐑤𐑲𐑩𐑯", "andr'kliiz n dh lai'n");
	
	return 0;
}

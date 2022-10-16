import anyAscii from './any-ascii.js';

function check(s, expected) {
	const actual = anyAscii(s);
	if (actual !== expected) {
		throw new Error(actual + " !== " + expected);
	}
}

check("", "");
check("\u0000\u0001\t\n\u001f ~\u007f", "\u0000\u0001\t\n\u001f ~\u007f");
check("sample", "sample");

check("\u{0080}", "");
check("\u{00ff}", "y");
check("\u{e000}", "");
check("\u{ffff}", "");
check("\u{e0020}", " ");
check("\u{e007e}", "~");
check("\u{f0000}", "");
check("\u{f0001}", "");
check("\u{10ffff}", "");

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

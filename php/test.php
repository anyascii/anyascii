<?php

require __DIR__ . '/AnyAscii.php';

function check($s, $expected) {
	if (AnyAscii::transliterate($s) != $expected) throw new Exception();
}

check("", "");
check("\x00\x01\t\n\x1f ~\x7f", "\x00\x01\t\n\x1f ~\x7f");
check("sample", "sample");

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
check("ময়মনসিংহ", "mymnsimh");
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
check("😎 👑 🍎", ":sunglasses: :crown: :apple:");
check("☆ ♯ ♰ ⚄ ⛌", "* # + 5 X");
check("№ ℳ ⅋ ⅍", "No M & A/S");

check("トヨタ", "toyota");
check("ߞߐߣߊߞߙߌ߫", "konakri");

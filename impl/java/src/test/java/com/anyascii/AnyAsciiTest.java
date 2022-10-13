package com.anyascii;

import org.junit.Assert;
import org.junit.Test;

public final class AnyAsciiTest {

    @Test public void test() {
        check("", "");
        check("\u0000\u0001\t\n\u001f ~\u007f", "\u0000\u0001\t\n\u001f ~\u007f");
        check("sample", "sample");

        check(0x0080, "");
        check(0x00ff, "y");
        check(0xe000, "");
        check(0xffff, "");
        check(0x000e0020, " ");
        check(0x000e007e, "~");
        check(0x000f0000, "");
        check(0x000f0001, "");
        check(0x0010ffff, "");
        check(0x00110000, "");
        check(0x7fffffff, "");
        check(0x80000033, "");
        check(0xffffffff, "");

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
    }

    private static void check(String s, String expected) {
        Assert.assertEquals(AnyAscii.isAscii(s), s.equals(expected));
        Assert.assertTrue(AnyAscii.isAscii(expected));
        Assert.assertEquals(expected, AnyAscii.transliterate(s));
    }

    private static void check(int codePoint, String expected) {
        Assert.assertTrue(AnyAscii.isAscii(expected));
        Assert.assertEquals(expected, AnyAscii.transliterate(codePoint));
    }

    @Test(expected = NullPointerException.class)
    public void testIsAsciiNpe() {
        AnyAscii.isAscii(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe1() {
        AnyAscii.transliterate(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe2() {
        AnyAscii.transliterate(0, null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe3() {
        AnyAscii.transliterate(0xff, null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe4() {
        AnyAscii.transliterate(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe5() {
        AnyAscii.transliterate(null, new StringBuilder());
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe6() {
        AnyAscii.transliterate("-", null);
    }

    @Test(expected = NullPointerException.class)
    public void testTransliterateNpe7() {
        AnyAscii.transliterate("", null);
    }
}

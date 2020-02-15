package com.hunterwb.anyascii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class AnyAsciiTest {

    @Test
    public void testAsciiString() {
        checkAscii("");
        checkAscii("a");
        checkAscii("123");
        checkAscii("!@#$%^&*()");
        checkAscii("\0 \u007F \u0001");
    }

    @Test
    public void testAsciiChar() {
        checkAscii('a');
        checkAscii('.');
        checkAscii('7');
        checkAscii('\u0011');
    }

    @Test
    public void testString() {
        check("résumé", "resume");
        check("άνθρωποι", "anthropoi");
        check("北亰", "BeiJing");
        check(new StringBuilder().appendCodePoint(0xE0033).appendCodePoint(0xE0033).toString(), "33");
    }

    @Test
    public void testReadme() {
        check("René François Lacôte", "Rene Francois Lacote");
        check("Großer Hörselberg", "Grosser Horselberg");
        check("Trần Hưng Đạo", "Tran Hung Dao");
        check("Nærøy", "Naeroy");
        check("Φειδιππίδης", "Feidippidis");
        check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
        check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
        check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
        check("სამტრედია", "samt'redia");
        check("深圳", "ShenZhen");
        check("深水埗", "ShenShuiBu");
        check("화성시", "hwaseongsi");
        check("華城市", "HuaChengShi");
        check("さいたま", "saitama");
        check("埼玉県", "QiYuXian");
        check("トヨタ", "toyota");
        check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "^say x ag");
    }

    private static void checkAscii(String s) {
        Assertions.assertTrue(AnyAscii.isAscii(s));
        Assertions.assertEquals(s, AnyAscii.transliterate(s));
    }

    private static void checkAscii(char c) {
        Assertions.assertTrue(AnyAscii.isAscii(c));
        StringBuilder sb = new StringBuilder();
        AnyAscii.transliterate(c, sb);
        String to = sb.toString();
        Assertions.assertEquals(1, to.length());
        Assertions.assertEquals(c, to.charAt(0));
    }

    private static void check(String s, String expected) {
        String actual = AnyAscii.transliterate(s);
        Assertions.assertTrue(AnyAscii.isAscii(actual));
        Assertions.assertEquals(expected, actual);
    }
}

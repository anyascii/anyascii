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
        check("άνθρωποι", "anthropoi");
        check("北亰", "BeiJing");
        check("résumé", "resume");
        check(new StringBuilder().appendCodePoint(0xE0033).appendCodePoint(0xE0033).toString(), "33");
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

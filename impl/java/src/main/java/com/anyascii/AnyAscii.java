package com.anyascii;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Unicode to ASCII transliteration
 * <p>
 * Converts Unicode characters to their best ASCII representation
 * <p>
 * AnyAscii provides ASCII-only replacement strings for practically all Unicode
 * characters. Text is converted character-by-character without considering the
 * context. The mappings for each script are based on popular existing
 * romanization systems. Symbolic characters are converted based on their
 * meaning or appearance. All ASCII characters in the input are left unchanged,
 * every other character is replaced with printable ASCII characters. Unknown
 * characters and some known characters are replaced with an empty string and
 * removed.
 */
public final class AnyAscii {

    private static volatile ShortMap<String[]> blocks = new ShortMap<String[]>();

    private AnyAscii() {}

    /**
     * Transliterates a string into ASCII.
     *
     * @param s a string
     * @return an ASCII-only string
     * @throws NullPointerException if {@code s == null}
     */
    public static String transliterate(String s) {
        if (isAscii(s)) return s;
        StringBuilder sb = new StringBuilder(s.length());
        transliterate(s, sb);
        return sb.toString();
    }

    /**
     * Determines whether a string is ASCII-only.
     *
     * @param s a string
     * @return {@code true} if the string is ASCII-only, {@code false} otherwise
     * @throws NullPointerException if {@code s == null}
     */
    public static boolean isAscii(CharSequence s) {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (!isAscii(s.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Determines whether a character is in ASCII.
     *
     * @param c a character
     * @return {@code true} if the character is in ASCII, {@code false}
     * otherwise
     */
    public static boolean isAscii(char c) {
        return isAscii((int) c);
    }

    /**
     * Determines whether a character is in ASCII.
     *
     * @param codePoint a character
     * @return {@code true} if the character is in ASCII, {@code false}
     * otherwise
     */
    public static boolean isAscii(int codePoint) {
        return (codePoint >>> 7) == 0;
    }

    /**
     * Transliterates a string into ASCII.
     *
     * @param s a string
     * @param dst the destination buffer
     * @throws NullPointerException if {@code s == null} or {@code dst == null}
     */
    public static void transliterate(CharSequence s, StringBuilder dst) {
        if (dst == null) throw new NullPointerException();
        int length = s.length();
        for (int i = 0; i < length;) {
            int cp = Character.codePointAt(s, i);
            transliterate(cp, dst);
            i += Character.charCount(cp);
        }
    }

    /**
     * Transliterates a character into ASCII.
     *
     * @param codePoint a character
     * @param dst the destination buffer
     * @throws NullPointerException if {@code dst == null}
     */
    public static void transliterate(int codePoint, StringBuilder dst) {
        if (isAscii(codePoint)) {
            dst.append((char) codePoint);
        } else {
            dst.append(transliterate(codePoint));
        }
    }

    /**
     * Transliterates a character into ASCII.
     *
     * @param codePoint a character
     * @return an ASCII-only string
     */
    public static String transliterate(int codePoint) {
        int blockNumInt = codePoint >>> 8;
        if (blockNumInt >= 0xf00) return "";
        short blockNum = (short) blockNumInt;
        ShortMap<String[]> blocks = AnyAscii.blocks;
        int blockIndex = blocks.index(blockNum);
        String[] block;
        if (blockIndex >= 0) {
            block = blocks.get(blockIndex);
        } else {
            block = loadBlock(blockNum);
            AnyAscii.blocks = blocks.put(blockIndex, blockNum, block);
        }
        int lo = codePoint & 0xff;
        if (block.length <= lo) return "";
        return block[lo];
    }

    private static String[] loadBlock(short blockNum) {
        InputStream input = AnyAscii.class.getResourceAsStream(String.format("%03x", blockNum));
        if (input == null) return new String[0];
        IOException e = null;
        String[] block = null;
        try {
            block = split(buffer(input));
        } catch (IOException readException) {
            e = readException;
        }
        try {
            input.close();
        } catch (IOException closeException) {
            if (e == null) e = closeException;
        }
        if (e != null) throw new RuntimeException(e);
        return block;
    }

    private static String[] split(InputStream input) throws IOException {
        Map<String, String> cache = new HashMap<String, String>(256);
        ArrayList<String> block = new ArrayList<String>(256);
        StringBuilder buf = new StringBuilder(0x3f);

        int b;
        while ((b = input.read()) != -1) {
            if (b == 0xff) {
                String s;
                if (buf.length() == 0) {
                    s = "";
                } else {
                    s = buf.toString();
                    buf.setLength(0);
                }

                String c = cache.get(s);
                if (c == null) {
                    cache.put(s, s);
                } else {
                    s = c;
                }
                block.add(s);
            } else {
                buf.append((char) b);
            }
        }
        return block.toArray(new String[0]);
    }

    private static InputStream buffer(InputStream input) throws IOException {
        if (input instanceof BufferedInputStream || input instanceof ByteArrayInputStream) {
            return input;
        }
        int avail = input.available();
        return new BufferedInputStream(input, avail <= 1 ? 2048 : avail);
    }
}

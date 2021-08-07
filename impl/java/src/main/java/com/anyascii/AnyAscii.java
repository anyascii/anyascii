package com.anyascii;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Unicode to ASCII transliteration
 *
 * Converts Unicode characters to their best ASCII representation
 *
 * AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted
 * character-by-character without considering the context. The mappings for each script are based on popular existing
 * romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in
 * the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters
 * and some known characters are replaced with an empty string and removed.
 */
public final class AnyAscii {

    private static volatile ShortMap<String[]> blocks = new ShortMap<String[]>();

    private AnyAscii() {}

    public static String transliterate(String s) {
        if (isAscii(s)) return s;
        StringBuilder sb = new StringBuilder(s.length());
        transliterate(s, sb);
        return sb.toString();
    }

    public static boolean isAscii(CharSequence s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isAscii(s.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isAscii(char c) {
        return isAscii((int) c);
    }

    public static boolean isAscii(int codePoint) {
        return (codePoint >>> 7) == 0;
    }

    public static void transliterate(CharSequence s, StringBuilder dst) {
        for (int i = 0; i < s.length();) {
            int cp = Character.codePointAt(s, i);
            transliterate(cp, dst);
            i += Character.charCount(cp);
        }
    }

    public static void transliterate(int codePoint, StringBuilder dst) {
        if (isAscii(codePoint)) {
            dst.append((char) codePoint);
        } else {
            dst.append(transliterate(codePoint));
        }
    }

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
        String[] block;
        if (input == null) {
            block = new String[0];
        } else {
            try {
                try {
                    block = split(buffer(input));
                } finally {
                    input.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return block;
    }

    @SuppressWarnings("deprecation")
    private static String[] split(InputStream input) throws IOException {
        String[] block = new String[256];
        int blockLen = 0;
        byte[] buf = new byte[8];
        int bufLen = 0;
        int b;
        while ((b = input.read()) != -1) {
            if (b == 0xff) {
                block[blockLen++] = bufLen == 0 ? "" : new String(buf, 0, 0, bufLen).intern();
                bufLen = 0;
            } else {
                if (bufLen == buf.length) {
                    buf = Arrays.copyOf(buf, buf.length * 2);
                }
                buf[bufLen++] = (byte) b;
            }
        }
        if (blockLen < 256) block = Arrays.copyOf(block, blockLen);
        return block;
    }

    private static InputStream buffer(InputStream input) throws IOException {
        if (input instanceof BufferedInputStream || input instanceof ByteArrayInputStream) {
            return input;
        }
        int avail = input.available();
        return new BufferedInputStream(input, avail <= 1 ? 2048 : avail);
    }
}

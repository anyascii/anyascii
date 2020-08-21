package com.anyascii;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Unicode to ASCII transliteration.
 *
 * Converts Unicode text to a reasonable representation using only ASCII.
 *
 * For most characters in Unicode, AnyAscii provides an ASCII-only replacement string.
 * Text is converted character-by-character without considering the context.
 * The mappings for each script are based on popular existing romanization schemes.
 * Symbolic characters are converted based on their meaning or appearance.
 * All ASCII characters in the input are left unchanged,
 * every other character is replaced with printable ASCII characters.
 * Unknown characters are removed.
 */
public final class AnyAscii {

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
        int blockNum = codePoint >>> 8;
        int blockId = Block.block(blockNum);
        if (blockId == -1) return "";
        String[] block = Block.blocks[blockId];
        if (block == null) Block.blocks[blockId] = block = loadBlock(blockNum);
        int lo = codePoint & 0xFF;
        if (block.length <= lo) return "";
        return block[lo];
    }

    private static String[] loadBlock(int blockNum) {
        InputStream input = AnyAscii.class.getResourceAsStream(String.format("%03x", blockNum));
        String[] block;
        if (input == null) {
            block = new String[0];
        } else {
            try {
                try {
                    block = split(new BufferedInputStream(input, 1024));
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
            if (b == 0xFF) {
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
}
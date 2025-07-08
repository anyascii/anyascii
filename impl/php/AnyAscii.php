<?php

/**
 * Unicode to ASCII transliteration
 */

/**
 * Unicode to ASCII transliteration
 * 
 * Converts Unicode characters to their best ASCII representation
 * 
 * AnyAscii provides ASCII-only replacement strings for practically all Unicode
 * characters. Text is converted character-by-character without considering the
 * context. The mappings for each script are based on popular existing
 * romanization systems. Symbolic characters are converted based on their
 * meaning or appearance. All ASCII characters in the input are left unchanged,
 * every other character is replaced with printable ASCII characters. Unknown
 * characters and some known characters are replaced with an empty string and
 * removed.
 */
final class AnyAscii
{

    private static array $blocks = [];

    private function __construct() {}

    /**
     * Transliterates a UTF-8 string into ASCII.
     * 
     * @param string $utf8String a UTF-8 string
     * @throws InvalidArgumentException if input is not valid UTF-8
     * @return string an ASCII-only string
     */
    public static function transliterate(string $utf8String): string
    {
        $result = '';
        $i = 0;
        $len = strlen($utf8String);
        while ($i < $len) {
            $byte = $utf8String[$i];
            if (ord($byte) < 0x80) {
                $i += 1;
                $result .= $byte;
            } else {
                $cp = mb_ord(substr($utf8String, $i, 4), 'UTF-8');
                if ($cp === false) {
                    throw new InvalidArgumentException('Invalid UTF-8 sequence at byte ' . $i);
                }
                if ($cp < 0x800) {
                    $i += 2;
                } elseif ($cp < 0x10000) {
                    $i += 3;
                } else {
                    $i += 4;
                }
                $blockNum = $cp >> 12;
                $block = self::$blocks[$blockNum] ??= self::readBlock($blockNum);
                $lo = $cp & 0xfff;
                if (isset($block[$lo])) {
                    $result .= $block[$lo];
                }
            }
        }
        return $result;
    }

    private static function readBlock(int $blockNum): array
    {
        $fileName = sprintf('%s/_data/%02x', __DIR__, $blockNum);
        if (!file_exists($fileName)) {
            return [];
        }
        $b = file_get_contents($fileName);
        if ($b === false) {
            throw new RuntimeException();
        }
        $s = gzinflate($b);
        if ($s === false) {
            throw new RuntimeException();
        }
        $block = explode("\t", $s);
        return $block;
    }
}

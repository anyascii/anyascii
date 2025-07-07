<?php

class AnyAscii {

	private static $blocks = array();

	public static function transliterate($utf8) {
		$result = '';
		$i = 0;
		$len = strlen($utf8);
		while ($i < $len) {
			$byte = $utf8[$i];
			if (ord($byte) < 0x80) {
				$i += 1;
				$result .= $byte;
				continue;
			}
			$cp = mb_ord(substr($utf8, $i, 4), 'UTF-8');
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
			if (!isset(self::$blocks[$blockNum])) {
				$fileName = sprintf('%s/_data/_%02x.php', __DIR__, $blockNum);
				$block = file_exists($fileName) ? require $fileName : array();
				self::$blocks[$blockNum] = $block;
			} else {
				$block = self::$blocks[$blockNum];
			}
			$lo = ($cp & 0xfff);
			if (isset($block[$lo])) {
				$result .= $block[$lo];
			}
		}
		return $result;
	}
}

package anyascii

import "strings"

func Transliterate(s string) string {
	var sb strings.Builder
	sb.Grow(len(s) / 2)
	for _, r := range s {
		if uint32(r) <= 0x7f {
			sb.WriteByte(byte(r))
		} else {
			sb.WriteString(TransliterateRune(r))
		}
	}
	return sb.String()
}

func TransliterateRune(r rune) string {
	blockNum := uint32(r) >> 8
	block := block(blockNum)
	lo := 3 * (int(r) & 0xff)
	if len(block) <= lo {
		return ""
	}
	l := block[lo+2]
	var len int
	if (l & 0x80) == 0 {
		len = 3
	} else {
		len = int(l & 0x7f)
	}
	if len <= 3 {
		return block[lo : lo+len]
	} else {
		plane := blockNum >> 8
		var bank string
		if plane == 1 {
			bank = bank1
		} else {
			bank = bank0
		}
		i := (int(block[lo]) << 8) | int(block[lo+1])
		return bank[i : i+len]
	}
}

package anyascii

import "strings"

func Transliterate(s string) string {
	var sb strings.Builder
	sb.Grow(len(s))
	for _, r := range s {
		sb.WriteString(TransliterateRune(r))
	}
	return sb.String()
}

func TransliterateRune(r rune) string {
	blockNum := uint32(r) >> 8
	block := Block(blockNum)
	lo := 3 * (int(r) & 0xFF)
	if len(block) <= lo {
		return ""
	}
	len := int(block[lo + 2])
	if len >= 32 {
		len = 3
	}
	if len <= 3 {
		return block[lo:lo + len]
	} else {
		i := (int(block[lo]) << 8) | int(block[lo + 1])
		return Strings[i:i + len]
	}
}

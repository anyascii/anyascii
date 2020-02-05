package anyascii

import "github.com/hunterwb/any-ascii/go"

func Transliterate(s string) string {
	return anyascii.Transliterate(s)
}

func TransliterateRune(r rune) string {
	return anyascii.TransliterateRune(r)
}

size_t anyascii(uint_least32_t utf32, const char **ascii) {
	uint_least32_t blocknum = utf32 >> 8;
	const unsigned char *b = block(blocknum);
	size_t blen = (size_t) b[0] * 3 + 1;
	size_t lo = (utf32 & 0xff) * 3 + 1;
	size_t l, len;
	if (lo > blen) return 0;
	l = b[lo + 2];
	len = l & 0x80 ? l & 0x7f : 3;
	if (len <= 3) {
		*ascii = (const char *) b + lo;
	} else {
	    uint_least32_t plane = blocknum >> 8;
		const char *bank = plane == 1 ? BANK1 : BANK0;
		size_t i = ((size_t) b[lo] << 8) | b[lo + 1];
		*ascii = bank + i;
	}
	return len;
}

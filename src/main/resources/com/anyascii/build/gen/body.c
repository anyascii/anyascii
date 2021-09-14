size_t anyascii(uint_least32_t utf32, const char **ascii) {
	uint_least32_t blocknum = utf32 >> 8;
	const char *b = block(blocknum);
	size_t blen = ((size_t) (unsigned char) b[0]) * 3 + 1;
	size_t lo = (utf32 & 0xff) * 3 + 1;
	if (lo > blen) return 0;
	size_t l = (unsigned char) b[lo + 2];
	size_t len = l & 0x80 ? l & 0x7f : 3;
	if (len <= 3) {
		*ascii = b + lo;
	} else {
		size_t i0 = (unsigned char) b[lo];
		size_t i1 = (unsigned char) b[lo + 1];
		size_t i = (i0 << 8) | i1;
		*ascii = bank + i;
	}
	return len;
}

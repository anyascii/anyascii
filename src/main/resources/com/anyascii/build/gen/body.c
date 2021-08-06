uint32_t anyascii(uint32_t utf32, const char **ascii) {
	uint16_t blocknum = utf32 >> 8;
	const char *b = block(blocknum);
	uint32_t blen = 1 + 3 * ((uint8_t) b[0]);
	uint32_t lo = 1 + 3 * (utf32 & 0xff);
	if (lo > blen) return 0;
	uint8_t l = b[lo + 2];
	uint8_t len = l & 0x80 ? l & 0x7f : 3;
	if (len <= 3) {
		*ascii = b + lo;
	} else {
		uint16_t i = (((uint8_t) b[lo]) << 8) | ((uint8_t) b[lo + 1]);
		*ascii = bank + i;
	}
	return len;
}

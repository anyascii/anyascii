#include <stddef.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include "anyascii.h"
#include "utf8.h"

static size_t transliterate_byte(uint32_t *state, uint32_t *utf32, char byte) {
	utf8_decode(state, utf32, (unsigned char) byte);
	switch (*state) {
	case UTF8_ACCEPT:;
		const char *ascii;
		size_t count = anyascii(*utf32, &ascii);
		if (count) return count - fwrite(ascii, 1, count, stdout);
		break;
	case UTF8_REJECT:
		*state = UTF8_ACCEPT;
		break;
	}
	return 0;
}

static int transliterate_io() {
	int c;
	uint32_t utf32;
	uint32_t state = 0;
	while ((c = getchar()) != EOF) {
		if (transliterate_byte(&state, &utf32, (char) c)) return feof(stdout) | ferror(stdout);
	}
	return ferror(stdin);
}

static int transliterate_args(int argc, char **argv) {
	for (int i = 1; i < argc; i++) {
		if (i > 1 && putchar(' ') == EOF) goto end;
		char *s = argv[i];
		uint32_t utf32;
		uint32_t state = 0;
		for (; *s; s++) {
			if (transliterate_byte(&state, &utf32, *s)) goto end;
		}
	}
	putchar('\n');
	end:
	return feof(stdout) | ferror(stdout);
}

int main(int argc, char **argv) {
	return argc <= 1 ? transliterate_io() : transliterate_args(argc, argv);
}

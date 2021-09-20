#include <stdint.h>

#ifndef UTF8_H
#define UTF8_H

#define UTF8_ACCEPT 0
#define UTF8_REJECT 12

void utf8_decode(uint32_t *state, uint32_t *utf32, uint32_t byte);

#endif

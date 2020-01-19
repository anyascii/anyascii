'use strict';

const assert = require('assert');
const anyAscii = require('./any-ascii.js');

const check = (s, expected) => assert.equal(anyAscii(s), expected);

check('', '');
check('a', 'a');
check('123', '123');
check('\u0000', '\u0000');
check('\u0001\u0002\u007F', '\u0001\u0002\u007F');
check('άνθρωποι', 'anthropoi');
check('北亰', 'BeiJing');
check('résumé', 'resume');
check(String.fromCodePoint(0xE0033) + String.fromCodePoint(0xE0033), '33');

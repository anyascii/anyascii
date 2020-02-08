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

check("© ㎧ Æ №", "(C) m/s AE No");
check("René François Lacôte", "Rene Francois Lacote");
check("Großer Hörselberg", "Grosser Horselberg");
check("Trần Hưng Đạo", "Tran Hung Dao");
check("Nærøy", "Naeroy");
check("Φειδιππίδης", "Feidippidis");
check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
check("深圳", "ShenZhen");
check("深水埗", "ShenShuiBu");
check("화성시", "hwaseongsi");
check("華城市", "HuaChengShi");
check("さいたま", "saitama");
check("埼玉県", "QiYuXian");
check("トヨタ", "toyota");
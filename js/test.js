'use strict';

const assert = require('assert');
const anyAscii = require('./any-ascii.js');

const check = (s, expected) => assert.equal(anyAscii(s), expected);

check("", "");
check("René François Lacôte", "Rene Francois Lacote");
check("Großer Hörselberg", "Grosser Horselberg");
check("Trần Hưng Đạo", "Tran Hung Dao");
check("Nærøy", "Naeroy");
check("Φειδιππίδης", "Feidippidis");
check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
check("სამტრედია", "samt'redia");
check("Աբովյան", "Abovyan");
check("สงขลา", "sngkhla");
check("ສະຫວັນນະເຂດ", "sahvannaekhd");
check("深圳", "ShenZhen");
check("深水埗", "ShenShuiBu");
check("화성시", "hwaseongsi");
check("華城市", "HuaChengShi");
check("さいたま", "saitama");
check("埼玉県", "QiYuXian");
check("トヨタ", "toyota");
check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "^say x ag");
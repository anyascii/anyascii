'use strict';

const block = require('./block.js');

module.exports = function anyAscii(string) {
    const result = [];
    for (const c of string) {
        const codePoint = c.codePointAt(0);
        if (codePoint <= 0x7F) {
            result.push(c);
            continue;
        }
        const blockNum = codePoint >>> 8;
        const lo = codePoint & 0xFF;
        const b = block(blockNum);
        if (b.length > lo) {
            result.push(b[lo]);
        }
    }
    return result.join('');
};
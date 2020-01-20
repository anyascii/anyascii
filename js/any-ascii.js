'use strict';

const block = require('./block.js');

const blocks = {};

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
        let b = blocks[blockNum];
        if (b === undefined) {
            blocks[blockNum] = b = block(blockNum);
        }
        if (b.length > lo) {
            result.push(b[lo]);
        }
    }
    return result.join('');
};
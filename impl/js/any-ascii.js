import block from './block.js';

const blocks = Object.create(null);

/**
 * Transliterates a Unicode string into ASCII.
 * 
 * @param {string} string
 * @return {string}
 */
export default function anyAscii(string) {
	let result = '';
	for (const c of string) {
		const codePoint = c.codePointAt(0);
		if (codePoint <= 0x7f) {
			result += c;
			continue;
		}
		const blockNum = codePoint >>> 8;
		const lo = codePoint & 0xff;
		let b = blocks[blockNum];
		if (b === undefined) {
			blocks[blockNum] = b = dedup(block(blockNum).split("\t"));
		}
		if (b.length > lo) {
			result += b[lo];
		}
	}
	return result;
}

function dedup(elems) {
	const cache = Object.create(null);
	for (let i = 0; i < elems.length; i++) {
		const e = elems[i];
		const c = cache[e];
		if (c === undefined) {
			cache[e] = e;
		} else {
			elems[i] = c;
		}
	}
	return elems;
}

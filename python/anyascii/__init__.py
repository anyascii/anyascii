import importlib

_blocks = {}


def anyascii(string):
    result = []
    for char in string:
        codepoint = ord(char)
        if codepoint <= 0x7f:
            result.append(char)
            continue
        blocknum = codepoint >> 8
        lo = codepoint & 0xff
        try:
            block = _blocks[blocknum]
        except KeyError:
            try:
                m = importlib.import_module('anyascii._data._%03x' % blocknum)
                block = tuple(m.b.split('\t'))
                del m.b
            except ImportError:
                block = ()
            _blocks[blocknum] = block
        if len(block) > lo:
            result.append(block[lo])
    return ''.join(result)

from sys import intern
from zlib import decompress

try:
    from importlib.resources import read_binary
except ImportError:
    from pkgutil import get_data as read_binary


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
                b = read_binary('anyascii._data', '%03x' % blocknum)
                s = decompress(b).decode('ascii')
                block = tuple(map(intern, s.split('\t')))
            except FileNotFoundError:
                block = ()
            _blocks[blocknum] = block
        if len(block) > lo:
            result.append(block[lo])
    return ''.join(result)

"""Unicode to ASCII transliteration"""

from sys import intern
from zlib import decompress, MAX_WBITS

try:
    from importlib.resources import read_binary
except ImportError:
    from pkgutil import get_data as read_binary

__version__ = '0.2.0'

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
                s = decompress(b, -MAX_WBITS).decode('ascii')
                block = tuple(map(intern, s.split('\t')))
            except FileNotFoundError:
                block = ()
            _blocks[blocknum] = block
        if len(block) > lo:
            result.append(block[lo])
    return ''.join(result)

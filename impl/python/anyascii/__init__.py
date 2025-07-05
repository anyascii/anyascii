"""Unicode to ASCII transliteration"""

from zlib import MAX_WBITS, decompress

try:
    from importlib.resources import files

    def read_binary(package, resource):
        return files(package).joinpath(resource).read_bytes()

except ImportError:
    try:
        from importlib.resources import read_binary
    except ImportError:
        from pkgutil import get_data as read_binary

__version__ = "0.4.0.dev0"

_blocks = {}


def anyascii(string):
    # type: (str) -> str
    """Transliterate a string into ASCII."""
    try:
        if string.isascii():
            return string
    except AttributeError:
        pass
    result = []
    for char in string:
        codepoint = ord(char)
        if codepoint <= 0x7F:
            result.append(char)
            continue
        blocknum = codepoint >> 12
        lo = codepoint & 0xFFF
        try:
            block = _blocks[blocknum]
        except KeyError:
            try:
                b = read_binary("anyascii._data", "%02x" % blocknum)
                s = decompress(b, -MAX_WBITS).decode("ascii")
                block = tuple(_dedup(s.split("\t")))
            except FileNotFoundError:
                block = ()
            _blocks[blocknum] = block
        if len(block) > lo:
            result.append(block[lo])
    return "".join(result)


def _dedup(elems):
    cache = {}
    for i, e in enumerate(elems):
        c = cache.setdefault(e, e)
        if e is not c:
            elems[i] = c
    return elems

"""Unicode to ASCII transliteration"""

try:
    from ._transliterate import transliterate as _transliterate_text
except ImportError:
    from ._pure import transliterate as _transliterate_text

__version__ = "0.3.4"


def anyascii(string):
    # type: (str) -> str
    """Transliterate a string into ASCII."""
    try:
        if string.isascii():
            return string
    except AttributeError:
        pass
    return _transliterate_text(string)

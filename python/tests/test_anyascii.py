from anyascii import anyascii


def check(s, expected):
    assert anyascii(s) == expected


def test():
    check('', '')
    check('a', 'a')
    check('123', '123')
    check('\u0000', '\u0000')
    check('\u0001\u0002\u007F', '\u0001\u0002\u007F')
    check('άνθρωποι', 'anthropoi')
    check('北亰', 'BeiJing')
    check('résumé', 'resume')
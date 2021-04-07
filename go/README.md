# AnyAscii [![build](https://travis-ci.com/anyascii/anyascii.svg?branch=master)](https://travis-ci.com/anyascii/anyascii)

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

Converts Unicode text to a reasonable representation using only ASCII.

For most characters in Unicode, AnyAscii provides an ASCII-only replacement string.
Text is converted character-by-character without considering the context.
The mappings for each script are based on popular existing romanization schemes.
Symbolic characters are converted based on their meaning or appearance.
All ASCII characters in the input are left unchanged,
every other character is replaced with printable ASCII characters.
Unknown characters are removed.

```go
import "github.com/anyascii/go"

s := anyascii.Transliterate("άνθρωποι")
// anthropoi
```

Go 1.10+ compatible

[**FULL README**](https://github.com/anyascii/anyascii)

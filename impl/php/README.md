# AnyAscii

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

Converts Unicode characters to their best ASCII representation

AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted character-by-character without considering the context. The mappings for each script are based on popular existing romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters and some known characters are replaced with an empty string and removed.

```php
$s = AnyAscii::transliterate('άνθρωποι');
// anthropoi
```

PHP 5.3+ compatible

`composer require anyascii/anyascii`

[**FULL README**](https://github.com/anyascii/anyascii)

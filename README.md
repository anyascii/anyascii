# AnyAscii [![build](https://travis-ci.org/anyascii/anyascii.svg?branch=master)](https://travis-ci.org/anyascii/anyascii)
[![jitpack](https://img.shields.io/jitpack/v/github/anyascii/anyascii)](https://jitpack.io/#com.anyascii/anyascii)
[![npm](https://img.shields.io/npm/v/any-ascii)](https://www.npmjs.com/package/any-ascii)
[![packagist](https://img.shields.io/packagist/v/anyascii/anyascii)](https://packagist.org/packages/anyascii/anyascii)
[![pypi](https://img.shields.io/pypi/v/anyascii)](https://pypi.org/project/anyascii/)
[![gem](https://img.shields.io/gem/v/any_ascii)](https://rubygems.org/gems/any_ascii)
[![crates.io](https://img.shields.io/crates/v/any_ascii)](https://crates.io/crates/any_ascii)
[![nuget](https://img.shields.io/nuget/v/AnyAscii)](https://www.nuget.org/packages/AnyAscii)

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

#### Table of Contents

* [Description](#description)
[Examples](#examples)
[Background](#background)
[Details](#details)
* [Implementations](#implementations):
[Go](#go)
[Java](#java)
[JavaScript](#javascript)
[PHP](#php)
[Python](#python)
[Ruby](#ruby)
[Rust](#rust)
[Shell](#shell)
[.NET](#net)
* [Unidecode](#unidecode)
[See Also](#see-also)

## Description

Converts Unicode text to a reasonable representation using only ASCII.

For most characters in Unicode, AnyAscii provides an ASCII-only replacement string.
Text is converted character-by-character without considering the context.
The mappings for each script are based on popular existing romanization schemes.
Symbolic characters are converted based on their meaning or appearance.
All ASCII characters in the input are left unchanged,
every other character is replaced with printable ASCII characters.
Unknown characters are removed.

## Examples

Representative examples for different languages comparing the AnyAscii output to the conventional romanization.

|Language (Script)|Input|Output|Conventional|
|---|---|---|---|
|French (Latin)|René François Lacôte|Rene Francois Lacote|Rene Francois Lacote|
|German (Latin)|Großer Hörselberg|Grosser Horselberg|Grosser Hoerselberg|
|Vietnamese (Latin)|Trần Hưng Đạo|Tran Hung Dao|Tran Hung Dao|
|Norwegian (Latin)|Nærøy|Naeroy|Naroy|
|Ancient Greek (Greek)|Φειδιππίδης|Feidippidis|Pheidippides|
|Modern Greek (Greek)|Δημήτρης Φωτόπουλος|Dimitris Fotopoylos|Dimitris Fotopoulos|
|Russian (Cyrillic)|Борис Николаевич Ельцин|Boris Nikolaevich El'tsin|Boris Nikolayevich Yeltsin|
|Ukrainian (Cyrillic)|Володимир Горбулін|Volodimir Gorbulin|Volodymyr Horbulin|
|Bulgarian (Cyrillic)|Търговище|T'rgovishche|Targovishte|
|Mandarin Chinese (Han)|深圳|ShenZhen|Shenzhen|
|Cantonese Chinese (Han)|深水埗|ShenShuiBu|Sham Shui Po|
|Korean (Hangul)|화성시|HwaSeongSi|Hwaseong-si|
|Korean (Han)|華城市|HuaChengShi|Hwaseong-si|
|Japanese (Hiragana)|さいたま|saitama|Saitama|
|Japanese (Han)|埼玉県|QiYuXian|Saitama-ken|
|Japanese (Katakana)|トヨタ|toyota|Toyota|
|Amharic (Ethiopic)|ደብረ ዘይት|debre zeyt|Dobre Zeyit|
|Tigrinya (Ethiopic)|ደቀምሓረ|dek'emhare|Dekemhare|
|Arabic|دمنهور|dmnhwr|Damanhur|
|Armenian|Աբովյան|Abovyan|Abovyan|
|Georgian|სამტრედია|samt'redia|Samtredia|
|Hebrew|אברהם הלוי פרנקל|'vrhm hlvy frnkl|Abraham Halevi Fraenkel|
|Manding (N'Ko)|ߞߐߣߊߞߙߌ߫|konakri|konakiri|
|Unified English Braille (Braille)|⠠⠎⠁⠽⠀⠭⠀⠁⠛|+say x ag|Say it again|
|Bengali|ময়মনসিংহ|mymnsimh|Mymensingh|
|Burmese (Myanmar)|ထန်တလန်|thntln|Thantlang|
|Gujarati|પોરબંદર|porbmdr|Porbandar|
|Hindi (Devanagari)|महासमुंद|mhasmumd|Mahasamund|
|Kannada|ಬೆಂಗಳೂರು|bemgluru|Bengaluru|
|Khmer|សៀមរាប|siemrab|Siem Reap|
|Lao|ສະຫວັນນະເຂດ|sahvannaekhd|Savannakhet|
|Malayalam|കളമശ്ശേരി|klmsseri|Kalamassery
|Odia|ଗଜପତି|gjpti|Gajapati|
|Punjabi (Gurmukhi)|ਜਲੰਧਰ|jlmdhr|Jalandhar|
|Sinhala|රත්නපුර|rtnpur|Ratnapura|
|Tamil|கன்னியாகுமரி|knniyakumri|Kanniyakumari|
|Telugu|శ్రీకాకుళం|srikakulm|Srikakulam|
|Thai|สงขลา|sngkhla|Songkhla|

|Symbols|Input|Output|
|---|---|---|
|Emojis|😎 👑 🍎|`:sunglasses: :crown: :apple:`|
|Misc.|☆ ♯ ♰ ⚄ ⛌|* # + 5 X|
|Letterlike|№ ℳ ⅋ ⅍|No M & A/S|

## Background

> Unicode is the foundation for text in all modern software:
> it’s how all mobile phones, desktops, and other computers represent the text of every language.
> People are using Unicode every time they type a key on their phone or desktop computer, and every time they look at a web page or text in an application.
> [*](https://www.unicode.org/reports/tr51/#Encoding)

[Unicode](https://en.wikipedia.org/wiki/Unicode) is the universal character set, a global standard to support all the world's languages.
It contains 140,000+ characters used by 150+ scripts along with emojis and various symbols.
Typically encoded into bytes using [UTF-8](https://en.wikipedia.org/wiki/UTF-8).

[ASCII](https://en.wikipedia.org/wiki/ASCII) is the most compatible character set, established in 1967.
It is a subset of Unicode and UTF-8 consisting of 128 characters using 7-bits.
The [printable](https://en.wikipedia.org/wiki/ASCII#Printable_characters) characters are English letters, digits, and punctuation,
with the remaining being [control characters](https://en.wikipedia.org/wiki/ASCII#Control_characters).
The characters found on a standard US keyboard correspond to the printable ASCII characters.

> ... expressed only in the original non-control ASCII range so as to be as widely compatible with as many existing tools, languages, and serialization formats as possible and avoid display issues in text editors and source control.
> [*](https://spec.graphql.org/June2018/#sec-Source-Text)

A language is written using characters from a specific [script](https://en.wikipedia.org/wiki/Writing_system).
A script can be [alphabetic](https://en.wikipedia.org/wiki/Alphabet), [logographic](https://en.wikipedia.org/wiki/Logogram), [syllabic](https://en.wikipedia.org/wiki/Syllabary), or something else.
Some languages use multiple scripts: Japanese uses Kanji, Hiragana, and Katakana.
Some scripts are used by multiple languages: [Han characters](https://en.wikipedia.org/wiki/Chinese_characters) are used in Chinese, Japanese, and Korean.
The script used by English and ASCII is known as the [Latin script](https://en.wikipedia.org/wiki/Latin_script).

When converting text between languages there are multiple properties that can be preserved:
- Meaning: [Translation](https://en.wikipedia.org/wiki/Translation)
- Appearance: Preserving the visual appearance of characters when converting between scripts is rarely possible and requires readers to have knowledge of the source language.
- Sound: [Transcription](https://en.wikipedia.org/wiki/Orthographic_transcription) uses the spelling and pronunciation rules of the target language to produce text that will be pronounced as accurately as possible to the original.
- Spelling: [Transliteration](https://en.wikipedia.org/wiki/Transliteration) converts each character individually using predictable rules.
  A reversible transliteration allows for reconstruction of the original text by using unique mappings for each character.

[Romanization](https://en.wikipedia.org/wiki/Romanization) is the conversion into the Latin script using transliteration or transcription or a mix of both.
Romanization is most commonly used when representing the names of people and places.

> _South Korea's Ministry of Culture & Tourism_:
> Clear to anyone, Romanization is for foreigners.
> Geographical names are Romanized to help foreigners find the place they intend to go to and help them remember cities, villages and mountains they visited and climbed.
> But it is Koreans who make up the Roman transcription of their proper names to print on their business cards and draw up maps for international tourists.
> Sometimes, they write the lyrics of a Korean song in Roman letters to help foreigners join in a singing session or write part of a public address (in Korean) in Roman letters for a visiting foreign VIP.
> In this sense, it is for both foreigners and the local public.
> The Romanization system must not be a code only for the native English-speaking community here but an important tool for international communication between Korean society, foreign residents in the country and the entire external world.
> If any method causes much confusion because it is unable to properly reflect the original sound to the extent that different words are transcribed into the same Roman characters too frequently, it definitely is not a good system.
> [*](https://web.archive.org/web/20070927204130/http://www.korea.net/korea/kor_loca.asp?code=A020303)

## Details

*Comprehensive*:
Supports as many Unicode characters as possible.
The benefits of providing full support even for rare or historic characters outweighs the small overhead of including them.

*Simple*:
Easy to use, understand, and update.
Able to be implemented with consistent behavior across multiple different programming languages.
Has benefits for performance and data size.

*Useful*:
Provides reasonable approximations of the spelling or pronunciation.
Based on popular romanization systems in general use.

## Implementations

AnyAscii is implemented in 9 different programming languages.

### Go

```go
import "github.com/anyascii/go"

s := anyascii.Transliterate("άνθρωποι")
// anthropoi
```

Go 1.10+ Compatible

### Java

```java
String s = AnyAscii.transliterate("άνθρωποι");
// anthropoi
```

Java 6+ compatible

Available from [**JitPack**](https://jitpack.io/#com.anyascii/anyascii)

### JavaScript

##### Node.js

```javascript
const anyAscii = require('any-ascii');

const s = anyAscii('άνθρωποι');
// anthropoi
```

Node.js 4.0+ compatible

Install: `npm install any-ascii`

### PHP

```php
$s = AnyAscii::transliterate('άνθρωποι');
// anthropoi
```

PHP 5.3+ compatible

Install: `composer require anyascii/anyascii`

### Python

```python
from anyascii import anyascii

s = anyascii('άνθρωποι')
#  anthropoi
```

Python 3.3+ compatible

Install: `pip install anyascii`

### Ruby

```ruby
require 'any_ascii'

s = AnyAscii.transliterate('άνθρωποι')
# anthropoi
```

Ruby 2.0+ compatible

Install: `gem install any_ascii`

### Rust

```rust
use any_ascii::any_ascii;

let s = any_ascii("άνθρωποι");
// anthropoi
```

Rust 1.20+ compatible

Install:
```toml
# Cargo.toml
[dependencies]
any_ascii = "0.1.6"
```

##### CLI

```console
$ anyascii άνθρωποι
anthropoi
```

Use `cd rust && cargo build --release` to build a native executable to `rust/target/release/anyascii`

### Shell

```console
$ anyascii άνθρωποι
anthropoi

$ echo άνθρωποι | anyascii
anthropoi

$ anyascii
> άνθρωποι
anthropoi
```

POSIX-compliant

[**Download**](https://raw.githubusercontent.com/anyascii/anyascii/master/sh/anyascii)

### .NET

Install from [**NuGet**](https://www.nuget.org/packages/AnyAscii)

##### C#

```cs
using AnyAscii;

string s = "άνθρωποι".Transliterate();
// anthropoi
```

## Unidecode

AnyAscii is an alternative to (and inspired by) [Unidecode](https://metacpan.org/pod/Text::Unidecode) and its many [ports](https://github.com/search?q=unidecode).
AnyAscii is more up-to-date and supports more than twice as many characters.
Unidecode was originally written in 2001 with minor updates through 2016.
It does not support any characters outside of the [BMP](https://en.wikipedia.org/wiki/Plane_(Unicode)#Basic_Multilingual_Plane).

Compare `table.tsv` and `unidecode/table.tsv` for a complete comparison between AnyAscii and Unidecode.
Note that the Unidecode output has been [modified slightly](unidecode/Unidecode.pl) and that unknown characters are replaced by `"[?] "` while they are removed by AnyAscii.

## See Also

[ALA-LC: Romanization Tables](https://www.loc.gov/catdir/cpso/roman.html)  
[BGN/PCGN: Guidance on Romanization Systems](https://www.gov.uk/government/publications/romanization-systems)  
[Discord: Emojis](https://github.com/anyascii/discord-emojis)  
[ISO: Transliteration Standards](https://www.iso.org/ics/01.140.10/x/p/1/u/1/w/1/d/1)  
[KNAB: Romanization Systems](https://www.eki.ee/knab/kblatyl2.htm)  
[Thomas T. Pedersen: Transliteration of Non-Roman Scripts](http://transliteration.eki.ee/)  
[UNGEGN: Working Group on Romanization Systems](https://www.eki.ee/wgrs/)  
[Unicode Technical Site](https://unicode.org/main.html)  
[Wikipedia: Romanization](https://www.google.com/search?q=site:en.wikipedia.org+romanization+OR+transliteration)  
[Wiktionary: Romanization](https://www.google.com/search?q=site:en.wiktionary.org+romanization+OR+transliteration)  

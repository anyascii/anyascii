# AnyAscii

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

##### Table of Contents

* [Description](#description)
* [Examples](#examples)
* [Implementations](#implementations):
[C](#c)
[Elixir](#elixir)
[Go](#go)
[Java](#java)
[JavaScript](#javascript)
[Julia](#julia)
[PHP](#php)
[Python](#python)
[Ruby](#ruby)
[Rust](#rust)
[Shell](#shell)
[.NET](#net)
* [Details](#details)

## Description

Converts Unicode characters to their best ASCII representation

AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted character-by-character without considering the context. The mappings for each script are based on popular existing romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters and some known characters are replaced with an empty string and removed.

## Examples

Representative examples for different languages comparing the AnyAscii output to the conventional romanization:

|Language (Script)|Input|Output|Conventional|
|---|---|---|---|
|French (Latin)|René François Lacôte|Rene Francois Lacote|Rene Francois Lacote|
|German (Latin)|Blöße|Blosse|Bloesse|
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
|Amharic (Ethiopic)|ደብረ ዘይት|debre zeyt|Debre Zeyit|
|Tigrinya (Ethiopic)|ደቀምሓረ|dek'emhare|Dekemhare|
|Arabic|دمنهور|dmnhwr|Damanhur|
|Armenian|Աբովյան|Abovyan|Abovyan|
|Georgian|სამტრედია|samt'redia|Samtredia|
|Hebrew|אברהם הלוי פרנקל|'vrhm hlvy frnkl|Abraham Halevi Fraenkel|
|Unified English Braille (Braille)|⠠⠎⠁⠽⠀⠭⠀⠁⠛|+say x ag|Say it again|
|Bengali|ময়মনসিংহ|mymnsimh|Mymensingh|
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
|Emojis|👑 🌴|`:crown: :palm_tree:`|
|Misc.|☆ ♯ ♰ ⚄ ⛌|* # + 5 X|
|Letterlike|№ ℳ ⅋ ⅍|No M & A/S|

## Implementations

AnyAscii is implemented across multiple programming languages with the same behavior and versioning

## C

https://raw.githubusercontent.com/anyascii/anyascii/master/impl/c/anyascii.h
https://raw.githubusercontent.com/anyascii/anyascii/master/impl/c/anyascii.c

## Elixir

https://hex.pm/packages/any_ascii

```elixir
iex> AnyAscii.transliterate("άνθρωποι") |> IO.iodata_to_binary()
"anthropoi"
```

Elixir 1.7+ and Erlang/OTP 21.3+ compatible

## Go

https://pkg.go.dev/github.com/anyascii/go

```go
import "github.com/anyascii/go"

s := anyascii.Transliterate("άνθρωποι")
// anthropoi
```

Go 1.10+ compatible

## Java

https://mvnrepository.com/artifact/com.anyascii/anyascii

```java
String s = AnyAscii.transliterate("άνθρωποι");
assert s.equals("anthropoi");
```

Java 6+ compatible

```xml
<dependency>
    <groupId>com.anyascii</groupId>
    <artifactId>anyascii</artifactId>
    <version>LATEST</version>
</dependency>
```

## JavaScript

https://npmjs.com/package/any-ascii

```javascript
import anyAscii from 'any-ascii';

const s = anyAscii('άνθρωποι');
// anthropoi
```

Uses ES modules

`npm install any-ascii`

## Julia

https://juliahub.com/ui/Packages/General/AnyAscii

```julia
julia> using AnyAscii
julia> anyascii("άνθρωποι")
"anthropoi"
```

Julia 1.0+ compatible

`pkg> add AnyAscii`

## PHP

https://packagist.org/packages/anyascii/anyascii

```php
$s = AnyAscii::transliterate('άνθρωποι');
// anthropoi
```

PHP 8.0+ compatible, requires extensions `mbstring` & `zlib`

`composer require anyascii/anyascii`

## Python

https://pypi.org/project/anyascii

```python
from anyascii import anyascii

s = anyascii('άνθρωποι')
assert s == 'anthropoi'
```

Python 3.3+ compatible

`pip install anyascii`

## Ruby

https://rubygems.org/gems/any_ascii

```ruby
require 'any_ascii'

s = AnyAscii.transliterate('άνθρωποι')
# anthropoi
```

Ruby 2.0+ compatible

`gem install any_ascii`

## Rust

https://crates.io/crates/any_ascii

```rust
use any_ascii::any_ascii;

let s = any_ascii("άνθρωποι");
// anthropoi
```

Rust 1.42+ compatible

`cargo add any_ascii`

Install executable: `cargo install any_ascii`

```console
$ anyascii άνθρωποι
anthropoi

$ echo άνθρωποι | anyascii
anthropoi
```

## Shell

https://raw.githubusercontent.com/anyascii/anyascii/master/impl/sh/anyascii

```console
$ anyascii άνθρωποι
anthropoi

$ echo άνθρωποι | anyascii
anthropoi
```

POSIX-compliant

## .NET

https://nuget.org/packages/AnyAscii

```cs
// C#
using AnyAscii;

string s = "άνθρωποι".Transliterate();
// anthropoi
```

.NET Core 3.0+ and .NET 5.0+ compatible

## Details

> Unicode is the universal character encoding. This encoding standard provides the basis for processing, storage and interchange of text data in any language in all modern software and information technology protocols ... Unicode covers all the characters for all the writing systems of the world, modern and ancient. It also includes technical symbols, punctuations, and many other characters used in writing text. [*](https://unicode.org/faq/basic_q.html)

**Unicode** provides a unique numeric value for each character and uses UTF-8 to encode sequences of characters into bytes. UTF-8 uses a variable number of bytes for each character and is backwards compatible with ASCII. Unicode has a name and various properties for each character along with algorithms for casing, collation, equivalence, line breaking, segmentation, text direction, and more.

**ASCII** is the lowest common denominator character encoding, established in 1967 and using 7 bits for 128 characters. The printable characters are space, English letters, digits, symbols, and punctuation. The other 33 characters are control characters including null, tab, newline, and delete. The characters found on a standard US keyboard are from ASCII. Most legacy 8-bit encodings were backwards compatible with ASCII.

> [use] the original non-control ASCII range so as to be as widely compatible with as many existing tools, languages, and serialization formats as possible and avoid display issues in text editors and source control. [*](https://spec.graphql.org/October2021/#sec-Language.Source-Text)

A **language** is written using characters from a **script**. Some languages use multiple scripts and some scripts are used by multiple languages. English uses the Latin script which is based on the Roman alphabet. Other languages using the Latin script require additional letters and diacritics. Unicode encodes characters based on their script and not their language, causing characters to be shared across multiple languages.

When converting text between languages there are multiple properties that can be preserved:

|Original|Transliteration (Spelling)|Transcription (Sound)|Translation (Meaning)|
|---|---|---|---|
|ευαγγέλιο|euaggelio|evangelio|gospel|

**Romanization** is the conversion into the Latin script using **transliteration** and transcription, it is most commonly used when representing the names of people and places. Some nations have an official romanization standard for their language and several organizations publish romanization standards for multiple languages.

AnyAscii follows romanization standards from [ALA-LC](https://loc.gov/catdir/cpso/roman), [BGN/PCGN](https://gov.uk/government/publications/romanization-systems), [ISO](https://iso.org/ics/01.140.10/x/p/1/u/1/w/1/d/1), [KNAB](https://eki.ee/knab/kblatyl2), [UNGEGN](https://eki.ee/wgrs), and other national or scholarly standards. The values and the sources used are documented mostly at `input/tables/` with some at `input/` and `src/main/java/`. AnyAscii transliterates a script based on its majority language. Romanization systems are preferred which use standard capitalization rules and do not represent letters with numbers or symbols. Letters are converted based on their phonetic values and not their visual appearance while symbols are converted based on their meaning or appearance. Unicode [confusables](https://unicode.org/reports/tr39) data is designed to address visual confusability.

Unicode can represent certain characters in two ways: either as a single precomposed character or as a base character followed by combining characters. These representations are to be considered equivalent in behavior and appearance and text can be converted between different [**normalization**](https://unicode.org/reports/tr15) forms to handle this. The default form is NFC which composes combining characters whenever possible, while NFD decomposes them all. The compatibility normalization forms NFKC and NFKD further convert variant characters to their regular versions.

When a character is compatibility equivalent to an all-ASCII string, AnyAscii always uses that as the transliteration. The normalization form of the input to AnyAscii sometimes changes the output, with NFC giving the best results. A simple alternative to AnyAscii that only covers variant and accented ASCII characters would be to convert to NFKD and then remove all non-ASCII characters. A way to remove diacritics and combining characters would be to convert to NFD, remove Nonspacing Mark characters, then convert to NFC.

ASCII characters in the input to AnyAscii will remain unchanged and other characters will be replaced by printable ASCII. Invalid or unknown characters are transliterated to an empty string and removed. Unassigned/reserved characters, noncharacters, surrogates, and private-use characters. In programming languages which use UTF-16, surrogate pairs are decoded to the underlying character.

AnyAscii is implemented across multiple programming languages with the same behavior and versioning. None of the implementations have any dependencies. Updates to AnyAscii may add support for additional characters or change the values for existing characters.

The model of AnyAscii is a mapping from single characters to ASCII strings. Simple convenience methods are also provided which take a string as input and apply the transformation to each character. Some implementations have just one function for both purposes because the programming language represents characters as 1-length strings. For custom behavior such as alternate mappings or skipping certain characters it is required to use AnyAscii at the per-character level for greater control.

Unicode unifies **Chinese** Hanzi, **Japanese** Kanji, and **Korean** Hanja into a single Han script and refers to them as **CJK** characters. A CJK character may be used by multiple languages with differing pronunciations. Varieties of Chinese such as Mandarin and Cantonese also use different pronunciations for the same characters. Additionally, each language or variety uses different romanization systems. The Unicode [Unihan Database](https://unicode.org/reports/tr38) consolidates comprehensive information on CJK characters.

AnyAscii uses Unihan data for transliteration. Extremely rare characters lack pronunciation data in Unihan and other sources. When a character is shared across languages, AnyAscii defaults to the Chinese Mandarin pinyin. AnyAscii transliterates Japanese Kanji very poorly because each Kanji has multiple pronunciations depending on the context. AnyAscii capitalizes the first letter for each CJK or Korean Hangul character.

AnyAscii is an improved alternative to [**Unidecode**](https://metacpan.org/pod/Text::Unidecode). The original Unidecode was written in Perl and last updated in 2016 but it has many [ports](https://github.com/search?q=unidecode) in different programming languages, the most popular is in [Python](https://github.com/avian2/unidecode) and has received minor updates. AnyAscii supports 3x more characters; Unidecode only supports a subset of the basic mulitlingual plane while AnyAscii supports all of Unicode. AnyAscii is better optimized for file size and memory usage. AnyAscii documents the sources used while Unidecode is less systematic. Many of Unidecode's values are based on the Unicode character names which are often misleading and Unidecode's data contains some typos and other mistakes. To compare the mappings see `table.tsv` and `unidecode/unidecode.tsv` and `unidecode/unidecode-py.tsv`.

**Emojis** are converted to shortcode format, like `:palm_tree:`. This format is used by Discord, GitHub, Slack, and others but the emoji names vary between platforms. AnyAscii uses the [Discord](https://github.com/anyascii/discord-emojis) name if available, otherwise it uses the Unicode character name. Emoji names may contain lowercase letters, numbers, and underscores.

AnyAscii supports Unicode 17.0 (2025). It covers 124k of the 159k total Unicode characters, it is missing 34k very rare CJK characters and 1k cuneiform. The bundled data files total 200-550 KB depending on the implementation.

**ISC License** Copyright (c) 2020-2026, Hunter WB

> Geographical names are Romanized to help foreigners find the place they intend to go to and help them remember cities, villages and mountains they visited and climbed. But it is Koreans who make up the Roman transcription of their proper names to print on their business cards and draw up maps for international tourists. Sometimes, they write the lyrics of a Korean song in Roman letters to help foreigners join in a singing session or write part of a public address (in Korean) in Roman letters for a visiting foreign VIP. In this sense, it is for both foreigners and the local public. The Romanization system must not be a code only for the native English-speaking community here but an important tool for international communication between Korean society, foreign residents in the country and the entire external world. [*](https://web.archive.org/web/20070927204130/http://www.korea.net/korea/kor_loca.asp?code=A020303)

# Any-Ascii [![build](https://travis-ci.org/hunterwb/any-ascii.svg?branch=master)](https://travis-ci.org/hunterwb/any-ascii)
[![jitpack](https://img.shields.io/jitpack/v/github/hunterwb/any-ascii)](https://jitpack.io/#com.hunterwb/any-ascii)
[![npm](https://img.shields.io/npm/v/any-ascii)](https://www.npmjs.com/package/any-ascii)
[![pypi](https://img.shields.io/pypi/v/anyascii)](https://pypi.org/project/anyascii/)
[![gem](https://img.shields.io/gem/v/any_ascii)](https://rubygems.org/gems/any_ascii)
[![crates.io](https://img.shields.io/crates/v/any_ascii)](https://crates.io/crates/any_ascii)

Unicode to ASCII transliteration

#### Table of Contents

- [Description](#description)
- [Examples](#examples)
- [Reasoning](#reasoning)
- [Implementations](#implementations)
  - [CLI](#cli)
  - [Go](#go)
  - [Java](#java)
  - [Node.js](#nodejs)
  - [Python](#python)
  - [Ruby](#ruby)
  - [Rust](#rust)
- [See Also](#see-also)

## Description

Converts Unicode text to a reasonable representation using only ASCII.

For most characters in Unicode, Any-Ascii provides an ASCII-only replacement string.
Text is converted character-by-character without considering the context.
The mappings for each language are based on popular existing romanization schemes.
Symbolic characters are converted based on their meaning or appearance.
All ASCII characters in the input are left unchanged,
every other character is replaced with printable ASCII characters.
Unknown characters are removed.

## Examples

Representative examples for different languages comparing the Any-Ascii output to the conventional romanization.

|Language|Script|Input|Output|Conventional|
|---|---|---|---|---|
|French|Latin|René François Lacôte|Rene Francois Lacote|Rene Francois Lacote|
|German|Latin|Großer Hörselberg|Grosser Horselberg|Grosser Hoerselberg|
|Vietnamese|Latin|Trần Hưng Đạo|Tran Hung Dao|Tran Hung Dao|
|Norwegian|Latin|Nærøy|Naeroy|Naroy|
|Ancient Greek|Greek|Φειδιππίδης|Feidippidis|Pheidippides|
|Modern Greek|Greek|Δημήτρης Φωτόπουλος|Dimitris Fotopoylos|Dimitris Fotopoulos|
|Russian|Cyrillic|Борис Николаевич Ельцин|Boris Nikolaevich El'tsin|Boris Nikolayevich Yeltsin|
|Arabic|Arabic|دمنهور|dmnhwr|Damanhur|
|Hebrew|Hebrew|אברהם הלוי פרנקל|'vrhm hlvy frnkl|Abraham Halevi Fraenkel|
|Georgian|Georgian|სამტრედია|samt'redia|Samtredia|
|Armenian|Armenian|Աբովյան|Abovyan|Abovyan|
|Thai|Thai|สงขลา|sngkhla|Songkhla|
|Lao|Lao|ສະຫວັນນະເຂດ|sahvannaekhd|Savannakhet|
|Mandarin Chinese|Han|深圳|ShenZhen|Shenzhen|
|Cantonese Chinese|Han|深水埗|ShenShuiBu|Sham Shui Po|
|Korean|Hangul|화성시|hwaseongsi|Hwaseong-si|
|Korean|Han|華城市|HuaChengShi|Hwaseong-si|
|Japanese|Hiragana|さいたま|saitama|Saitama|
|Japanese|Han|埼玉県|QiYuXian|Saitama-ken|
|Japanese|Katakana|トヨタ|toyota|Toyota|
|Unified English Braille|Braille|⠠⠎⠁⠽⠀⠭⠀⠁⠛|^say x ag|Say it again|
|Bengali|Bengali|ময়মনসিংহ|mymnsimh|Mymensingh|
|Gujarati|Gujarati|પોરબંદર|porbmdr|Porbandar|
|Hindi|Devanagari|महासमुंद|mhasmumd|Mahasamund|
|Punjabi|Gurmukhi|ਜਲੰਧਰ|jlmdhr|Jalandhar|
|Odia|Odia|ଗଜପତି|gjpti|Gajapati|
|Tamil|Tamil|கன்னியாகுமரி|knniyakumri|Kanniyakumari|
|Telugu|Telugu|శ్రీకాకుళం|srikakulm|Srikakulam|

## Reasoning

[Unicode](https://en.wikipedia.org/wiki/Unicode) is the universal character set, a global standard to support all the world's languages.
It consists of 130,000+ characters used by 150 writing systems.
Along with characters used in language, it also contains various technical symbols, emojis, and other symbolic characters.
The `String` type in programming languages usually corresponds to Unicode text.
Whenever text is used digitally on computers or the internet it is almost always represented using Unicode characters.
Unicode characters are not stored directly but instead encoded into bytes using an encoding, typically [UTF-8](https://en.wikipedia.org/wiki/UTF-8).

[ASCII](https://en.wikipedia.org/wiki/ASCII) is the most compatible character set, established in 1967.
It is a subset of Unicode and UTF-8 consisting of 128 characters using 7-bits in the range `0x00` - `0x7F`.
The [printable](https://en.wikipedia.org/wiki/ASCII#Printable_characters) characters are English letters, digits, and punctuation in the range `0x20` - `0x7E`,
with the remaining being [control characters](https://en.wikipedia.org/wiki/ASCII#Control_characters).
All of the characters found on a standard US keyboard correspond to the printable ASCII characters.

Conversion into the [Latin script](https://en.wikipedia.org/wiki/Latin_script) used by English and ASCII is called [romanization](https://en.wikipedia.org/wiki/Romanization).

When converting between writing systems there are multiple properties that can be preserved:
- Meaning: [Translation](https://en.wikipedia.org/wiki/Translation) replaces text with an equivalent in the target language with the same meaning.
  This relies heavily on context and [automatic translation](https://en.wikipedia.org/wiki/Machine_translation) is extremely complicated.
- Appearance: Preserving the visual appearance of a character when converting between languages is rarely possible and requires readers to have knowledge of the source language.
- Sound: [Orthographic transcription](https://en.wikipedia.org/wiki/Orthographic_transcription) uses the spelling and pronunciation rules of the target language to produce text that a speaker of the target language will pronounce as accurately as possible to the original.
- Spelling: [Transliteration](https://en.wikipedia.org/wiki/Transliteration) converts each letter individually using predictable rules.
  An unambiguous transliteration allows for reconstruction of the original text by using unique mappings for each letter.
  A phonetic transliteration instead uses the most phonetically accurate mappings which may result in duplicates or ambiguity.

## Implementations

### CLI

```console
$ anyascii άνθρωποι
anthropoi
```

 Use `cd rust && cargo build --release` to build a native executable to `rust/target/release/anyascii`

### Go

```go
package main

import (
    "github.com/hunterwb/any-ascii"
)

func main() {
    s := anyascii.Transliterate("άνθρωποι")
    // anthropoi
}
```

Go 1.10+ Compatible

### Java

```java
String s = AnyAscii.transliterate("άνθρωποι");
// anthropoi
```

Java 6+ compatible

Available through [**JitPack**](https://jitpack.io/#com.hunterwb/any-ascii)

##### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.hunterwb</groupId>
    <artifactId>any-ascii</artifactId>
    <version>0.1.2</version>
</dependency>
```

##### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.hunterwb:any-ascii:0.1.2'
}
```

### Node.js

```javascript
const anyAscii = require('any-ascii');

const s = anyAscii('άνθρωποι');
// anthropoi
```

Node.js 4.0+ compatible

Install latest release: `npm install any-ascii`

Install pre-release: `npm install hunterwb/any-ascii`

### Python

```python
from anyascii import anyascii

s = anyascii('άνθρωποι')
#  anthropoi
```

Python 3.3+ compatible

Install latest release: `pip install anyascii`

Install pre-release: `pip install https://github.com/hunterwb/any-ascii/archive/master.zip#subdirectory=python`

### Ruby

```ruby
require 'any_ascii'

s = AnyAscii.transliterate('άνθρωποι')
# anthropoi
```

Ruby 2.0+ compatible

Install latest release: `gem install any_ascii`

Use pre-release:
```ruby
# Gemfile
gem 'any_ascii', git: 'https://github.com/hunterwb/any-ascii', glob: 'ruby/any_ascii.gemspec'
```

### Rust

```rust
use any_ascii::any_ascii;

let s = any_ascii("άνθρωποι");
// anthropoi
```

Rust 1.20+ compatible

Use latest release:
```toml
# Cargo.toml
[dependencies]
any_ascii = "0.1.2"
```

Use pre-release:
```toml
# Cargo.toml
[dependencies]
any_ascii = { git = "https://github.com/hunterwb/any-ascii" }
```

## See Also

[ALA-LC Romanization](https://www.loc.gov/catdir/cpso/roman.html)  
[BGN/PCGN Romanization](https://www.gov.uk/government/publications/romanization-systems)  
[Compart: Unicode Charts](https://www.compart.com/en/unicode/)  
[ICAO 9303: Machine Readable Passports](https://www.icao.int/publications/Documents/9303_p3_cons_en.pdf)  
[ISO 15919: Indic Romanization](https://en.wikipedia.org/wiki/ISO_15919)  
[ISO 9: Cyrillic Romanization](https://en.wikipedia.org/wiki/ISO_9)  
[KNAB Romanization Systems](https://www.eki.ee/knab/kblatyl2.htm)  
[Sean M. Burke: Unidecode](https://metacpan.org/pod/Text::Unidecode)  
[Sean M. Burke: Unidecode, Perl Journal](http://interglacial.com/tpj/22/)  
[Thomas T. Pedersen: Transliteration of Non-Roman Scripts](http://transliteration.eki.ee/)  
[UNGEGN Romanization](https://www.eki.ee/wgrs/)  
[Unicode CLDR: Transliteration Guidelines](http://cldr.unicode.org/index/cldr-spec/transliteration-guidelines)  
[Unicode Unihan Database](https://www.unicode.org/reports/tr38/tr38-27.html)  
[Unified English Braille](http://www.iceb.org/ueb.html)  
[Wikipedia: Romanization of Arabic](https://en.wikipedia.org/wiki/Romanization_of_Arabic)  
[Wikipedia: Romanization of Armenian](https://en.wikipedia.org/wiki/Romanization_of_Armenian)  
[Wikipedia: Romanization of Georgian](https://en.wikipedia.org/wiki/Romanization_of_Georgian)  
[Wikipedia: Romanization of Greek](https://en.wikipedia.org/wiki/Romanization_of_Greek)  
[Wikipedia: Romanization of Hebrew](https://en.wikipedia.org/wiki/Romanization_of_Hebrew)  
[Wikipedia: Romanization of Japanese](https://en.wikipedia.org/wiki/Romanization_of_Japanese)  
[Wikipedia: Romanization of Russian](https://en.wikipedia.org/wiki/Romanization_of_Russian)
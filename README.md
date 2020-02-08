# Any Ascii
[![jitpack](https://img.shields.io/jitpack/v/github/hunterwb/any-ascii)](https://jitpack.io/#com.hunterwb/any-ascii)
[![pypi](https://img.shields.io/pypi/v/anyascii)](https://pypi.org/project/anyascii/)
[![npm](https://img.shields.io/npm/v/any-ascii)](https://www.npmjs.com/package/any-ascii)

Unicode to ASCII transliteration

#### Table of Contents


- [Examples](#examples)
- [Implementations](#implementations)
  - [CLI](#cli)
  - [Go](#go)
  - [Java](#java)
  - [Node.js](#nodejs)
  - [Python](#python)
  - [Rust](#rust)
- [Glossary](#glossary)
- [See Also](#see-also)


## Examples

|Language|Script|Input|Output|Actual|
|---|---|---|---|---|
|||© ㎧ Æ №|(C) m/s AE No||
|French|Latin|René François Lacôte|Rene Francois Lacote|Rene Francois Lacote|
|German|Latin|Großer Hörselberg|Grosser Horselberg|Grosser Hoerselberg|
|Vietnamese|Latin|Trần Hưng Đạo|Tran Hung Dao|Tran Hung Dao|
|Norwegian|Latin|Nærøy|Naeroy|Naroy|
|Ancient Greek|Greek|Φειδιππίδης|Feidippidis|Pheidippides|
|Modern Greek|Greek|Δημήτρης Φωτόπουλος|Dimitris Fotopoylos|Dimitris Fotopoulos|
|Russian|Cyrillic|Борис Николаевич Ельцин|Boris Nikolaevich El'tsin|Boris Nikolayevich Yeltsin|
|Hebrew|Hebrew|אברהם הלוי פרנקל|'vrhm hlvy frnkl|Abraham Halevi Fraenkel|
|Mandarin Chinese|Han|深圳|ShenZhen|Shenzhen|
|Cantonese Chinese|Han|深水埗|ShenShuiBu|Sham Shui Po|
|Korean|Hangul|화성시|hwaseongsi|Hwaseong-si|
|Korean|Han|華城市|HuaChengShi|Hwaseong-si|
|Japanese|Hiragana|さいたま|saitama|Saitama|
|Japanese|Han|埼玉県|QiYuXian|Saitama-ken|
|Japanese|Katakana|トヨタ|toyota|Toyota|

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
    <version>0.1.1</version>
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
    implementation 'com.hunterwb:any-ascii:0.1.1'
}
```

### Node.js

```javascript
const anyAscii = require('any-ascii');

const s = anyAscii('άνθρωποι');
// anthropoi
```

Node.js 4+ compatible

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

### Rust

```rust
use any_ascii::any_ascii;

let s = any_ascii("άνθρωποι");
// anthropoi
```

Use pre-release:
```toml
[dependencies]
any_ascii = { git = "https://github.com/hunterwb/any-ascii" }
```

## Glossary

- [**Unicode**](https://en.wikipedia.org/wiki/Unicode):
The universal character set, a global standard to support all the world's languages.
Consists of 100,000+ characters used by 150 writing systems.
Typically encoded into bytes using [UTF-8](https://en.wikipedia.org/wiki/UTF-8).
- [**ASCII**](https://en.wikipedia.org/wiki/ASCII):
The most compatible character set.
A subset of Unicode/UTF-8 consisting of 128 characters using 7-bits in the range `0x00` - `0x7F`.
The [printable](https://en.wikipedia.org/wiki/ASCII#Printable_characters) characters are English letters, digits, and punctuation in the range `0x20` - `0x7E`,
with the remaining being [control characters](https://en.wikipedia.org/wiki/ASCII#Control_characters).
- [**Transliteration**](https://en.wikipedia.org/wiki/Transliteration):
A mapping from one writing system into another, typically done one character at a time using predictable rules.
Transliteration generally preserves the spelling of words, while translation preserves the meaning, and transcription preserves the sound.
Transliteration into the [Latin script](https://en.wikipedia.org/wiki/Latin_script) used by English is known as [romanization](https://en.wikipedia.org/wiki/Romanization).

## See Also

[ALA-LC Romanization](https://www.loc.gov/catdir/cpso/roman.html)  
[BGN/PCGN Romanization](https://www.gov.uk/government/publications/romanization-systems)  
[Compart: Unicode Charts](https://www.compart.com/en/unicode/)  
[ICAO 9303: Machine Readable Passports](https://www.icao.int/publications/Documents/9303_p3_cons_en.pdf)  
[ISO 9: Cyrillic Romanization](https://en.wikipedia.org/wiki/ISO_9)  
[Sean M. Burke: Unidecode](https://metacpan.org/pod/Text::Unidecode)  
[Sean M. Burke: Unidecode, Perl Journal](http://interglacial.com/tpj/22/)  
[UNGEGN Romanization](https://www.eki.ee/wgrs/)  
[Unicode CLDR: Transliteration Guidelines](http://cldr.unicode.org/index/cldr-spec/transliteration-guidelines)  
[Unicode Unihan Database](https://www.unicode.org/reports/tr38/tr38-27.html)  
[Wikipedia: Romanization of Greek](https://en.wikipedia.org/wiki/Romanization_of_Greek)
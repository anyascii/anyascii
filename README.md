# Any Ascii
[![jitpack](https://img.shields.io/jitpack/v/github/hunterwb/any-ascii)](https://jitpack.io/#com.hunterwb/any-ascii)
[![pypi](https://img.shields.io/pypi/v/anyascii)](https://pypi.org/project/anyascii/)
[![npm](https://img.shields.io/npm/v/any-ascii)](https://www.npmjs.com/package/any-ascii)

Unicode to ASCII transliteration

#### Table of Contents

- [Glossary](#glossary)
- [Examples](#examples)
- [Java](#java)
- [Python](#python)
- [Node.js](#nodejs)
- [See Also](#see-also)

## Glossary

- [**Unicode**](https://en.wikipedia.org/wiki/Unicode):
The universal character set, a global standard to support all the world's languages.
Consists of 100,000+ characters used by 150 writing systems.
Typically encoded into bytes using [UTF-8](https://en.wikipedia.org/wiki/UTF-8).
- [**ASCII**](https://en.wikipedia.org/wiki/ASCII):
The most compatible character set.
A subset of Unicode/UTF-8 consisting of 128 characters using 7-bits in the range `0x00` - `0x7F`.
The [visible](https://en.wikipedia.org/wiki/ASCII#Printable_characters) characters are English letters, digits, and punctuation in the range `0x20` - `0x7E`,
with the remaining being [control characters](https://en.wikipedia.org/wiki/ASCII#Control_characters).
- [**Transliteration**](https://en.wikipedia.org/wiki/Transliteration):
A mapping from one writing system into another, typically done one character at a time using predictable rules.
Transliteration into the [Latin script](https://en.wikipedia.org/wiki/Latin_script) used by English is known as [romanization](https://en.wikipedia.org/wiki/Romanization).

## Examples

|Script|Input|Output|Actual|
|---|---|---|---|
|Mandarin Chinese|深圳|ShenZhen|Shenzhen|
|Cantonese Chinese|深水埗|ShenShuiBu|Sham Shui Po|
|Russian Cyrillic|Борис Николаевич Ельцин|Boris Nikolaevich El'tsin|Boris Nikolayevich Yeltsin|
|Korean Hangul|반기문|bangimun|Ban Ki-Moon|
|Japanese Hiragana|さいたま|saitama|Saitama|
|Japanese Kanji|埼玉県|QiYuXian|Saitama-ken|
|Ancient Greek|Φειδιππίδης|Feidippidis|Pheidippides|
|Modern Greek|Δημήτρης Φωτόπουλος|Dimitris Fotopoylos|Dimitris Fotopoulos|

## Java

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
    <version>0.1.0</version>
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
    implementation 'com.hunterwb:any-ascii:0.1.0'
}
```

## Python

```python
from anyascii import anyascii

s = anyascii('άνθρωποι')
#  anthropoi
```

Python 3.3+ compatible

Install latest release: `pip install anyascii`

Install from master: `pip install https://github.com/hunterwb/any-ascii/archive/master.zip#subdirectory=python`

## Node.js

```javascript
const anyAscii = require('any-ascii');

const s = anyAscii('άνθρωποι');
// anthropoi
```

Node.js 4+ compatible

Install latest release: `npm install any-ascii`

Install from master: `npm install hunterwb/any-ascii`

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
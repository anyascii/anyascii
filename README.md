# Any Ascii
[![JitPack](https://jitpack.io/v/com.hunterwb/any-ascii.svg)](https://jitpack.io/#com.hunterwb/any-ascii)

#### Table of Contents

- [Java](#Java)
- [Python](#Python)

## Java

```java
String s = AnyAscii.transliterate("άνθρωποι");
// anthropoi
```

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
    <version>${version}</version>
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
    implementation "com.hunterwb:any-ascii:${version}"
}
```

## Python

```python
from anyascii import anyascii

s = anyascii('άνθρωποι')
#  anthropoi
```
# AnyAscii [![build](https://app.travis-ci.com/anyascii/anyascii.svg?branch=master)](https://app.travis-ci.com/anyascii/anyascii)

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

Converts Unicode characters to their best ASCII representation

AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted character-by-character without considering the context. The mappings for each script are based on popular existing romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters and some known characters are replaced with an empty string and removed.

```rust
use any_ascii::any_ascii;

let s = any_ascii("άνθρωποι");
// anthropoi
```

Rust 1.36+ compatible

```toml
# Cargo.toml
[dependencies]
any_ascii = "*"
```

Install executable: `cargo install any_ascii`

```console
$ anyascii άνθρωποι
anthropoi

$ echo άνθρωποι | anyascii
anthropoi
```

[**FULL README**](https://github.com/anyascii/anyascii)

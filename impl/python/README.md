# AnyAscii

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

Converts Unicode characters to their best ASCII representation

AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted character-by-character without considering the context. The mappings for each script are based on popular existing romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters and some known characters are replaced with an empty string and removed.

```python
from anyascii import anyascii

s = anyascii("άνθρωποι")
assert s == "anthropoi"
```

Python 3.10+ compatible

`pip install anyascii-rs`

This is a Rust-backed fork of [AnyAscii](https://github.com/anyascii/anyascii)
published as `anyascii-rs`. It installs the same `anyascii` import package, so
it replaces the original `anyascii` distribution; do not install both in one
environment.

On supported platforms, pip selects a Rust-backed wheel for standard CPython
3.10 and later. The universal Python wheel supports Python 3.10 and later,
including environments without a compatible native wheel. Subinterpreters
use the pure Python implementation bundled in the wheel.

## Benchmark

This table compares Unidecode 1.4.0, `anyascii` 0.3.3 and `anyascii-rs`. The values show the time for one call. A lower value is better. The last column shows how many times faster `anyascii-rs` is than `anyascii` 0.3.3. Each mixed text contains the short samples, one after the other. The test repeats this text and cuts it to the given length.

|Sample|Characters|Unidecode 1.4.0|anyascii 0.3.3|anyascii-rs|Speedup vs anyascii 0.3.3\*|
|---|--:|--:|--:|--:|--:|
|ASCII|90|58 ns|37 ns|33 ns|1.1×|
|Latin|62|4.7 µs|2.8 µs|185 ns|14.9×|
|Cyrillic|57|8.5 µs|4.8 µs|309 ns|15.4×|
|Greek|34|5.4 µs|3.0 µs|238 ns|12.5×|
|Chinese|19|3.5 µs|2.0 µs|242 ns|8.1×|
|Japanese|26|4.4 µs|2.4 µs|232 ns|10.2×|
|Emoji|26|2.9 µs|1.5 µs|230 ns|6.7×|
|Small mixed text|100|6.4 µs|3.9 µs|188 ns|20.9×|
|Medium mixed text|1,000|106 µs|60.5 µs|2.4 µs|24.9×|
|Long mixed text|5,000|539 µs|305 µs|11.8 µs|25.9×|
|Very long mixed text|150,000|16.0 ms|8.9 ms|346 µs|25.8×|

\* This is the speedup after `anyascii` 0.3.3 has been warmed up. `anyascii` 0.3.3 loads its data for each block of characters on the first use. Thus, the first call for a new block is slower.

We did the test with Python 3.13 on Apple Silicon. Each value is the best result of 7 runs. For ASCII text, all three libraries return the input string unchanged. Thus, the times are almost the same.

## Building distributions

Run `python -m build` from `impl/python` to build the universal wheel and source
archive. From `impl/python` in a full checkout, run
`maturin build --release --locked --interpreter python` with Rust 1.85 or later,
maturin 1.16, and standard CPython 3.10 or later. The Python workflow builds
and tests native wheels for Linux (glibc) and macOS on x86-64 and ARM64, and
Windows on x86-64.

After an in-place native build with `maturin develop`, use `python -m build`
to build the universal wheel from the source archive. The archive excludes
the compiled extension.

When bumping the Python version, update `pyproject.toml` and
`anyascii.__version__` together. After bumping the Rust crate version, run
`cargo update --workspace --manifest-path native/Cargo.toml` from `impl/python`
to refresh the native wheel's lockfile.

[**FULL README**](https://github.com/PLPeeters/anyascii)

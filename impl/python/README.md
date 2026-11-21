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

Python 3.3+ compatible

`pip install anyascii-rs`

This is a Rust-backed fork of [AnyAscii](https://github.com/anyascii/anyascii)
published as `anyascii-rs`. It installs the same `anyascii` import package, so
it replaces the original `anyascii` distribution; do not install both in one
environment.

On supported platforms, pip selects a Rust-backed wheel for standard CPython
3.10 and later. The universal Python wheel supports Python 3.3 and later,
including environments without a compatible native wheel. Subinterpreters
use the pure Python implementation bundled in the wheel.

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

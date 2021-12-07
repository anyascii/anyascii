# AnyAscii [![build](https://app.travis-ci.com/anyascii/anyascii.svg?branch=master)](https://app.travis-ci.com/anyascii/anyascii)

Unicode to ASCII transliteration

[**Web Demo**](https://anyascii.com)

Converts Unicode characters to their best ASCII representation

AnyAscii provides ASCII-only replacement strings for practically all Unicode characters. Text is converted character-by-character without considering the context. The mappings for each script are based on popular existing romanization systems. Symbolic characters are converted based on their meaning or appearance. All ASCII characters in the input are left unchanged, every other character is replaced with printable ASCII characters. Unknown characters and some known characters are replaced with an empty string and removed.

## Installation

[Available in Hex](https://hex.pm/docs/publish), the package can be installed
by adding `anyascii` to your list of dependencies in `mix.exs`:

```elixir
def deps do
  [
    {:anyascii, "~> 0.3.1"}
  ]
end
```

Documentation can be generated with [ExDoc](https://github.com/elixir-lang/ex_doc)
and published on [HexDocs](https://hexdocs.pm). Once published, the docs can
be found at [https://hexdocs.pm/anyascii](https://hexdocs.pm/anyascii).

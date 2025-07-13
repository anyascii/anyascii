defmodule AnyAscii do
  @moduledoc """
  Unicode to ASCII transliteration

  Converts Unicode characters to their best ASCII representation

  AnyAscii provides ASCII-only replacement strings for practically all Unicode
  characters. Text is converted character-by-character without considering the
  context. The mappings for each script are based on popular existing
  romanization systems. Symbolic characters are converted based on their meaning
  or appearance. All ASCII characters in the input are left unchanged, every
  other character is replaced with printable ASCII characters. Unknown
  characters and some known characters are replaced with an empty string and
  removed.
  """

  import Bitwise

  @blocks_path_pattern :code.priv_dir(:any_ascii) |> Path.join("*")
  blocks_paths = Path.wildcard(@blocks_path_pattern)

  @blocks_hash blocks_paths
               |> Enum.map(&File.read!/1)
               |> :erlang.md5()

  for path <- blocks_paths, do: @external_resource(path)

  @doc """
  Transliterates chardata into ASCII.

  ## Examples

      iex> AnyAscii.transliterate("άνθρωποι") |> IO.iodata_to_binary()
      "anthropoi"

      iex> AnyAscii.transliterate(~c"Борис") |> IO.iodata_to_binary()
      "Boris"

      iex> AnyAscii.transliterate([?深]) |> IO.iodata_to_binary()
      "Shen"

  """
  @spec transliterate(IO.chardata()) :: IO.chardata()
  def transliterate(chardata)

  def transliterate([c | t]) when c in 0..0x7F,
    do: [c | transliterate(t)]

  def transliterate([c | t]) when c in 0x80..0x10FFFF,
    do: [transliterate_char(c) | transliterate(t)]

  def transliterate([h | t]),
    do: [transliterate(h) | transliterate(t)]

  def transliterate([]),
    do: []

  def transliterate(<<s::binary>>),
    do: transliterate(String.to_charlist(s))

  defp transliterate_char(c) do
    block_num = c >>> 8
    lo = c &&& 0xFF
    block = get_block(block_num)
    if lo < tuple_size(block), do: elem(block, lo), else: []
  end

  for path <- blocks_paths do
    block_num = Path.basename(path) |> String.to_integer()

    block_content =
      path
      |> File.read!()
      |> :zlib.unzip()
      |> String.split("\t")
      |> Enum.map(fn
        "" -> []
        <<c>> -> c
        s -> s
      end)
      |> List.to_tuple()

    defp get_block(unquote(block_num)), do: unquote(Macro.escape(block_content))
  end

  defp get_block(_), do: {}

  @doc false
  @spec __mix_recompile__?() :: boolean()
  def __mix_recompile__? do
    actual_blocks_hash =
      @blocks_path_pattern
      |> Path.wildcard()
      |> Enum.map(&File.read!/1)
      |> :erlang.md5()

    actual_blocks_hash != @blocks_hash
  end
end

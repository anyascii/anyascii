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

  defp get_block(block_num) do
    key = {__MODULE__, block_num}

    case :persistent_term.get(key, nil) do
      nil ->
        b = read_block(block_num)
        :persistent_term.put(key, b)
        b

      b ->
        b
    end
  end

  defp read_block(block_num) do
    path = Path.join([Application.app_dir(:any_ascii), "priv", Integer.to_string(block_num)])

    if File.exists?(path) do
      path
      |> File.read!()
      |> :zlib.unzip()
      |> String.split("\t")
      |> Enum.map(&minimize_string/1)
      |> List.to_tuple()
    else
      {}
    end
  end

  defp minimize_string(s) do
    case s do
      "" -> []
      <<c>> -> c
      _ -> s
    end
  end
end

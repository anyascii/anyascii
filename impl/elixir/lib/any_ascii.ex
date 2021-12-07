defmodule AnyAscii do
  use Application
  use Bitwise

  @moduledoc """

  """
  @impl true

  def start(_type, _args) do
    AnyAscii = :ets.new(__MODULE__, [:set, :public, :named_table])
    {:ok, self()}
  end

  def transliterate(s) when is_binary(s) do
    s
    |> String.to_charlist()
    |> Enum.reduce([], fn char, result ->
      [transliterate(char) | result]
    end)
    |> Enum.reverse()
    |> to_string
  end

  def transliterate(s) when is_number(s) and s <= 127 do
    s
  end

  def transliterate(s) when is_number(s) do
    block_num = s >>> 8
    lo = s &&& 0xFF

    block_num
    |> cached_block(:ets.lookup(__MODULE__, block_num))
    |> Enum.at(lo, " ")
  end

  defp cached_block(block_num, []) do
    page =
      block_num
      |> Integer.to_string(16)
      |> String.downcase()
      |> String.pad_leading(3, "0")

    data =
      [__DIR__, "data", page]
      |> Path.join()
      |> File.read!()
      |> String.split(<<255>>)

    :ets.insert(__MODULE__, {block_num, data})
    data
  end

  defp cached_block(_block_num, [{_key, value} | _]) do
    value
  end
end

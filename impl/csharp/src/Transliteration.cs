using System;
using System.Buffers;
using System.Text;

namespace AnyAscii
{
	public static partial class Transliteration
	{

		public static string Transliterate(this string s)
		{
			if (s.IsAscii()) return s;
			ArrayBufferWriter<byte> writer = new ArrayBufferWriter<byte>(s.Length);
			foreach (Rune r in s.EnumerateRunes())
			{
				Transliterate(r, writer);
			}
			return Encoding.ASCII.GetString(writer.WrittenSpan);
		}

		public static void Transliterate(this Rune r, IBufferWriter<byte> writer)
		{
			if (r.IsAscii)
			{
				writer.GetSpan(1)[0] = (byte)r.Value;
				writer.Advance(1);
			}
			else
			{
				writer.Write(Transliterate(r));
			}
		}

		public static ReadOnlySpan<byte> Transliterate(this Rune r)
		{
			int utf32 = r.Value;
			uint blockNum = (uint)utf32 >> 8;
			ReadOnlySpan<byte> block = Block(blockNum);
			int lo = 3 * (utf32 & 0xff);
			if (block.Length <= lo) return ReadOnlySpan<byte>.Empty;
			uint l = block[lo + 2];
			int len = (int)((l & 0x80) != 0 ? l & 0x7f : 3);
			if (len <= 3)
			{
				return block.Slice(lo, len);
			}
			else
			{
				int i = (block[lo] << 8) | block[lo + 1];
				ReadOnlySpan<byte> bank = len < Bank2Length ? Bank1 : Bank2;
				return bank.Slice(i, len);
			}
		}

		public static bool IsAscii(this string s)
		{
			foreach (char c in s)
			{
				if (!c.IsAscii()) return false;
			}
			return true;
		}

		public static bool IsAscii(this char c)
		{
			return (c >> 7) == 0;
		}
	}
}

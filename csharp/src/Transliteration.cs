using System.Text;
using System;

namespace AnyAscii
{
    public static partial class Transliteration
    {

        public static string Transliterate(this string s)
        {
            if (s.IsAscii()) return s;
            StringBuilder sb = new StringBuilder(s.Length);
            Transliterate(s, sb);
            return sb.ToString();
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
            return IsAscii((int)c);
        }

        public static bool IsAscii(int utf32)
        {
            return (utf32 >> 7) == 0;
        }

        public static void Transliterate(this string s, StringBuilder dst)
        {
            for (int i = 0; i < s.Length; i++)
            {
                int utf32 = char.ConvertToUtf32(s, i);
                if (utf32 > 0xffff) i++;
                Transliterate(utf32, dst);
            }
        }

        public static void Transliterate(int utf32, StringBuilder dst)
        {
            if (IsAscii(utf32))
            {
                dst.Append((char)utf32);
            }
            else
            {
                int blockNum = (int)((uint)utf32 >> 8);
                if (blocks.TryGetValue(blockNum, out Lazy<string[]> blockLazy))
                {
                    string[] block = blockLazy.Value;
                    int lo = utf32 & 0xff;
                    if (block.Length <= lo) return;
                    dst.Append(block[lo]);
                }
            }
        }
    }
}

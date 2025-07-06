# frozen_string_literal: true

require 'zlib'

# Unicode to ASCII transliteration
module AnyAscii
  def self.transliterate(string)
    return string if string.ascii_only?

    result = String.new('')
    string.each_codepoint do |cp|
      if cp <= 0x7f
        result << cp
      else
        block_num = cp >> 12
        lo = cp & 0xfff
        block = BLOCKS[block_num]
        result << block[lo] if lo < block.length
      end
    end
    result
  end

  BLOCKS = Hash.new do |blocks, block_num|
    blocks[block_num] = read_block(block_num)
  end
  private_constant :BLOCKS

  def self.read_block(block_num)
    file_name = File.join(__dir__, 'data', format('%02x', block_num))
    return [] unless File.file?(file_name)

    b = File.binread(file_name)
    s = inflate_raw(b).force_encoding(Encoding::US_ASCII)
    dedup(s.split("\t").dup)
  end
  private_class_method :read_block

  def self.inflate_raw(string)
    zstream = Zlib::Inflate.new(-Zlib::MAX_WBITS)
    buf = zstream.inflate(string)
    zstream.finish
    zstream.close
    buf
  end
  private_class_method :inflate_raw

  def self.dedup(elems)
    cache = {}
    elems.each_with_index do |e, i|
      c = cache.fetch(e) do
        cache[e] = e
      end
      unless e.equal?(c)
        elems[i] = c
      end
    end
    elems
  end
  private_class_method :dedup
end

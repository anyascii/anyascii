# frozen_string_literal: true

require 'zlib'

# Unicode to ASCII transliteration
module AnyAscii
  def self.transliterate(string)
    return string if string.ascii_only?

    result = String.new('')
    string.each_codepoint do |cp|
      if cp <= 127
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

    unzip(File.binread(file_name))
      .force_encoding(Encoding::UTF_8)
      .split("\t")
  end
  private_class_method :read_block

  def self.unzip(string)
    zstream = Zlib::Inflate.new(-Zlib::MAX_WBITS)
    buf = zstream.inflate(string)
    zstream.finish
    zstream.close
    buf
  end
  private_class_method :unzip
end

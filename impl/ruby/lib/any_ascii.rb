require 'zlib'

module AnyAscii

	BLOCKS = Hash.new do |blocks, block_num|
		file_name = File.join(__dir__, 'data', '%03x' % block_num)
		if File.file?(file_name)
			b = IO.binread(file_name)
			zi = Zlib::Inflate.new(-Zlib::MAX_WBITS)
			s = zi.inflate(b)
			zi.close
			block = s.split("\t")
		else
			block = []
		end
		blocks[block_num] = block
	end

	private_constant :BLOCKS

	def self.transliterate(string)
		if string.ascii_only?
			return string
		end
		result = ''
		string.each_codepoint do |cp|
			if cp <= 127
				result << cp
			else
				block_num = cp >> 8
				lo = cp & 0xff
				block = BLOCKS[block_num]
				if block.length > lo
					result << block[lo]
				end
			end
		end
		return result
	end
end

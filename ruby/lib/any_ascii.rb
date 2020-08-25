module AnyAscii

	BLOCKS = Hash.new { |h, k|
		s = '%03x' % k
		begin
			require_relative "data/#{s}.rb"
		rescue LoadError
			b = []
		else
			b = Object.const_get("X#{s}")::B
		end
		h[k] = b
	}

	private_constant :BLOCKS

	def self.transliterate(string)
		if string.ascii_only?
			return string
		end
		result = ''
		string.each_codepoint { |cp|
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
		}
		return result
	end
end
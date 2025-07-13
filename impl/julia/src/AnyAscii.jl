module AnyAscii

export anyascii

const blocks = Dict{UInt8, Vector{String}}()

function anyascii(string::AbstractString)::String
	buf = IOBuffer(sizehint=div(ncodeunits(string), 2))
	for c in string
		if isascii(c)
			write(buf, UInt8(c))
		else
			print(buf, anyascii(c))
		end
	end
	String(take!(buf))
end

function anyascii(char::AbstractChar)::String
	cp = codepoint(char)
	if cp >= 0xf0000
		return ""
	end
	blocknum = cp >> 12
	block = get!(blocks, blocknum) do
		file = joinpath(@__DIR__, "data", string(blocknum, base=16, pad=2))
		isfile(file) ? splitblock(read(file)) : String[]
	end
	lo = (cp & 0xfff) + 1
	lo <= lastindex(block) ? block[lo] : ""
end

function splitblock(bytes::Vector{UInt8})::Vector{String}
	cache = Dict{String, String}()
	sizehint!(cache, 4096)
	block = String[]
	sizehint!(block, 4096)
	d = 1
	while true
		i = d
		d = findnext(b -> b == 0xff, bytes, d)
		s = String(bytes[i:d - 1])
		c = get!(cache, s, s)
		push!(block, c)
		if d == lastindex(bytes)
			break
		end
		d += 1
	end
	block
end

end

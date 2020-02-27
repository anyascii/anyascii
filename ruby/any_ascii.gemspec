Gem::Specification.new do |s|
  s.name        = 'any_ascii'
  s.version     = '0.1.3'
  s.summary     = 'Unicode to ASCII transliteration'
  s.description =
    'Unicode to ASCII transliteration.' +
    ' Converts Unicode text to a reasonable representation using only ASCII.' +
    ' For most characters in Unicode, Any-Ascii provides an ASCII-only replacement string.' +
    ' Text is converted character-by-character without considering the context.' +
    ' The mappings for each language are based on popular existing romanization schemes.' +
    ' Symbolic characters are converted based on their meaning or appearance.' +
    ' All ASCII characters in the input are left unchanged,' +
    ' every other character is replaced with printable ASCII characters.' +
    ' Unknown characters are removed.'
  s.authors     = ['Hunter WB']
  s.email       = 'hunter@hunterwb.com'
  s.files       = Dir.glob 'lib/**/*.rb'
  s.homepage    = 'https://github.com/hunterwb/any-ascii'
  s.license     = 'ISC'
  s.required_ruby_version = '>= 2.0.0'
end
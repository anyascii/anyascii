# frozen_string_literal: true

Gem::Specification.new do |s|
  s.name = 'any_ascii'
  s.version = '0.3.2'
  s.summary = 'Unicode to ASCII transliteration'
  s.description = File.read('README.md')
  s.author = 'Hunter WB'
  s.email = 'hunter@hunterwb.com'
  s.files = Dir['lib/any_ascii.rb', 'lib/data/*', 'LICENSE']
  s.homepage = 'https://github.com/anyascii/anyascii'
  s.license = 'ISC'
  s.required_ruby_version = '>= 2.0.0'
end

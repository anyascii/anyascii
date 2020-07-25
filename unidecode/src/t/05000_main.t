# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0;
use strict; use warnings; use Test;
print q[# //Time-stamp: "2014-06-17 18:52:58 MDT sburke@cpan.org"], "\n";

BEGIN { plan tests => 20; }
use utf8;
use Text::Unidecode;

ok 1; print "# ^-- Hello\n";

print "# Text::Unidecode v$Text::Unidecode::VERSION\n",
      "# Perl $^V\n",
      "# I am test file " . __FILE__ . "\n",
      "# Starting tests...\n";

ok binmode(*STDOUT, ":utf8"); print "# ^-- Testing binmode on STDOUT\n";
ok binmode(*STDERR, ":utf8"); print "# ^-- Testing binmode on STDERR\n";

# Be super-sure that our universe is sane
print "# Testing string literals...\n";
ok  "ගඎ", "ගඎ",   "Failure in comparing identity!?!?";
ok  "ගඎ" => 'ගඎ',   "quoting operator brokenness!?";
ok  "ගඎ" => q[ගඎ],   "quoting operator brokenness!?";
ok  "ගඎ" => qq[ගඎ],   "quoting operator brokenness!?";
ok  "ගඎ" => "\x{0d9c}\x{0d8e}", "quoting operator brokenness!?";


# Syntactic sugar for our calls to "ok"...
my $from;
my $be = sub { return unidecode($from); };

print "# Latin-1...\n";
ok $be, "AEneid", ($from= "Æneid");
ok $be, "etude", ($from= "étude");

print "# Chinese...\n";
ok $be, "Bei Jing ", ($from="北亰");

print "# Canadian syllabics...\n";
ok $be, "shanana", ($from= "ᔕᓇᓇ");

print "# Cherokee...\n";
ok $be, "taliqua", ($from="ᏔᎵᏆ");

print "# Syriac...\n";
ok $be, "ptu'i", ($from= "ܦܛܽܐܺ");

print "# Devanagari...\n";
ok $be, "abhijiit", ($from= "अभिजीत");

print "# Bengali...\n";
ok $be, "abhijiit", ($from= "অভিজীত");

print "# Malayalaam...\n";
ok $be, "abhijiit", ($from= "അഭിജീത");
ok $be, "mlyaalm", ($from= "മലയാലമ്");
 # the Malayaalam word for "Malayaalam"
 # Yes, if we were doing it right, that'd be "malayaalam", not "mlyaalm"

print "# Japanese, astonishingly unmangled...\n";
ok $be, "genmaiCha ", ($from="げんまい茶");   # 

print "# Bye:\n";
ok 1;

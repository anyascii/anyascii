# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0;
use Test; use strict; use warnings;
BEGIN { plan tests => 11 };
print q[# // Time-stamp: "2014-07-04 02:16:23 MDT sburke@cpan.org"], "\n";

# Summary of, well, things.
ok 1; print "# ^-- Hello\n";

use utf8;

ok binmode(*STDOUT, ":utf8"); print "# ^-- Testing binmode on STDOUT\n";
ok binmode(*STDERR, ":utf8"); print "# ^-- Testing binmode on STDERR\n";

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

ok "a", unidecode("a"); print "# ^-- sanity\n";

{
  my($i, $n, $s);
  my $f = "# ^-- Testing: \"%s\" (U+%04x) returns true.  (\"%s\")\n";
  #print "# Format: $f\n";

  # Remember, when we call "ok" here, we're just testing for *truth*
  # The test would fail iif Unidecode returns an undef or "" (or "0")
  $n = unidecode( $s=chr($i= 0x0100 ) ); ok $n; printf $f => $s, $i, $n;
  $n = unidecode( $s=chr($i= 0x0400 ) ); ok $n; printf $f => $s, $i, $n;
  $n = unidecode( $s=chr($i= 0x0f00 ) ); ok $n; printf $f => $s, $i, $n;
  $n = unidecode( $s=chr($i= 0x01ff ) ); ok $n; printf $f => $s, $i, $n;
  $n = unidecode( $s=chr($i= 0x04ff ) ); ok $n; printf $f => $s, $i, $n;
  $n = unidecode( $s=chr($i= 0x0fff ) ); ok $n; printf $f => $s, $i, $n;

  #$n = unidecode( $s=chr($i=0x10000) ); ok $n; printf $f, $n, $s, $i;
}

print "# Bye:\n";
ok 1;

# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use Test; use strict; use warnings; use utf8;
print q[# //Time-stamp: "2014-07-04 02:16:17 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests => 7};

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

ok "a", unidecode("\xE1");
ok "\x{e1}", "\xE1"; # check sanity
ok "a", unidecode("\x{e1}");

# Now, probably provoke the loading of Unidecode/01.pm...
ok "oe", unidecode( "\x{153}" );

ok "a", unidecode( "\x{FF41}" );

{
  print "#Now notes about \%INC ...\n";
  my @incs;
  for(sort grep m/unidec/i, keys %INC) {
    push @incs, "# Package $_ from file: ", $INC{$_} || "?", "\n";
  }
  ok scalar(@incs);
  print @incs;
}

ok 1;
#End

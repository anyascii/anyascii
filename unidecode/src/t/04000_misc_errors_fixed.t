# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings; use Test; use utf8;
print q[# //Time-stamp: "2014-07-04 02:21:51 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests => 4;}

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

print "# Checking various errors to be fixed...\n";

ok 1;
binmode($_, ":utf8") for (*STDOUT, *STDIN, *STDERR);



ok( "\x{02b0}", "ʰ" );
ok( unidecode( "ʰ" ), "h" );



ok 1;

# End



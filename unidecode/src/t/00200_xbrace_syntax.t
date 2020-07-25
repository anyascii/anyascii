# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0;  use strict; use warnings; use Test; 
print q[# //Time-stamp: "2014-06-17 18:27:46 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests => 5};
ok 1;

# We don't need "use utf8" here.  We'll check that in other versions.


print "# Checking  that xXX and x{XXXX} syntaxes work in this Perl...\n";

# No actual manipulation of the xXXXX or anything here.
# This file is just so we'll error out if we're in some ancient pre-\x{XXXX} Perl.

ok  "\xE1"; 
ok  "\x{E1}";
ok  "\x{9053}";

print "# Bye:\n";
ok 1;

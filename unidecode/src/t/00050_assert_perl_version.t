# -*- coding:utf-8; mode:CPerl -*-
require 5;
# This file is for asserting Perl version, and basic sanity.

use warnings; use strict;
use Test;
BEGIN {plan tests => 4;}
BEGIN {ok 1;
 my $modtime = q[ timestamp Time-stamp: "2014-06-17 06:18:08 MDT sburke@cpan.org" ];
 print "# So far, we're at line ", __LINE__, " of file ", __FILE__, "\n";
 print "#  with$modtime\n#\n# I'm running under Perl v$]\n";
}

# And now:

print "# Requiring version:\n";
ok 1;



require     5.8.0;  # <================== THE VERSION.



# ("require" is done at runtime)

ok 1;

print "#\n# Phrased better, I'm under Perl version $^V\n";

print "# And that's that.\n";

ok 1;

# End

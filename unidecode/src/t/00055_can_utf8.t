# -*- coding:utf-8; mode:CPerl -*-
require 5;
use warnings; use strict; use Test;
BEGIN {plan tests => 3;}
BEGIN {ok 1;
 print '# Time-stamp: "2014-06-17 23:33:34 MDT sburke@cpan.org"', "\n";
 print "# Checking that we can do: use utf8\n";
}

use utf8;
ok 1;

print "# And that's that.\n";

ok 1;

# End

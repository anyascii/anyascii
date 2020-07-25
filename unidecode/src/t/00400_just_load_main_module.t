# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings; use Test; use utf8;
print q[# //Time-stamp: "2016-11-10 23:42:30 MST"], "\n";
 # (this used to be called "00400_just_load_module.t"

BEGIN {plan tests => 3;}


print "# Let's just see if a bare 'use Text::Unidecode' works:\n";

ok 1;

use Text::Unidecode;

print "# We just loaded Unidecode version: ",
  $Text::Unidecode::VERSION || "?", "\n";

print "# It was last modified ",
   $Text::Unidecode::Last_Modified || die(),
   "\n",
;

ok "a", unidecode("a"); # sanity

print "# Bye:\n";
ok 1;

# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0;  use warnings; use strict;
use Test;
BEGIN {plan tests => 8;}
print q[# // Time-stamp: "2014-06-23 03:42:54 MDT sburke@cpan.org"], "\n";

print "# Checking basic operations with Unicode characters...\n";
      
use utf8;

ok( binmode($_, ':utf8') ) foreach ( *STDERR, *STDIN, *STDOUT );

ok length( "\x{9053}\x{5fb7}\x{7d93}" ), 3;
ok length( "道德經"                    ), 3;

ok "道德經", "\x{9053}\x{5fb7}\x{7d93}" ;

ok "道德經", join( '', chr(0x9053), chr(0x5fb7), chr(0x7d93) );

print "# Bye:\n";
ok 1;

# End

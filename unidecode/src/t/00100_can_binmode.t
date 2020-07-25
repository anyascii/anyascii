# -*- coding:utf-8; mode:CPerl -*-
use strict; use warnings; use Test; 
print q[# //Time-stamp: "2014-06-17 23:16:40 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests => 5;}

print "# Hello:\n";
ok 1;

print "# Testing: binmode(*STDOUT, ':utf8') ...\n";
ok                binmode(*STDOUT, ':utf8');

print "# Testing: binmode(*STDERR, ':utf8') ...\n";
ok                binmode(*STDERR, ':utf8');

print "# Testing: binmode(*STDIN,  ':utf8') ...\n";
ok                binmode(*STDIN,  ':utf8');


print "# Bye:\n";
ok 1;


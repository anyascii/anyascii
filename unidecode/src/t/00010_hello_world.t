# -*- coding:utf-8; mode:CPerl -*-
require 5;
  # Note that "require [ver]" is RUNTIME, but "use [ver]" is compile-time.
BEGIN {
print "1..2\n";
print '# Last Modified Time-stamp: "2014-06-17 23:12:03 MDT sburke@cpan.org"', "\n";
print "# I'm testing absolute bare minimum sanity of our test harness.\n";
print "#   Current time GMT: ",   scalar(   gmtime()), "\n";
print "#   Current time local: ", scalar(localtime()), "\n";
print "#   Current perl version: $]\n";
print "# Hello:\n";
print "ok 1\n";
}

print "# Bye:\n";
print "ok 2\n";
# And that's that.


# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings; use Test; use utf8;
print q[# //Time-stamp: "2015-08-28 16:36:36 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests =>  44  ;}

print "# Let's test tolerating some Win-1252 characters:\n";

ok 1;

use Text::Unidecode;

print "# We just loaded Unidecode version: ",
  $Text::Unidecode::VERSION || "?", "\n";


ok binmode(*STDOUT, ":utf8"); print "# ^-- Testing binmode on STDOUT\n";
ok binmode(*STDERR, ":utf8"); print "# ^-- Testing binmode on STDERR\n";

print "# Some basic sanity stuff...\n";
ok "a", unidecode("a"); # sanity
ok "e", unidecode("e");
ok "ae", unidecode("æ");


my $f1 = "# ^-- About {{%s}}\n";
my $f2 = "# ^-- About {{%s}} and %s\n";
my $f3 = "# ^-- About {{%s}}( U+%04x ) and %s\n";
our($n,$m);

$n = "æ"; ok q{ae},  unidecode($n);  printf $f1, $n;
$n = "é"; ok q{e},   unidecode($n);  printf $f1, $n;
$n = "ÿ"; ok q{y},   unidecode($n);  printf $f1, $n;

$n = "\xe6"; ok q{ae},  unidecode($n);  printf $f1, $n;
$n = "\xe9"; ok q{e},   unidecode($n);  printf $f1, $n;
$n = "\xff"; ok q{y},   unidecode($n);  printf $f1, $n;

print "##############\n#\n# Now about 1252 support...\n";

# I'm constructing the Win-1252 characters only using \xXX,
# never as character literals!

$n = "\x91"; ok q{'},  unidecode($n);  printf $f1, $n;
$n = "\x92"; ok q{'},  unidecode($n);  printf $f1, $n;
$n = "\x93"; ok q{"},  unidecode($n);  printf $f1, $n;
$n = "\x94"; ok q{"},  unidecode($n);  printf $f1, $n;

print "#\n#\n# Now let's actually just do the whole 0x80-0x9F table!\n";

sub pr2 (@) {
  my($format,$n,$m) = @_;
  printf $f3, $n, ord($n), $m;
  return;
}

# Testing the equivalency of the Win-1252 value and the proper Unicode value
($n,$m)=("\x80", q{€} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
#($n,$m)=("\x81",
($n,$m)=("\x82", q{‚} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x83", q{ƒ} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x84", q{„} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x85", q{…} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x86", q{†} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x87", q{‡} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;

($n,$m)=("\x88", q{ˆ} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x89", q{‰} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x8a", q{Š} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x8b", q{‹} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x8c", q{Œ} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
#($n,$m)=("\x8d"
($n,$m)=("\x8e", q{Ž} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
#($n,$m)=("\x8f"

#($n,$m)=("\x90"
($n,$m)=("\x91", q{‘} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x92", q{’} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x93", q{“} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x94", q{”} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x95", q{•} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x96", q{–} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x97", q{—} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;

($n,$m)=("\x98", q{˜} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x99", q{™} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x9a", q{š} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x9b", q{›} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x9c", q{œ} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
#($n,$m)=("\x9d"
($n,$m)=("\x9e", q{ž} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;
($n,$m)=("\x9f", q{Ÿ} ); ok unidecode($m), unidecode($n); pr2 $f2,$n,$m;



#======================================================================
print "# Bye:\n";
ok 1;


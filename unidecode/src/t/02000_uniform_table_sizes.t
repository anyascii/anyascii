# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0;
use Test; use strict; use warnings;
our $lastmod = q[Time-stamp: "2016-11-26 04:19:16 MST"];

my @Bank_Numbers;
BEGIN { @Bank_Numbers = (0 .. 0xFF); }
BEGIN {plan tests =>  2  +  4 * @Bank_Numbers};

ok 1; print "# ...just saying hello.\n";

print "# ($lastmod)\n";
print "#\n# LOADING ALL MODULES... and checking fullness of each table.\n";

our $Bank_Length_Should_Be = 256;

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

$| = 1;

print "#\n", map "#\t$_\n",
  '  For each iteration:',
  '   1:  It\'s true that "defined unidecode $char"',
  '   2:  We\'ve got a value',
  '   3:  It\'s an arrayref',
  '   4:  The arrayref is the right size',
;

#
#  TODO: instead have @Bank_Numbers, above, use contents of Text::Unidecode::BankList instead of (1 .. 0xff);
#  NOTE: And it WILL have to change once we support surrogates

$Text::Unidecode::Note_Broken_Tables = 1;

use Text::Unidecode::x00 ();

Bank:
foreach my $banknum ( @Bank_Numbers ) {
  my $charnum = $banknum << 8;

  # Shut up warnings about UTF-16 surrogate characters
  # This is per https://rt.cpan.org/Ticket/Display.html?id=97456
  my $char = do { no warnings 'utf8'; chr( $charnum ) };

  printf "##\n# About to test the size of banknum 0x%02x via charnum U+%04x\:\n",
    $banknum, $charnum
  ;

  if(ok( defined( unidecode($char) ))) {  # ==================== OK1
    # yay
  } else {
    print "# Somehow couldn't get a defined value from unidecode for #$charnum\n";
    skip(1);
    skip(1);
    skip(1);
    next Bank;
  }

  my $bank_arrayref =
        $Text::Unidecode::Broken_Table_Copy{$banknum}
    ||  $Text::Unidecode::Char[$banknum]
    ||  undef
  ;

  if(ok( defined($bank_arrayref) )) {   # ==================== OK2
    # yay
  } else {
    print "# No \$Text::Unidecode::Char[$banknum] in memory?!\n";
    skip(1);
    skip(1);
    next Bank;
  }

  if(ok( ref($bank_arrayref), 'ARRAY' )) {   # ==================== OK3
    # yay
  } else {
    print "# \$Text::Unidecode::Char[$banknum] isn't an arrayref?!\n",
      "# It's: \"", $bank_arrayref, "\" - ", ref($bank_arrayref) || "not a ref", "\n";
    skip(1);
    next Bank;
  }

  my $bank_name = sprintf "Bank_0x%02x", $banknum;
  if( ok( scalar(@{ $bank_arrayref  }), $Bank_Length_Should_Be, " Length of $bank_name" )) { #  ==================== OK4
    next Bank;
  }

}

#print map "$_ : $INC{$_}\n", sort keys %INC;

print "# Bye:\n";
ok 1;  #Byebye.

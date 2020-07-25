# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings;
my $Time_Stamp = q[Time-stamp: "2016-11-26 04:43:48 MST"];

my @Tables;
BEGIN {
  @Tables = (
    0x00 .. 0xD7,

    # D8-to-DB = high surrogates
    # DC-to-DF =  low surrogates

    0xE0 .. 0xEF, # Private Use Area.
    0xF0 .. 0xF8, # Private Use Area.

    0xF9 .. 0xFF, # various things

    # TODO: instead pull from Text::Unidecode::Blackbox::..."Banklist" or something?
  );
}

use Test;
BEGIN { plan tests => scalar(@Tables) };
print qq[# // $Time_Stamp\n];

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

print "#\n# About to load these ", scalar(@Tables), " tables:\n";
{
  use Text::Wrap;
  my $string_of_tables =
    join(" ",
      map( sprintf("%02x", $_), @Tables )
    )
  ;
  
  print "#\n", wrap(
    "#  ", # first tab
    "#  ", # subsequent tab
    $string_of_tables,
  ), "\n#\n#\n";
}


my $nullmap = $Text::Unidecode::NULLMAP;

print "# About to compare things to nullmap ($nullmap) and check uniqueness.\n";
print "# Tables to consider: \n";



my %Seen;
Table:
foreach my $table_number (@Tables) {
  printf "# Loading table 0x%02x...\n", $table_number;

  my $charnum = 0xFF + $table_number * 0x0100;

  my($throw_away_value) = unidecode( chr( $charnum  ) );
                          # ^--- is just to force
			  # the loading of that table.

  my $table_for_this = $Text::Unidecode::Char[$table_number];
  my $string_for_table_for_this = "" . $table_for_this;
  my $hex_for_this_table_number =
    sprintf( "%02x", $table_number );

  
  my $seen_before_list = 
   (  $Seen{ $string_for_table_for_this } ||= []  );

  push @{ $seen_before_list },
    $hex_for_this_table_number;

  if($table_for_this eq $nullmap) {
    print "# Table 0x$hex_for_this_table_number is ",
        "$string_for_table_for_this",
        " which is NULLTABLE.\n";
    ok 0; # loading that bank just got us a null table
    print "#\n";
    next Table;
  }


  if( @$seen_before_list  > 1 ) {
    print "# Table 0x$hex_for_this_table_number is ",
      "$string_for_table_for_this",
      " which is NOT UNIQUE--\n";
    print "#  I've already seen it for: ",
      join(" ", @$seen_before_list), "\n",
    ;
    ok 0; # We've already seen this table before.
    print "#\n";
    next Table;
  }

  die "WHAT" unless @$seen_before_list == 1;

  #So, it's just us, just now:
  print "# Table 0x$hex_for_this_table_number is ",
    "$string_for_table_for_this= unique.\n";
  ok 1; # This table is unique (so far)
  print "#\n";

}

print "#\n# \%INC:\n";
foreach my $x (sort {lc($a) cmp lc($b)} keys %INC) {
  print "#   [$x] = [", $INC{$x} || '', "]\n";
}

print "# End.\n";

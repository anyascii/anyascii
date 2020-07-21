use strict;
use warnings;

use lib 'lib';
use Text::Unidecode;

open(my $f, '>:raw', 'table.tsv') or die;
binmode($f, ":utf8");
for (my $cp = hex('0xA0'); $cp <= hex('0xFFFF'); $cp++) {
	my $input = chr($cp);
	my $output = unidecode($input);
	if ($output ne '' and index($output, "\n") == -1 and index($output, "[?]") == -1) {
		print $f "$input\t$output\n";
	}
}
close $f;

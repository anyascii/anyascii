package AnyAscii;

use strict;
use warnings;

my $blocks = [];

sub new { bless {}, $_[0]; }

sub transliterate {
    my ($self, $utf8) = @_;

    my $i      = 0;
    my $result = '';
    my $rUtf8  = [ split '', $utf8 ];

    my $len = scalar @$rUtf8;

    while ($i < $len) {
		my $cp = $self->_utf8NextCodepoint($rUtf8, \$i);

        if ($cp < 0x80) {
			$result .= chr $cp;
			next;
		}

        my $blockNum = $cp >> 8;
        my $block    = $blocks->[$blockNum] // [];

        unless (@$block) {
            my $pkg = sprintf 'AnyAscii::_%03x', $blockNum;

            eval 'require ' . $pkg;

            unless ($@) {
			    $blocks->[$blockNum] = $block = $pkg->block();
            }
		}

		my $lo = $cp & 0xff;

		if (defined $block->[$lo]) {
			$result .= $block->[$lo];
		}
	}

	return $result;
}

sub _utf8NextCodepoint {
    my ($self, $rs, $ri) = @_;

	my $b1 = ord $rs->[$$ri++];

    return $b1 if $b1 < 0x80;

    my $b2 = ord $rs->[$$ri++];

    return (($b1 & 0x1f) << 6) | ($b2 & 0x3f) if $b1 < 0xe0;

    my $b3 = ord $rs->[$$ri++];

    return (($b1 & 0xf) << 12) | (($b2 & 0x3f) << 6) | ($b3 & 0x3f) if $b1 < 0xf0;

    my $b4 = ord $rs->[$$ri++];

    return (($b1 & 0x7) << 18) | (($b2 & 0x3f) << 12) | (($b3 & 0x3f) << 6) | ($b4 & 0x3f);
}

1;

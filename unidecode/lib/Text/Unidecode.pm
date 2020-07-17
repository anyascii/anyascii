;;;;# -*-coding:utf-8;-*-                                               µ ← col73

require 5;
use 5.8.0;
package Text::Unidecode;
$Last_Modified =' Time-stamp: "2016-11-26 05:01:56 MST"';
use utf8;
use strict;
use integer; # vroom vroom!
use vars qw($VERSION @ISA @EXPORT @Char $UNKNOWN $NULLMAP $TABLE_SIZE $Last_Modified
   $Note_Broken_Tables %Broken_Table_Size %Broken_Table_Copy
);
$VERSION = '1.30';
require Exporter;
@ISA = ('Exporter');
@EXPORT = ('unidecode');
$Note_Broken_Tables = 0;
BEGIN { *DEBUG = sub () {0} unless defined &DEBUG }
$UNKNOWN = '[?] ';
$TABLE_SIZE = 256;
$NULLMAP = [( $UNKNOWN ) x $TABLE_SIZE];  # for blocks we can't load

#--------------------------------------------------------------------------
{
  my $x = join '', "\x00" .. "\x7F";
  die "the 7-bit purity test fails!" unless $x eq unidecode($x);
}

#--------------------------------------------------------------------------

sub unidecode {
  # Destructive in void context -- in other contexts, nondestructive.

  unless(@_) {  # Sanity: Nothing coming in!
    return() if wantarray;
    return '';
  }

  if( defined wantarray ) {
    # We're in list or scalar context (i.e., just not void context.)
    #  So make @_'s items no longer be aliases.
    @_ = map $_, @_;
  } else {
    # Otherwise (if we're in void context), then just let @_ stay
    #  aliases, and alter their elements IN-PLACE!
  }

  foreach my $n (@_) {
    next unless defined $n;    

    # Shut up potentially fatal warnings about UTF-16 surrogate
    # characters when running under perl -w
    # This is per https://rt.cpan.org/Ticket/Display.html?id=97456
    no warnings 'utf8';

    $n =~ s~([^\x00-\x7f])~${$Char[ord($1)>>8]||t($1)}[ord($1)&255]~egs;
  }
  # That means:
  #   Replace character 0xABCD with $Char[0xAB][0xCD], loading
  #    the table 0xAB as needed.
  #
  #======================================================================
  #
  # Yes, that's dense code.  It's the warp core!
  # Here is an expansion into pseudocode... as best as I can manage it...
  #
  #     $character = $1;
  #     $charnum = ord($character);
  #     $charnum_lowbits  = $charnum & 255;
  #     $charnum_highbits = $charnum >> 8;
  #  
  #     $table_ref = $Char->[$charnum_highbits];
  #  
  #     if($table_ref) {
  #       # As expected, we got the arrayref for this table.
  #     } else {
  #       # Uhoh, we couldn't find the arrayref for this table.
  #       # So we call t($character).
  #       #  It loads a table.  Namely, it does:
  #       Load_Table_For( $charnum_highbits );
  #        # ...which does magic, and puts something in
  #        #     $Char->[$charnum_highbits],
  #        #     so NOW we actually CAN do:
  #       $table_ref = $Char->[$charnum_highbits];
  #     }
  #     
  #     $for_this_char
  #       = $table_ref->[ $charnum_lowbits ];
  #
  #     # Although the syntax we actually use is the odd
  #      but COMPLETE EQUIVALENT to this syntax:
  #  
  #     $for_this_char
  #       = ${ $table_ref }[ $charnum_lowbits ];
  #     
  #     and $for_this_char is the replacement text for this
  #      character, in:
  #      $n =~ s~(char)~replacement~egs
  #
  #  (And why did I use s~x~y~ instead of s/x/y/ ?
  #  It's all the same for Perl: perldoc perlretut says:
  #       As with the match "m//" operator, "s///" can 
  #       use other delimiters, such as "s!!!" and "s{}{}", 
  #  I didn't do it for sake of obscurity. I think it's just to
  #  keep my editor's syntax highlighter from crashing,
  #  which was a problem with s/// when the insides are as gory
  #  as we have here.

  return unless defined wantarray; # void context
  return @_ if wantarray;  # normal list context -- return the copies
  # Else normal scalar context:
  return $_[0] if @_ == 1;
  return join '', @_;      # rarer fallthru: a list in, but a scalar out.
}

#======================================================================

sub make_placeholder_map {
  return [( $UNKNOWN ) x $TABLE_SIZE ];
}
sub make_placeholder_map_nulls {
  return [( "" ) x $TABLE_SIZE ];
}

#======================================================================

sub t {   # "t" is for "t"able.
  # Load (and return) a char table for this character
  # this should get called only once per table per session.
  my $bank = ord($_[0]) >> 8;
  return $Char[$bank] if $Char[$bank];
 
  load_bank($bank);
        
  # Now see how that fared...

  if(ref($Char[$bank] || '') ne 'ARRAY') {
    DEBUG > 1 and print
      " Loading failed for bank $bank (err $@).  Using null map.\n";
    return $Char[$bank] = $NULLMAP;
  }


  DEBUG > 1 and print " Loading succeeded.\n";
  my $cb = $Char[$bank];

  # Sanity-check it:
  if(@$cb == $TABLE_SIZE) {
    # As expected.  Fallthru.

  } else {
    if($Note_Broken_Tables) {
      $Broken_Table_Size{$bank} = scalar @$cb;
      $Broken_Table_Copy{$bank} = [ @$cb ];
    }

    if(@$cb > $TABLE_SIZE) {
      DEBUG and print "Bank $bank is too large-- it has ", scalar @$cb,
        " entries in it.  Pruning.\n";
      splice @$cb, $TABLE_SIZE;
       # That two-argument form splices everything off into nowhere,
       #  starting with the first overage character.

    } elsif( @$cb < $TABLE_SIZE) {
      DEBUG and print "Bank $bank is too small-- it has ", scalar @$cb,
        " entries in it.  Now padding it.\n";
      if(@$cb == 0) {
        DEBUG and print "  (Yes, ZERO entries!)\n";
      }
      push @$cb,
	  ( $UNKNOWN )  x  ( $TABLE_SIZE - @$cb)
	  # i.e., however many items, times the deficit
      ;
      # And fallthru...

    } else {
      die "UNREACHABLE CODE HERE (INSANE)";
    }
  }

  # Check for undefness in block:

  for(my $i = 0; $i < $TABLE_SIZE; ++$i) {
    unless(defined $cb->[$i]) {
      DEBUG and printf "Undef at position %d in block x%02x\n",
        $i, $bank;
      $cb->[$i] = '';
    }
  }

  return $Char[$bank];
}

#-----------------------------------------------------------------------

our $eval_loaded_okay;

sub load_bank {

  # This is in its own sub, for sake of sweeping the scary thing
  #  (namely, a call to eval) under the rug.
  # I.e., to paraphrase what Larry Wall once said to me: if
  #  you're going to do something odd, maybe you should do it
  #  in private.

  my($banknum) = @_;  # just as an integer value

  DEBUG and printf
      "# Eval-loading %s::x%02x ...\n";

  $eval_loaded_okay = 0;
  my $code = 
      sprintf( "require %s::x%02x; \$eval_loaded_okay = 1;\n",
               __PACKAGE__,
	       $banknum);

  {
    local $SIG{'__DIE__'};
    eval($code);
  }

  return 1 if $eval_loaded_okay;
  return 0;
}

#======================================================================

1;
__END__

=encoding utf8

=head1 NAME

Text::Unidecode -- plain ASCII transliterations of Unicode text

=head1 SYNOPSIS

  use utf8;
  use Text::Unidecode;
  print unidecode(
    "北亰\n"
    # Chinese characters for Beijing (U+5317 U+4EB0)
  );
  
  # That prints: Bei Jing 

=head1 DESCRIPTION

It often happens that you have non-Roman text data in Unicode, but
you can't display it-- usually because you're trying to
show it to a user via an application that doesn't support Unicode,
or because the fonts you need aren't accessible.  You could
represent the Unicode characters as "???????" or
"\15BA\15A0\1610...", but that's nearly useless to the user who
actually wants to read what the text says.

What Text::Unidecode provides is a function, C<unidecode(...)> that
takes Unicode data and tries to represent it in US-ASCII characters
(i.e., the universally displayable characters between 0x00 and
0x7F).  The representation is
almost always an attempt at I<transliteration>-- i.e., conveying,
in Roman letters, the pronunciation expressed by the text in
some other writing system.  (See the example in the synopsis.)


NOTE:

To make sure your perldoc/Pod viewing setup for viewing this page is
working: The six-letter word "résumé" should look like "resume" with
an "/" accent on each "e".

For further tests, and help if that doesn't work, see below,
L</A POD ENCODING TEST>.


=head1 DESIGN PHILOSOPHY

Unidecode's ability to transliterate from a given language is limited
by two factors:

=over

=item * The amount and quality of data in the written form of the
original language

So if you have Hebrew data
that has no vowel points in it, then Unidecode cannot guess what
vowels should appear in a pronunciation.
S f y hv n vwls n th npt, y wn't gt ny vwls
n th tpt.  (This is a specific application of the general principle
of "Garbage In, Garbage Out".)

=item * Basic limitations in the Unidecode design

Writing a real and clever transliteration algorithm for any single
language usually requires a lot of time, and at least a passable
knowledge of the language involved.  But Unicode text can convey
more languages than I could possibly learn (much less create a
transliterator for) in the entire rest of my lifetime.  So I put
a cap on how intelligent Unidecode could be, by insisting that
it support only context-I<in>sensitive transliteration.  That means
missing the finer details of any given writing system,
while still hopefully being useful.

=back

Unidecode, in other words, is quick and
dirty.  Sometimes the output is not so dirty at all:
Russian and Greek seem to work passably; and
while Thaana (Divehi, AKA Maldivian) is a definitely non-Western
writing system, setting up a mapping from it to Roman letters
seems to work pretty well.  But sometimes the output is I<very
dirty:> Unidecode does quite badly on Japanese and Thai.

If you want a smarter transliteration for a particular language
than Unidecode provides, then you should look for (or write)
a transliteration algorithm specific to that language, and apply
it instead of (or at least before) applying Unidecode.

In other words, Unidecode's
approach is broad (knowing about dozens of writing systems), but
shallow (not being meticulous about any of them).

=head1 FUNCTIONS

Text::Unidecode provides one function, C<unidecode(...)>, which
is exported by default.  It can be used in a variety of calling contexts:

=over

=item C<$out = unidecode( $in );> # scalar context

This returns a copy of $in, transliterated.

=item C<$out = unidecode( @in );> # scalar context

This is the same as C<$out = unidecode(join "", @in);>

=item C<@out = unidecode( @in );> # list context

This returns a list consisting of copies of @in, each transliterated.  This
is the same as C<@out = map scalar(unidecode($_)), @in;>

=item C<unidecode( @items );> # void context

=item C<unidecode( @bar, $foo, @baz );> # void context

Each item on input is replaced with its transliteration.  This
is the same as C<for(@bar, $foo, @baz) { $_ = unidecode($_) }>

=back

You should make a minimum of assumptions about the output of
C<unidecode(...)>.  For example, if you assume an all-alphabetic
(Unicode) string passed to C<unidecode(...)> will return an all-alphabetic
string, you're wrong-- some alphabetic Unicode characters are
transliterated as strings containing punctuation (e.g., the
Armenian letter "Թ" (U+0539), currently transliterates as "T`"
(capital-T then a backtick).

However, these are the assumptions you I<can> make:

=over

=item *

Each character 0x0000 - 0x007F transliterates as itself.  That is,
C<unidecode(...)> is 7-bit pure.

=item *

The output of C<unidecode(...)> always consists entirely of US-ASCII
characters-- i.e., characters 0x0000 - 0x007F.

=item *

All Unicode characters translate to a sequence of (any number of)
characters that are newline ("\n") or in the range 0x0020-0x007E.  That
is, no Unicode character translates to "\x01", for example.  (Although if
you have a "\x01" on input, you'll get a "\x01" in output.)

=item *

Yes, some transliterations produce a "\n" but it's just a few, and
only with good reason.  Note that the value of newline ("\n") varies
from platform to platform-- see L<perlport>.

=item *

Some Unicode characters may transliterate to nothing (i.e., empty string).

=item *

Very many Unicode characters transliterate to multi-character sequences.
E.g., Unihan character U+5317, "北", transliterates as the four-character string
"Bei ".

=item *

Within these constraints, I<I may change> the transliteration of characters
in future versions.  For example, if someone convinces me that
that the Armenian letter "Թ", currently transliterated as "T`", would
be better transliterated as "D", I I<may> well make that change.

=item *

Unfortunately, there are many characters that Unidecode doesn't know a
transliteration for.  This is generally because the character has been
added since I last revised the Unidecode data tables.  I'm I<always>
catching up!

=back

=head1 DESIGN GOALS AND CONSTRAINTS

Text::Unidecode is meant to be a transliterator of last resort,
to be used once you've decided that you can't just display the
Unicode data as is, I<and once you've decided you don't have a
more clever, language-specific transliterator available,> or once
you've I<already applied> smarter algorithms or mappings that you prefer
and you now just want Unidecode to do cleanup.

Unidecode
transliterates context-insensitively-- that is, a given character is
replaced with the same US-ASCII (7-bit ASCII) character or characters,
no matter what the surrounding characters are.

The main reason I'm making Text::Unidecode work with only
context-insensitive substitution is that it's fast, dumb, and
straightforward enough to be feasible.  It doesn't tax my
(quite limited) knowledge of world languages.  It doesn't require
me writing a hundred lines of code to get the Thai syllabification
right (and never knowing whether I've gotten it wrong, because I
don't know Thai), or spending a year trying to get Text::Unidecode
to use the ChaSen algorithm for Japanese, or trying to write heuristics
for telling the difference between Japanese, Chinese, or Korean, so
it knows how to transliterate any given Uni-Han glyph.  And
moreover, context-insensitive substitution is still mostly useful,
but still clearly couldn't be mistaken for authoritative.

Text::Unidecode is an example of the 80/20 rule in
action-- you get 80% of the usefulness using just 20% of a
"real" solution.

A "real" approach to transliteration for any given language can
involve such increasingly tricky contextual factors as these:

=over

=item The previous / preceding character(s)

What a given symbol "X" means, could
depend on whether it's followed by a consonant, or by vowel, or
by some diacritic character.

=item Syllables

A character "X" at end of a syllable could mean something
different from when it's at the start-- which is especially problematic
when the language involved doesn't explicitly mark where one syllable
stops and the next starts.

=item Parts of speech

What "X" sounds like at the end of a word,
depends on whether that word is a noun, or a verb, or what.

=item Meaning

By semantic context, you can tell that this ideogram "X" means "shoe"
(pronounced one way) and not "time" (pronounced another),
and that's how you know to transliterate it one way instead of the other.

=item Origin of the word

"X" means one thing in loanwords and/or placenames (and
derivatives thereof), and another in native words.

=item "It's just that way"

"X" normally makes
the /X/ sound, except for this list of seventy exceptions (and words based
on them, sometimes indirectly).  Or: you never can tell which of the three
ways to pronounce "X" this word actually uses; you just have to know
which it is, so keep a dictionary on hand!

=item Language

The character "X" is actually used in several different languages, and you
have to figure out which you're looking at before you can determine how
to transliterate it.

=back

Out of a desire to avoid being mired in I<any> of these kinds of
contextual factors, I chose to exclude I<all of them> and just stick
with context-insensitive replacement.


=head1 A POD ENCODING TEST

=over

=item *

"Brontë" is six characters that should look like "Bronte", but
with double-dots on the "e" character.

=item *

"Résumé" is six characters that should look like "Resume", but
with /-shaped accents on the "e" characters.

=item *

"læti" should be I<four> letters long-- the second letter should not
be two letters "ae", but should be a single letter that
looks like an "a" entirely fused with an "e".

=item *

"χρονος" is six Greek characters that should look kind of like: xpovoc

=item *

"КАК ВАС ЗОВУТ" is three short Russian words that should look a
lot like: KAK BAC 3OBYT

=item *

"ടധ" is two Malayalam characters that should look like: sw

=item *

"丫二十一" is four Chinese characters that should look like: C<Y=+->

=item *

"Ｈｅｌｌｏ" is five characters that should look like: Hello

=back

If all of those come out right, your Pod viewing setup is working
fine-- welcome to the 2010s!  If those are full of garbage characters,
consider viewing this page as HTML at
L<https://metacpan.org/pod/Text::Unidecode>
or
L<http://search.cpan.org/perldoc?Text::Unidecode>


If things look mostly okay, but the Malayalam and/or the Chinese are
just question-marks or empty boxes, it's probably just that your
computer lacks the fonts for those.

=head1 TODO

Lots:

* Rebuild the Unihan database.  (Talk about hitting a moving target!)

* Add tone-numbers for Mandarin hanzi?  Namely: In Unihan, when tone
marks are present (like in "kMandarin: dào", should I continue to
transliterate as just "Dao", or should I put in the tone number:
"Dao4"?  It would be pretty jarring to have digits appear where
previously there was just alphabetic stuff-- But tone numbers
make Chinese more readable.
(I have a clever idea about doing this, for Unidecode v2 or v3.)

* Start dealing with characters over U+FFFF.  Cuneiform! Emojis! Whatever!

* Fill in all the little characters that have crept into the Misc Symbols
Etc blocks.

* More things that need tending to are detailed in the TODO.txt file,
included in this distribution.  Normal installs probably don't leave
the TODO.txt lying around, but if nothing else, you can see it at
L<http://search.cpan.org/search?dist=Text::Unidecode>

=head1 MOTTO

The Text::Unidecode motto is:

  It's better than nothing!

...in I<both> meanings: 1) seeing the output of C<unidecode(...)> is
better than just having all font-unavailable Unicode characters
replaced with "?"'s, or rendered as gibberish; and 2) it's the
worst, i.e., there's nothing that Text::Unidecode's algorithm is
better than.  All sensible transliteration algorithms (like for
German, see below) are going to be smarter than Unidecode's.

=head1 WHEN YOU DON'T LIKE WHAT UNIDECODE DOES

I will repeat the above, because some people miss it:

Text::Unidecode is meant to be a transliterator of I<last resort,>
to be used once you've decided that you can't just display the
Unicode data as is, I<and once you've decided you don't have a
more clever, language-specific transliterator available>-- or once
you've I<already applied> a smarter algorithm and now just want Unidecode
to do cleanup.

In other words, when you don't like what Unidecode does, I<do it
yourself.>  Really, that's what the above says.  Here's how
you would do this for German, for example:

In German, there's the typographical convention that an umlaut (the
double-dots on: ä ö ü) can be written as an "-e", like with "Schön"
becoming "Schoen".  But Unidecode doesn't do that-- I have Unidecode
simply drop the umlaut accent and give back "Schon".

(I chose this not because I'm a big meanie, but because
I<generally> changing "ü" to "ue" is disastrous for all text
that's I<not in German>.  Finnish "Hyvää päivää" would turn
into "Hyvaeae paeivaeae".  And I discourage you from being I<yet
another> German who emails me, trying to impel me to consider
a typographical nicety of German to be more important than
I<all other languages>.)

If you know that the text you're handling is probably in German, and
you want to apply the "umlaut becomes -e" rule, here's how to do it
for yourself (and then use Unidecode as I<the fallback> afterwards):

  use utf8;  # <-- probably necessary.

  our( %German_Characters ) = qw(
   Ä AE   ä ae
   Ö OE   ö oe
   Ü UE   ü ue
   ß ss 
  );
  
  use Text::Unidecode qw(unidecode);
  
  sub german_to_ascii {
    my($german_text) = @_;
    
    $german_text =~
      s/([ÄäÖöÜüß])/$German_Characters{$1}/g;
    
    # And now, as a *fallthrough*:
    $german_text = unidecode( $german_text );
    return $german_text;
  }

To pick another example, here's something that's not about a
specific language, but simply having a preference that may or
may not agree with Unidecode's (i.e., mine).  Consider the "¥"
symbol.  Unidecode changes that to "Y=".  If you want "¥" as
"YEN", then...

  use Text::Unidecode qw(unidecode);

  sub my_favorite_unidecode {
    my($text) = @_;
    
    $text =~ s/¥/YEN/g;
    
    # ...and anything else you like, such as:
    $text =~ s/€/Euro/g;
    
    # And then, as a fallback,...
    $text = unidecode($text);
     
    return $text;    
  }

Then if you do:

  print my_favorite_unidecode("You just won ¥250,000 and €40,000!!!");

...you'll get:

  You just won YEN250,000 and Euro40,000!!!

...just as you like it.

(By the way, the reason I<I> don't have Unidecode just turn "¥" into "YEN"
is that the same symbol also stands for yuan, the Chinese
currency.  A "Y=" is nicely, I<safely> neutral as to whether
we're talking about yen or yuan-- Japan, or China.)

Another example: for hanzi/kanji/hanja, I have designed
Unidecode to transliterate according to the value that that
character has in Mandarin (otherwise Cantonese,...).  Some
users have complained that applying Unidecode to Japanese
produces gibberish.

To make a long story short: transliterating from Japanese is
I<difficult> and it requires a I<lot> of context-sensitivity.
If you have text that you're fairly sure is in
Japanese, you're going to have to use a Japanese-specific
algorithm to transliterate Japanese into ASCII.  (And then
you can call Unidecode on the output from that-- it is useful
for, for example, turning ｆｕｌｌｗｉｄｔｈ characters into
their normal (ASCII) forms.

(Note, as of August 2016: I have titanic but tentative plans for
making the value of Unihan characters be something you could set
parameters for at runtime, in changing the order of "Mandarin else
Cantonese else..." in the value retrieval.  Currently that preference
list is hardwired on my end, at module-build time.  Other options I'm
considering allowing for: whether the Mandarin and Cantonese values
should have the tone numbers on them; whether every Unihan value
should have a terminal space; and maybe other clever stuff I haven't
thought of yet.)


=head1 CAVEATS

If you get really implausible nonsense out of C<unidecode(...)>, make
sure that the input data really is a utf8 string.  See
L<perlunicode> and L<perlunitut>.

I<Unidecode will work disastrously bad on Japanese.> That's because
Japanese is very very hard.  To extend the Unidecode motto,
Unidecode is better than nothing, and with Japanese, I<just barely!>

On pure Mandarin, Unidecode will frequently give odd values--
that's because a single hanzi can have several readings, and Unidecode
only knows what the Unihan database says is the most common one.


=head1 THANKS

Thanks to (in only the sloppiest of sorta-chronological order): 
Jordan Lachler, Harald Tveit Alvestrand, Melissa Axelrod,
Abhijit Menon-Sen, Mark-Jason Dominus, Joe Johnston,
Conrad Heiney, fileformat.info,
Philip Newton, 唐鳳, Tomaž Šolc, Mike Doherty, JT Smith and the
MadMongers, Arden Ogg, Craig Copris,
David Cusimano, Brendan Byrd, Hex Martin,
and
I<many>
other pals who have helped with the ideas or values for Unidecode's
transliterations, or whose help has been in the
secret F5 tornado that constitutes the internals of Unidecode's
implementation.

And thank you to the many people who have encouraged me to plug away
at this project.  A decade went by before I had any idea that more
than about 4 or 5 people were using or getting any value
out of Unidecode.  I am told that actually
my figure was missing some zeroes on the end!


=head1 PORTS

Some wonderful people have ported Unidecode to other languages!

=over

=item *

Python: L<https://pypi.python.org/pypi/Unidecode>

=item *

PHP: L<https://github.com/silverstripe-labs/silverstripe-unidecode>

=item *

Ruby: L<http://www.rubydoc.info/gems/unidecode/1.0.0/frames>

=item *

JavaScript: L<https://www.npmjs.org/package/unidecode>

=item *

Java: L<https://github.com/xuender/unidecode>

=back

I can't vouch for the details of each port, but these are clever
people, so I'm sure they did a fine job.


=head1 SEE ALSO

An article I wrote for I<The Perl Journal> about
Unidecode:  L<http://interglacial.com/tpj/22/>
(B<READ IT!>)

Jukka Korpela's L<http://www.cs.tut.fi/~jkorpela/fui.html8> which is
brilliantly useful, and its code is brilliant (so, view source!).  I
was I<kinda> thinking about maybe doing something I<sort of> like that
for the v2.x versions of Unicode-- but now he's got me convinced that
I should go right ahead.

Tom Christiansen's
I<Perl Unicode Cookbook>,
L<http://www.perl.com/pub/2012/04/perlunicook-standard-preamble.html>

Unicode Consortium: L<http://www.unicode.org/>

Searchable Unihan database:
L<http://www.unicode.org/cgi-bin/GetUnihanData.pl>

Geoffrey Sampson.  1990.  I<Writing Systems: A Linguistic Introduction.>
ISBN: 0804717567

Randall K. Barry (editor).  1997.  I<ALA-LC Romanization Tables:
Transliteration Schemes for Non-Roman Scripts.>
ISBN: 0844409405
[ALA is the American Library Association; LC is the Library of
Congress.]

Rupert Snell.  2000.  I<Beginner's Hindi Script (Teach Yourself
Books).>  ISBN: 0658009109

=head1 LICENSE

Copyright (c) 2001, 2014, 2015, 2016 Sean M. Burke.

Unidecode is distributed under the Perl Artistic License
( L<perlartistic> ), namely:

This library is free software; you can redistribute it and/or modify
it under the same terms as Perl itself.

This program is distributed in the hope that it will be useful, but
without any warranty; without even the implied warranty of
merchantability or fitness for a particular purpose.

=head1 DISCLAIMER

Much of Text::Unidecode's internal data is based on data from The
Unicode Consortium, with which I am unaffiliated.  A good deal of the
internal data comes from suggestions that have been contributed by
people other than myself.

The views and conclusions contained in my software and documentation
are my own-- they should not be interpreted as representing official
policies, either expressed or implied, of The Unicode Consortium; nor
should they be interpreted as necessarily the views or conclusions of
people who have contributed to this project.

Moreover, I discourage you from inferring that choices that I've made
in Unidecode reflect political or linguistic prejudices on my
part.  Just because Unidecode doesn't do great on your language,
or just because it might seem to do better on some another
language, please don't think I'm out to get you!

=head1 AUTHOR

Your pal, Sean M. Burke C<sburke@cpan.org>

=head1 O HAI!

If you're using Unidecode for anything interesting, be cool and
email me, I'm always curious what people use this for.  (The
answers so far have surprised me!)

=cut

#################### SCOOBIE SNACK ####################

Lest there be any REMAINING doubt that the Unicode Consortium has
a sense of humor, the CDROM that comes with /The Unicode Standard,
Version 3.0/ book, has an audio track of the Unicode anthem [!].
The lyrics are:

	Unicode, Oh Unicode!
	--------------------

	Oh, beautiful for Uni-Han,
	for spacious User Zone!
	For rampant scripts of India
	and polar Nunavut!

	  Chorus:
		Unicode, Oh Unicode!
		May all your code points shine forever
		and your beacon light the world!

	Oh, marvelous for sixteen bits,
	for precious surrogates!
	For Bi-Di algorithm dear
	and stalwart I-P-A!

	Oh, glorious for Hangul fair,
	for symbols mathematical!
	For myriad exotic scripts
	and punctuation we adore!

# End.

# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings; use Test; use utf8;
print q[# //Time-stamp: "2014-07-22 05:40:49 MDT sburke@cpan.org"], "\n";

BEGIN {plan tests => 75;}

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";

print "# Checking various errors to be fixed...\n";

ok 1;
binmode($_, ":utf8") for (*STDOUT, *STDIN, *STDERR);

# Thank you very much to 

ok( unidecode( "Ý" ), "Y" ); # https://rt.cpan.org/Ticket/Display.html?id=96889


#From Tomaž Šolc superstar!


ok( unidecode( "ĳ"  ), "ij") ;
ok( unidecode( "ǲ"  ), "Dz") ;
ok( unidecode( "Ƞ"  ), "N") ;
ok( unidecode( "ȡ"  ), "d") ;

print "# Block 02: 'Latin; IPA; spacing accents', U+02xx\n";
ok( unidecode( "ȴ"  ), 'l') ;    # U+0234
ok( unidecode( "ȵ"  ), 'n') ;    # U+0235
ok( unidecode( "ȶ"  ), 't') ;    # U+0236
ok( unidecode( "ȷ"  ), 'j') ;    # U+0237
ok( unidecode( "ȸ"  ), 'db') ;   # U+0238
ok( unidecode( "ȹ"  ), 'qp') ;   # U+0239
ok( unidecode( "Ⱥ"  ), 'A') ;    # U+023a
ok( unidecode( "Ȼ"  ), 'C') ;    # U+023b
ok( unidecode( "ȼ"  ), 'c') ;    # U+023c
ok( unidecode( "Ƚ"  ), 'L') ;    # U+023d
ok( unidecode( "Ⱦ"  ), 'T') ;    # U+023e
ok( unidecode( "ȿ"  ), 's') ;    # U+023f
ok( unidecode( "ɀ"  ), 'z') ;    # U+0240


ok( unidecode( "Ƀ"  ),'B') ;   #U+0243
ok( unidecode( "Ʉ"  ),'U') ;   #U+0244
ok( unidecode( "Ʌ"  ),'^') ;   #U+0245
ok( unidecode( "Ɇ"  ),'E') ;   #U+0246
ok( unidecode( "ɇ"  ),'e') ;   #U+0247
ok( unidecode( "Ɉ"  ),'J') ;   #U+0248
ok( unidecode( "ɉ"  ),'j') ;   #U+0249
ok( unidecode( "Ɋ"  ),'q') ;   #U+024a
ok( unidecode( "ɋ"  ),'q') ;   #U+024b
ok( unidecode( "Ɍ"  ),'R') ;   #U+024c
ok( unidecode( "ɍ"  ),'r') ;   #U+024d
ok( unidecode( "Ɏ"  ),'Y') ;   #U+024e
ok( unidecode( "ɏ"  ),'y') ;   #U+024f

ok( unidecode( "ɐ"  ),'a') ;   #U+0250


ok( unidecode( "ɸ"  ), 'F') ;    # \x{0278}

ok( unidecode( "ɹ"  ), 'r') ;    # \x{0279}
ok( unidecode( "ɺ"  ), 'r') ;    # \x{027a}
ok( unidecode( "ɻ"  ), 'r') ;    # \x{027b}
ok( unidecode( "ɼ"  ), 'r') ;    # \x{027c}
ok( unidecode( "ɽ"  ), 'r') ;    # \x{027d}
ok( unidecode( "ɾ"  ), 'r') ;    # \x{027e}
ok( unidecode( "ɿ"  ), 'r') ;    # \x{027f}
ok( unidecode( "ʀ"  ), 'R') ;    # \x{0280}
ok( unidecode( "ʁ"  ), 'R') ;    # \x{0281}



ok( unidecode( "ʉ"  ), 'u') ;    # \x{0289}


# ok( unidecode( ""  ), '') ;    # \x{02__}

ok( unidecode( "ʌ"  ), '^') ;    # \x{028c}
ok( unidecode( "ʍ"  ), 'w') ;    # \x{028d}
ok( unidecode( "ʎ"  ), 'y') ;    # \x{028e}
ok( unidecode( "ʏ"  ), 'Y') ;    # \x{028f}

ok( unidecode( "ʮ"  ), 'h') ;    # \x{02ae}
ok( unidecode( "ʯ"  ), 'h') ;    # \x{02af}
ok( unidecode( "ʰ"  ), 'h') ;    # \x{02b0}

# Rejecting TS's suggested mapping of "ʰ" to "k".  I see what he
# means, but it's too much of a stretch

#======================================================================



#ok( unidecode( ""  ), '') ;    # \x{02__}

print "# Combining Latin letters, U+03xx\n";

ok( unidecode( "ͣ" ), 'a');
ok( unidecode( "ͤ" ), 'e');
ok( unidecode( "ͥ" ), 'i');
ok( unidecode( "ͦ" ), 'o');
ok( unidecode( "ͧ" ), 'u');
ok( unidecode( "ͨ" ), 'c');
ok( unidecode( "ͩ" ), 'd');
ok( unidecode( "ͪ" ), 'h');
ok( unidecode( "ͫ" ), 'm');
ok( unidecode( "ͬ" ), 'r');
ok( unidecode( "ͭ" ), 't');
ok( unidecode( "ͮ" ), 'v');
ok( unidecode( "ͯ" ), 'x');

print "# Russian things\n";
ok( unidecode( "Е" ), 'E');
ok( unidecode( "г" ), 'g');
ok( unidecode( "е" ), 'e');


print "# Stuff...\n";
ok( unidecode( "։" ), '.');
 # U+0589 | Armenian full stop

ok( unidecode( "\x{05c0}" ), '|');
 # U+05C0 | Hebrew punctuation paseq



ok( unidecode( "ẛ" ), 's');
ok( unidecode( "ẜ" ), 's');
ok( unidecode( "ẝ" ), 's');
ok( unidecode( "ẞ" ), 'Ss');
ok( unidecode( "ẟ" ), 'd');

# That's all of Tomaž's stuff before 0x20

#======================================================================

ok 1;

# End



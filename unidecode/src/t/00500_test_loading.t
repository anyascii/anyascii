# -*- coding:utf-8; mode:CPerl -*-
use 5.8.0; use strict; use warnings; use Test;
BEGIN {plan tests => 12};
print q[# // Time-stamp: "2014-07-04 02:16:00 MDT sburke@cpan.org"], "\n";

# Summary of, well, things.
ok 1;

use Text::Unidecode;
print "# Text::Unidecode version $Text::Unidecode::VERSION\n";
ok "a", unidecode("a");

{
  my($i, $n);
  my $f = "# ^-- \"%s\" (U+%04x)\n";
  print "# These cause loading of those modules...\n";
  $n = unidecode( chr($i=0x0100) ); ok $n; printf $f, $n, $i;
  $n = unidecode( chr($i=0x0400) ); ok $n; printf $f, $n, $i;
  $n = unidecode( chr($i=0x0f00) ); ok $n; printf $f, $n, $i;
  $n = unidecode( chr($i=0xff00) ); ok $n; printf $f, $n, $i;
}

ok 1;
print "#\n# Now that we've loaded Text::Unidecode, let's see what else is\n",
      "#  in \%INC by now...\n";
{
  my @out;
  push @out,
    "\n\nPerl v",
    defined($^V) ? sprintf('%vd', $^V) : $],
    " under $^O ",
    (defined(&Win32::BuildNumber) and defined &Win32::BuildNumber())
      ? ("(Win32::BuildNumber ", &Win32::BuildNumber(), ")") : (),
    (defined $MacPerl::Version)
      ? ("(MacPerl version $MacPerl::Version)") : (),
    "\n"
  ;

  # Ugly code to walk the symbol tables:
  my %v;
  my @stack = ('');  # start out in %::
  my $this;
  my $count = 0;
  my $pref;
  while(@stack) {
    $this = shift @stack;
    die "Too many packages?" if ++$count > 1000;
    next if exists $v{$this};
    next if $this eq 'main'; # %main:: is %::

    #print "Peeking at $this => ${$this . '::VERSION'}\n";
    no strict 'refs';
    if(defined ${$this . '::VERSION'} ) {
      $v{$this} = ${$this . '::VERSION'}
    } elsif(
       defined *{$this . '::ISA'} or defined &{$this . '::import'}
       or ($this ne '' and grep defined *{$_}{'CODE'}, values %{$this . "::"})
       # If it has an ISA, an import, or any subs...
    ) {
      # It's a class/module with no version.
      $v{$this} = undef;
    } else {
      # It's probably an unpopulated package.
      ## $v{$this} = '...';
    }
    
    $pref = length($this) ? "$this\::" : '';
    push @stack, map m/^(.+)::$/ ? "$pref$1" : (), keys %{$this . '::'};
    #print "Stack: @stack\n";
  }
  push @out, " Modules in memory:\n";
  delete @v{'', '[none]'};
  foreach my $p (sort {lc($a) cmp lc($b)} keys %v) {
    my $indent = ' ' x (2 + ($p =~ tr/:/:/));
    push @out,  '  ', $indent, $p, defined($v{$p}) ? " v$v{$p};\n" : ";\n";
  }
  push @out, sprintf "[at %s (local) / %s (GMT)]\n",
    scalar(gmtime), scalar(localtime);
  my $x = join '', @out;
  $x =~ s/^/#/mg;
  print $x;

}
ok 1;

print "# Running",
  (chr(65) eq 'A') ? " in an ASCII world.\n" : " in a non-ASCII world.\n",
  "#\n",
;
ok 1;

print "# \@INC:\n", map("#   [$_]\n", @INC), "#\n#\n";
ok 1;

print "# \%INC:\n";
foreach my $x (sort {lc($a) cmp lc($b)} keys %INC) {
  print "#   [$x] = [", $INC{$x} || '', "]\n";
}

ok 1;

print "#\n#\n# Bye:\n";
ok 1;


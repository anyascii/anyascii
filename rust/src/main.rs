extern crate any_ascii;
use any_ascii::any_ascii;

fn main() {
    let args = std::env::args().skip(1).collect::<Vec<_>>();

    if args.is_empty() {
        println!(
            "{}\n{}\n{}\n{}",
            env!("CARGO_PKG_NAME"),
            env!("CARGO_PKG_VERSION"),
            env!("CARGO_PKG_DESCRIPTION"),
            env!("CARGO_PKG_HOMEPAGE")
        );
    } else {
        println!("{}", any_ascii(&args.join(" ")));
    }
}

extern crate any_ascii;
use any_ascii::any_ascii;

fn main() {
    let arg = std::env::args()
        .skip(1)
        .collect::<Vec<_>>()
        .join(" ");

    match arg.as_str() {
        "-h" | "--help" => println!(
            "{} {}\n{}\n{}",
            env!("CARGO_PKG_NAME"),
            env!("CARGO_PKG_VERSION"),
            env!("CARGO_PKG_DESCRIPTION"),
            env!("CARGO_PKG_HOMEPAGE")
        ),
        "-v" | "--version" => println!("{}", env!("CARGO_PKG_VERSION")),
        _ => println!("{}", any_ascii(&arg)),
    }
}
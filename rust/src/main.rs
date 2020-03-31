extern crate any_ascii;
use any_ascii::any_ascii;

fn main() {
    let arg = std::env::args()
        .skip(1)
        .collect::<Vec<_>>()
        .join(" ");

    match arg.as_str() {
        "-v" | "--version" => println!("{}", env!("CARGO_PKG_VERSION")),
        _ => println!("{}", any_ascii(&arg)),
    }
}
use any_ascii::any_ascii;

fn main() {
    let arg = std::env::args()
        .skip(1)
        .collect::<Vec<_>>()
        .join(" ");

    match arg.as_str() {
        "-v" | "--version" => print!("{}", env!("CARGO_PKG_VERSION")),
        _ => print!("{}", any_ascii(&arg)),
    }
}
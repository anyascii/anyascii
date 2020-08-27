use any_ascii::{any_ascii, any_ascii_char};
use std::io::{BufRead, Write};

fn main() {
    let args = std::env::args();
    if args.len() == 1 {
        transliterate_io();
    } else {
        transliterate_args(args.skip(1).collect());
    }
}

fn transliterate_io() {
    let stdin = std::io::stdin();
    let mut stdin_lock = stdin.lock();
    let mut buf_in = String::new();

    let stdout = std::io::stdout();
    let mut stdout_lock = stdout.lock();
    let mut buf_out = String::new();

    while stdin_lock.read_line(&mut buf_in).unwrap() > 0 {
        for c in buf_in.chars() {
            if c.is_ascii() {
                buf_out.push(c);
            } else {
                buf_out.push_str(any_ascii_char(c));
            }
        }
        buf_in.clear();
        stdout_lock.write_all(buf_out.as_bytes()).unwrap();
        buf_out.clear();
    }
}

fn transliterate_args(args: Vec<String>) {
    println!("{}", any_ascii(&args.join(" ")));

    match args[0].as_str() {
        "-v" | "--version" | "-h" | "--help" => eprint!(
            "anyascii {}\n{}\n{}\n{}\n",
            env!("CARGO_PKG_VERSION"),
            env!("CARGO_PKG_AUTHORS"),
            env!("CARGO_PKG_DESCRIPTION"),
            env!("CARGO_PKG_HOMEPAGE"),
        ),
        _ => {},
    }
}

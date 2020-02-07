use any_ascii::any_ascii;

fn main() {
    let s = std::env::args()
        .skip(1)
        .map(|a| any_ascii(&a))
        .collect::<Vec<_>>()
        .join(" ");

    print!("{}", s);
}
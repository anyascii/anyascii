mod block;

/// ```
/// # use any_ascii::any_ascii;
/// assert_eq!("anthropoi", any_ascii("άνθρωποι"));
/// assert_eq!("sample", any_ascii("sample"));
/// assert_eq!("ShenZhen", any_ascii("深圳"));
/// assert_eq!("Boris", any_ascii("Борис"));
/// assert_eq!("toyota", any_ascii("トヨタ"));
/// ```
pub fn any_ascii(s: &str) -> String {
    s.chars().map(any_ascii_char).collect()
}

/// ```
/// # use any_ascii::any_ascii_char;
/// assert_eq!("ae", any_ascii_char('æ'));
/// assert_eq!("e", any_ascii_char('é'));
/// assert_eq!("k", any_ascii_char('k'));
/// assert_eq!("ss", any_ascii_char('ß'));
/// assert_eq!("Shen", any_ascii_char('深'));
/// assert_eq!("c", any_ascii_char('ç'));
/// assert_eq!("l", any_ascii_char('λ'));
/// assert_eq!("zh", any_ascii_char('ж'));
/// ```
pub fn any_ascii_char(c: char) -> &'static str {
    let block_num = ((c as u32) >> 8) as u16;
    let block_bytes = block::block(block_num);
    let block: &'static [[u8; 3]] = unsafe {
        std::slice::from_raw_parts(
            block_bytes.as_ptr() as *const [u8; 3],
            block_bytes.len() / 3
        )
    };
    let lo = (c as u8) as usize;
    if let Some(ptr) = block.get(lo) {
        let mut len = ptr[2] as usize;
        if len >= 32 {
            len = 3;
        }
        if len <= 3 {
            unsafe {
                std::str::from_utf8_unchecked(ptr.get_unchecked(..len))
            }
        } else {
            let i = (((ptr[0] as u16) << 8) | (ptr[1] as u16)) as usize;
            unsafe {
                include_str!("strings.txt").get_unchecked(i..i + len)
            }
        }
    } else {
        ""
    }
}

#[test]
fn test() {
    fn check(s: &str, expected: &str) {
        assert_eq!(any_ascii(s), expected);
    }

    check("", "");
    check("René François Lacôte", "Rene Francois Lacote");
    check("Großer Hörselberg", "Grosser Horselberg");
    check("Trần Hưng Đạo", "Tran Hung Dao");
    check("Nærøy", "Naeroy");
    check("Φειδιππίδης", "Feidippidis");
    check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
    check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
    check("دمنهور", "dmnhwr");
    check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
    check("სამტრედია", "samt'redia");
    check("Աբովյան", "Abovyan");
    check("สงขลา", "sngkhla");
    check("ສະຫວັນນະເຂດ", "sahvannaekhd");
    check("深圳", "ShenZhen");
    check("深水埗", "ShenShuiBu");
    check("화성시", "hwaseongsi");
    check("華城市", "HuaChengShi");
    check("さいたま", "saitama");
    check("埼玉県", "QiYuXian");
    check("トヨタ", "toyota");
    check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "^say x ag");
    check("ময়মনসিংহ", "mymnsimh");
    check("પોરબંદર", "porbmdr");
    check("महासमुंद", "mhasmumd");
    check("ಬೆಂಗಳೂರು", "bemgluru");
    check("ਜਲੰਧਰ", "jlmdhr");
    check("ଗଜପତି", "gjpti");
    check("கன்னியாகுமரி", "knniyakumri");
    check("శ్రీకాకుళం", "srikakulm");
}
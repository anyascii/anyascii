//! Unicode to ASCII transliteration
//!
//! Converts Unicode characters to their best ASCII representation
//!
//! AnyAscii provides ASCII-only replacement strings for practically all Unicode
//! characters. Text is converted character-by-character without considering the
//! context. The mappings for each script are based on popular existing
//! romanization systems. Symbolic characters are converted based on their
//! meaning or appearance. All ASCII characters in the input are left unchanged,
//! every other character is replaced with printable ASCII characters. Unknown
//! characters and some known characters are replaced with an empty string and
//! removed.
//!
//! This crate supports `no_std` + `alloc`

#![no_std]

extern crate alloc;
use alloc::string::String;

mod data;

/// Transliterates a Unicode String into ASCII
///
/// ```
/// # use any_ascii::any_ascii;
/// assert_eq!("anthropoi", any_ascii("άνθρωποι"));
/// assert_eq!("sample", any_ascii("sample"));
/// assert_eq!("ShenZhen", any_ascii("深圳"));
/// assert_eq!("Boris", any_ascii("Борис"));
/// assert_eq!("toyota", any_ascii("トヨタ"));
/// ```
pub fn any_ascii(s: &str) -> String {
    let mut r = String::with_capacity(s.len() / 2);
    for c in s.chars() {
        if c.is_ascii() {
            r.push(c);
        } else {
            r.push_str(any_ascii_char(c));
        }
    }
    r
}

/// Transliterates a Unicode char into ASCII
///
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
/// assert_eq!(":crown:", any_ascii_char('👑'));
/// assert_eq!("#", any_ascii_char('♯'));
/// ```
pub fn any_ascii_char(c: char) -> &'static str {
    let block_num = (c as u32) >> 8;
    let block = data::block(block_num);
    let lo = (c as u8) as usize;
    if let Some(ptr) = block.get(lo) {
        let l = ptr[2];
        let len = if (l & 0x80) == 0 {
            3
        } else {
            (l & 0x7f) as usize
        };
        if len <= 3 {
            let ascii_bytes = &ptr[..len];
            unsafe { core::str::from_utf8_unchecked(ascii_bytes) }
        } else {
            let plane = block_num >> 8;
            let bank = if plane == 1 { data::BANK1 } else { data::BANK0 };
            let i = u16::from_be_bytes([ptr[0], ptr[1]]) as usize;
            unsafe { bank.get_unchecked(i..i + len) }
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
    check("\x00\x01\t\n\x1f ~\x7f", "\x00\x01\t\n\x1f ~\x7f");
    check("sample", "sample");

    check("\u{0080}", "");
    check("\u{00ff}", "y");
    check("\u{e000}", "");
    check("\u{ffff}", "");
    check("\u{e0020}", " ");
    check("\u{e007e}", "~");
    check("\u{f0000}", "");
    check("\u{f0001}", "");
    check("\u{10ffff}", "");

    check("René François Lacôte", "Rene Francois Lacote");
    check("Blöße", "Blosse");
    check("Trần Hưng Đạo", "Tran Hung Dao");
    check("Nærøy", "Naeroy");
    check("Φειδιππίδης", "Feidippidis");
    check("Δημήτρης Φωτόπουλος", "Dimitris Fotopoylos");
    check("Борис Николаевич Ельцин", "Boris Nikolaevich El'tsin");
    check("Володимир Горбулін", "Volodimir Gorbulin");
    check("Търговище", "T'rgovishche");
    check("深圳", "ShenZhen");
    check("深水埗", "ShenShuiBu");
    check("화성시", "HwaSeongSi");
    check("華城市", "HuaChengShi");
    check("さいたま", "saitama");
    check("埼玉県", "QiYuXian");
    check("ደብረ ዘይት", "debre zeyt");
    check("ደቀምሓረ", "dek'emhare");
    check("دمنهور", "dmnhwr");
    check("Աբովյան", "Abovyan");
    check("სამტრედია", "samt'redia");
    check("אברהם הלוי פרנקל", "'vrhm hlvy frnkl");
    check("⠠⠎⠁⠽⠀⠭⠀⠁⠛", "+say x ag");
    check("ময়মনসিংহ", "mymnsimh");
    check("ထန်တလန်", "thntln");
    check("પોરબંદર", "porbmdr");
    check("महासमुंद", "mhasmumd");
    check("ಬೆಂಗಳೂರು", "bemgluru");
    check("សៀមរាប", "siemrab");
    check("ສະຫວັນນະເຂດ", "sahvannaekhd");
    check("കളമശ്ശേരി", "klmsseri");
    check("ଗଜପତି", "gjpti");
    check("ਜਲੰਧਰ", "jlmdhr");
    check("රත්නපුර", "rtnpur");
    check("கன்னியாகுமரி", "knniyakumri");
    check("శ్రీకాకుళం", "srikakulm");
    check("สงขลา", "sngkhla");
    check("👑 🌴", ":crown: :palm_tree:");
    check("☆ ♯ ♰ ⚄ ⛌", "* # + 5 X");
    check("№ ℳ ⅋ ⅍", "No M & A/S");

    check("トヨタ", "toyota");
    check("ߞߐߣߊߞߙߌ߫", "konakri");
    check("𐬰𐬀𐬭𐬀𐬚𐬎𐬱𐬙𐬭𐬀", "zarathushtra");
    check("ⵜⵉⴼⵉⵏⴰⵖ", "tifinagh");
    check("𐍅𐌿𐌻𐍆𐌹𐌻𐌰", "wulfila");
    check("ދިވެހި", "dhivehi");
    check("ᨅᨔ ᨕᨘᨁᨗ", "bs ugi");
    check("ϯⲙⲓⲛϩⲱⲣ", "timinhor");
    check("𐐜 𐐢𐐮𐐻𐑊 𐐝𐐻𐐪𐑉", "Dh Litl Star");
    check("ꁌꐭꑤ", "pujjytxiep");
    check("ⰳⰾⰰⰳⱁⰾⰹⱌⰰ", "glagolica");
    check("ᏎᏉᏯ", "SeQuoYa");
    check("ㄓㄨㄤ ㄅㄥ ㄒㄧㄠ", "zhuang beng xiao");
    check("ꚩꚫꛑꚩꚳ ꚳ꛰ꛀꚧꚩꛂ", "ipareim m'shuoiya");
    check("ᓀᐦᐃᔭᐍᐏᐣ", "nehiyawewin");
    check("ᠤᠯᠠᠭᠠᠨᠴᠠᠪ", "ulaganqab");
    check("𐑨𐑯𐑛𐑮𐑩𐑒𐑤𐑰𐑟 𐑯 𐑞 𐑤𐑲𐑩𐑯", "andr'kliiz n dh lai'n");

    check("🐂 🦉 🦆 🦓 ☕ 🍿 ✈ 🎷 🎤 🌡 🦹", ":ox: :owl: :duck: :zebra: :coffee: :popcorn: :airplane: :saxophone: :microphone: :thermometer: :supervillain:");
    check("에 가 힣 널 뢌 땚 꺵", "E Ga Hih Neol Lwass Ttaelp Kkyaeng");

    check("一以已亦易意依亿億衣", "YiYiYiYiYiYiYiYiYiYi");
    check("事是时時使十式市室士", "ShiShiShiShiShiShiShiShiShiShi");
    check("件间間见見建减減键鍵", "JianJianJianJianJianJianJianJianJianJian");
}

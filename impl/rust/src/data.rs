pub const BANK1: &str = include_str!("bank1.txt");
pub const BANK2: &str = include_str!("bank2.txt");
pub const BANK2_LENGTH: usize = 8;

pub fn block(block_num: u32) -> &'static [[u8; 3]] {
    let b: &'static [u8] = match block_num {
        0x000 => include_bytes!("data/000"),
        0x001 => include_bytes!("data/001"),
        0x002 => include_bytes!("data/002"),
        0x003 => include_bytes!("data/003"),
        0x004 => include_bytes!("data/004"),
        0x005 => include_bytes!("data/005"),
        0x006 => include_bytes!("data/006"),
        0x007 => include_bytes!("data/007"),
        0x008 => include_bytes!("data/008"),
        0x009 => include_bytes!("data/009"),
        0x00a => include_bytes!("data/00a"),
        0x00b => include_bytes!("data/00b"),
        0x00c => include_bytes!("data/00c"),
        0x00d => include_bytes!("data/00d"),
        0x00e => include_bytes!("data/00e"),
        0x00f => include_bytes!("data/00f"),
        0x010 => include_bytes!("data/010"),
        0x011 => include_bytes!("data/011"),
        0x012 => include_bytes!("data/012"),
        0x013 => include_bytes!("data/013"),
        0x014 => include_bytes!("data/014"),
        0x015 => include_bytes!("data/015"),
        0x016 => include_bytes!("data/016"),
        0x017 => include_bytes!("data/017"),
        0x018 => include_bytes!("data/018"),
        0x019 => include_bytes!("data/019"),
        0x01a => include_bytes!("data/01a"),
        0x01b => include_bytes!("data/01b"),
        0x01c => include_bytes!("data/01c"),
        0x01d => include_bytes!("data/01d"),
        0x01e => include_bytes!("data/01e"),
        0x01f => include_bytes!("data/01f"),
        0x020 => include_bytes!("data/020"),
        0x021 => include_bytes!("data/021"),
        0x022 => include_bytes!("data/022"),
        0x023 => include_bytes!("data/023"),
        0x024 => include_bytes!("data/024"),
        0x025 => include_bytes!("data/025"),
        0x026 => include_bytes!("data/026"),
        0x027 => include_bytes!("data/027"),
        0x028 => include_bytes!("data/028"),
        0x029 => include_bytes!("data/029"),
        0x02a => include_bytes!("data/02a"),
        0x02b => include_bytes!("data/02b"),
        0x02c => include_bytes!("data/02c"),
        0x02d => include_bytes!("data/02d"),
        0x02e => include_bytes!("data/02e"),
        0x02f => include_bytes!("data/02f"),
        0x030 => include_bytes!("data/030"),
        0x031 => include_bytes!("data/031"),
        0x032 => include_bytes!("data/032"),
        0x033 => include_bytes!("data/033"),
        0x034 => include_bytes!("data/034"),
        0x035 => include_bytes!("data/035"),
        0x036 => include_bytes!("data/036"),
        0x037 => include_bytes!("data/037"),
        0x038 => include_bytes!("data/038"),
        0x039 => include_bytes!("data/039"),
        0x03a => include_bytes!("data/03a"),
        0x03b => include_bytes!("data/03b"),
        0x03c => include_bytes!("data/03c"),
        0x03d => include_bytes!("data/03d"),
        0x03e => include_bytes!("data/03e"),
        0x03f => include_bytes!("data/03f"),
        0x040 => include_bytes!("data/040"),
        0x041 => include_bytes!("data/041"),
        0x042 => include_bytes!("data/042"),
        0x043 => include_bytes!("data/043"),
        0x044 => include_bytes!("data/044"),
        0x045 => include_bytes!("data/045"),
        0x046 => include_bytes!("data/046"),
        0x047 => include_bytes!("data/047"),
        0x048 => include_bytes!("data/048"),
        0x049 => include_bytes!("data/049"),
        0x04a => include_bytes!("data/04a"),
        0x04b => include_bytes!("data/04b"),
        0x04c => include_bytes!("data/04c"),
        0x04d => include_bytes!("data/04d"),
        0x04e => include_bytes!("data/04e"),
        0x04f => include_bytes!("data/04f"),
        0x050 => include_bytes!("data/050"),
        0x051 => include_bytes!("data/051"),
        0x052 => include_bytes!("data/052"),
        0x053 => include_bytes!("data/053"),
        0x054 => include_bytes!("data/054"),
        0x055 => include_bytes!("data/055"),
        0x056 => include_bytes!("data/056"),
        0x057 => include_bytes!("data/057"),
        0x058 => include_bytes!("data/058"),
        0x059 => include_bytes!("data/059"),
        0x05a => include_bytes!("data/05a"),
        0x05b => include_bytes!("data/05b"),
        0x05c => include_bytes!("data/05c"),
        0x05d => include_bytes!("data/05d"),
        0x05e => include_bytes!("data/05e"),
        0x05f => include_bytes!("data/05f"),
        0x060 => include_bytes!("data/060"),
        0x061 => include_bytes!("data/061"),
        0x062 => include_bytes!("data/062"),
        0x063 => include_bytes!("data/063"),
        0x064 => include_bytes!("data/064"),
        0x065 => include_bytes!("data/065"),
        0x066 => include_bytes!("data/066"),
        0x067 => include_bytes!("data/067"),
        0x068 => include_bytes!("data/068"),
        0x069 => include_bytes!("data/069"),
        0x06a => include_bytes!("data/06a"),
        0x06b => include_bytes!("data/06b"),
        0x06c => include_bytes!("data/06c"),
        0x06d => include_bytes!("data/06d"),
        0x06e => include_bytes!("data/06e"),
        0x06f => include_bytes!("data/06f"),
        0x070 => include_bytes!("data/070"),
        0x071 => include_bytes!("data/071"),
        0x072 => include_bytes!("data/072"),
        0x073 => include_bytes!("data/073"),
        0x074 => include_bytes!("data/074"),
        0x075 => include_bytes!("data/075"),
        0x076 => include_bytes!("data/076"),
        0x077 => include_bytes!("data/077"),
        0x078 => include_bytes!("data/078"),
        0x079 => include_bytes!("data/079"),
        0x07a => include_bytes!("data/07a"),
        0x07b => include_bytes!("data/07b"),
        0x07c => include_bytes!("data/07c"),
        0x07d => include_bytes!("data/07d"),
        0x07e => include_bytes!("data/07e"),
        0x07f => include_bytes!("data/07f"),
        0x080 => include_bytes!("data/080"),
        0x081 => include_bytes!("data/081"),
        0x082 => include_bytes!("data/082"),
        0x083 => include_bytes!("data/083"),
        0x084 => include_bytes!("data/084"),
        0x085 => include_bytes!("data/085"),
        0x086 => include_bytes!("data/086"),
        0x087 => include_bytes!("data/087"),
        0x088 => include_bytes!("data/088"),
        0x089 => include_bytes!("data/089"),
        0x08a => include_bytes!("data/08a"),
        0x08b => include_bytes!("data/08b"),
        0x08c => include_bytes!("data/08c"),
        0x08d => include_bytes!("data/08d"),
        0x08e => include_bytes!("data/08e"),
        0x08f => include_bytes!("data/08f"),
        0x090 => include_bytes!("data/090"),
        0x091 => include_bytes!("data/091"),
        0x092 => include_bytes!("data/092"),
        0x093 => include_bytes!("data/093"),
        0x094 => include_bytes!("data/094"),
        0x095 => include_bytes!("data/095"),
        0x096 => include_bytes!("data/096"),
        0x097 => include_bytes!("data/097"),
        0x098 => include_bytes!("data/098"),
        0x099 => include_bytes!("data/099"),
        0x09a => include_bytes!("data/09a"),
        0x09b => include_bytes!("data/09b"),
        0x09c => include_bytes!("data/09c"),
        0x09d => include_bytes!("data/09d"),
        0x09e => include_bytes!("data/09e"),
        0x09f => include_bytes!("data/09f"),
        0x0a0 => include_bytes!("data/0a0"),
        0x0a1 => include_bytes!("data/0a1"),
        0x0a2 => include_bytes!("data/0a2"),
        0x0a3 => include_bytes!("data/0a3"),
        0x0a4 => include_bytes!("data/0a4"),
        0x0a5 => include_bytes!("data/0a5"),
        0x0a6 => include_bytes!("data/0a6"),
        0x0a7 => include_bytes!("data/0a7"),
        0x0a8 => include_bytes!("data/0a8"),
        0x0a9 => include_bytes!("data/0a9"),
        0x0aa => include_bytes!("data/0aa"),
        0x0ab => include_bytes!("data/0ab"),
        0x0ac => include_bytes!("data/0ac"),
        0x0ad => include_bytes!("data/0ad"),
        0x0ae => include_bytes!("data/0ae"),
        0x0af => include_bytes!("data/0af"),
        0x0b0 => include_bytes!("data/0b0"),
        0x0b1 => include_bytes!("data/0b1"),
        0x0b2 => include_bytes!("data/0b2"),
        0x0b3 => include_bytes!("data/0b3"),
        0x0b4 => include_bytes!("data/0b4"),
        0x0b5 => include_bytes!("data/0b5"),
        0x0b6 => include_bytes!("data/0b6"),
        0x0b7 => include_bytes!("data/0b7"),
        0x0b8 => include_bytes!("data/0b8"),
        0x0b9 => include_bytes!("data/0b9"),
        0x0ba => include_bytes!("data/0ba"),
        0x0bb => include_bytes!("data/0bb"),
        0x0bc => include_bytes!("data/0bc"),
        0x0bd => include_bytes!("data/0bd"),
        0x0be => include_bytes!("data/0be"),
        0x0bf => include_bytes!("data/0bf"),
        0x0c0 => include_bytes!("data/0c0"),
        0x0c1 => include_bytes!("data/0c1"),
        0x0c2 => include_bytes!("data/0c2"),
        0x0c3 => include_bytes!("data/0c3"),
        0x0c4 => include_bytes!("data/0c4"),
        0x0c5 => include_bytes!("data/0c5"),
        0x0c6 => include_bytes!("data/0c6"),
        0x0c7 => include_bytes!("data/0c7"),
        0x0c8 => include_bytes!("data/0c8"),
        0x0c9 => include_bytes!("data/0c9"),
        0x0ca => include_bytes!("data/0ca"),
        0x0cb => include_bytes!("data/0cb"),
        0x0cc => include_bytes!("data/0cc"),
        0x0cd => include_bytes!("data/0cd"),
        0x0ce => include_bytes!("data/0ce"),
        0x0cf => include_bytes!("data/0cf"),
        0x0d0 => include_bytes!("data/0d0"),
        0x0d1 => include_bytes!("data/0d1"),
        0x0d2 => include_bytes!("data/0d2"),
        0x0d3 => include_bytes!("data/0d3"),
        0x0d4 => include_bytes!("data/0d4"),
        0x0d5 => include_bytes!("data/0d5"),
        0x0d6 => include_bytes!("data/0d6"),
        0x0d7 => include_bytes!("data/0d7"),
        0x0f9 => include_bytes!("data/0f9"),
        0x0fa => include_bytes!("data/0fa"),
        0x0fb => include_bytes!("data/0fb"),
        0x0fc => include_bytes!("data/0fc"),
        0x0fd => include_bytes!("data/0fd"),
        0x0fe => include_bytes!("data/0fe"),
        0x0ff => include_bytes!("data/0ff"),
        0x100 => include_bytes!("data/100"),
        0x101 => include_bytes!("data/101"),
        0x102 => include_bytes!("data/102"),
        0x103 => include_bytes!("data/103"),
        0x104 => include_bytes!("data/104"),
        0x105 => include_bytes!("data/105"),
        0x106 => include_bytes!("data/106"),
        0x107 => include_bytes!("data/107"),
        0x108 => include_bytes!("data/108"),
        0x109 => include_bytes!("data/109"),
        0x10a => include_bytes!("data/10a"),
        0x10b => include_bytes!("data/10b"),
        0x10c => include_bytes!("data/10c"),
        0x10d => include_bytes!("data/10d"),
        0x10e => include_bytes!("data/10e"),
        0x10f => include_bytes!("data/10f"),
        0x110 => include_bytes!("data/110"),
        0x111 => include_bytes!("data/111"),
        0x112 => include_bytes!("data/112"),
        0x113 => include_bytes!("data/113"),
        0x114 => include_bytes!("data/114"),
        0x115 => include_bytes!("data/115"),
        0x116 => include_bytes!("data/116"),
        0x117 => include_bytes!("data/117"),
        0x118 => include_bytes!("data/118"),
        0x119 => include_bytes!("data/119"),
        0x11a => include_bytes!("data/11a"),
        0x11b => include_bytes!("data/11b"),
        0x11c => include_bytes!("data/11c"),
        0x11d => include_bytes!("data/11d"),
        0x11e => include_bytes!("data/11e"),
        0x11f => include_bytes!("data/11f"),
        0x124 => include_bytes!("data/124"),
        0x12f => include_bytes!("data/12f"),
        0x130 => include_bytes!("data/130"),
        0x131 => include_bytes!("data/131"),
        0x132 => include_bytes!("data/132"),
        0x133 => include_bytes!("data/133"),
        0x134 => include_bytes!("data/134"),
        0x144 => include_bytes!("data/144"),
        0x145 => include_bytes!("data/145"),
        0x146 => include_bytes!("data/146"),
        0x16a => include_bytes!("data/16a"),
        0x16b => include_bytes!("data/16b"),
        0x16e => include_bytes!("data/16e"),
        0x16f => include_bytes!("data/16f"),
        0x170 => include_bytes!("data/170"),
        0x171 => include_bytes!("data/171"),
        0x172 => include_bytes!("data/172"),
        0x173 => include_bytes!("data/173"),
        0x174 => include_bytes!("data/174"),
        0x175 => include_bytes!("data/175"),
        0x176 => include_bytes!("data/176"),
        0x177 => include_bytes!("data/177"),
        0x178 => include_bytes!("data/178"),
        0x179 => include_bytes!("data/179"),
        0x17a => include_bytes!("data/17a"),
        0x17b => include_bytes!("data/17b"),
        0x17c => include_bytes!("data/17c"),
        0x17d => include_bytes!("data/17d"),
        0x17e => include_bytes!("data/17e"),
        0x17f => include_bytes!("data/17f"),
        0x180 => include_bytes!("data/180"),
        0x181 => include_bytes!("data/181"),
        0x182 => include_bytes!("data/182"),
        0x183 => include_bytes!("data/183"),
        0x184 => include_bytes!("data/184"),
        0x185 => include_bytes!("data/185"),
        0x186 => include_bytes!("data/186"),
        0x187 => include_bytes!("data/187"),
        0x188 => include_bytes!("data/188"),
        0x189 => include_bytes!("data/189"),
        0x18a => include_bytes!("data/18a"),
        0x18b => include_bytes!("data/18b"),
        0x18c => include_bytes!("data/18c"),
        0x18d => include_bytes!("data/18d"),
        0x1b0 => include_bytes!("data/1b0"),
        0x1b1 => include_bytes!("data/1b1"),
        0x1b2 => include_bytes!("data/1b2"),
        0x1bc => include_bytes!("data/1bc"),
        0x1cf => include_bytes!("data/1cf"),
        0x1d0 => include_bytes!("data/1d0"),
        0x1d1 => include_bytes!("data/1d1"),
        0x1d2 => include_bytes!("data/1d2"),
        0x1d3 => include_bytes!("data/1d3"),
        0x1d4 => include_bytes!("data/1d4"),
        0x1d5 => include_bytes!("data/1d5"),
        0x1d6 => include_bytes!("data/1d6"),
        0x1d7 => include_bytes!("data/1d7"),
        0x1df => include_bytes!("data/1df"),
        0x1e0 => include_bytes!("data/1e0"),
        0x1e1 => include_bytes!("data/1e1"),
        0x1e2 => include_bytes!("data/1e2"),
        0x1e4 => include_bytes!("data/1e4"),
        0x1e7 => include_bytes!("data/1e7"),
        0x1e8 => include_bytes!("data/1e8"),
        0x1e9 => include_bytes!("data/1e9"),
        0x1ec => include_bytes!("data/1ec"),
        0x1ed => include_bytes!("data/1ed"),
        0x1ee => include_bytes!("data/1ee"),
        0x1f0 => include_bytes!("data/1f0"),
        0x1f1 => include_bytes!("data/1f1"),
        0x1f2 => include_bytes!("data/1f2"),
        0x1f3 => include_bytes!("data/1f3"),
        0x1f4 => include_bytes!("data/1f4"),
        0x1f5 => include_bytes!("data/1f5"),
        0x1f6 => include_bytes!("data/1f6"),
        0x1f7 => include_bytes!("data/1f7"),
        0x1f8 => include_bytes!("data/1f8"),
        0x1f9 => include_bytes!("data/1f9"),
        0x1fa => include_bytes!("data/1fa"),
        0x1fb => include_bytes!("data/1fb"),
        0x200 => include_bytes!("data/200"),
        0x201 => include_bytes!("data/201"),
        0x202 => include_bytes!("data/202"),
        0x203 => include_bytes!("data/203"),
        0x204 => include_bytes!("data/204"),
        0x205 => include_bytes!("data/205"),
        0x206 => include_bytes!("data/206"),
        0x207 => include_bytes!("data/207"),
        0x208 => include_bytes!("data/208"),
        0x209 => include_bytes!("data/209"),
        0x20a => include_bytes!("data/20a"),
        0x20b => include_bytes!("data/20b"),
        0x20c => include_bytes!("data/20c"),
        0x20d => include_bytes!("data/20d"),
        0x20e => include_bytes!("data/20e"),
        0x20f => include_bytes!("data/20f"),
        0x210 => include_bytes!("data/210"),
        0x211 => include_bytes!("data/211"),
        0x212 => include_bytes!("data/212"),
        0x213 => include_bytes!("data/213"),
        0x214 => include_bytes!("data/214"),
        0x215 => include_bytes!("data/215"),
        0x216 => include_bytes!("data/216"),
        0x217 => include_bytes!("data/217"),
        0x218 => include_bytes!("data/218"),
        0x219 => include_bytes!("data/219"),
        0x21a => include_bytes!("data/21a"),
        0x21b => include_bytes!("data/21b"),
        0x21c => include_bytes!("data/21c"),
        0x21d => include_bytes!("data/21d"),
        0x21e => include_bytes!("data/21e"),
        0x21f => include_bytes!("data/21f"),
        0x220 => include_bytes!("data/220"),
        0x221 => include_bytes!("data/221"),
        0x222 => include_bytes!("data/222"),
        0x223 => include_bytes!("data/223"),
        0x224 => include_bytes!("data/224"),
        0x225 => include_bytes!("data/225"),
        0x226 => include_bytes!("data/226"),
        0x227 => include_bytes!("data/227"),
        0x228 => include_bytes!("data/228"),
        0x229 => include_bytes!("data/229"),
        0x22a => include_bytes!("data/22a"),
        0x22b => include_bytes!("data/22b"),
        0x22c => include_bytes!("data/22c"),
        0x22d => include_bytes!("data/22d"),
        0x22e => include_bytes!("data/22e"),
        0x22f => include_bytes!("data/22f"),
        0x230 => include_bytes!("data/230"),
        0x231 => include_bytes!("data/231"),
        0x232 => include_bytes!("data/232"),
        0x233 => include_bytes!("data/233"),
        0x234 => include_bytes!("data/234"),
        0x235 => include_bytes!("data/235"),
        0x236 => include_bytes!("data/236"),
        0x237 => include_bytes!("data/237"),
        0x238 => include_bytes!("data/238"),
        0x239 => include_bytes!("data/239"),
        0x23a => include_bytes!("data/23a"),
        0x23b => include_bytes!("data/23b"),
        0x23c => include_bytes!("data/23c"),
        0x23d => include_bytes!("data/23d"),
        0x23e => include_bytes!("data/23e"),
        0x23f => include_bytes!("data/23f"),
        0x240 => include_bytes!("data/240"),
        0x241 => include_bytes!("data/241"),
        0x242 => include_bytes!("data/242"),
        0x243 => include_bytes!("data/243"),
        0x244 => include_bytes!("data/244"),
        0x245 => include_bytes!("data/245"),
        0x246 => include_bytes!("data/246"),
        0x247 => include_bytes!("data/247"),
        0x248 => include_bytes!("data/248"),
        0x249 => include_bytes!("data/249"),
        0x24a => include_bytes!("data/24a"),
        0x24b => include_bytes!("data/24b"),
        0x24c => include_bytes!("data/24c"),
        0x24d => include_bytes!("data/24d"),
        0x24e => include_bytes!("data/24e"),
        0x24f => include_bytes!("data/24f"),
        0x250 => include_bytes!("data/250"),
        0x251 => include_bytes!("data/251"),
        0x252 => include_bytes!("data/252"),
        0x253 => include_bytes!("data/253"),
        0x254 => include_bytes!("data/254"),
        0x255 => include_bytes!("data/255"),
        0x256 => include_bytes!("data/256"),
        0x257 => include_bytes!("data/257"),
        0x258 => include_bytes!("data/258"),
        0x259 => include_bytes!("data/259"),
        0x25a => include_bytes!("data/25a"),
        0x25b => include_bytes!("data/25b"),
        0x25c => include_bytes!("data/25c"),
        0x25d => include_bytes!("data/25d"),
        0x25e => include_bytes!("data/25e"),
        0x25f => include_bytes!("data/25f"),
        0x260 => include_bytes!("data/260"),
        0x261 => include_bytes!("data/261"),
        0x262 => include_bytes!("data/262"),
        0x263 => include_bytes!("data/263"),
        0x264 => include_bytes!("data/264"),
        0x265 => include_bytes!("data/265"),
        0x266 => include_bytes!("data/266"),
        0x267 => include_bytes!("data/267"),
        0x268 => include_bytes!("data/268"),
        0x269 => include_bytes!("data/269"),
        0x26a => include_bytes!("data/26a"),
        0x26b => include_bytes!("data/26b"),
        0x26c => include_bytes!("data/26c"),
        0x26d => include_bytes!("data/26d"),
        0x26e => include_bytes!("data/26e"),
        0x26f => include_bytes!("data/26f"),
        0x270 => include_bytes!("data/270"),
        0x271 => include_bytes!("data/271"),
        0x272 => include_bytes!("data/272"),
        0x273 => include_bytes!("data/273"),
        0x274 => include_bytes!("data/274"),
        0x275 => include_bytes!("data/275"),
        0x276 => include_bytes!("data/276"),
        0x277 => include_bytes!("data/277"),
        0x278 => include_bytes!("data/278"),
        0x279 => include_bytes!("data/279"),
        0x27a => include_bytes!("data/27a"),
        0x27b => include_bytes!("data/27b"),
        0x27c => include_bytes!("data/27c"),
        0x27d => include_bytes!("data/27d"),
        0x27e => include_bytes!("data/27e"),
        0x27f => include_bytes!("data/27f"),
        0x280 => include_bytes!("data/280"),
        0x281 => include_bytes!("data/281"),
        0x282 => include_bytes!("data/282"),
        0x283 => include_bytes!("data/283"),
        0x284 => include_bytes!("data/284"),
        0x285 => include_bytes!("data/285"),
        0x286 => include_bytes!("data/286"),
        0x287 => include_bytes!("data/287"),
        0x288 => include_bytes!("data/288"),
        0x289 => include_bytes!("data/289"),
        0x28a => include_bytes!("data/28a"),
        0x28b => include_bytes!("data/28b"),
        0x28c => include_bytes!("data/28c"),
        0x28d => include_bytes!("data/28d"),
        0x28e => include_bytes!("data/28e"),
        0x28f => include_bytes!("data/28f"),
        0x290 => include_bytes!("data/290"),
        0x291 => include_bytes!("data/291"),
        0x292 => include_bytes!("data/292"),
        0x293 => include_bytes!("data/293"),
        0x294 => include_bytes!("data/294"),
        0x295 => include_bytes!("data/295"),
        0x296 => include_bytes!("data/296"),
        0x297 => include_bytes!("data/297"),
        0x298 => include_bytes!("data/298"),
        0x299 => include_bytes!("data/299"),
        0x29a => include_bytes!("data/29a"),
        0x29b => include_bytes!("data/29b"),
        0x29c => include_bytes!("data/29c"),
        0x29d => include_bytes!("data/29d"),
        0x29e => include_bytes!("data/29e"),
        0x29f => include_bytes!("data/29f"),
        0x2a0 => include_bytes!("data/2a0"),
        0x2a1 => include_bytes!("data/2a1"),
        0x2a2 => include_bytes!("data/2a2"),
        0x2a3 => include_bytes!("data/2a3"),
        0x2a4 => include_bytes!("data/2a4"),
        0x2a5 => include_bytes!("data/2a5"),
        0x2a6 => include_bytes!("data/2a6"),
        0x2a7 => include_bytes!("data/2a7"),
        0x2a8 => include_bytes!("data/2a8"),
        0x2a9 => include_bytes!("data/2a9"),
        0x2aa => include_bytes!("data/2aa"),
        0x2ab => include_bytes!("data/2ab"),
        0x2ac => include_bytes!("data/2ac"),
        0x2ad => include_bytes!("data/2ad"),
        0x2ae => include_bytes!("data/2ae"),
        0x2af => include_bytes!("data/2af"),
        0x2b0 => include_bytes!("data/2b0"),
        0x2b1 => include_bytes!("data/2b1"),
        0x2b2 => include_bytes!("data/2b2"),
        0x2b3 => include_bytes!("data/2b3"),
        0x2b4 => include_bytes!("data/2b4"),
        0x2b5 => include_bytes!("data/2b5"),
        0x2b6 => include_bytes!("data/2b6"),
        0x2b7 => include_bytes!("data/2b7"),
        0x2b8 => include_bytes!("data/2b8"),
        0x2b9 => include_bytes!("data/2b9"),
        0x2ba => include_bytes!("data/2ba"),
        0x2bb => include_bytes!("data/2bb"),
        0x2bc => include_bytes!("data/2bc"),
        0x2bd => include_bytes!("data/2bd"),
        0x2be => include_bytes!("data/2be"),
        0x2bf => include_bytes!("data/2bf"),
        0x2c0 => include_bytes!("data/2c0"),
        0x2c1 => include_bytes!("data/2c1"),
        0x2c2 => include_bytes!("data/2c2"),
        0x2c3 => include_bytes!("data/2c3"),
        0x2c4 => include_bytes!("data/2c4"),
        0x2c5 => include_bytes!("data/2c5"),
        0x2c6 => include_bytes!("data/2c6"),
        0x2c7 => include_bytes!("data/2c7"),
        0x2c8 => include_bytes!("data/2c8"),
        0x2c9 => include_bytes!("data/2c9"),
        0x2ca => include_bytes!("data/2ca"),
        0x2cb => include_bytes!("data/2cb"),
        0x2cc => include_bytes!("data/2cc"),
        0x2cd => include_bytes!("data/2cd"),
        0x2ce => include_bytes!("data/2ce"),
        0x2cf => include_bytes!("data/2cf"),
        0x2d0 => include_bytes!("data/2d0"),
        0x2d1 => include_bytes!("data/2d1"),
        0x2d2 => include_bytes!("data/2d2"),
        0x2d3 => include_bytes!("data/2d3"),
        0x2d4 => include_bytes!("data/2d4"),
        0x2d5 => include_bytes!("data/2d5"),
        0x2d6 => include_bytes!("data/2d6"),
        0x2d7 => include_bytes!("data/2d7"),
        0x2d8 => include_bytes!("data/2d8"),
        0x2d9 => include_bytes!("data/2d9"),
        0x2da => include_bytes!("data/2da"),
        0x2db => include_bytes!("data/2db"),
        0x2dc => include_bytes!("data/2dc"),
        0x2dd => include_bytes!("data/2dd"),
        0x2de => include_bytes!("data/2de"),
        0x2df => include_bytes!("data/2df"),
        0x2e0 => include_bytes!("data/2e0"),
        0x2e1 => include_bytes!("data/2e1"),
        0x2e2 => include_bytes!("data/2e2"),
        0x2e3 => include_bytes!("data/2e3"),
        0x2e4 => include_bytes!("data/2e4"),
        0x2e5 => include_bytes!("data/2e5"),
        0x2e6 => include_bytes!("data/2e6"),
        0x2e7 => include_bytes!("data/2e7"),
        0x2e8 => include_bytes!("data/2e8"),
        0x2e9 => include_bytes!("data/2e9"),
        0x2ea => include_bytes!("data/2ea"),
        0x2eb => include_bytes!("data/2eb"),
        0x2f8 => include_bytes!("data/2f8"),
        0x2f9 => include_bytes!("data/2f9"),
        0x2fa => include_bytes!("data/2fa"),
        0x300 => include_bytes!("data/300"),
        0x301 => include_bytes!("data/301"),
        0x302 => include_bytes!("data/302"),
        0x303 => include_bytes!("data/303"),
        0x304 => include_bytes!("data/304"),
        0x305 => include_bytes!("data/305"),
        0x306 => include_bytes!("data/306"),
        0x307 => include_bytes!("data/307"),
        0x308 => include_bytes!("data/308"),
        0x309 => include_bytes!("data/309"),
        0x30a => include_bytes!("data/30a"),
        0x30b => include_bytes!("data/30b"),
        0x30c => include_bytes!("data/30c"),
        0x30d => include_bytes!("data/30d"),
        0x30e => include_bytes!("data/30e"),
        0x30f => include_bytes!("data/30f"),
        0x310 => include_bytes!("data/310"),
        0x311 => include_bytes!("data/311"),
        0x312 => include_bytes!("data/312"),
        0x313 => include_bytes!("data/313"),
        0x314 => include_bytes!("data/314"),
        0x315 => include_bytes!("data/315"),
        0x316 => include_bytes!("data/316"),
        0x317 => include_bytes!("data/317"),
        0x318 => include_bytes!("data/318"),
        0x319 => include_bytes!("data/319"),
        0x31f => include_bytes!("data/31f"),
        0x320 => include_bytes!("data/320"),
        0xe00 => include_bytes!("data/e00"),
        _ => &[],
    };
    unsafe { core::slice::from_raw_parts(b.as_ptr().cast(), b.len() / 3) }
}

'use strict';

const blocks = {};

module.exports = function block(blockNum) {
    let b = blocks[blockNum];
    if (b !== undefined) return b;
    switch (blockNum) {
        case 0x000:
            b = require('./data/000.js');
            break;
        case 0x001:
            b = require('./data/001.js');
            break;
        case 0x002:
            b = require('./data/002.js');
            break;
        case 0x003:
            b = require('./data/003.js');
            break;
        case 0x004:
            b = require('./data/004.js');
            break;
        case 0x005:
            b = require('./data/005.js');
            break;
        case 0x006:
            b = require('./data/006.js');
            break;
        case 0x007:
            b = require('./data/007.js');
            break;
        case 0x009:
            b = require('./data/009.js');
            break;
        case 0x00a:
            b = require('./data/00a.js');
            break;
        case 0x00b:
            b = require('./data/00b.js');
            break;
        case 0x00c:
            b = require('./data/00c.js');
            break;
        case 0x00d:
            b = require('./data/00d.js');
            break;
        case 0x00e:
            b = require('./data/00e.js');
            break;
        case 0x00f:
            b = require('./data/00f.js');
            break;
        case 0x010:
            b = require('./data/010.js');
            break;
        case 0x011:
            b = require('./data/011.js');
            break;
        case 0x012:
            b = require('./data/012.js');
            break;
        case 0x013:
            b = require('./data/013.js');
            break;
        case 0x014:
            b = require('./data/014.js');
            break;
        case 0x015:
            b = require('./data/015.js');
            break;
        case 0x016:
            b = require('./data/016.js');
            break;
        case 0x017:
            b = require('./data/017.js');
            break;
        case 0x018:
            b = require('./data/018.js');
            break;
        case 0x01d:
            b = require('./data/01d.js');
            break;
        case 0x01e:
            b = require('./data/01e.js');
            break;
        case 0x01f:
            b = require('./data/01f.js');
            break;
        case 0x020:
            b = require('./data/020.js');
            break;
        case 0x021:
            b = require('./data/021.js');
            break;
        case 0x022:
            b = require('./data/022.js');
            break;
        case 0x023:
            b = require('./data/023.js');
            break;
        case 0x024:
            b = require('./data/024.js');
            break;
        case 0x025:
            b = require('./data/025.js');
            break;
        case 0x027:
            b = require('./data/027.js');
            break;
        case 0x028:
            b = require('./data/028.js');
            break;
        case 0x029:
            b = require('./data/029.js');
            break;
        case 0x02a:
            b = require('./data/02a.js');
            break;
        case 0x02c:
            b = require('./data/02c.js');
            break;
        case 0x02e:
            b = require('./data/02e.js');
            break;
        case 0x02f:
            b = require('./data/02f.js');
            break;
        case 0x030:
            b = require('./data/030.js');
            break;
        case 0x031:
            b = require('./data/031.js');
            break;
        case 0x032:
            b = require('./data/032.js');
            break;
        case 0x033:
            b = require('./data/033.js');
            break;
        case 0x034:
            b = require('./data/034.js');
            break;
        case 0x035:
            b = require('./data/035.js');
            break;
        case 0x036:
            b = require('./data/036.js');
            break;
        case 0x037:
            b = require('./data/037.js');
            break;
        case 0x038:
            b = require('./data/038.js');
            break;
        case 0x039:
            b = require('./data/039.js');
            break;
        case 0x03a:
            b = require('./data/03a.js');
            break;
        case 0x03b:
            b = require('./data/03b.js');
            break;
        case 0x03c:
            b = require('./data/03c.js');
            break;
        case 0x03d:
            b = require('./data/03d.js');
            break;
        case 0x03e:
            b = require('./data/03e.js');
            break;
        case 0x03f:
            b = require('./data/03f.js');
            break;
        case 0x040:
            b = require('./data/040.js');
            break;
        case 0x041:
            b = require('./data/041.js');
            break;
        case 0x042:
            b = require('./data/042.js');
            break;
        case 0x043:
            b = require('./data/043.js');
            break;
        case 0x044:
            b = require('./data/044.js');
            break;
        case 0x045:
            b = require('./data/045.js');
            break;
        case 0x046:
            b = require('./data/046.js');
            break;
        case 0x047:
            b = require('./data/047.js');
            break;
        case 0x048:
            b = require('./data/048.js');
            break;
        case 0x049:
            b = require('./data/049.js');
            break;
        case 0x04a:
            b = require('./data/04a.js');
            break;
        case 0x04b:
            b = require('./data/04b.js');
            break;
        case 0x04c:
            b = require('./data/04c.js');
            break;
        case 0x04d:
            b = require('./data/04d.js');
            break;
        case 0x04e:
            b = require('./data/04e.js');
            break;
        case 0x04f:
            b = require('./data/04f.js');
            break;
        case 0x050:
            b = require('./data/050.js');
            break;
        case 0x051:
            b = require('./data/051.js');
            break;
        case 0x052:
            b = require('./data/052.js');
            break;
        case 0x053:
            b = require('./data/053.js');
            break;
        case 0x054:
            b = require('./data/054.js');
            break;
        case 0x055:
            b = require('./data/055.js');
            break;
        case 0x056:
            b = require('./data/056.js');
            break;
        case 0x057:
            b = require('./data/057.js');
            break;
        case 0x058:
            b = require('./data/058.js');
            break;
        case 0x059:
            b = require('./data/059.js');
            break;
        case 0x05a:
            b = require('./data/05a.js');
            break;
        case 0x05b:
            b = require('./data/05b.js');
            break;
        case 0x05c:
            b = require('./data/05c.js');
            break;
        case 0x05d:
            b = require('./data/05d.js');
            break;
        case 0x05e:
            b = require('./data/05e.js');
            break;
        case 0x05f:
            b = require('./data/05f.js');
            break;
        case 0x060:
            b = require('./data/060.js');
            break;
        case 0x061:
            b = require('./data/061.js');
            break;
        case 0x062:
            b = require('./data/062.js');
            break;
        case 0x063:
            b = require('./data/063.js');
            break;
        case 0x064:
            b = require('./data/064.js');
            break;
        case 0x065:
            b = require('./data/065.js');
            break;
        case 0x066:
            b = require('./data/066.js');
            break;
        case 0x067:
            b = require('./data/067.js');
            break;
        case 0x068:
            b = require('./data/068.js');
            break;
        case 0x069:
            b = require('./data/069.js');
            break;
        case 0x06a:
            b = require('./data/06a.js');
            break;
        case 0x06b:
            b = require('./data/06b.js');
            break;
        case 0x06c:
            b = require('./data/06c.js');
            break;
        case 0x06d:
            b = require('./data/06d.js');
            break;
        case 0x06e:
            b = require('./data/06e.js');
            break;
        case 0x06f:
            b = require('./data/06f.js');
            break;
        case 0x070:
            b = require('./data/070.js');
            break;
        case 0x071:
            b = require('./data/071.js');
            break;
        case 0x072:
            b = require('./data/072.js');
            break;
        case 0x073:
            b = require('./data/073.js');
            break;
        case 0x074:
            b = require('./data/074.js');
            break;
        case 0x075:
            b = require('./data/075.js');
            break;
        case 0x076:
            b = require('./data/076.js');
            break;
        case 0x077:
            b = require('./data/077.js');
            break;
        case 0x078:
            b = require('./data/078.js');
            break;
        case 0x079:
            b = require('./data/079.js');
            break;
        case 0x07a:
            b = require('./data/07a.js');
            break;
        case 0x07b:
            b = require('./data/07b.js');
            break;
        case 0x07c:
            b = require('./data/07c.js');
            break;
        case 0x07d:
            b = require('./data/07d.js');
            break;
        case 0x07e:
            b = require('./data/07e.js');
            break;
        case 0x07f:
            b = require('./data/07f.js');
            break;
        case 0x080:
            b = require('./data/080.js');
            break;
        case 0x081:
            b = require('./data/081.js');
            break;
        case 0x082:
            b = require('./data/082.js');
            break;
        case 0x083:
            b = require('./data/083.js');
            break;
        case 0x084:
            b = require('./data/084.js');
            break;
        case 0x085:
            b = require('./data/085.js');
            break;
        case 0x086:
            b = require('./data/086.js');
            break;
        case 0x087:
            b = require('./data/087.js');
            break;
        case 0x088:
            b = require('./data/088.js');
            break;
        case 0x089:
            b = require('./data/089.js');
            break;
        case 0x08a:
            b = require('./data/08a.js');
            break;
        case 0x08b:
            b = require('./data/08b.js');
            break;
        case 0x08c:
            b = require('./data/08c.js');
            break;
        case 0x08d:
            b = require('./data/08d.js');
            break;
        case 0x08e:
            b = require('./data/08e.js');
            break;
        case 0x08f:
            b = require('./data/08f.js');
            break;
        case 0x090:
            b = require('./data/090.js');
            break;
        case 0x091:
            b = require('./data/091.js');
            break;
        case 0x092:
            b = require('./data/092.js');
            break;
        case 0x093:
            b = require('./data/093.js');
            break;
        case 0x094:
            b = require('./data/094.js');
            break;
        case 0x095:
            b = require('./data/095.js');
            break;
        case 0x096:
            b = require('./data/096.js');
            break;
        case 0x097:
            b = require('./data/097.js');
            break;
        case 0x098:
            b = require('./data/098.js');
            break;
        case 0x099:
            b = require('./data/099.js');
            break;
        case 0x09a:
            b = require('./data/09a.js');
            break;
        case 0x09b:
            b = require('./data/09b.js');
            break;
        case 0x09c:
            b = require('./data/09c.js');
            break;
        case 0x09d:
            b = require('./data/09d.js');
            break;
        case 0x09e:
            b = require('./data/09e.js');
            break;
        case 0x09f:
            b = require('./data/09f.js');
            break;
        case 0x0a0:
            b = require('./data/0a0.js');
            break;
        case 0x0a1:
            b = require('./data/0a1.js');
            break;
        case 0x0a2:
            b = require('./data/0a2.js');
            break;
        case 0x0a3:
            b = require('./data/0a3.js');
            break;
        case 0x0a4:
            b = require('./data/0a4.js');
            break;
        case 0x0a5:
            b = require('./data/0a5.js');
            break;
        case 0x0a6:
            b = require('./data/0a6.js');
            break;
        case 0x0a7:
            b = require('./data/0a7.js');
            break;
        case 0x0ab:
            b = require('./data/0ab.js');
            break;
        case 0x0ac:
            b = require('./data/0ac.js');
            break;
        case 0x0ad:
            b = require('./data/0ad.js');
            break;
        case 0x0ae:
            b = require('./data/0ae.js');
            break;
        case 0x0af:
            b = require('./data/0af.js');
            break;
        case 0x0b0:
            b = require('./data/0b0.js');
            break;
        case 0x0b1:
            b = require('./data/0b1.js');
            break;
        case 0x0b2:
            b = require('./data/0b2.js');
            break;
        case 0x0b3:
            b = require('./data/0b3.js');
            break;
        case 0x0b4:
            b = require('./data/0b4.js');
            break;
        case 0x0b5:
            b = require('./data/0b5.js');
            break;
        case 0x0b6:
            b = require('./data/0b6.js');
            break;
        case 0x0b7:
            b = require('./data/0b7.js');
            break;
        case 0x0b8:
            b = require('./data/0b8.js');
            break;
        case 0x0b9:
            b = require('./data/0b9.js');
            break;
        case 0x0ba:
            b = require('./data/0ba.js');
            break;
        case 0x0bb:
            b = require('./data/0bb.js');
            break;
        case 0x0bc:
            b = require('./data/0bc.js');
            break;
        case 0x0bd:
            b = require('./data/0bd.js');
            break;
        case 0x0be:
            b = require('./data/0be.js');
            break;
        case 0x0bf:
            b = require('./data/0bf.js');
            break;
        case 0x0c0:
            b = require('./data/0c0.js');
            break;
        case 0x0c1:
            b = require('./data/0c1.js');
            break;
        case 0x0c2:
            b = require('./data/0c2.js');
            break;
        case 0x0c3:
            b = require('./data/0c3.js');
            break;
        case 0x0c4:
            b = require('./data/0c4.js');
            break;
        case 0x0c5:
            b = require('./data/0c5.js');
            break;
        case 0x0c6:
            b = require('./data/0c6.js');
            break;
        case 0x0c7:
            b = require('./data/0c7.js');
            break;
        case 0x0c8:
            b = require('./data/0c8.js');
            break;
        case 0x0c9:
            b = require('./data/0c9.js');
            break;
        case 0x0ca:
            b = require('./data/0ca.js');
            break;
        case 0x0cb:
            b = require('./data/0cb.js');
            break;
        case 0x0cc:
            b = require('./data/0cc.js');
            break;
        case 0x0cd:
            b = require('./data/0cd.js');
            break;
        case 0x0ce:
            b = require('./data/0ce.js');
            break;
        case 0x0cf:
            b = require('./data/0cf.js');
            break;
        case 0x0d0:
            b = require('./data/0d0.js');
            break;
        case 0x0d1:
            b = require('./data/0d1.js');
            break;
        case 0x0d2:
            b = require('./data/0d2.js');
            break;
        case 0x0d3:
            b = require('./data/0d3.js');
            break;
        case 0x0d4:
            b = require('./data/0d4.js');
            break;
        case 0x0d5:
            b = require('./data/0d5.js');
            break;
        case 0x0d6:
            b = require('./data/0d6.js');
            break;
        case 0x0d7:
            b = require('./data/0d7.js');
            break;
        case 0x0f9:
            b = require('./data/0f9.js');
            break;
        case 0x0fa:
            b = require('./data/0fa.js');
            break;
        case 0x0fb:
            b = require('./data/0fb.js');
            break;
        case 0x0fc:
            b = require('./data/0fc.js');
            break;
        case 0x0fd:
            b = require('./data/0fd.js');
            break;
        case 0x0fe:
            b = require('./data/0fe.js');
            break;
        case 0x0ff:
            b = require('./data/0ff.js');
            break;
        case 0x1d4:
            b = require('./data/1d4.js');
            break;
        case 0x1d5:
            b = require('./data/1d5.js');
            break;
        case 0x1d6:
            b = require('./data/1d6.js');
            break;
        case 0x1d7:
            b = require('./data/1d7.js');
            break;
        case 0x1ee:
            b = require('./data/1ee.js');
            break;
        case 0x1f1:
            b = require('./data/1f1.js');
            break;
        case 0x1f2:
            b = require('./data/1f2.js');
            break;
        case 0x200:
            b = require('./data/200.js');
            break;
        case 0xe00:
            b = require('./data/e00.js');
            break;
        case 0x201:
            b = require('./data/201.js');
            break;
        case 0x202:
            b = require('./data/202.js');
            break;
        case 0x203:
            b = require('./data/203.js');
            break;
        case 0x204:
            b = require('./data/204.js');
            break;
        case 0x205:
            b = require('./data/205.js');
            break;
        case 0x206:
            b = require('./data/206.js');
            break;
        case 0x207:
            b = require('./data/207.js');
            break;
        case 0x208:
            b = require('./data/208.js');
            break;
        case 0x209:
            b = require('./data/209.js');
            break;
        case 0x20a:
            b = require('./data/20a.js');
            break;
        case 0x20b:
            b = require('./data/20b.js');
            break;
        case 0x20c:
            b = require('./data/20c.js');
            break;
        case 0x20d:
            b = require('./data/20d.js');
            break;
        case 0x20e:
            b = require('./data/20e.js');
            break;
        case 0x20f:
            b = require('./data/20f.js');
            break;
        case 0x210:
            b = require('./data/210.js');
            break;
        case 0x211:
            b = require('./data/211.js');
            break;
        case 0x212:
            b = require('./data/212.js');
            break;
        case 0x213:
            b = require('./data/213.js');
            break;
        case 0x214:
            b = require('./data/214.js');
            break;
        case 0x215:
            b = require('./data/215.js');
            break;
        case 0x216:
            b = require('./data/216.js');
            break;
        case 0x217:
            b = require('./data/217.js');
            break;
        case 0x218:
            b = require('./data/218.js');
            break;
        case 0x219:
            b = require('./data/219.js');
            break;
        case 0x21a:
            b = require('./data/21a.js');
            break;
        case 0x21b:
            b = require('./data/21b.js');
            break;
        case 0x21c:
            b = require('./data/21c.js');
            break;
        case 0x21d:
            b = require('./data/21d.js');
            break;
        case 0x21e:
            b = require('./data/21e.js');
            break;
        case 0x21f:
            b = require('./data/21f.js');
            break;
        case 0x220:
            b = require('./data/220.js');
            break;
        case 0x221:
            b = require('./data/221.js');
            break;
        case 0x222:
            b = require('./data/222.js');
            break;
        case 0x223:
            b = require('./data/223.js');
            break;
        case 0x224:
            b = require('./data/224.js');
            break;
        case 0x225:
            b = require('./data/225.js');
            break;
        case 0x226:
            b = require('./data/226.js');
            break;
        case 0x227:
            b = require('./data/227.js');
            break;
        case 0x228:
            b = require('./data/228.js');
            break;
        case 0x229:
            b = require('./data/229.js');
            break;
        case 0x22a:
            b = require('./data/22a.js');
            break;
        case 0x22b:
            b = require('./data/22b.js');
            break;
        case 0x22c:
            b = require('./data/22c.js');
            break;
        case 0x22d:
            b = require('./data/22d.js');
            break;
        case 0x22e:
            b = require('./data/22e.js');
            break;
        case 0x22f:
            b = require('./data/22f.js');
            break;
        case 0x230:
            b = require('./data/230.js');
            break;
        case 0x231:
            b = require('./data/231.js');
            break;
        case 0x232:
            b = require('./data/232.js');
            break;
        case 0x233:
            b = require('./data/233.js');
            break;
        case 0x234:
            b = require('./data/234.js');
            break;
        case 0x235:
            b = require('./data/235.js');
            break;
        case 0x236:
            b = require('./data/236.js');
            break;
        case 0x237:
            b = require('./data/237.js');
            break;
        case 0x238:
            b = require('./data/238.js');
            break;
        case 0x239:
            b = require('./data/239.js');
            break;
        case 0x23a:
            b = require('./data/23a.js');
            break;
        case 0x23b:
            b = require('./data/23b.js');
            break;
        case 0x23c:
            b = require('./data/23c.js');
            break;
        case 0x23d:
            b = require('./data/23d.js');
            break;
        case 0x23e:
            b = require('./data/23e.js');
            break;
        case 0x23f:
            b = require('./data/23f.js');
            break;
        case 0x240:
            b = require('./data/240.js');
            break;
        case 0x241:
            b = require('./data/241.js');
            break;
        case 0x242:
            b = require('./data/242.js');
            break;
        case 0x243:
            b = require('./data/243.js');
            break;
        case 0x244:
            b = require('./data/244.js');
            break;
        case 0x245:
            b = require('./data/245.js');
            break;
        case 0x246:
            b = require('./data/246.js');
            break;
        case 0x247:
            b = require('./data/247.js');
            break;
        case 0x248:
            b = require('./data/248.js');
            break;
        case 0x249:
            b = require('./data/249.js');
            break;
        case 0x24a:
            b = require('./data/24a.js');
            break;
        case 0x24b:
            b = require('./data/24b.js');
            break;
        case 0x24c:
            b = require('./data/24c.js');
            break;
        case 0x24d:
            b = require('./data/24d.js');
            break;
        case 0x24e:
            b = require('./data/24e.js');
            break;
        case 0x24f:
            b = require('./data/24f.js');
            break;
        case 0x250:
            b = require('./data/250.js');
            break;
        case 0x251:
            b = require('./data/251.js');
            break;
        case 0x252:
            b = require('./data/252.js');
            break;
        case 0x253:
            b = require('./data/253.js');
            break;
        case 0x254:
            b = require('./data/254.js');
            break;
        case 0x255:
            b = require('./data/255.js');
            break;
        case 0x256:
            b = require('./data/256.js');
            break;
        case 0x257:
            b = require('./data/257.js');
            break;
        case 0x258:
            b = require('./data/258.js');
            break;
        case 0x259:
            b = require('./data/259.js');
            break;
        case 0x25a:
            b = require('./data/25a.js');
            break;
        case 0x25b:
            b = require('./data/25b.js');
            break;
        case 0x25c:
            b = require('./data/25c.js');
            break;
        case 0x25d:
            b = require('./data/25d.js');
            break;
        case 0x25e:
            b = require('./data/25e.js');
            break;
        case 0x25f:
            b = require('./data/25f.js');
            break;
        case 0x260:
            b = require('./data/260.js');
            break;
        case 0x261:
            b = require('./data/261.js');
            break;
        case 0x262:
            b = require('./data/262.js');
            break;
        case 0x263:
            b = require('./data/263.js');
            break;
        case 0x264:
            b = require('./data/264.js');
            break;
        case 0x265:
            b = require('./data/265.js');
            break;
        case 0x266:
            b = require('./data/266.js');
            break;
        case 0x267:
            b = require('./data/267.js');
            break;
        case 0x268:
            b = require('./data/268.js');
            break;
        case 0x269:
            b = require('./data/269.js');
            break;
        case 0x26a:
            b = require('./data/26a.js');
            break;
        case 0x26b:
            b = require('./data/26b.js');
            break;
        case 0x26c:
            b = require('./data/26c.js');
            break;
        case 0x26d:
            b = require('./data/26d.js');
            break;
        case 0x26e:
            b = require('./data/26e.js');
            break;
        case 0x26f:
            b = require('./data/26f.js');
            break;
        case 0x270:
            b = require('./data/270.js');
            break;
        case 0x271:
            b = require('./data/271.js');
            break;
        case 0x272:
            b = require('./data/272.js');
            break;
        case 0x273:
            b = require('./data/273.js');
            break;
        case 0x274:
            b = require('./data/274.js');
            break;
        case 0x275:
            b = require('./data/275.js');
            break;
        case 0x276:
            b = require('./data/276.js');
            break;
        case 0x277:
            b = require('./data/277.js');
            break;
        case 0x278:
            b = require('./data/278.js');
            break;
        case 0x279:
            b = require('./data/279.js');
            break;
        case 0x27a:
            b = require('./data/27a.js');
            break;
        case 0x27b:
            b = require('./data/27b.js');
            break;
        case 0x27c:
            b = require('./data/27c.js');
            break;
        case 0x27d:
            b = require('./data/27d.js');
            break;
        case 0x27e:
            b = require('./data/27e.js');
            break;
        case 0x27f:
            b = require('./data/27f.js');
            break;
        case 0x280:
            b = require('./data/280.js');
            break;
        case 0x281:
            b = require('./data/281.js');
            break;
        case 0x282:
            b = require('./data/282.js');
            break;
        case 0x283:
            b = require('./data/283.js');
            break;
        case 0x284:
            b = require('./data/284.js');
            break;
        case 0x285:
            b = require('./data/285.js');
            break;
        case 0x286:
            b = require('./data/286.js');
            break;
        case 0x287:
            b = require('./data/287.js');
            break;
        case 0x288:
            b = require('./data/288.js');
            break;
        case 0x289:
            b = require('./data/289.js');
            break;
        case 0x28a:
            b = require('./data/28a.js');
            break;
        case 0x28b:
            b = require('./data/28b.js');
            break;
        case 0x28c:
            b = require('./data/28c.js');
            break;
        case 0x28d:
            b = require('./data/28d.js');
            break;
        case 0x28e:
            b = require('./data/28e.js');
            break;
        case 0x28f:
            b = require('./data/28f.js');
            break;
        case 0x290:
            b = require('./data/290.js');
            break;
        case 0x291:
            b = require('./data/291.js');
            break;
        case 0x292:
            b = require('./data/292.js');
            break;
        case 0x293:
            b = require('./data/293.js');
            break;
        case 0x294:
            b = require('./data/294.js');
            break;
        case 0x295:
            b = require('./data/295.js');
            break;
        case 0x296:
            b = require('./data/296.js');
            break;
        case 0x297:
            b = require('./data/297.js');
            break;
        case 0x298:
            b = require('./data/298.js');
            break;
        case 0x299:
            b = require('./data/299.js');
            break;
        case 0x29a:
            b = require('./data/29a.js');
            break;
        case 0x29b:
            b = require('./data/29b.js');
            break;
        case 0x29c:
            b = require('./data/29c.js');
            break;
        case 0x29d:
            b = require('./data/29d.js');
            break;
        case 0x29e:
            b = require('./data/29e.js');
            break;
        case 0x29f:
            b = require('./data/29f.js');
            break;
        case 0x2a0:
            b = require('./data/2a0.js');
            break;
        case 0x2a1:
            b = require('./data/2a1.js');
            break;
        case 0x2a2:
            b = require('./data/2a2.js');
            break;
        case 0x2a3:
            b = require('./data/2a3.js');
            break;
        case 0x2a4:
            b = require('./data/2a4.js');
            break;
        case 0x2a5:
            b = require('./data/2a5.js');
            break;
        case 0x2a6:
            b = require('./data/2a6.js');
            break;
        case 0x2a7:
            b = require('./data/2a7.js');
            break;
        case 0x2a8:
            b = require('./data/2a8.js');
            break;
        case 0x2aa:
            b = require('./data/2aa.js');
            break;
        case 0x2ad:
            b = require('./data/2ad.js');
            break;
        case 0x2ae:
            b = require('./data/2ae.js');
            break;
        case 0x2af:
            b = require('./data/2af.js');
            break;
        case 0x2b0:
            b = require('./data/2b0.js');
            break;
        case 0x2b1:
            b = require('./data/2b1.js');
            break;
        case 0x2b2:
            b = require('./data/2b2.js');
            break;
        case 0x2b3:
            b = require('./data/2b3.js');
            break;
        case 0x2b4:
            b = require('./data/2b4.js');
            break;
        case 0x2b5:
            b = require('./data/2b5.js');
            break;
        case 0x2b6:
            b = require('./data/2b6.js');
            break;
        case 0x2b8:
            b = require('./data/2b8.js');
            break;
        case 0x2bf:
            b = require('./data/2bf.js');
            break;
        case 0x2c1:
            b = require('./data/2c1.js');
            break;
        case 0x2c4:
            b = require('./data/2c4.js');
            break;
        case 0x2c6:
            b = require('./data/2c6.js');
            break;
        case 0x2c7:
            b = require('./data/2c7.js');
            break;
        case 0x2c9:
            b = require('./data/2c9.js');
            break;
        case 0x2cb:
            b = require('./data/2cb.js');
            break;
        case 0x2ce:
            b = require('./data/2ce.js');
            break;
        case 0x2e5:
            b = require('./data/2e5.js');
            break;
        case 0x2f8:
            b = require('./data/2f8.js');
            break;
        case 0x2f9:
            b = require('./data/2f9.js');
            break;
        case 0x2fa:
            b = require('./data/2fa.js');
            break;
        default:
            b = [];
    }
    blocks[blockNum] = b;
    return b;
}
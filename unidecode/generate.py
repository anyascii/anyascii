# /// script
# dependencies = [
#     "unidecode==1.4.0",
# ]
# ///
from unidecode import unidecode

import warnings
warnings.filterwarnings('ignore')

with open('unidecode-py.tsv', 'w') as f:
    for cp in range(0x80, 0x10ffff + 1):
        c = chr(cp)
        s = unidecode(c)
        if s != '' and '\n' not in s:
            f.write(f'{c}\t{s}\n')

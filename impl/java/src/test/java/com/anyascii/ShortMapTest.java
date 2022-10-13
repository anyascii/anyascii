package com.anyascii;

import org.junit.Assert;
import org.junit.Test;

public final class ShortMapTest {

    @Test public void test() {
        ShortMap<Integer> m = new ShortMap<Integer>();

        m = add(m, 2);
        check(m, 2);
        m = add(m, 3);
        check(m, 2, 3);
        m = add(m, 0);
        check(m, 0, 2, 3);
        m = add(m, 1);
        check(m, 0, 1, 2, 3);
        m = add(m, 5);
        check(m, 0, 1, 2, 3, 5);
        m = add(m, 4);
        check(m, 0, 1, 2, 3, 4, 5);
    }

    private static ShortMap<Integer> add(ShortMap<Integer> m, int key) {
        return m.put(m.index((short) key), (short) key, key);
    }

    private static void check(ShortMap<Integer> m, int... keys) {
        int i = 0;
        for (int key : keys) {
            Assert.assertEquals(m.index((short) key), i);
            Assert.assertEquals(m.get(i).intValue(), key);
            i++;
        }
    }
}

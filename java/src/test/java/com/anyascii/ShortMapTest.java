package com.anyascii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ShortMapTest {

    @Test public void test() {
        ShortMap<String> m = new ShortMap<String>();

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

    private static ShortMap<String> add(ShortMap<String> m, int key) {
        return m.put(m.index((short) key), (short) key, Integer.toString(key));
    }

    private static void check(ShortMap<String> m, int... keys) {
        int i = 0;
        for (int key : keys) {
            Assertions.assertEquals(m.index((short) key), i);
            Assertions.assertEquals(m.get(i), Integer.toString(key));
            i++;
        }
    }
}

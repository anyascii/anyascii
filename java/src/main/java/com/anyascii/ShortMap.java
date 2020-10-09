package com.anyascii;

import java.util.Arrays;

final class ShortMap<T> {

    private final short[] keys;

    private final Object[] vals;

    ShortMap() {
        this(new short[0], new Object[0]);
    }

    private ShortMap(short[] keys, Object[] vals) {
        this.keys = keys;
        this.vals = vals;
    }

    int index(short key) {
        return Arrays.binarySearch(keys, key);
    }

    @SuppressWarnings("unchecked")
    T get(int index) {
        return (T) vals[index];
    }

    ShortMap<T> put(int index, short key, T val) {
        int i = -index - 1;
        int length = keys.length;
        short[] nkeys = Arrays.copyOf(keys, length + 1);
        Object[] nvals = Arrays.copyOf(vals, length + 1);
        System.arraycopy(nkeys, i, nkeys, i + 1, length - i);
        System.arraycopy(nvals, i, nvals, i + 1, length - i);
        nkeys[i] = key;
        nvals[i] = val;
        return new ShortMap<T>(nkeys, nvals);
    }
}

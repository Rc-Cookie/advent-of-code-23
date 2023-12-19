package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

public abstract class FastSolution extends Solution {

    private static final int[] CHAR_VAL = new int[128];
    private static final boolean[] IS_HEX = new boolean[128];
    static {
        for(int i=0; i<10; i++) {
            CHAR_VAL['0'+i] = i;
            IS_HEX['0'+i] = true;
        }
        for(int i=0; i<26; i++) {
            CHAR_VAL['a'+i] = CHAR_VAL['A'+i] = 10+i;
            IS_HEX['a'+i] = IS_HEX['A'+i] = i < 6;
        }
    }

    public final int eol(int from) {
        return indexOf('\n', from, chars.length);
    }

    public final int indexOf(char c, int from) {
        return indexOf(c, from, chars.length);
    }

    public final int indexOf(char c, int from, int to) {
        while(chars[from] != c) {
            from++;
            if(from == to)
                return -1;
        }
        return from;
    }

    public final int count(char c, int from, int to) {
        int count = 0;
        for(; from<to; from++)
            if(chars[from] == c)
                count++;
        return count;
    }

    public final int parseUInt(int from, int to) {
        int x = 0;
        for(; from<to; from++)
            x = 10 * x + chars[from] - '0';
        return x;
    }

    public final int parseUInt(int from) {
        int x = 0;
        do x = 10 * x + chars[from++] - '0';
        while(from != chars.length && chars[from] >= '0' && chars[from] <= '9');
        return x;
    }

    public final int parseUIntHex(int from, int to) {
        int x = 0;
        for(; from<to; from++)
            x = x << 4 | CHAR_VAL[chars[from]];
        return x;
    }

    public final int parseUIntHex(int from) {
        int x = 0;
        do x = x << 4 | chars[from++] - '0';
        while(from != chars.length && chars[from] < IS_HEX.length && IS_HEX[chars[from]]);
        return x;
    }

    public final long parseULong(int from, int to) {
        long x = 0;
        for(; from<to; from++)
            x = 10 * x + chars[from] - '0';
        return x;
    }

    public final long parseULong(int from) {
        long x = 0;
        do x = 10 * x + chars[from++] - '0';
        while(from != chars.length && chars[from] >= '0' && chars[from] <= '9');
        return x;
    }

    public final long parseULongHex(int from, int to) {
        long x = 0;
        for(; from<to; from++)
            x = x << 4 | CHAR_VAL[chars[from]];
        return x;
    }

    public final long parseULongHex(int from) {
        long x = 0;
        do x = x << 4 | chars[from++] - '0';
        while(from != chars.length && chars[from] < IS_HEX.length && IS_HEX[chars[from]]);
        return x;
    }



    public static boolean gbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        return (data[i >> 6] & 1L << (i & 63)) != 0;
    }

    public static boolean gbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        return (data[i >> 6] & 1L << (i & 63)) != 0;
    }

    public static void sbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >> 6] |= 1L << (i & 63);
    }

    public static void sbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        data[i >> 6] |= 1L << (i & 63);
    }

    public static void cbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >> 6] &= ~(1L << (i & 63));
    }

    public static void cbi(long[] data, int x, int y, int width, int step, int off) {
        int i = (x + width * y) * step + off;
        data[i >> 6] &= ~(1L << (i & 63));
    }
}

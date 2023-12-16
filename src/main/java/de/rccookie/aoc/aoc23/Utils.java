package de.rccookie.aoc.aoc23;

final class Utils {

    private Utils() { }



    public static boolean gbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        return (data[i >> 6] & 1L << (i & 63)) != 0;
    }

    public static void sbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >> 6] |= 1L << (i & 63);
    }

    public static void cbi(long[] data, int x, int y, int width) {
        int i = x + width * y;
        data[i >> 6] &= ~(1L << (i & 63));
    }
}

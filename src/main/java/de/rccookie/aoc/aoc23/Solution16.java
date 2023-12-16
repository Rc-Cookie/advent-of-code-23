package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;
import sun.misc.Unsafe;

import static de.rccookie.aoc.aoc23.Utils.gbi;
import static de.rccookie.aoc.aoc23.Utils.sbi;

public class Solution16 extends Solution {

    private static final Unsafe UNSAFE = (Unsafe) de.rccookie.util.Utils.getField(Unsafe.class, "theUnsafe");
    private static final long ARR_OFF = UNSAFE.arrayBaseOffset(long[].class);

    private static final byte RIGHT = 0;
    private static final byte DOWN = 1;
    private static final byte LEFT = 2;
    private static final byte UP = 3;

    private static final int[] DIRS_X = { 1, 0, -1, 0 };
    private static final int[] DIRS_Y = { 0, 1, 0, -1 };
    private static final char[] SPLITTER_FOR_DIR = { '|', '-', '|', '-' };
    private final byte[][] NEXT_DIRS = new byte[128][];
    {
        NEXT_DIRS['|'] = new byte[] {    UP,  DOWN,    UP,    UP };
        NEXT_DIRS['-'] = new byte[] { RIGHT, RIGHT,  LEFT, RIGHT };
        NEXT_DIRS['.'] = new byte[] { RIGHT,  DOWN,  LEFT,    UP };
        NEXT_DIRS['/'] = new byte[] {    UP,  LEFT,  DOWN, RIGHT };
        NEXT_DIRS['\\']= new byte[] {  DOWN, RIGHT,    UP,  LEFT };
    }

    private long[] energized;

    @Override
    public Object task1() {
        int width = charTable[0].length;
        int height = charTable.length;
        energized = new long[(width * height * 4 + 63) >> 6];

        return findEnergized(0, 0, RIGHT, width, height);
    }

    @Override
    public Object task2() {
        int width = charTable[0].length;
        int height = charTable.length;
        energized = new long[(width * height * 4 + 63) >> 6];

        int max = 0;
        for(int i=0; i<charTable.length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(0, i, RIGHT, width, height),
                    findEnergized(charTable.length-1, i, LEFT, width, height)
            );
        }
        for(int i=0; i<charTable[0].length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(i, 0, DOWN, width, height),
                    findEnergized(i, charTable[0].length-1, UP, width, height)
            );
        }
        return max;
    }

    private int findEnergized(int x, int y, int dir, int width, int height) {
        UNSAFE.setMemory(energized, ARR_OFF, 8 * (long) energized.length, (byte) 0);
        followLight(x, y, dir, energized, width, height);
        int count = 0;
        for(long l : energized)
            for(int i=0; i<64; i+=4)
                if((l >> i & 0xF) != 0)
                    count++;
        return count;
    }

    private void followLight(int x, int y, int dir, long[] energized, int width, int height) {
        while(x >= 0 && y >= 0 && x < width && y < height && !gbi(energized, x, y, width, 4, dir)) {
            sbi(energized, x, y, width, 4, dir);

            char c = charTable[y][x];
            if(c == SPLITTER_FOR_DIR[dir]) {
                dir = NEXT_DIRS[c][dir];
                followLight(x, y, (dir+2) & 3, energized, width, height);
            }
            else dir = NEXT_DIRS[c][dir];
            x += DIRS_X[dir];
            y += DIRS_Y[dir];
        }
    }
}

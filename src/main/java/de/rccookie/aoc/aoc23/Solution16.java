package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

import static de.rccookie.aoc.aoc23.Utils.*;

public class Solution16 extends Solution {

    private static final byte RIGHT = 0;
    private static final byte DOWN = 1;
    private static final byte LEFT = 2;
    private static final byte UP = 3;

    private static final constInt2[] DIRS = { int2.X, int2.Y, int2.X.negated(), int2.Y.negated() };
    private static final char[] SPLITTER_FOR_DIR = { '|', '-', '|', '-' };
    private final byte[][] NEXT_DIRS = new byte[128][];
    {
        NEXT_DIRS['|'] = new byte[] {    UP,  DOWN,    UP,    UP };
        NEXT_DIRS['-'] = new byte[] { RIGHT, RIGHT,  LEFT, RIGHT };
        NEXT_DIRS['.'] = new byte[] { RIGHT,  DOWN,  LEFT,    UP };
        NEXT_DIRS['/'] = new byte[] {    UP,  LEFT,  DOWN, RIGHT };
        NEXT_DIRS['\\']= new byte[] {  DOWN, RIGHT,    UP,  LEFT };
    }

    @Override
    public Object task1() {
        return findEnergized(int2.zero, RIGHT, charTable[0].length, charTable.length);
    }

    @Override
    public Object task2() {
        int width = charTable[0].length;
        int height = charTable.length;

        int max = 0;
        for(int i=0; i<charTable.length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(new int2(0, i), RIGHT, width, height),
                    findEnergized(new int2(charTable.length-1, i), LEFT, width, height)
            );
        }
        for(int i=0; i<charTable[0].length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(new int2(i, 0), DOWN, width, height),
                    findEnergized(new int2(i, charTable[0].length-1), UP, width, height)
            );
        }
        return max;
    }

    private int findEnergized(constInt2 start, int dir, int width, int height) {
        long[] energized = new long[(width * height * 4 + 63) >> 6];
        followLight(start, dir, energized, width, height);
        int count = 0;
        for(long l : energized)
            for(int i=0; i<64; i+=4)
                if((l >> i & 0xF) != 0)
                    count++;
        return count;
    }

    private void followLight(constInt2 start, int dir, long[] energized, int width, int height) {
        int2 pos = start.clone();
        while(pos.geq(constInt2.zero) && pos.x() < width && pos.y() < height && !gbi(energized, pos.x(), pos.y(), width, 4, dir)) {
            sbi(energized, pos.x(), pos.y(), width, 4, dir);

            char c = charTable[pos.y()][pos.x()];
            if(c == SPLITTER_FOR_DIR[dir]) {
                dir = NEXT_DIRS[c][dir];
                followLight(pos, (dir+2) & 3, energized, width, height);
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }
    }
}

package de.rccookie.aoc.aoc23.clean;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

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
        return findEnergized(int2.zero, RIGHT);
    }

    @Override
    public Object task2() {
        int max = 0;
        for(int i=0; i<charTable.length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(new int2(0, i), RIGHT),
                    findEnergized(new int2(charTable.length-1, i), LEFT)
            );
        }
        for(int i=0; i<charTable[0].length; i++) {
            max = Mathf.max(
                    max,
                    findEnergized(new int2(i, 0), DOWN),
                    findEnergized(new int2(i, charTable[0].length-1), UP)
            );
        }
        return max;
    }

    private int findEnergized(constInt2 start, int dir) {
        boolean[][][] energized = new boolean[charTable.length][charTable[0].length][4];
        followLight(start, dir, energized);
        int count = 0;
        for(int i=0; i<energized.length; i++) {
            for(int j=0; j<energized[i].length; j++) {
                for(int k=0; k<energized[i][j].length; k++) {
                    if(energized[i][j][k]) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

    private void followLight(constInt2 start, int dir, boolean[][][] energized) {
        int2 pos = start.clone();
        while(pos.geq(constInt2.zero) && pos.y() < energized.length && pos.x() < energized[0].length && !energized[pos.y()][pos.x()][dir]) {
            energized[pos.y()][pos.x()][dir] = true;

            char c = charTable[pos.y()][pos.x()];
            if(c == SPLITTER_FOR_DIR[dir]) {
                dir = NEXT_DIRS[c][dir];
                followLight(pos, (dir+2) & 3, energized);
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }
    }
}

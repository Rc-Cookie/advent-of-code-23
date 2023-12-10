package de.rccookie.aoc.aoc23.clean;

import java.util.List;
import java.util.Map;

import de.rccookie.aoc.Solution;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

public class Solution10 extends Solution {

    private static final constInt2 UP = int2.Y.negated();
    private static final constInt2 DOWN = int2.Y;
    private static final constInt2 LEFT = int2.X.negated();
    private static final constInt2 RIGHT = int2.X;

    private static final Map<Character, Map<constInt2, constInt2>> nextDirs = Map.of(
        '|', Map.of(UP, UP, DOWN, DOWN),
        '-', Map.of(LEFT, LEFT, RIGHT, RIGHT),
        'L', Map.of(DOWN, RIGHT, LEFT, UP),
        'J', Map.of(RIGHT, UP, DOWN, LEFT),
        '7', Map.of(RIGHT, DOWN, UP, LEFT),
        'F', Map.of(UP, RIGHT, LEFT, DOWN),
        '.', Map.of()
    );

    @Override
    public Object task1() {
        char[][] field = charTable('.');
        int2 sPos = getSPos(field);

        for(constInt2 dir : List.of(UP, DOWN, LEFT)) {
            int length = followPipeToS(field, sPos, dir);
            if(length != -1)
                return length / 2;
        }
        return null;
    }

    private static int2 getSPos(char[][] field) {
        int2 sPos = null;
        for(int i=1; i<field.length-1; i++) for(int j=1; j<field[i].length-1; j++) {
            if(field[i][j] == 'S') {
                sPos = new int2(j,i);
                break;
            }
        }
        return sPos;
    }

    private static int followPipeToS(char[][] field, int2 pos, constInt2 dir) {
        pos = pos.clone();
        int length = 0;
        do {
            pos.add(dir);
            length++;
            char c = field[pos.y()][pos.x()];
            if(c == 'S') return length;
            dir = nextDirs.get(c).get(dir);
        } while(dir != null);
        return -1;
    }

    @Override
    public Object task2() {
        char[][] field = charTable('.');
        int2 sPos = getSPos(field);

        byte[][] ioFlipMask = null;
        for(constInt2 dir : List.of(UP, DOWN, LEFT)) {
            char pipe = getSPipe(field, sPos, dir);
            if(pipe != 0) {
                field[sPos.y()][sPos.x()] = pipe;
                ioFlipMask = getIOFlipMask(field, sPos, dir);
                break;
            }
        }
        assert ioFlipMask != null;

        int insideCount = 0;
        for(int i=0; i<ioFlipMask.length; i++) {
            byte inside = (byte) -1;
            for(int j=0; j<ioFlipMask[i].length-1; j++) {
                if(ioFlipMask[i][j] != 0)
                    inside *= ioFlipMask[i][j];
                else if(inside == 1)
                    insideCount++;
            }
        }
        return insideCount;
    }

    private static char getSPipe(char[][] field, constInt2 start, constInt2 dir) {
        int2 pos = start.clone();
        constInt2 startingDir = dir;
        do {
            pos.add(dir);
            if(pos.equals(start)) {
                for(char pipe : nextDirs.keySet()) {
                    constInt2 out = nextDirs.get(pipe).get(dir);
                    if(out != null && out.equals(startingDir))
                        return pipe;
                }
                throw new AssertionError();
            }
            dir = nextDirs.get(field[pos.y()][pos.x()]).get(dir);
        } while(dir != null);
        return 0;
    }

    private static byte[][] getIOFlipMask(char[][] field, constInt2 start, constInt2 dir) {
        int2 pos = start.clone();
        byte[][] mask = new byte[field.length - 2][field[0].length - 2];

        while(true) {
            char c = field[pos.y()][pos.x()];
            mask[pos.y() - 1][pos.x() - 1] = c != '-' && c != 'L' && c != 'J' ? (byte) -1 : (byte) 1;

            pos.add(dir);
            if(pos.equals(start))
                return mask;

            dir = nextDirs.get(field[pos.y()][pos.x()]).get(dir);
        }
    }
}

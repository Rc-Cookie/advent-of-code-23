package de.rccookie.aoc.aoc23;

public class Solution10 extends FastSolution {

    private static final byte LEFT = 0;
    private static final byte UP = 1;
    private static final byte DOWN = 2;
    private static final byte RIGHT = 3;

    private final byte[][] nextDirs = new byte[128][];
    {
        nextDirs['|'] = new byte[] { -1,   UP,    DOWN,  -1    };
        nextDirs['-'] = new byte[] { LEFT, -1,    -1,    RIGHT };
        nextDirs['L'] = new byte[] { UP,   -1,    RIGHT, -1    };
        nextDirs['J'] = new byte[] { -1,   -1,    LEFT,  UP    };
        nextDirs['7'] = new byte[] { -1,   LEFT,  -1,    DOWN  };
        nextDirs['F'] = new byte[] { DOWN, RIGHT, -1,    -1    };
        nextDirs['.'] = new byte[] { -1,   -1,    -1,    -1    };
    }

    private final int[] dirs = { -1, 0, 0, 1 };
    private int lineLenWithNewline;
    private char SPipe;
    private int S;
    private byte startDir;

    @Override
    public Object task1() {
        setup();
        int pos = S;
        int dir = startDir;
        int length = 0;
        while(true) {
            pos += dirs[dir];
            length++;
            char c = input.charAt(pos);
            if(c == 'S') return length / 2;
            dir = nextDirs[c][dir];
        }
    }

    private void setup() {
        lineLenWithNewline = input.indexOf('\n') + 1;
        dirs[UP] = -lineLenWithNewline;
        dirs[DOWN] = lineLenWithNewline;

        S = input.indexOf('S');
        SPipe = getSPipe();
        nextDirs['S'] = nextDirs[SPipe];

        startDir = 0;
        while(nextDirs['S'][startDir] == -1)
            startDir++;
        startDir = (byte) (3 - startDir);
    }

    private char getSPipe() {
        int x = S % lineLenWithNewline;
        boolean left = x > 0 && nextDirs[input.charAt(S-1)][LEFT] != -1;
        boolean right = x < lineLenWithNewline - 2 && nextDirs[input.charAt(S+1)][RIGHT] != -1;
        boolean up = S >= lineLenWithNewline && nextDirs[input.charAt(S-lineLenWithNewline)][UP] != -1;
        if(left) {
            if(right) return '-';
            if(up) return 'J';
            return '7';
        }
        if(right)
            return up ? 'L' : 'F';
        return '|';
    }

    @Override
    public Object task2() {
        setup();

        byte[] ioFlipMask = getIOFlipMask(S, startDir);

        int insideCount = 0;
        byte inside = (byte) -1;
        for(int i=0; i<ioFlipMask.length; i++) {
            if(ioFlipMask[i] != 0)
                inside *= ioFlipMask[i];
            else if(inside == 1)
                insideCount++;
        }
        return insideCount;
    }

    private byte[] getIOFlipMask(int start, byte dir) {
        int pos = start;
        byte[] mask = new byte[input.length()];

        char disallowed = SPipe != '-' && SPipe != 'L' && SPipe != 'J' ? 0 : 'S';

        char c = input.charAt(pos);
        while(true) {
            mask[pos] = c != '-' && c != 'L' && c != 'J' && c != disallowed ? (byte) -1 : (byte) 1;
            pos += dirs[dir];

            if(pos == start)
                return mask;

            c = input.charAt(pos);
            dir = nextDirs[c][dir];
        }
    }
}

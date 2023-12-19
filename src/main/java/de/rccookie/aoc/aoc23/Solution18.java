package de.rccookie.aoc.aoc23;

public class Solution18 extends FastSolution {

    private static final int[] DIRS_X = new int[128];
    private static final int[] DIRS_Y = new int[128];
    static {
        DIRS_X['0'] = DIRS_X['R'] = 1;
        DIRS_Y['1'] = DIRS_Y['D'] = 1;
        DIRS_X['2'] = DIRS_X['L'] = -1;
        DIRS_Y['3'] = DIRS_Y['U'] = -1;
    }

    @Override
    public Object task1() {
        int area = 0;
        int perimeter = 0;
        int lastX = 0;
        int lastY = 0;

        int pos = 0;
        do {
            char c = chars[pos];
            pos += 2;
            int len = CHAR_VALUE[chars[pos]];
            if(chars[pos+1] != ' ')
                len = 10 * len + CHAR_VALUE[chars[++pos]];
            perimeter += len;

            int x = lastX + DIRS_X[c] * len;
            int y = lastY + DIRS_Y[c] * len;
            area += lastX * y - lastY * x;
            lastX = x;
            lastY = y;

            pos += 12;
        } while(pos < chars.length);

        return ((area + perimeter) >> 1) + 1;
    }

    @Override
    public Object task2() {
        long area = 0;
        int perimeter = 0;
        int lastX = 0;
        int lastY = 0;

        int pos = 0;
        do {
            pos += 6;
            if(chars[pos] == '#') pos++;
            int len = CHAR_VALUE[chars[pos]] << 16 | CHAR_VALUE[chars[pos+1]] << 12 | CHAR_VALUE[chars[pos+2]] << 8 | CHAR_VALUE[chars[pos+3]] << 4 | CHAR_VALUE[chars[pos+4]];
            perimeter += len;

            int x = lastX + DIRS_X[chars[pos+5]] * len;
            int y = lastY + DIRS_Y[chars[pos+5]] * len;
            area += (long) lastX * y - (long) lastY * x;
            lastX = x;
            lastY = y;

            pos += 8;
        } while(pos != chars.length);

        return ((area + perimeter) >> 1) + 1;
    }
}

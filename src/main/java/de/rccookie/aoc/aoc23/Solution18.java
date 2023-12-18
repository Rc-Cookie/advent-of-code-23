package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

public class Solution18 extends Solution {

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

        for(int i=0; i<linesArr.length; i++) {
            int len = Integer.parseInt(linesArr[i], 2, linesArr[i].indexOf(' ', 3), 10);
            perimeter += len;
            char c = linesArr[i].charAt(0);
            int x = lastX + DIRS_X[c] * len;
            int y = lastY + DIRS_Y[c] * len;
            area += lastX * y - lastY * x;
            lastX = x;
            lastY = y;
        }
        return (Math.abs(area) + perimeter) / 2 + 1;
    }

    @Override
    public Object task2() {
        long area = 0;
        int perimeter = 0;
        int lastX = 0;
        int lastY = 0;

        for(int i=0; i<linesArr.length; i++) {
            int len = Integer.parseInt(linesArr[i], linesArr[i].indexOf('#', 5) + 1, linesArr[i].length()-2, 16);
            perimeter += len;
            char c = linesArr[i].charAt(linesArr[i].length() - 2);
            int x = lastX + DIRS_X[c] * len;
            int y = lastY + DIRS_Y[c] * len;
            area += (long) lastX * y - (long) lastY * x;
            lastX = x;
            lastY = y;
        }
        return (Math.abs(area) + perimeter) / 2 + 1;
    }
}

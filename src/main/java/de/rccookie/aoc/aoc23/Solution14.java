package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution14 extends FastSolution {

    @Override
    public Object task1() {
        int[] nextFree = new int[charTable[0].length];
        for(int i=0; i<charTable.length; i++) {
            for(int j=0; j<charTable[i].length; j++) {
                if(charTable[i][j] == '#')
                    nextFree[j] = i + 1;
                else if(charTable[i][j] == 'O') {
                    charTable[i][j] = '.';
                    charTable[nextFree[j]++][j] = 'O';
                }
            }
        }
        int load = 0;
        for(int i=0; i<charTable.length; i++)
            for(int j=0; j<charTable[i].length; j++)
                if(charTable[i][j] == 'O')
                    load += charTable.length - i;
        return load;
    }

    @Override
    public Object task2() {
        int width = charTable[0].length;
        int height = charTable.length;

        long[] positions = new long[(charTable[0].length * charTable.length + 63) >> 6];
        for(int x=0; x<width; x++)
            for(int y=0; y<height; y++)
                if(charTable[y][x] == 'O')
                    sbi(positions, x, y, width);

        Map<Positions, Integer> dejavu = new HashMap<>();
        List<Positions> positionsInOrder = new ArrayList<>();

        for(int i=0; i<1000000000; i++) {
            Positions p = new Positions(positions);
            if(dejavu.putIfAbsent(p,i) != null) {
                int prev = dejavu.get(p);
                return northLoad(positionsInOrder.get(prev + (1000000000 - i) % (i - prev)).positions, width, height);
            }
            positionsInOrder.add(p);
            positions = tiltCycle(positions, width, height);
        }
        return northLoad(positions, width, height);
    }

    private long[] tiltCycle(long[] positions, int width, int height) {
        long[] newPositions = tiltNorth(positions, width, height);
        tiltWest(newPositions, width, height);
        tiltSouth(newPositions, width, height);
        tiltEast(newPositions, width, height);
        return newPositions;
    }

    private void tiltNorthInline(long[] positions, int width, int height) {
        for(int x=0; x<width; x++) {
            int nextFree = 0;
            for(int y=0; y<height; y++) {
                if(gbi(positions, x, y, width)) {
                    cbi(positions, x, y, width);
                    sbi(positions, x, nextFree++, width);
                }
                else if(charTable[y][x] == '#')
                    nextFree = y + 1;
            }
        }
    }

    private long[] tiltNorth(long[] positions, int width, int height) {
        long[] newPositions = new long[positions.length];
        for(int x=0; x<width; x++) {
            int nextFree = 0;
            for(int y=0; y<height; y++) {
                if(gbi(positions, x, y, width)) {
                    cbi(newPositions, x, y, width);
                    sbi(newPositions, x, nextFree++, width);
                }
                else if(charTable[y][x] == '#')
                    nextFree = y + 1;
            }
        }
        return newPositions;
    }

    private void tiltSouth(long[] positions, int width, int height) {
        for(int x=0; x<width; x++) {
            int nextFree = height - 1;
            for(int y=nextFree; y>=0; y--) {
                if(gbi(positions, x, y, width)) {
                    cbi(positions, x, y, width);
                    sbi(positions, x, nextFree--, width);
                }
                else if(charTable[y][x] == '#')
                    nextFree = y - 1;
            }
        }
    }

    private void tiltWest(long[] positions, int width, int height) {
        for(int y=0; y<height; y++) {
            int nextFree = 0;
            for(int x=0; x<width; x++) {
                if(gbi(positions, x, y, width)) {
                    cbi(positions, x, y, width);
                    sbi(positions, nextFree++, y, width);
                }
                else if(charTable[y][x] == '#')
                    nextFree = x + 1;
            }
        }
    }

    private void tiltEast(long[] positions, int width, int height) {
        for(int y=0; y<height; y++) {
            int nextFree = width - 1;
            for(int x=nextFree; x>=0; x--) {
                if(gbi(positions, x, y, width)) {
                    cbi(positions, x, y, width);
                    sbi(positions, nextFree--, y, width);
                }
                else if(charTable[y][x] == '#')
                    nextFree = x - 1;
            }
        }
    }

    private int northLoad(long[] positions, int width, int height) {
        int load = 0;
        for(int y=0; y<height; y++)
            for(int x=0; x<width; x++)
                if(gbi(positions, x, y, width))
                    load += height - y;
        return load;
    }

    private String toString(long[] positions, int width, int height) {
        StringBuilder str = new StringBuilder((height + 1) * width);
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                if(gbi(positions, x, y, width))
                    str.append('O');
                else if(charTable[y][x] == '#')
                    str.append('#');
                else str.append('.');
            }
            str.append('\n');
        }
        return str.deleteCharAt(str.length() - 1).toString();
    }

    record Positions(long[] positions) {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Positions p && Arrays.equals(positions, p.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(positions);
        }
    }
}

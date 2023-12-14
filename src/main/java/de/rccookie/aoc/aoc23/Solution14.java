package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rccookie.aoc.Solution;

public class Solution14 extends Solution {

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
        boolean[][] positions = new boolean[charTable.length][charTable[0].length];
        for(int i=0; i<positions.length; i++)
            for(int j=0; j<positions[i].length; j++)
                positions[i][j] = charTable[i][j] == 'O';

        Map<Positions, Integer> dejavu = new HashMap<>();
        List<Positions> positionsInOrder = new ArrayList<>();

        for(int i=0; i<1000000000; i++) {
            Positions p = new Positions(positions);
            if(dejavu.putIfAbsent(p,i) != null) {
                int prev = dejavu.get(p);
                return northLoad(positionsInOrder.get(prev + (1000000000 - i) % (i - prev)).positions);
            }
            positionsInOrder.add(p);
            positions = tiltCycle(positions);
        }
        return northLoad(positions);
    }

    private boolean[][] tiltCycle(boolean[][] positions) {
        boolean[][] newPositions = tiltNorth(positions);
        tiltWest(newPositions);
        tiltSouth(newPositions);
        tiltEast(newPositions);
        return newPositions;
    }

    private boolean[][] tiltNorth(boolean[][] positions) {
        boolean[][] newPositions = new boolean[positions.length][positions[0].length];
        for(int j=0; j<positions[0].length; j++) {
            int nextFree = 0;
            for(int i=0; i<positions.length; i++) {
                if(charTable[i][j] == '#')
                    nextFree = i + 1;
                else if(positions[i][j]) {
                    newPositions[i][j] = false;
                    newPositions[nextFree++][j] = true;
                }
            }
        }
        return newPositions;
    }

    private void tiltNorthInplace(boolean[][] positions) {
        for(int j=0; j<positions[0].length; j++) {
            int nextFree = 0;
            for(int i=0; i<positions.length; i++) {
                if(charTable[i][j] == '#')
                    nextFree = i + 1;
                else if(positions[i][j]) {
                    positions[i][j] = false;
                    positions[nextFree++][j] = true;
                }
            }
        }
    }

    private void tiltSouth(boolean[][] positions) {
        for(int j=0; j<positions[0].length; j++) {
            int nextFree = positions.length - 1;
            for(int i=nextFree; i>=0; i--) {
                if(charTable[i][j] == '#')
                    nextFree = i - 1;
                else if(positions[i][j]) {
                    positions[i][j] = false;
                    positions[nextFree--][j] = true;
                }
            }
        }
    }

    private void tiltWest(boolean[][] positions) {
        for(int i=0; i<positions.length; i++) {
            int nextFree = 0;
            for(int j=0; j<positions[i].length; j++) {
                if(charTable[i][j] == '#')
                    nextFree = j + 1;
                else if(positions[i][j]) {
                    positions[i][j] = false;
                    positions[i][nextFree++] = true;
                }
            }
        }
    }

    private void tiltEast(boolean[][] positions) {
        for(int i=0; i<positions.length; i++) {
            int nextFree = positions[i].length - 1;
            for(int j = nextFree; j >= 0; j--) {
                if(charTable[i][j] == '#')
                    nextFree = j - 1;
                else if(positions[i][j]) {
                    positions[i][j] = false;
                    positions[i][nextFree--] = true;
                }
            }
        }
    }

    private int northLoad(boolean[][] positions) {
        int load = 0;
        for(int i=0; i<positions.length; i++)
            for(int j=0; j<positions[i].length; j++)
                if(positions[i][j])
                    load += positions.length - i;
        return load;
    }

    private void print(boolean[][] positions) {
        for(int i=0; i<positions.length; i++) {
            for(int j=0; j<positions[i].length; j++) {
                if(charTable[i][j] == '#')
                    System.out.print('#');
                else if(positions[i][j])
                    System.out.print('O');
                else System.out.print('.');
            }
            System.out.println();
        }
    }

    record Positions(boolean[][] positions) {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Positions p && Arrays.deepEquals(positions, p.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(positions);
        }
    }
}

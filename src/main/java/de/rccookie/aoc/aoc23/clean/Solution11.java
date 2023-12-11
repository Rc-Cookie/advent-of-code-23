package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;

import de.rccookie.aoc.Solution;
import de.rccookie.math.int2;

public class Solution11 extends Solution {

    @Override
    public Object task1() {
        return calcDistances(2);
    }

    @Override
    public Object task2() {
        return calcDistances(1000000);
    }

    private long calcDistances(int expansion) {
        int[] rowSizes = new int[charTable.length];
        int[] columnSizes = new int[charTable[0].length];
        Arrays.fill(rowSizes, expansion);
        Arrays.fill(columnSizes, expansion);
        int galaxyCount = 0;

        for(int i=0; i<charTable.length; i++) for(int j=0; j<charTable[i].length; j++) {
            if(charTable[i][j] == '#') {
                rowSizes[i] = columnSizes[j] = 1;
                galaxyCount++;
            }
        }

        int2[] galaxies = new int2[galaxyCount];
        galaxyCount = 0;

        int2 cur = int2.zero();
        for(int i=0; i<charTable.length; cur.set(0, cur.y() + rowSizes[i]), i++)
            for(int j=0; j<charTable[i].length; cur.add(columnSizes[j++], 0))
                if(charTable[i][j] == '#')
                    galaxies[galaxyCount++] = cur.clone();

        long sum = 0;
        for(int i=0; i<galaxyCount; i++) for(int j=i+1; j<galaxyCount; j++)
            sum += Math.abs(galaxies[j].x() - galaxies[i].x()) + galaxies[j].y() - galaxies[i].y();

        return sum;
    }
}

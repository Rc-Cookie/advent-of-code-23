package de.rccookie.aoc.aoc23;

import java.util.Arrays;

public class Solution11 extends FastSolution {

    @Override
    public Object task1() {
        return calcDistances(2);
    }

    @Override
    public Object task2() {
        return calcDistances(1000000);
    }

    private long calcDistances(int expansion) {
        // Using the char table 2D array is actually ~15% faster but would be unfair preprocessing
        int len = chars.length;
        int width = 0;
        while(chars[width] != '\n') width++;
        int widthWithNewline = width + 1;
        int height = len / widthWithNewline;

        int[] rowSizes = new int[height];
        int[] columnSizes = new int[width];
        Arrays.fill(rowSizes, expansion);
        Arrays.fill(columnSizes, expansion);
        int galaxyCount = 0;

        for(int i=0; i<height; i++) for(int j=0; j<width; j++) {
            if(chars[i * widthWithNewline + j] == '#') {
                rowSizes[i] = columnSizes[j] = 1;
                galaxyCount++;
            }
        }

        // Using one long array instead of two int arrays is ~25% faster
        long[] galaxies = new long[galaxyCount];
        galaxyCount = 0;

        for(int i=0, y=0; i<height; y += rowSizes[i++])
            for(int j=0, x=0; j<width; x += columnSizes[j++])
                if(chars[i * widthWithNewline + j] == '#')
                    galaxies[galaxyCount++] = (long) y << 32 | x;

        long sum = 0;
        for(int i=0; i<galaxyCount; i++) for(int j=i+1; j<galaxyCount; j++)
            sum += Math.abs((galaxies[j] & 0xFFFFFFFFL) - (galaxies[i] & 0xFFFFFFFFL)) + (galaxies[j] >> 32) - (galaxies[i] >> 32);

        return sum;
    }
}

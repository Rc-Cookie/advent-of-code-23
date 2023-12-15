package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

public class Solution15 extends Solution {

    @Override
    public Object task1() {
        int sum = 0;
        int pos = 0;
        while(true) {
            int end = input.indexOf(',', pos);
            if(end == -1)
                return sum + hash(pos, input.length() - 1);
            sum += hash(pos, end);
            pos = end + 1;
        }
    }

    @Override
    public Object task2() {

        long[][] boxes = new long[256][8];

        int pos = 0;
        boolean last = false;
        while(!last) {
            int end = input.indexOf(',', pos);
            last = end == -1;
            if(last)
                end = input.length() - 1;

            if(input.charAt(end-1) == '-') {
                int hash = hash(pos, end-1);
                for(int i = 0; i< boxes[hash].length && boxes[hash][i] != 0; i++) {
                    if(strEquals(pos, end - 1 - pos, boxes[hash][i])) {
                        for(; i< boxes[hash].length - 1 && boxes[hash][i] != 0; i++)
                            boxes[hash][i] = boxes[hash][i+1];
                        boxes[hash][i] = 0;
                    }
                }
            }
            else {
                int index = input.indexOf('=', pos+1);
                long val = pos | (long) (index - pos) << 16 | (long) Integer.parseInt(input, index+1, end, 10) << 32;
                int hash = hash(pos, index);
                int i = 0;
                while(boxes[hash][i] != 0) {
                    if(strEquals(pos, index - pos, boxes[hash][i])) {
                        boxes[hash][i] = val;
                        break;
                    }
                    i++;
                }
                if(boxes[hash][i] == 0)
                    boxes[hash][i] = val;
            }
            pos = end + 1;
        }

        long sum = 0;
        for(int i=0; i<boxes.length; i++)
            for(int j=0; j<boxes[i].length && boxes[i][j] != 0; j++)
                sum += (i+1L) * (j+1L) * (boxes[i][j] >>> 32);
        return sum;
    }

    private boolean strEquals(int low, int len, long x) {
        if(len != ((int) x >> 16 & 0xFFFF))
            return false;
        int lowX = (int) x & 0xFFFF;
        for(int i=0; i<len; i++)
            if(input.charAt(low + i) != input.charAt(lowX + i))
                return false;
        return true;
    }

    private int hash(int start, int end) {
        int hash = 0;
        for(int i=start; i<end; i++)
            hash = ((hash + input.charAt(i)) * 17) & 0xFF;
        return hash;
    }
}

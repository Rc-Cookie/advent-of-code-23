package de.rccookie.aoc.aoc23;

public class Solution3 extends FastSolution {

    @Override
    public Object task1() {
        int sum = 0;
        for(int i=0; i<charTable.length; i++) {
            int num = 0;
            boolean last = false;
            for(int j=0; j<charTable[i].length; j++) {
                char c = charTable[i][j];
                if(c >= '0' && c <= '9') {
                    num = 10 * num + (c - '0');
                    if(last || (i != 0 && charTable[i-1][j] != '.') || (i != charTable.length-1 && charTable[i+1][j] != '.')) {
                        while(++j < charTable[i].length && (c = charTable[i][j]) >= '0' && c <= '9')
                            num = 10 * num + (c - '0');
                        sum += num;
                        num = 0;
                    }
                    last = false;
                }
                else {
                    last = (c != '.' || (i != 0 && charTable[i-1][j] != '.') || (i != charTable.length-1 && charTable[i+1][j] != '.'));
                    if(last)
                        sum += num;
                    num = 0;
                }
            }
        }
        return sum;
    }

    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public Object task2() {
        // Add padding around table to prevent the need of bounds checks -> >10% faster
        short[][] numLookup = new short[charTable.length+2][charTable[0].length+2];
        for(int i=0; i<charTable.length; i++) {
            int num = 0;
            int numCount = 0;
            for(int j=0; j<charTable[i].length; j++) {
                char c = charTable[i][j];
                if(c >= '0' && c <= '9') {
                    num = 10 * num + (c - '0');
                    numCount++;
                }
                else {
                    for(int k=0; k<numCount; k++)
                        numLookup[i+1][j-k] = (short) num;
                    numCount = 0;
                    num = 0;
                }
            }
            for(int k=0; k<numCount; k++)
                numLookup[i+1][charTable[i].length - k] = (short) num;
        }

        int sum = 0;
        for(int i=0; i<charTable.length; i++) {
            gearLoop: for(int j=0; j<charTable[i].length; j++) {
                if(charTable[i][j] != '*') continue;
                short a = 0, b = 0;
                for(int k=0; k<3; k++) for(int l=0; l<3; l++) {
                    short x = numLookup[i+k][j+l];
                    if(x == 0 || a == x) continue;
                    if(a == 0) a = x;
                    else if(b == 0) b = x;
                    else if(b != x) continue gearLoop;
                }
                sum += a * b;
            }
        }
        return sum;
    }
}

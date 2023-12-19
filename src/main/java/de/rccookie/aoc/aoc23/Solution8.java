package de.rccookie.aoc.aoc23;

@SuppressWarnings("DuplicatedCode")
public class Solution8 extends FastSolution {

    @Override
    public Object task1() {
        int[] lookup = new int[26426];

        int pos = input.indexOf('\n');
        byte[] dirs = new byte[pos];
        for(int i=0; i<dirs.length; i++)
            dirs[i] = (byte) (16 - (input.charAt(i) - 'L') / 3 * 8);

        pos += 2;
        int len = input.length();
        while(pos < len) {
            short index = (short) index(input, pos);
            lookup[index] = index(input, pos + 7) << 16 | index(input, pos + 12);
            pos += 17;
        }

        pos = 0;
        int count = 0;
        do {
            pos = (lookup[pos] >> dirs[count % dirs.length]) & 0xFFFF;
            count++;
        } while(pos != 26425);
        return count;
    }

    @Override
    public Object task2() {

        int nodeCount = 0;
        short[] nodes = new short[800];
        int[] lookup = new int[26426];

        int pos = input.indexOf('\n');
        byte[] dirs = new byte[pos];
        for(int i=0; i<dirs.length; i++)
            dirs[i] = (byte) (16 - (input.charAt(i) - 'L') / 3 * 8);

        pos += 2;
        int len = input.length();
        while(pos < len) {
            short index = (short) index(input, pos);
            lookup[index] = index(input, pos + 7) << 16 | index(input, pos + 12);
            nodes[nodeCount++] = index;
            pos += 17;
        }

        long prod = dirs.length;
        for(int i=0; i<nodeCount; i++) {
            pos = nodes[i];
            if((pos & 31) != 0) continue;
            int count = 0;
            do {
                pos = (lookup[pos] >> dirs[count % dirs.length]) & 0xFFFF;
                count++;
            } while((pos & 31) != 25);
            prod = prod / dirs.length * count;
        }
        return prod;
    }

    static int index(String str, int start) {
        return ((str.charAt(start) - 'A') << 10) + ((str.charAt(start+1) - 'A') << 5) + (str.charAt(start+2) - 'A');
    }
}

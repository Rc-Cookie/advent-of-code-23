package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

public class Solution19 extends Solution {

    private static final int[] FIELD_INDEX = new int[26];
    static {
        FIELD_INDEX['x'-'a'] = 0;
        FIELD_INDEX['m'-'a'] = 1;
        //noinspection PointlessArithmeticExpression
        FIELD_INDEX['a'-'a'] = 2;
        FIELD_INDEX['s'-'a'] = 3;
    }

    @Override
    public Object task1() {
        long[][] states = new long[26*32*32 + 2][];
        int i = 0;
        for(; i<linesArr.length; i++) {
            if(linesArr[i].isBlank()) break;
            int state = index(linesArr[i], 0, linesArr[i].indexOf('{'));
            String[] parts = linesArr[i].substring(linesArr[i].indexOf('{')+1, linesArr[i].length() - 1).split(",");
            states[state] = new long[parts.length];
            for(int j = 0; j < parts.length - 1; j++)
                states[state][j] = encodeInstr(parts[j]);
            states[state][parts.length - 1] = index(parts[parts.length - 1], 0, parts[parts.length - 1].length());
        }

        i++;
        long[] parts = new long[linesArr.length - i];
        for(int j=0; j<parts.length; j++)
            parts[j] = encodePart(linesArr[i+j]);

        int sum = 0;
        for(long part : parts) {
            int state = index("in", 0, 2);
            do {
                for(i=0; i<states[state].length; i++) {
                    long instr = states[state][i];
                    if(i == states[state].length - 1) {
                        state = (int) instr;
                        break;
                    }
                    else if(lessThan(instr)) {
                        if((part >> 16*field(instr) & 0xFFFF) < threshold(instr)) {
                            state = next(instr);
                            break;
                        }
                    }
                    else if((part >> 16*field(instr) & 0xFFFF) > threshold(instr)) {
                        state = next(instr);
                        break;
                    }
                }
            } while(state < states.length - 2);
            if(state == states.length - 2)
                sum += (int) ((part & 0xFFFF) + (part >> 16 & 0xFFFF) + (part >> 32 & 0xFFFF) + (part >> 48 & 0xFFFF));
        }
        return sum;
    }

    private static int index(String str, int start, int end) {
        if(end == start + 1) {
            char c = str.charAt(start);
            if(c == 'A') return 26*32*32;
            if(c == 'R') return 26*32*32 + 1;
            return c << 10 | 26 << 5 | 26;
        }
        if(end == start + 2)
            return (str.charAt(start) - 'a') << 10 | (str.charAt(start+1) - 'a') << 5 | 26;
        return (str.charAt(start) - 'a') << 10 | (str.charAt(start+1) - 'a') << 5 | (str.charAt(start+2) - 'a');
    }

    private static long encodeInstr(String instr) {
        int colon = instr.indexOf(':');
        return (long) index(instr, colon+1, instr.length()) << 33 | (long) (instr.charAt(1)-'<') << 31 | Long.parseLong(instr, 2, colon, 10) << 2 | FIELD_INDEX[instr.charAt(0)-'a'];
    }

    private static long encodePart(String part) {
        int comma1 = part.indexOf(','), comma2 = part.indexOf(',', comma1+3), comma3 = part.indexOf(',', comma2+3);
        return Long.parseLong(part, 3, comma1, 10) |
               Long.parseLong(part, comma1+3, comma2, 10) << 16 |
               Long.parseLong(part, comma2+3, comma3, 10) << 32 |
               Long.parseLong(part, comma3+3, part.length()-1, 10) << 48;
    }

    private static int field(long instr) {
        return (int) instr & 3;
    }

    private static int threshold(long instr) {
        return (int) instr >> 2;
    }

    private static boolean lessThan(long instr) {
        return (instr >> 32 & 1) == 0;
    }

    private static int next(long instr) {
        return (int) (instr >> 33);
    }
}

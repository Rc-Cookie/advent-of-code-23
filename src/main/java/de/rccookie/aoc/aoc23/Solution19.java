package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;
import de.rccookie.util.IntWrapper;

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
        IntWrapper count = new IntWrapper();
        long[][] states = parseStates(count);

        long[] parts = new long[linesArr.length - count.value - 1];
        for(int j=0; j<parts.length; j++)
            parts[j] = encodePart(linesArr[count.value + 1 + j], 0, linesArr[count.value + 1 + j].length());

        int start = index("in", 0, 2);

        int sum = 0;
        for(long part : parts) {
            int state = start;
            do {
                for(int i=0; i<states[state].length; i++) {
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
            } while(state < states.length);
            if(state == states.length)
                sum += (int) ((part & 0xFFFF) + (part >> 16 & 0xFFFF) + (part >> 32 & 0xFFFF) + (part >> 48 & 0xFFFF));
        }
        return sum;
    }

    @Override
    public Object task2() {
        return getSum(parseStates(null), index("in", 0, 2), 1, 4000, 1, 4000, 1, 4000, 1, 4000);
    }

    private long[][] parseStates(IntWrapper count) {
        long[][] states = new long[26*32*32][];
        int i = 0;
        for(; i<linesArr.length; i++) {
            if(linesArr[i].isBlank()) break;
            int state = index(linesArr[i], 0, linesArr[i].indexOf('{'));
            String[] parts = linesArr[i].substring(linesArr[i].indexOf('{')+1, linesArr[i].length() - 1).split(",");
            states[state] = new long[parts.length];
            for(int j = 0; j < parts.length - 1; j++)
                states[state][j] = encodeInstr(parts[j], 0, parts[j].length());
            states[state][parts.length - 1] = index(parts[parts.length - 1], 0, parts[parts.length - 1].length());
        }
        if(count != null)
            count.value = i;
        return states;
    }

    private long getSum(long[][] states, int state, int min0, int max0, int min1, int max1, int min2, int max2, int min3, int max3) {

        if(state == states.length + 1)
            return 0;
        if(state == states.length)
            return (long) (max0 - min0 + 1) * (max1 - min1 + 1) * (max2 - min2 + 1) * (max3 - min3 + 1);

        long sum = 0;
        for(int i=0; i<states[state].length - 1; i++) {
            long instr = states[state][i];

            boolean lessThan = lessThan(instr);
            switch(field(instr)) {
                case 0 -> {
                    if(lessThan) {
                        if(max0 < threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(min0 < threshold(instr)) {
                            sum += getSum(states, next(instr), min0, threshold(instr)-1, min1, max1, min2, max2, min3, max3);
                            min0 = threshold(instr);
                        }
                    }
                    else {
                        if(min0 > threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(max0 > threshold(instr)) {
                            sum += getSum(states, next(instr), threshold(instr)+1, max0, min1, max1, min2, max2, min3, max3);
                            max0 = threshold(instr);
                        }
                    }
                }
                case 1 -> {
                    if(lessThan) {
                        if(max1 < threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min0, max1, min2, max2, min3, max3);
                        if(min1 < threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, min1, threshold(instr)-1, min2, max2, min3, max3);
                            min1 = threshold(instr);
                        }
                    }
                    else {
                        if(min1 > threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(max1 > threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, threshold(instr)+1, max1, min2, max2, min3, max3);
                            max1 = threshold(instr);
                        }
                    }
                }
                case 2 -> {
                    if(lessThan) {
                        if(max2 < threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(min2 < threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, min1, max1, min2, threshold(instr)-1, min3, max3);
                            min2 = threshold(instr);
                        }
                    }
                    else {
                        if(min2 > threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(max2 > threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, min1, max1, threshold(instr)+1, max2, min3, max3);
                            max2 = threshold(instr);
                        }
                    }
                }
                case 3 -> {
                    if(lessThan) {
                        if(max3 < threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(min3 < threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, threshold(instr)-1);
                            min3 = threshold(instr);
                        }
                    }
                    else {
                        if(min3 > threshold(instr))
                            return sum + getSum(states, next(instr), min0, max0, min1, max1, min2, max2, min3, max3);
                        if(max3 > threshold(instr)) {
                            sum += getSum(states, next(instr), min0, max0, min1, max1, min2, max2, threshold(instr)+1, max3);
                            max3 = threshold(instr);
                        }
                    }
                }
            }
        }
        return sum + getSum(states, (int) states[state][states[state].length-1], min0, max0, min1, max1, min2, max2, min3, max3);
    }

    private static int index(String str, int start, int end) {
        if(end == start + 1)
            return 26*32*32 + (str.charAt(start) == 'A' ? 0 : 1);
        return (str.charAt(start) - 'a') << 10 | (str.charAt(start+1) - 'a') << 5 | (end == start + 2 ? 26 : (str.charAt(start+2) - 'a'));
    }

    private static long encodeInstr(String instr, int start, int end) {
        int colon = instr.indexOf(':', start, end);
        return (long) index(instr, colon+1, end) << 33 | (long) (instr.charAt(start+1)-'<') << 31 | Long.parseLong(instr, start + 2, colon, 10) << 2 | FIELD_INDEX[instr.charAt(start)-'a'];
    }

    private static long encodePart(String part, int start, int end) {
        int comma1 = part.indexOf(',', start, end), comma2 = part.indexOf(',', comma1+3, end), comma3 = part.indexOf(',', comma2+3, end);
        return Long.parseLong(part, start+3, comma1, 10) |
               Long.parseLong(part, comma1+3, comma2, 10) << 16 |
               Long.parseLong(part, comma2+3, comma3, 10) << 32 |
               Long.parseLong(part, comma3+3, end-1, 10) << 48;
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

    private static String state(int index) {
        if(index == 26*32*32) return "A";
        if(index == 26*32*32 + 1) return "R";
        return "" + (char) ((index >> 10 & 31) + 'a') + (char) ((index >> 5 & 31) + 'a') + ((index & 31) == 26 ? "" : "" + (char) ((index & 31) + 'a'));
    }
}

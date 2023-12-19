package de.rccookie.aoc.aoc23;

import de.rccookie.util.IntWrapper;

@SuppressWarnings("PointlessArithmeticExpression")
public class Solution19 extends FastSolution {

    private static final int[] FIELD_INDEX = new int[26];
    static {
        FIELD_INDEX['x'-'a'] = 0;
        FIELD_INDEX['m'-'a'] = 1;
        FIELD_INDEX['a'-'a'] = 2;
        FIELD_INDEX['s'-'a'] = 3;
    }

    @Override
    public Object task1() {
        IntWrapper endPos = new IntWrapper();
        long[][] states = parseStates(endPos);

        int pos = endPos.value + 1;
        long[] parts = new long[count('\n', pos, chars.length)];

        for(int j=0; j<parts.length; j++) {
            int eol = eol(pos);
            parts[j] = encodePart(pos, eol);
            pos = eol + 1;
        }

        int start = 8634;

        int sum = 0;
        for(long part : parts)
            if(simulate(part, start, states) == states.length)
                sum += (int) ((part & 0xFFFF) + (part >> 16 & 0xFFFF) + (part >> 32 & 0xFFFF) + (part >> 48 & 0xFFFF));
        return sum;
    }

    private static int simulate(long part, int start, long[][] states) {
        int state = start;
        do {
            for(int i = 0; i < states[state].length; i++) {
                long instr = states[state][i];
                if(i == states[state].length - 1) {
                    state = (int) instr;
                    break;
                }
                else if(lessThan(instr)) {
                    if((part >> 16 * field(instr) & 0xFFFF) < threshold(instr)) {
                        state = next(instr);
                        break;
                    }
                }
                else if((part >> 16 * field(instr) & 0xFFFF) > threshold(instr)) {
                    state = next(instr);
                    break;
                }
            }
        } while(state < states.length);
        return state;
    }

    @Override
    public Object task2() {
        return getSum(parseStates(null), 8634, 1, 4000, 1, 4000, 1, 4000, 1, 4000);
    }

    private long[][] parseStates(IntWrapper endPos) {
        long[][] states = new long[26*32*32][];
        int pos = 0;
        while(chars[pos] != '\n') {
            int state = index(pos, pos = indexOf('{', pos + 2));
            pos++;
            int eol = eol(pos+2);
            states[state] = new long[count(',', pos+5, eol-1) + 1];
            for(int i=0; i<states[state].length-1; i++)
                states[state][i] = encodeInstr(pos, (pos = indexOf(',', pos) + 1) - 1);
            states[state][states[state].length-1] = index(pos, eol-1);
            pos = eol + 1;
        }
        if(endPos != null)
            endPos.value = pos;
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

    private int index(int start, int end) {
        if(end == start + 1)
            return 26*32*32 + (chars[start] == 'A' ? 0 : 1);
        return (chars[start] - 'a') << 10 | (chars[start+1] - 'a') << 5 | (end == start + 2 ? 26 : (chars[start+2] - 'a'));
    }

    private long encodeInstr(int start, int end) {
        int colon = indexOf(':', start, end);
        return (long) index(colon+1, end) << 33 | (long) (chars[start+1]-'<') << 31 | parseULong(start + 2, colon) << 2 | FIELD_INDEX[chars[start]-'a'];
    }

    private long encodePart(int start, int end) {
        int comma1 = indexOf(',', start, end), comma2 = indexOf(',', comma1+3, end), comma3 = indexOf(',', comma2+3, end);
        return parseULong(start+3, comma1) |
               parseULong(comma1+3, comma2) << 16 |
               parseULong(comma2+3, comma3) << 32 |
               parseULong(comma3+3, end-1) << 48;
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

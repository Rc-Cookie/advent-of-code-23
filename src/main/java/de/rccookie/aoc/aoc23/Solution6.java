package de.rccookie.aoc.aoc23;

public class Solution6 extends FastSolution {

    @Override
    public Object task1() {
        // Parse numbers without String.split() or substring() -> >50x faster
        int posT, posD = 9, numEnd;
        long options = 1;
        while(posD != linesArr[0].length()) {
            posD = skipWhitespaces(linesArr[1], posD + 1);
            posT = skipWhitespaces(linesArr[0], posD); // Assumption: distance number is longer or as long as the time number

            numEnd = linesArr[0].indexOf(' ', posT);
            if(numEnd == -1)
                numEnd = linesArr[0].length();

            // Calculate immediately; this way we don't need to accumulate them
            // temporarily in a list whose size we don't know ahead of time
            options *= winCount(
                    Integer.parseInt(linesArr[0], posT, numEnd, 10),
                    Integer.parseInt(linesArr[1], posD, numEnd, 10)
            );
            posD = numEnd;
        }
        return options;
    }

    @Override
    public Object task2() {
        return winCount(
                (int) parseJoined(linesArr[0]),
                parseJoined(linesArr[1])
        );
    }

    private static long parseJoined(String line) {
        long x = 0;
        int pos = line.indexOf(' '), numEnd, i;
        while(pos != line.length()) {
            pos = skipWhitespaces(line, pos);
            numEnd = line.indexOf(' ', pos);
            if(numEnd == -1)
                numEnd = line.length();
            // Basically a bitshift x << (numEnd - pos), but with radix 10
            for(i=pos; i<numEnd; i++)
                x *= 10;
            x += Long.parseLong(line, pos, numEnd, 10);
            pos = numEnd;
        }
        return x;
    }

    private static int skipWhitespaces(String str, int pos) {
        while(str.charAt(pos) == ' ') pos++;
        return pos;
    }

    private static long winCount(long time, long distance) {
        // Binary search for lowest beating time
        long low = 1, high = (time + 1) / 2, i;
        while(low != high) {
            i = (low + high) / 2;
            if(i * (time - i) > distance) {
                if((i-1) * (time - (i-1)) <= distance)
                    // Function is symmetric around i = time/2, so we can calculate the number of
                    // wins from knowing the lowest beating time
                    return time - 2*i + 1;
                high = i;
            }
            else low = i+1;
        }
        return time - 2*low + 1;
    }
}

package de.rccookie.aoc.aoc23;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution1 extends FastSolution {

    @Override
    public Object task1() {
        return sum("\\d");
    }

    @Override
    public Object task2() {
        return sum("one|two|three|four|five|six|seven|eight|nine|\\d");
    }

    private long sum(String pattern) {
        Pattern first = Pattern.compile(pattern);
        Pattern last = Pattern.compile(".*("+pattern+")");
        return sum(line -> {
            Matcher a = first.matcher(line), b = last.matcher(line);
            if(!a.find() || !b.find())
                throw new AssertionError();
            return parse(a.group()) * 10L + parse(b.group(1));
        });
    }

    private static int parse(String str) {
        return switch(str.toLowerCase()) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> Integer.parseInt(str);
        };
    }
}

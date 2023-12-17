package de.rccookie.aoc.aoc23;

import java.util.Arrays;

import com.diogonunes.jcolor.Attribute;
import de.rccookie.aoc.Solution;
import de.rccookie.graph.DistancePath;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.graph.HashGraph;
import de.rccookie.math.Mathf;
import de.rccookie.math.int3;
import de.rccookie.util.Console;

public class Solution17 extends Solution {

    @Override
    public Object task1() {
        return findPath(1, 3);
    }

    @Override
    public Object task2() {
        return findPath(4, 10);
    }

    private int findPath(int minMove, int maxMove) {

        Graph<Integer, Integer> graph = new HashGraph<>();

        for(int i=0; i<charTable.length; i++) {
            for(int j=0; j<charTable[i].length; j++) {

                int node = compress(j,i,0);
                int dist = 0;
                for(int k=1; k<=maxMove && k<=j; k++) {
                    dist += charTable[i][j-k] - '0';
                    if(k >= minMove)
                        graph.connect(node, compress(j-k,i,1), dist);
                }
                dist = 0;
                for(int k=1; k<=maxMove && j+k<charTable[0].length; k++) {
                    dist += charTable[i][j+k] - '0';
                    if(k >= minMove)
                        graph.connect(node, compress(j+k,i,1), dist);
                }

                node ^= 1;
                dist = 0;
                for(int k=1; k<=maxMove && k<=i; k++) {
                    dist += charTable[i-k][j] - '0';
                    if(k >= minMove)
                        graph.connect(node, compress(j,i-k,0), dist);
                }
                dist = 0;
                for(int k=1; k<=maxMove && i+k<charTable.length; k++) {
                    dist += charTable[i+k][j] - '0';
                    if(k >= minMove)
                        graph.connect(node, compress(j,i+k,0), dist);
                }
            }
        }

        int start = 0;
        int end = compress(charTable[0].length - 1, charTable.length - 1, 0);
        graph.connectToAll(start, graph.adj(start|1).keySet(), (a,b) -> graph.edge(start|1, b));
        graph.remove(start|1);
        graph.set(end|1, end);

        DistancePath<Integer, Integer> path = Graphs.shortestPath(
                graph,
                start,
                end,
                Integer::doubleValue,
                Solution17::dist
        );
//        showPath(path);
        return Mathf.round(path.distance());
    }

    private static int compress(int x, int y, int state) {
        return x << 9 | y << 1 | state;
    }

    private static int dist(int a, int b) {
        return Math.abs((b >> 9) - (a >> 9)) + Math.abs((b >> 1 & 0xFF) - (a >> 1 & 0xFF));
    }

    private static int3 decompress(int data) {
        return new int3(data >> 9 & 0xFF, data >> 1 & 0xFF, data & 1);
    }

    private void showPath(DistancePath<Integer, Integer> path) {
        String[][] output = new String[charTable.length][charTable[0].length];
        for(int i=0; i<output.length; i++) {
            int _i = i;
            Arrays.setAll(output[i], j -> charTable[_i][j] + "");
        }

        path.forEach((aa, bb, d) -> {
            int3 a = decompress(aa), b = decompress(bb);
            int3 diff = b.subed(a);
            for(int i=0; i<-diff.x(); i++)
                output[a.y()][a.x()-i] = Console.colored("<", Attribute.BOLD(), Attribute.RED_TEXT());
            for(int i=0; i<diff.x(); i++)
                output[a.y()][a.x()+i] = Console.colored(">", Attribute.BOLD(), Attribute.RED_TEXT());
            for(int i=0; i<-diff.y(); i++)
                output[a.y()-i][a.x()] = Console.colored("^", Attribute.BOLD(), Attribute.RED_TEXT());
            for(int i=0; i<diff.y(); i++)
                output[a.y()+i][a.x()] = Console.colored("v", Attribute.BOLD(), Attribute.RED_TEXT());
        });
        for(int i=0; i<charTable.length; i++)
            System.out.println(String.join("", output[i]));
    }
}

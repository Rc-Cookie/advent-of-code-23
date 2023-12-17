package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;

import com.diogonunes.jcolor.Attribute;
import de.rccookie.aoc.Solution;
import de.rccookie.graph.DistancePath;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.graph.HashGraph;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt3;
import de.rccookie.math.int3;
import de.rccookie.util.Console;

public class Solution17 extends Solution {

    private static final int[] DIRS_X = { 1, 0, -1, 0 };
    private static final int[] DIRS_Y = { 0, 1, 0, -1 };

    @Override
    public Object task1() {
        return findPath(1, 3);
    }

    @Override
    public Object task2() {
        return findPath(4, 10);
    }

    private int findPath(int minMove, int maxMove) {

        Graph<constInt3, Integer> graph = new HashGraph<>();

        for(int i=0; i<charTable.length; i++) {
            for(int j=0; j<charTable[i].length; j++) {
                for(int state : new int[] { 0,1 }) {
                    constInt3 node = new int3(j, i, state);
                    for(int dir : new int[] { state, state + 2 }) {
                        int dist = 0;
                        for(int k=1; k<=maxMove; k++) {
                            if(i + k * DIRS_Y[dir] < 0 || j + k * DIRS_X[dir] < 0 || i + k * DIRS_Y[dir] >= charTable.length || j + k * DIRS_X[dir] >= charTable[0].length)
                                break;
                            dist += charTable[i + k * DIRS_Y[dir]][j + k * DIRS_X[dir]] - '0';
                            if(k >= minMove)
                                graph.connect(node, new int3(j + k * DIRS_X[dir], i + k * DIRS_Y[dir], 1 - state), dist);
                        }
                    }
                }
            }
        }

        constInt3 start = new constInt3(0, 0, 0);
        constInt3 end = new constInt3(charTable[0].length - 1, charTable.length - 1, 0);
        graph.connectToAll(start, graph.adj(start.withZ(1)).keySet(), (a,b) -> graph.edge(start.withZ(1), b));
        graph.remove(start.withZ(1));
        graph.set(end.withZ(1), end);

        DistancePath<constInt3, Integer> path = Graphs.shortestPath(
                graph,
                start,
                end,
                Integer::doubleValue,
                (a,b) -> Math.abs(b.x() - a.x()) + Math.abs(b.y() - a.y())
        );
//        showPath(path);
        return Mathf.round(path.distance());
    }

    private void showPath(DistancePath<constInt3, Integer> path) {
        String[][] output = new String[charTable.length][charTable[0].length];
        for(int i=0; i<output.length; i++) {
            int _i = i;
            Arrays.setAll(output[i], j -> charTable[_i][j] + "");
        }

        path.forEach((a, b, d) -> {
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

package de.rccookie.aoc.aoc23;

import java.util.Arrays;
import java.util.Comparator;

import de.rccookie.aoc.Solution;
import de.rccookie.graph.BinaryHeap;
import de.rccookie.graph.Heap;

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

        int[][] graph = new int[1 << 17][2 * (maxMove - minMove + 1)];

        for(int i=0; i<charTable.length; i++) {
            for(int j=0; j<charTable[i].length; j++) {

                int node = transition(j,i,0,0);
                int dist = 0;
                int nextIndex = 0;
                for(int k=1; k<=maxMove && k<=j; k++) {
                    dist += charTable[i][j-k] - '0';
                    if(k >= minMove)
                        graph[node][nextIndex++] = 1 << 31 | transition(j - k, i, 1, dist);
                }
                dist = 0;
                for(int k=1; k<=maxMove && j+k<charTable[0].length; k++) {
                    dist += charTable[i][j + k] - '0';
                    if(k >= minMove)
                        graph[node][nextIndex++] = 1 << 31 | transition(j + k, i, 1, dist);
                }

                node ^= 1;
                dist = 0;
                nextIndex = 0;
                for(int k=1; k<=maxMove && k<=i; k++) {
                    dist += charTable[i-k][j] - '0';
                    if(k >= minMove)
                        graph[node][nextIndex++] = 1 << 31 | transition(j, i - k, 0, dist);
                }
                dist = 0;
                for(int k=1; k<=maxMove && i+k<charTable.length; k++) {
                    dist += charTable[i+k][j] - '0';
                    if(k >= minMove)
                        graph[node][nextIndex++] = 1 << 31 | transition(j, i + k, 0, dist);
                }
            }
        }

        int start = 0;
        int end = transition(charTable[0].length - 1, charTable.length - 1, 0, 0);
        int count = 0;
        while(graph[start][count] != 0) count++;
        System.arraycopy(graph[start|1], 0, graph[start], count, count);
        graph[start|1][0] = 0;

        return shortestPath(graph, start, end);
    }

    private static int transition(int x, int y, int state, int dist) {
        return dist << 17 | x << 9 | y << 1 | state;
    }

    private static int dist(int a, int b) {
        return Math.abs((b >> 9 & 0xFF) - (a >> 9 & 0xFF)) + Math.abs((b >> 1 & 0xFF) - (a >> 1 & 0xFF));
    }

    private static int dist(int x) {
        return x >> 17 & 0xFF;
    }

    private static int node(int x) {
        return x & 0x1FFFF;
    }

    private static int shortestPath(int[][] graph, int start, int end) {

        short[] d = new short[graph.length];
        Arrays.fill(d, Short.MAX_VALUE);
        d[start] = 0;

        Heap<Integer> q = new BinaryHeap<>(Comparator.comparingInt(n -> d[n] + dist(n, end)));
//        BinaryIntHeap q = new BinaryIntHeap();
        q.enqueue(start);

        while(!q.isEmpty()) {
            int n = q.dequeue();
            short nd = d[n];
            if(n == end || n == (end|1))
                return nd;

            for(int adj : graph[n]) {
                int dist = nd + dist(adj);
                int m = node(adj);
                if(dist < d[m]) {
                    d[m] = (short) dist;
                    if(!q.updateDecreased(m))
                        q.enqueue(m);
                }
            }
        }

        return -1;
    }
}

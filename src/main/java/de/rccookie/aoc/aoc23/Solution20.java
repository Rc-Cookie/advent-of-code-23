package de.rccookie.aoc.aoc23;

@SuppressWarnings("DuplicatedCode")
public class Solution20 extends FastSolution {

    private long[] adj, src;
    private long[] lastOutput;
    private byte[] modules;

    @Override
    public Object task1() {
        parseNetwork();

        long lowCount = 0;
        long highCount = 0;
        for(int i=0; i<1000; i++) {
            int[] signalQueue = new int[64];
            int low = 0, high = 1;
            while(low != high) {
                int signal = signalQueue[low++];
                low &= 63;
                if((signal & 1) != 0) highCount++;
                else lowCount++;
                byte output = simulateModule(signal);
                if(output != -1) {
                    sbi(lastOutput, signal >> 1, output);
                    for(int j = count(this.adj[signal >> 1]) - 1; j >= 0; j--) {
                        signalQueue[high++] = (int) (this.adj[signal >> 1] >>> 10 * j & 0x3FF) << 1 | output;
                        high &= 63;
                    }
                }
            }
        }
        return lowCount * highCount;
    }

    @Override
    public Object task2() {
        parseNetwork();

        int[] required = {
                (int) src[((int) (src[(int) src[466*2+1] * 2 + 1] >> 30) & 0x3FF) * 2 + 1],
                (int) src[((int) (src[(int) src[466*2+1] * 2 + 1] >> 20) & 0x3FF) * 2 + 1],
                (int) src[((int) (src[(int) src[466*2+1] * 2 + 1] >> 10) & 0x3FF) * 2 + 1],
                (int) src[((int) src[(int) src[466*2+1] * 2 + 1] & 0x3FF) * 2 + 1]
        };

        long lcm = 1;
        for(int i=0; i<4; i++) {
            int count = 0;
            buttonLoop: while(true) {
                count++;
                int[] signalQueue = new int[64];
                int low = 0, high = 1;
                signalQueue[0] = (int) (adj[0] >> 10*i & 0x3FF) << 1;
                while(low != high) {
                    int signal = signalQueue[low++];
                    low &= 63;
                    byte output = simulateModule(signal);
                    if(output != -1) {
                        if(output == 0 && (required[0] == signal >> 1 || required[1] == signal >> 1 || required[2] == signal >> 1 || required[3] == signal >> 1))
                            break buttonLoop;
                        sbi(lastOutput, signal >> 1, output);
                        for(int j = count(this.adj[signal >> 1]) - 1; j >= 0; j--) {
                            signalQueue[high++] = (int) (this.adj[signal >> 1] >>> 10 * j & 0x3FF) << 1 | output;
                            high &= 63;
                        }
                    }
                }
            }
            lcm *= count;
        }

        return lcm;
    }

    private void parseNetwork() {
        adj = new long[26*26+1];
        src = new long[(26*26+1) * 2];
        lastOutput = new long[(26*26+1 + 63) >> 6];
        modules = new byte[26*26+1];

        int pos = 0;
        while(pos < chars.length) {
            byte module = switch(chars[pos]) {
                case '%' -> 0;
                case '&' -> 2;
                default -> 3;
            };
            int name = index(pos + 1, pos = indexOf(' ', pos + 3));
            modules[name] = module;

            pos += 2;
            do {
                int next = index(pos + 2, pos += 4);
                adj[name] = adj[name] << 10 | next;
                src[next*2] = src[next*2] << 10 | (src[2*next + 1] >>> 30 & 0x3FF);
                src[next*2 + 1] = src[next*2 + 1] << 10 | name;
            } while(chars[pos] != '\n');
            pos++;
        }
        modules[466] = 3;
    }

    private byte simulateModule(int signal) {
        return switch(modules[signal >> 1]) {
            case 3 -> (byte) (signal & 1);
            case 2 -> {
                long src = this.src[signal|1];
                for(int j=0; src != 0; j++) {
                    if(!gbi(lastOutput, (int) (src & 0x3FF)))
                        yield 1;
                    if(j == 5)
                        src = this.src[signal & ~1];
                    else src >>>= 10;
                }
                yield 0;
            }
            default -> {
                if((signal & 1) == 1) yield -1;
                yield modules[signal >> 1] ^= 1;
            }
        };
    }

    private int index(int start, int end) {
        if(end - start != 2)
            return 0;
        return (chars[start] - 'a') * 26 + (chars[start+1] - 'a') + 1;
    }

    private static int count(long adj) {
        int count = 0;
        while(adj != 0) {
            adj >>>= 10;
            count++;
        }
        return count;
    }
}

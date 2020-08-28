package prince.utils;

public class Randomizator {
    private static Randomizator instance;
    private int seed = 0;

    private Randomizator() {

    }

    public synchronized static Randomizator getInstance() {
        if (instance == null)
            instance = new Randomizator();
        return instance;
    }

    public int getSeed() {
        return seed;
    }

    public Randomizator setSeed(int seed) {
        this.seed = seed;
        return this;
    }

    public int getRandom(int maxValue) {
        seed = seed * 214013 + 2531011;
        return ((seed >> 16) % (maxValue + 1));
    }

    public int makeRandomSequence(int seed, int n, int p, int maxValue) {
        int r0 = -1, r1 = -1;
        this.seed = seed;
        getRandom(1);
        for (int i = 0; i <= n; i++) {
            if (i % p == 0)
                r0 = -1;
            do {
                r1 = getRandom(maxValue);
            } while (r1 == r0);
            r0 = r1;
        }
        return r1;
    }
}

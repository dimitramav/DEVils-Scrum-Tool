package ys09.data;

public class Limits {

    public static final int DEFAULT_COUNT = 10;
    public static final int TOTAL_NOT_FETCHED = -1;

    private final long start;
    private final int  count;
    private long total = TOTAL_NOT_FETCHED;

    public Limits(long start) {
        this(start, DEFAULT_COUNT);
    }

    public Limits(long start, int count) {
        this.start = start;
        this.count = count;
    }

    public long getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    public long getTotal() {
        return total;
    }

    void setTotal(long total) {
        this.total = total;
    }
}

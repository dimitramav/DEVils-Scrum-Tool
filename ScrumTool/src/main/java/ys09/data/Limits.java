package ys09.data;


public class Limits {

    public static final int DEFAULT_COUNT = 2;         // Records per page
    public static final int TOTAL_NOT_FETCHED = -1;

    private final int start;
    private final int count;
    private long total = TOTAL_NOT_FETCHED;

    //public Limits(String start) {
    //    this(start, DEFAULT_COUNT);
    //}

    public Limits(int currentPage) {
        if (currentPage <= 0)
            currentPage = 1000000;
        this.start = ((currentPage-1) * DEFAULT_COUNT);
        this.count = DEFAULT_COUNT;
    }

    public int getStart() {
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

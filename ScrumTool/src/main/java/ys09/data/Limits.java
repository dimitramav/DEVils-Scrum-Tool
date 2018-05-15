package ys09.data;

import java.util.Date;


public class Limits {

    public static final int DEFAULT_COUNT = 10;
    public static final int TOTAL_NOT_FETCHED = -1;

    private final Date start;
    private final int  count;
    private long total = TOTAL_NOT_FETCHED;

    //public Limits(String start) {
    //    this(start, DEFAULT_COUNT);
    //}

    public Limits(int count, Date start) {
        this.start = start;
        this.count = count;
    }

    public Date getStart() {
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

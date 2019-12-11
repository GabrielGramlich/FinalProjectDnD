package Final;

public class Rolls {
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int total;

    Rolls(int first, int second, int third, int fourth, int total) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.total = total;
    }

    public int getFirst() { return first; }
    public int getSecond() { return second; }
    public int getThird() { return third; }
    public int getFourth() { return fourth; }
    public int getTotal() { return total; }
}

package tombenpotter.sanguimancy.util;

public class Triplet{
    int first;
    int second;
    int third;

    public Triplet(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    @Override
    public String toString() {
        return "(" + getFirst() + ", " + getSecond() + ", " + getThird() + ")";
    }
}

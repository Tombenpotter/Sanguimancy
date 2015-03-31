package tombenpotter.sanguimancy.api.objects;

public class Timer {
    private int reset;
    private int counter = 0;

    public Timer(int reset) {
        this.reset = reset;
    }

    public boolean update() {
        if (counter++ == reset) {
            counter = 0;
            return true;
        }
        return false;
    }

}

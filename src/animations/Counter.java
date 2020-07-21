package animations;

/**
 * A counter class.
 */
public class Counter {

    private int count;

    /**
     * Constructor.
     *
     * @param value number for the count
     */
    public Counter(int value) {
        this.count = value;
    }

    /**
     * add number to current count.
     *
     * @param number increase the count by this number
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number decrease the count by this number
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}
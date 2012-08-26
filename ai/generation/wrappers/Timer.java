/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

/**
 *
 * @author VOLT
 */
public class Timer {

    private long end;
    private final long start;
    private final long period;

    /**
     * Instantiates a new Timer with a given time period in milliseconds.
     *
     * @param period Time period in milliseconds.
     */
    public Timer(final long period) {
        this.period = period;
        start = System.currentTimeMillis();
        end = start + period;
    }

    /**
     * Returns the number of milliseconds elapsed since the start time.
     *
     * @return The elapsed time in milliseconds.
     */
    public long getElapsed() {
        return System.currentTimeMillis() - start;
    }

    /**
     * Returns the number of milliseconds remaining until the timer is up.
     *
     * @return The remaining time in milliseconds.
     */
    public long getRemaining() {
        if (isRunning()) {
            return end - System.currentTimeMillis();
        }
        return 0;
    }

    /**
     * Returns <tt>true</tt> if this timer's time period has not yet elapsed.
     *
     * @return <tt>true</tt> if the time period has not yet passed.
     */
    public boolean isRunning() {
        return System.currentTimeMillis() < end;
    }

    /**
     * Restarts this timer using its period.
     */
    public void reset() {
        end = System.currentTimeMillis() + period;
    }
}
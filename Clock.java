
/**
 * Clock class is responsible for keeping track of time values. Clock keeps
 * track of the global clock, and the job clock.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public class Clock
{
    // instance variables
    private int globalClock;
    private int jobClock;
    /**
     * Constructor for objects of class Clock
     */
    public Clock()
    {
        // initialise global clock to 0 
        globalClock = 0;
    }

    /**
     * Increases the global clock by one.
     */
    public void tickClock() {
        globalClock++;
    }
    
    /**
     * Returns the value of the global clock for a Clock object.
     * 
     * @return      int global clock of a Clock object
     */
    public int getTime() {
        return this.globalClock;
    }
    
    /**
     * Sets job clock of a Clock object.
     * 
     * @param      int job clock of a Clock object
     */
    public void setJobClock(int jobClock) {
        this.jobClock = jobClock; 
    }
    
    /**
     * returns the job clock of a Clock object.
     * 
     * @return      int job clock of a Clock object
     */
    public int getJobClock() {
        return jobClock;
    }
    
    /**
     * Decrements the job clock by 1.
     */
    public void decrementJobClock() {
        jobClock--;
    }
}

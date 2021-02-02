
/**
 * The Cpu class is responsible for holding current Job objects, keeping track of the time
 * remaining for the current job on the Cpu, and determines if there is a job on the Cpu.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public class Cpu
{
    // instance variables
    private Job job;
    private int cpuQuantumClock;
    private boolean busyFlag;
    /**
     * Default constructor for objects of class Cpu
     */
    public Cpu()
    {
        // default constructor
    }
    
    /**
     * Sets the Job object, quantum clock and busy flag to true.
     * 
     * @param   Job object and int value of the quantum clock 
     */
    public void addJob(Job job, int cpuQuantumClock) {
        this.job = job;
        this.cpuQuantumClock = cpuQuantumClock;
        busyFlag = true;
    }
    
    /**
     * Sets the busy flag to false.
     */
    public void resetBusyFlag() {
        busyFlag = false;
    }

    /**
     * Checks the value of the busy flag.
     * 
     * @return      boolean value of the busy flag
     */
    public boolean isBusy() {
        return busyFlag;         
    }
    
    /**
     * returns the quantum clock of the Cpu object.
     * 
     * @return      int value of the quantum clock
     */
    public int getQuantumClock() {
        return cpuQuantumClock;
    }
    
    /**
     * Decrements the quantum clock by 1.
     */
    public void decrementQuantumClock() {
        cpuQuantumClock--;
    }
    
    /**
     * returns the job object that is on the cpu.
     * 
     * @return      Job object
     */
    public Job getCpuJob() {
        return job;
    }
}


/**
 * Job class creates a blueprint for a Job object. The class constructor will create a Job
 * object given the arrival time, pid, and cpu time required. The class methods provide
 * ways to get/set Job object class variables.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public class Job
{
    // instance variables
    private int pid;
    private int arrivalTime;
    private int cpuTimeRequired;
    private int cpuTimeRemaining;
    private int currentQueue;
    private int responseTime;
    /**
     * Constructor for objects of class Job.
     */
    public Job(int arrivalTime, int pid, int cpuTimeRequired)
    {
        // initialise instance variables
        this.arrivalTime = arrivalTime;
        this.pid = pid;
        this.cpuTimeRequired = cpuTimeRequired;
        this.cpuTimeRemaining = cpuTimeRequired;
    }
    
    /**
     * Returns the arrival time of a Job.
     * 
     * @return      int arrival time of a Job object
     */
    public int getArrivalTime(){
        return arrivalTime;
    }
    
    /**
     * returns the pid of a Job object.
     * 
     * @return      int pid of a Job object
     */
    public int getPid(){
        return pid;
    }
    
    /**
     * returns the cpu time required of a Job object.
     * 
     * @return      int cpu time required of a Job object
     */
    public int getCpuTimeRequired(){
        return cpuTimeRequired;
    }
    
    /**
     * Sets the cpu time remaining for a job object.
     * 
     * @param      int time remaining for a job object to be on the cpu
     */
    public void setCpuTimeRemaining(int timeRemaining){
        this.cpuTimeRemaining = timeRemaining;
    }
    
    /**
     * returns the cpu time remaining of a Job object.
     * 
     * @return      int cpu time remaining of a Job object
     */
    public int getCpuTimeRemaining(){
        return cpuTimeRemaining;
    }
    
    /**
     * Sets the current queue of a Job object.
     * 
     * @param       int queue number for the job to be set to
     */
    public void setCurrentQueue(int QueueNumber){
        currentQueue = QueueNumber;
    }
    
    /**
     * returns the current queue of a Job object.
     * 
     * @return      int current queue of a Job object
     */
    public int getCurrentQueue(){
        return currentQueue;
    }
    
                /**
     * Sets the response time of a Cpu object.
     * 
     * @param      int response time of a Cpu object
     */
     public void setResponseTime(int responseTime) {
        this.responseTime = responseTime; 
    }
    
    /**
     * returns the response time of a Cpu object.
     * 
     * @return      int response time of a Cpu object
     */
    public int getResponseTime() {
        return responseTime;
    }
}

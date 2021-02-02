import java.io.*;
import java.util.Scanner;
/**
 * MFQ class that runs the simulation. The class runs the simulation in a loop very 
 * closely to the flowchart provided for the lab.
 * MFQ will tick the clock, submit Job objects from the input queue into queue 1,
 * check if the Cpu class's busyflag is true/false, decrement/increment Clock and Cpu class 
 * variables, Preempt Jobs from the Cpu and put them into the next lowest level queue,
 * and submit jobs to the Cpu. 
 * MFQ will also output messages for events such as an arrival or departure from
 * the queuing network.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public class MFQ
{
    // instance variables
    private PrintWriter pw;
    private String [] tokens;
    private ObjectQueue inputQueue;
    private ObjectQueue queue1;
    private ObjectQueue queue2;
    private ObjectQueue queue3;
    private ObjectQueue queue4;
    private Job job;
    private Clock clock;
    private Cpu cpu;
    private double totalTimeSum;
    private int jobCounter;
    private double responseTimeSum;
    private double cpuTimeRequiredSum;

    // Constants
    public static final int QUEUE1_TIME_QUANTUM = 2;
    public static final int QUEUE2_TIME_QUANTUM = 4;
    public static final int QUEUE3_TIME_QUANTUM = 8;
    public static final int QUEUE4_TIME_QUANTUM = 16;
    /**
     * Constructor for objects of class InputQueue
     */
    public MFQ(PrintWriter pw)
    {
        // initialise instance variables in constructor
        this.pw = pw;
        this.inputQueue = new ObjectQueue();
        this.queue1 = new ObjectQueue();
        this.queue2 = new ObjectQueue();
        this.queue3 = new ObjectQueue();
        this.queue4 = new ObjectQueue();
        this.clock = new Clock(); 
        this.cpu = new Cpu(); 
    }

    /**
     * Retrieves Jobs from mfq5.txt file in string format, splits them and inserts their integer
     * values into the inputQueue
     */
    public void getJobs() throws IOException{
        //get jobs from mfq.txt
        Scanner fileScan = new Scanner(new File("mfq5.txt"));
        // splits each line and stores the three values into a new job object
        // inserts that job into the input queue
        while(fileScan.hasNext()) {
            String line = fileScan.nextLine();
            String delims = "[ ]+";
            tokens = line.split(delims);
            // inserts arrival time, pid, and cpu time needed to a new job object respectively
            // and parses each split string from string to int
            job = new Job(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2])); 
            inputQueue.insert(job);
        }
        fileScan.close(); 
    }

    /**
     * Method to run the multilevel feedback queue simulation
     */
    public void startSimulation() {
        // Run the loop until all queues are empty and no jobs are on the cpu
        while(!inputQueue.isEmpty() || !queue1.isEmpty() || !queue2.isEmpty() 
        || !queue3.isEmpty() || !queue4.isEmpty() || cpu.isBusy() == true) { 
            // start by ticking the clock     
            clock.tickClock();   
            // counter for responseTime
            // since preemption occurs when job is on queue1, the amount of loops = time taken to reach cpu
            int responseTime = 0; 
            // query the job at the front of the input queue
            // Sends a job from the input queue to queue1 when it's their arrival time 
            while(!inputQueue.isEmpty() && ((Job) inputQueue.query()).getArrivalTime() == clock.getTime()) { 
                job = (Job) inputQueue.query();
                queue1.insert(inputQueue.remove()); //remove from input queue and insert to queue1               
                eventOutput(job, "Arrival  "); // output arrival event
                job.setCurrentQueue(1);
                job.setResponseTime(responseTime);
                responseTimeSum += responseTime; // sum of response times for all jobs
                responseTime++;
            }
            // check if the cpu is busy
            checkBusyFlag(); 
        }
    }

    /**
     * Checks is the job on the cpu is finished.
     * 
     * @return  true if job is finished, false if not
     */
    private boolean isJobFinished() {
        if(clock.getJobClock() == 0) {
            jobCounter++; // for every completed job, increment job counter
            return true;
        } 
        return false;
    }

    /**
     * Checks the cpu busy flag. If true, decrements quantum clock and job clock 
     * then checks if the job is finished or if preemption should occur, if busy flag
     * is false, it submits a job to the cpu.
     */
    private void checkBusyFlag() {
        if(cpu.isBusy() == true) {
            cpu.decrementQuantumClock();
            clock.decrementJobClock();
            // if job is finished, output the event, reset busy flag, and submit new job to cpu
            if(isJobFinished() == true) {
                // Sum of time on cpu for each job for outstats() 
                cpuTimeRequiredSum += cpu.getCpuJob().getCpuTimeRequired(); 
                eventOutput(cpu.getCpuJob(), "Departure"); 
                cpu.resetBusyFlag();
                submitJob();
                // if job isn't finished but there's a job in queue1, preemption will occur
            } else if(checkPreemption() == true) {
                preemption();
                submitJob(); // submit new job after preemption
            }
        } else if(cpu.isBusy() == false) {
            submitJob();
        }    
    }

    /**
     * Submits new job to cpu. submitJob prioritizes upper level queues first.
     */
    private void submitJob() {
        // removes job from corresponding queue, adds it to the cpu w/ its corresponding
        // constant for quantum time if queue is not empty
        if(!queue1.isEmpty()) { 
            job = (Job) queue1.remove();
            cpu.addJob(job, QUEUE1_TIME_QUANTUM);
            clock.setJobClock(job.getCpuTimeRemaining()); // sets the job clock to jobs remaining time
        } else if(!queue2.isEmpty()) {
            job = (Job) queue2.remove();
            cpu.addJob(job, QUEUE2_TIME_QUANTUM);
            clock.setJobClock(job.getCpuTimeRemaining());
        } else if(!queue3.isEmpty()) {
            job = (Job) queue3.remove();
            cpu.addJob(job, QUEUE3_TIME_QUANTUM);
            clock.setJobClock(job.getCpuTimeRemaining());
        } else if(!queue4.isEmpty()) {
            job = (Job) queue4.remove();
            cpu.addJob(job, QUEUE4_TIME_QUANTUM);
            clock.setJobClock(job.getCpuTimeRemaining());
        }
    }

    /**
     * Checks if preemption should occur or not.
     * Preemption occurs if the quantum clock has reached 0 for a job or if there is a job
     * waiting in queue 1.
     * 
     * @return      boolean true or false for if preemption should occur
     */
    private boolean checkPreemption() {
        return cpu.getQuantumClock() == 0 || !queue1.isEmpty() ? true : false;
    }

    /**
     * Takes the job from the Cpu, resets the busy flag and sends it to the next lower level
     * queue.
     */
    private void preemption() {
        job = cpu.getCpuJob();
        job.setCpuTimeRemaining(clock.getJobClock()); // sets the time the job still needs on the cpu
        cpu.resetBusyFlag();
        sendToLLQ();
    }

    /**
     * Inserts jobs into the next lower level queue.
     */
    private void sendToLLQ() {
        switch (job.getCurrentQueue()) { // get current level queue for job
            
            case 1 : // case for queue 1
            job.setCurrentQueue(2); // set current queue to next lower level queue
            queue2.insert(job); // inserts job into that lower level queue
            break;

            case 2 : // case for queue 2
            job.setCurrentQueue(3);
            queue3.insert(job);
            break;

            case 3 : // case for queue 3
            job.setCurrentQueue(4);
            queue4.insert(job);
            break;

            case 4 : // case for queue 4
            job.setCurrentQueue(4);
            queue2.insert(job);
            break;
        }
    }

    /**
     * Outputs the event to console and csis.txt file
     * and gets values for totalTimesum and waitTimeSum.
     * 
     * @param   Job object and String event (arrival/departure)
     */
    public void eventOutput(Job job, String event) {
        int totalTime = clock.getTime() - job.getArrivalTime();
        totalTimeSum += totalTime; // sum of the time each job is in the system for outstats()
        //print output to console and csis.txt for arrivals and departures
        if(event.equals("Departure")){
            System.out.printf("\n%-15s %-12d %-25d %-12d %-12d %-12d", event, clock.getTime(),
                job.getPid(), totalTime, job.getCurrentQueue(), job.getResponseTime());
            pw.printf("\n%-15s %-12d %-25d %-12d %-12d %-12d", event, clock.getTime(),
                job.getPid(), totalTime, job.getCurrentQueue(), job.getResponseTime());
        } else {
            System.out.printf("\n%-15s %-12d %-12d %-12d", 
                event, clock.getTime(), job.getPid(), job.getCpuTimeRequired());
            pw.printf("\n%-15s %-12d %-12d %-12d", 
                event, clock.getTime(), job.getPid(), job.getCpuTimeRequired());   
        }
    }

    /**
     * Header for eventOutput. Outputs the titles of each column to console and csis.txt.
     */
    public void header() {
        // output to console
        System.out.printf("%-13S %-14S %-9S %-11S %-11S %-14S %-15S\n", "Event", 
            "system", "pid", "cpu time", "total time", "lowest level", "response");
        System.out.printf("%19S %26S %13S %10S %12S\n", "time", "needed", "in system", 
            "queue", "time");
        // output to csis.txt    
        pw.printf("%-13S %-14S %-9S %-11S %-11S %-14S %-15S\n", "Event", 
            "system", "pid", "cpu time", "total time", "lowest level", "response");
        pw.printf("%19S %26S %13S %10S %12S\n", "time", "needed", "in system", 
            "queue", "time");                
    }

    /**
     * Outputs the total number of jobs, time of all jobs in the system, average response time,
     * average turnaround time, average waiting time, and average throughput for the system
     * to console and csis.txt.
     */
    public void outstats() {
        double avgResponseTime = responseTimeSum / jobCounter;
        double avgTurnAroundTime = totalTimeSum / jobCounter;
        double waitTimeSum = totalTimeSum - cpuTimeRequiredSum; 
        double avgWaitTime = waitTimeSum / jobCounter;
        double avgThroughput = jobCounter / totalTimeSum;
        // output to console
        System.out.println("\n\nTotal number of jobs: " + jobCounter);
        System.out.println("Total time of all jobs in system: " + (int) totalTimeSum);
        System.out.printf("Average response time: %.2f\n", avgResponseTime);
        System.out.printf("Average turnaround time for the jobs: %.2f\n", avgTurnAroundTime);
        System.out.printf("Average waiting time: %.2f\n", avgWaitTime);
        System.out.printf("Average throughput for the system as a whole: %.2f", avgThroughput);
        // output to csis.txt
        pw.println("\n\nTotal number of jobs: " + jobCounter);
        pw.println("Total time of all jobs in system: " + (int) totalTimeSum);
        pw.printf("Average response time: %.2f\n", avgResponseTime);
        pw.printf("Average turnaround time for the jobs: %.2f\n", avgTurnAroundTime);
        pw.printf("Average waiting time: %.2f\n", avgWaitTime);
        pw.printf("Average throughput for the system as a whole: %.2f", avgThroughput);
    }
}

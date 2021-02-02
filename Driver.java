import java.io.*;
/**
 * Main driver class that makes corresponding method calls to the MFQ class to run the simulation.
 *
 * @author Joshua Matson id# 012024010
 * @version 4/5/2019
 */
public class Driver
{
    /**
     * Main method of the queue simulation
     */
    public static void main(String [] args) throws IOException
    {
        PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
        MFQ input = new MFQ(pw);
        input.getJobs();
        input.header();
        input.startSimulation();
        input.outstats();
        pw.close();
    }
}

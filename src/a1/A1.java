/*
 * Masimba Banda c3059877 COMP2240 Assignment 1 main class
 * To compare four scheduling algorithms FCFS, RR, FB and NRR
 * Created 26 August 2019. Last modified 6 September 2019
 */
package a1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Masi
 */
public class A1 {

    /**
     * @param args the command line arguments
     */
    
    //variables for the main class to readfile and algorith schedules
    private final FCFS fcfs = new FCFS();
    
    private final RR rr = new RR();
    
    private final FB fb = new FB();
    
    private final NRR nrr = new NRR();
    
    private final String fileName;
    
    private final static A1 a1 = new A1();
    
    private FileReader fileReader;
    
    private BufferedReader buffer;
    
    private static int DISP;
    
    private int numProcesses;
    
    private static int position = 0;
    
    private String [] transactions;
    
    private String [] arrivalTimes;
    
    private String [] execSizes;
    
    private ProcessID process [];  
               
    private String fcfsSummary;
    
    private String rrSummary;
     
    private String fbSummary;
       
    private String nrrSummary;    
    
    private boolean isFCFS;
    
    private boolean isRR;
    
    private boolean isFB;
    
    private boolean isNRR;
    
    private static double fcfsAverageTurnArround, rrAverageTurnArround, fbAverageTurnArround, nrrAverageTurnArround;
    
    private static double fcfsAverageWait, rrAverageWait, fbAverageWait, nrrAverageWait;
    
    
    //default constructor
    
    A1 () {
        
        fileName = "datafile2.txt";
        DISP = 0;
        numProcesses = 0;
        position = 0;
        transactions = null; 
        arrivalTimes = null;
        execSizes = null;
        fcfsSummary = "";
        rrSummary = "";
        fbSummary = "";
        nrrSummary = "";
        isFCFS = false;
        isRR = false;
        isFB = false;
        isNRR = false;
        fcfsAverageTurnArround = 0;
        rrAverageTurnArround = 0;
        fbAverageTurnArround = 0;
        nrrAverageTurnArround = 0;
        fcfsAverageWait = 0;
        rrAverageWait = 0;
        fbAverageWait = 0;
        nrrAverageWait = 0;
        
      
    
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        a1.readFile();
        a1.summary();
        
    }
    
    //method to readfile 
    //captures the require input and sets them as variables using 
    //stringbuilder and then set them as an array
    
    public void readFile() {
        
        String idNum = "";
        
        try {
            
            fileReader = new FileReader(fileName);
            buffer = new BufferedReader(fileReader);

            int j;
            
                       
            char[] buff = new char[fileName.length()];
            
            StringBuilder strBuilder = new StringBuilder();
            
            
             while ((j = buffer.read(buff)) != -1) {

                    strBuilder.append(buff, 0, j);

                    idNum = strBuilder.toString();

            }
            
            buffer.close();
        } catch (IOException e) {
            System.out.println("File not found: Try again");
            System.exit(0);
        }        
        
        String[] variables;
        variables = idNum.split("\\s+");
        
        for (String result1 : variables) {
            if (result1.equals("ID:")) {
                numProcesses++;

            }
        }
         
          transactions = new String[numProcesses];
          
          arrivalTimes = new String[numProcesses];
          
          execSizes = new String[numProcesses];
          
          process = new ProcessID[numProcesses];
                    
          position = 0;
         
         for (int i = 0; i < variables.length-1; i++) {

                 if (variables[i].equals("ID:"))  {
                    transactions[position] =  variables[i+1];
                    process[position] = new ProcessID();
                    process[position].setID(variables[i+1]);
                    
         }
         
                 if (variables[i].equals("Arrive:")) {
                     arrivalTimes[position] = variables[i+1];
                    process[position].setArrivalTime(Integer.parseInt(variables[i+1]));
                 }
                 
                 if (variables[i].equals("ExecSize:")) {
                     execSizes[position] = variables[i+1];
                     process[position].setExecSize(Integer.parseInt(variables[i+1]));
                     position++;
                }
            
      }   
        
           for (int i = 0; i < variables[i].length()-1;i++) {
                   if (variables[i].equals("DISP:")) {
                       DISP = Integer.parseInt(variables[i+1]);
                       break;
                   }
                       
           }
        
           //call the various algorithms and calculate the required times
           //after running each algorithm
         fcfsAlgorithm ();
         fcfsAverageTurnArround = fcfs. getAverageTurnArroundTime ();
         fcfsAverageWait =  fcfs.getAverageWaitTime();
         fcfs.reset();
         rrAlgorithm ();
         rrAverageTurnArround = rr. getAverageTurnArroundTime ();
         rrAverageWait = rr.getAverageWaitTime();
         rr.reset();
         fbAlgorithm ();
         fbAverageTurnArround = fb. getAverageTurnArroundTime ();
         fbAverageWait = fb.getAverageWaitTime();
         fb.reset();    
         nrrAlgorithm();
         nrrAverageTurnArround = nrr. getAverageTurnArroundTime ();
         nrrAverageWait = nrr.getAverageWaitTime();

    }
    
    //method for the fcfs algorithm
    public void fcfsAlgorithm () {
        
         fcfs.setDISP(DISP);
         fcfs.setFCFSProcesses(process);
         System.out.println(fcfs.toString());
         isFCFS = true;
    }
    
    //method for the round robin algorithm
     public void rrAlgorithm () {
               
         rr.setDISP(DISP);
         rr.setRRProcesses(process);
         System.out.println(rr.toString());
         isRR = true;
    }
     
    //method for the feedback algorithm
    public void fbAlgorithm () {
        
         fb.setDISP(DISP);
         fb.setFBProcesses(process);
         System.out.println(fb.toString());
         isFB = true;
    } 
    
    //method for the narrow round robin algorithm
     public void nrrAlgorithm () {
        
         nrr.setDISP(DISP);
         nrr.setNRRProcesses(process);
         System.out.println(nrr.toString());
         isNRR = true;
    } 
    
    
    //print the algorithms that were used
    public void summary() {
        
         System.out.println("\nSummary");
         System.out.println("Algorithm        Average Turnaround Time   Average Waiting Time");
         
        if (isFCFS) {
          fcfsSummary =
          String.format("FCFS%18.2f %25.2f", fcfsAverageTurnArround,fcfsAverageWait);
          System.out.println(fcfsSummary);
         }
            
         if (isRR) {
          rrSummary =
          String.format("RR%20.2f %25.2f", rrAverageTurnArround,rrAverageWait);
         
          System.out.println(rrSummary);
         }
         
         if (isFB) {
         fbSummary =
          String.format("FB (constant) %8.2f %25.2f", fbAverageTurnArround,fbAverageWait);
         
          System.out.println(fbSummary);
         }
         
         if (isNRR) {
         nrrSummary =
          String.format("NRR%19.2f%26.2f", nrrAverageTurnArround,nrrAverageWait);
         
          System.out.println(nrrSummary);
         }

    }
    
}

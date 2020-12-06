/*
 * Masimba Banda c3059877 COMP2240 Assignment 1 FCFS class
 * Has FCFS algorithm that uses Queue features
 * Created 26 August 2019. Last modified 6 September 2019
 */
package a1;

import java.util.Iterator;
import java.util.Queue;

/**
 *
 * @author Masi
 */


class FCFS extends Schedule {
    
    private ProcessID pro [] ;
    
    private String fcfsProcessInfo;
    
    private Queue <ProcessID> fcfsQueue;
    
    private int DISP ;
    
    
    //default constructor
     FCFS () {
         
         DISP = 0;
         fcfsProcessInfo = "";
         pro =  new ProcessID [0];
         
         
     }
     
     //set the input DSP from file
     public void setDISP (int DISP) {
         this.DISP = DISP;
     }
     

     //the number of processes
     public int getNumProcesses () {
         
         return readyQueue.size();
     }
        
     //set processes to begin simulation
     public void setFCFSProcesses (ProcessID [] process) {
        
         pro = process;
        
         beginSimulation(pro);

         fcfsProcess ();
     }
     

     //the fcfs algorithm
     public void fcfsProcess () {
         
           while (!readyQueue.isEmpty()) {
                            
               //first item start
               //if there are items in the readyqueue run dispatcher
               
               
               if (completedProcesses.isEmpty()) {
                        runDispatcher = true;
                        if (runDispatcher) {
                            Dispatcher () ;
                        }
                        readyQueue.element().setStartTime(readyQueue.element().getArrivalTime() + DISP);
                        timeCounter = readyQueue.element().getStartTime();
                        
                        
                      while (processingTime != readyQueue.element().getExecSize()) {

                          processingTime++;
                          timeCounter++;
                          
                      }
                                     
                       readyQueue.element().setExitTime(timeCounter);                      
                       completedProcesses.add(readyQueue.remove());
                   processingTime = 0;
                   
               } 
               
               
               //add subsequent items                                                           
            // run dispatcher as long as there is process waiting in readyQueue
               
               
               if (runDispatcher)
               
                 if (!readyQueue.isEmpty()) {
                        readyQueue.element().setStartTime(timeCounter + DISP);
                        timeCounter = readyQueue.element().getStartTime();

                      while (processingTime != readyQueue.element().getExecSize()) {
                          processingTime++;
                          timeCounter++;
                 }
                      readyQueue.element().setExitTime(timeCounter);
                 }
                   completedProcesses.add(readyQueue.remove());
                   processingTime = 0;
                       }
           
           //   run dispatcher as long as there is a process waiting in readyQueue
           //and stop when empty 
           if (readyQueue.isEmpty()) {
               runDispatcher = false;
           }
           
           //stop simulation when all processes completed
           if (completedProcesses.size()  == totalProcesses ) {
              runSimulation = false;
          }

     }
     
     //reset the variables after calculation of results for next run
    @Override
     public void reset (){
        for (ProcessID pro1 : pro) {
            pro1.setExecSize(pro1.getInitialExecSize());
            pro1.setStartTime(pro1.getInitialStartTime());
            pro1.setTurnArroundTime(0);
            pro1.setWaitTime(0);
        }
     }
     
    
     //print all the resutls
    @Override
    public String toString() {
        
             
        System.out.println("FCFS:");
         Iterator<ProcessID> fcfs = completedProcesses.iterator();
        while (fcfs.hasNext()) {
            ProcessID proID = fcfs.next();
         fcfsProcessInfo +=   String.format("T%d: %s\n", proID.getStartTime(), proID.getID());
        } 
            
        fcfsProcessInfo +=  String.format("\nProcess    Turnaround Time   Waiting Time");
        
        fcfsProcessInfo += " \n";
              
        Iterator<ProcessID> results = completedProcesses.iterator();
        while (results.hasNext()) {
            ProcessID proID = results.next();
         fcfsProcessInfo +=   String.format("%-11s%-17d %d\n",proID.getID(), proID.getTurnArroundTime(), proID.getWaitTime() );
        }
        
        
          return fcfsProcessInfo;
    }
}

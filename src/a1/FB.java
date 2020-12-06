/*
  * Masimba Banda c3059877 COMP2240 Assignment 1 RR class
 * Has FB algorithm that uses Queue features
 * Created 30 August 2019. Last modified 6 September 2019
 */
package a1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

/**
 *
 * @author Masi
 */
 class FB extends Schedule{
    
    private ProcessID pro [] ;
    
    private String fbProcessInfo;
    
    private Queue <ProcessID> fbQueue;
    
    private int DISP ;
    
    //default constructor 
    FB (){
        
         DISP = 0;
         fbProcessInfo = "";
         pro =  new ProcessID [0];
        
    }
    
    
         //set the input DSP from file

    public void setDISP (int DISP) {
        this.DISP = DISP;
    }
    
             //set processes to begin simulation

    public void setFBProcesses (ProcessID [] process) {
              
        pro = process;
        
        beginSimulation(pro);
        
        fbProcess();
        
           }
    
    
    /* the feddback processes
     runs using starting time quantum of 4 and priority levels 0 to 5.
     priorities increase with each time in queue inorder for process to
     be eventually run
    */
    public void fbProcess () {
        
    System.out.println("\nFB (constant):");
    
    isFB = true;
    
       while (!readyQueue.isEmpty()) {
           
           //first time run 
           
                   
           if (completedProcesses.isEmpty()) {
               runDispatcher = true;       
                if (runDispatcher) {
                            Dispatcher () ;
                        }
                 readyQueue.element().setStartTime(readyQueue.element().getArrivalTime() + DISP);
                 timeCounter = readyQueue.element().getStartTime();
                        
                            
                        //process until end of quantum time
                  if (readyQueue.element().getExecSize() > TIME_QUANTUM) {
                          
                          TIME_QUANTUM = 4;
                          
                          readyQueue.element().setExecSize(readyQueue.element().getExecSize() - TIME_QUANTUM);
                          
                          while (TIME_QUANTUM != 0 ) {
                          
                          processingTime++;
                          timeCounter++;
                          TIME_QUANTUM--;
                         }
                          
                          System.out.println("T" + readyQueue.element().getStartTime() + ": " + readyQueue.element().getID());
                          readyQueue.element().setExitTime(timeCounter);
                          completedProcesses.add(readyQueue.peek());
                          readyQueue.add(readyQueue.remove());
                          
                      }
                  
                   
                  else if (readyQueue.element().getExecSize() <= TIME_QUANTUM) {
                     
                       quantumRemaining = readyQueue.element().getExecSize();

                       readyQueue.element().setStartTime(timeCounter + DISP);
                       timeCounter = readyQueue.element().getStartTime();
                       
                      
                       
                         while (quantumRemaining != 0 ) {
                            quantumRemaining--; 
                            processingTime++;
                            timeCounter++;
                         }

                            System.out.println("T" + readyQueue.element().getStartTime() + ": " + readyQueue.element().getID());
                            readyQueue.element().setExitTime(timeCounter);
                            completedProcesses.add(readyQueue.remove());
                            
                  }
                processingTime = 0;

           }

           //run next items 
           
           
            if (!readyQueue.isEmpty()) {
                
                        TIME_QUANTUM = 4;

                        //process for ITEMS above time quantum
                        if (readyQueue.element().getExecSize() > TIME_QUANTUM) {
                            
                            readyQueue.element().setStartTime(timeCounter + DISP);
                        timeCounter = readyQueue.element().getStartTime();
                            
                            readyQueue.element().setExecSize(readyQueue.element().getExecSize() - TIME_QUANTUM);
                            
                            while (TIME_QUANTUM != 0) {
                            processingTime++;
                            TIME_QUANTUM--;
                            timeCounter++;
                            }
                            
                            System.out.println("T" + readyQueue.element().getStartTime() + ": " + readyQueue.element().getID());
                            completedProcesses.add(readyQueue.peek());
                            readyQueue.add(readyQueue.remove());
                          
                        }
                        
                        
                        TIME_QUANTUM = 4;
                        
                        if (readyQueue.element().getExecSize() <= TIME_QUANTUM &&
                                readyQueue.element().getExecSize() != 0) {
                            
                            
                            readyQueue.element().setStartTime(timeCounter + DISP);
                            timeCounter = readyQueue.element().getStartTime();
                            
                            
                            quantumRemaining = readyQueue.element().getExecSize();

                          while (quantumRemaining  != 0 ) {
                            quantumRemaining--; 
                            processingTime++;
                            timeCounter++;
                         }
                            System.out.println("T" + readyQueue.element().getStartTime() + ": " + readyQueue.element().getID());
                            readyQueue.element().setExitTime(timeCounter);
                            completedProcesses.add(readyQueue.remove());

                 }
                   //   readyQueue.element().setExitTime(timeCounter);
                 }
           

            if (readyQueue.isEmpty())  {
              runDispatcher = false;

            }
           
       }   //end while loop

    
    
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
      
     //print all the results  
    @Override
    public String toString () {
       
       ArrayList<ProcessID> fbList = new ArrayList<>();

         Iterator<ProcessID> fb = completedProcesses.iterator();
        while (fb.hasNext()) {
            ProcessID proID = fb.next();
            fbList.add(proID);
        } 
            
        fbProcessInfo +=  String.format("\nProcess    Turnaround Time   Waiting Time");
        
        fbProcessInfo += "\n";
              
        Iterator<ProcessID> results = completedProcesses.iterator();
        while (results.hasNext()) {
            ProcessID proID = results.next();
             
        }
        
        for (int i = 0; i < totalProcesses;i ++) {
           fbProcessInfo += String.format("%-11s%-18d%d\n",fbList.get(i).getID(), fbList.get(i).getTurnArroundTime(), fbList.get(i).getWaitTime());
        }
        
          return fbProcessInfo;
    }
    
    
    
}

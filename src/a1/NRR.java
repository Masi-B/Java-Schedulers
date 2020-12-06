/*
 * Masimba Banda c3059877 COMP2240 Assignment 1 NRR class
 * Has NRR algorithm that uses Queue features
 * Created 3 September 2019. Last modified 6 September 2019
 */
package a1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Masi
 */
class NRR extends Schedule {
    
    private ProcessID pro [] ;
        
    private String nrrProcessInfo;
     
    private int DISP ;
    
    //default constructor
    NRR (){
        
         DISP = 0;
         nrrProcessInfo = "";
         pro =  new ProcessID [0];
    }
    
    
    //set disp time from input
    public void setDISP (int DISP) {
        this.DISP = DISP;
    }
    
    //set processes and begin simulation
    public void setNRRProcesses (ProcessID [] process) {
        
        pro = process;
                
        beginSimulation(pro);
        
        nrrProcess();
                
        
           }
    
    
     /* the narrow round robin processes
     runs using starting time quantum of 4, then redued by 1 after each queue
     move... until minimum of 2 milliseconds
    */
    public void nrrProcess () {
        
    System.out.println("\nNRR:");
    
      
    
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
                            
                          //  System.out
                  }
                processingTime = 0;
           }
       
           //run next items 
           //variables to check for subsequent runs and order 
        ProcessID nextProcess = null; 
        ProcessID prevProcess = null;
        ProcessID swapProcess = null;
        
           
            if (!readyQueue.isEmpty()) {
                
                        TIME_QUANTUM = 4;

                        
                        for (int i = 0; i < readyQueue.size(); i++) {
                            
                                    nextProcess = readyQueue.get(i); 
                                    readyQueue.remove();
                                    break;
                        }
                        
                        for (int i = 0; i < completedProcesses.size();i++) {
                            
                            if (nextProcess != null  && 
                                    nextProcess.getArrivalTime()> completedProcesses.get(i).getExitTime()
                                    && completedProcesses.get(i).getExecSize()> 0) {
                             
                            swapProcess = nextProcess;
                            nextProcess = prevProcess;
                            prevProcess = swapProcess;
                            
                            readyQueue.add(prevProcess);
                            }

                          break;   
                        }
                        
                if (nextProcess != null)     {    

         Iterator<ProcessID> rr = completedProcesses.iterator();
        while (rr.hasNext()) {
            ProcessID proID = rr.next();
            if (proID.equals(nextProcess)) {
                TIME_QUANTUM--;

            
            }

        } 
                    
                       
                        if (nextProcess.getExecSize() > TIME_QUANTUM) {
                            
                            nextProcess.setStartTime(timeCounter + DISP);
                        timeCounter = nextProcess.getStartTime();
                            
                        if (!readyQueue.isEmpty()) { //last item
                        
                            //THIS IS THE PART TO CHANGE
                            nextProcess.setExecSize(nextProcess.getExecSize() - TIME_QUANTUM);
                            
                            
                            
                            while (TIME_QUANTUM != 0) {
                            processingTime++;
                            TIME_QUANTUM--;
                            timeCounter++;
                            }
                            
                            
                            
                            System.out.println("T" + nextProcess.getStartTime() + ": " + nextProcess.getID());
                            nextProcess.setExitTime(timeCounter);
                            readyQueue.add(nextProcess);

                        }
                        
                        else {
                            
                            quantumRemaining = nextProcess.getExecSize();
                                                        
                            while (quantumRemaining != 0) {
                            processingTime++;
                            quantumRemaining--;
                            timeCounter++;
                            }

                            System.out.println("T" + nextProcess.getStartTime() + ": " + nextProcess.getID());
                            nextProcess.setExitTime(timeCounter);
                            
                        }
                        
                         completedProcesses.add(nextProcess);
                        
                        }
                        
                                    
                      else if (nextProcess.getExecSize() <= TIME_QUANTUM &&
                                nextProcess.getExecSize() != 0) {

                        TIME_QUANTUM = 4;
                            
                            nextProcess.setStartTime(timeCounter + DISP);
                            timeCounter = nextProcess.getStartTime();
                            
                                                        
                            quantumRemaining = nextProcess.getExecSize();

                          while (quantumRemaining  != 0 ) {
                            quantumRemaining--; 
                            processingTime++;
                            timeCounter++;
                         }
                            System.out.println("T" + nextProcess.getStartTime() + ": " + nextProcess.getID());
                            nextProcess.setExitTime(timeCounter);
                            completedProcesses.add(nextProcess);

                 }
                 }
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
      
     //print all variables
    @Override
    public String toString () {
            
       ArrayList<ProcessID> rrList = new ArrayList<>();

         Iterator<ProcessID> rr = completedProcesses.iterator();
        while (rr.hasNext()) {
            ProcessID proID = rr.next();
            if (!rrList.contains(proID))
              rrList.add(proID);
             
        } 
            
        nrrProcessInfo +=  String.format("\nProcess    Turnaround Time   Waiting Time");
        
        nrrProcessInfo += "\n";
              
        
        for (int i = 0; i < totalProcesses;i ++) {
           nrrProcessInfo += String.format("%-11s%-18d%d\n",rrList.get(i).getID(), rrList.get(i).getTurnArroundTime(), rrList.get(i).getWaitTime() );
        }
     
        
          return nrrProcessInfo;
    }
    
    
}

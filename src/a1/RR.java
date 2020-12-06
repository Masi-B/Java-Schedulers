/*
 * Masimba Banda c3059877 COMP2240 Assignment 1 RR class
 * Has RR algorithm that uses Queue features
 * Created 30 August 2019. Last modified 6 September 2019
 */
package a1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Masi
 */
 class RR  extends Schedule {
    
    
    private ProcessID pro [] ;
        
    private String rrProcessInfo;
        
    private int DISP ;
    
    //default constructor
    RR (){
        
         DISP = 0;
         rrProcessInfo = "";
         pro =  new ProcessID [0];
    }
    
    
    
    public void setDISP (int DISP) {
        this.DISP = DISP;
    }
    
         //set processes to begin simulation

    public void setRRProcesses (ProcessID [] process) {
        
        pro = process;
          
        
        beginSimulation(pro);
        
        rrProcess();
        
        
        
           }
    
    /* the round robin processes
     runs using time quantum of 4
    */
    public void rrProcess () {
        
    System.out.println("\nRR:");
    
      
    
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
           //variables to check for subsequent runs and order 
        ProcessID nextProcess = null; 
        ProcessID prevProcess = null;
        ProcessID swapProcess = null;
        
           
            if (!readyQueue.isEmpty()) {
                
                        TIME_QUANTUM = 4;
                        
                        //process for ITEMS above time quantum
                        
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
                        
                        if (nextProcess.getExecSize() > TIME_QUANTUM) {
                            
                            nextProcess.setStartTime(timeCounter + DISP);
                        timeCounter = nextProcess.getStartTime();
                            
                        //last item, then run till the end 
                        if (!readyQueue.isEmpty()) { 
                        
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
                        
                   //processes below quantum time get run once    
                        
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
            //    pro[i].setExitTime(0);
        }
      }
      
   
      
    //print all the results
    @Override
    public String toString () {
       
        
       ArrayList<ProcessID> rrList = new ArrayList<>();
       
       
       Iterator<ProcessID> rr = completedProcesses.iterator();
        while (rr.hasNext()) {
            ProcessID proID = rr.next();
            if (!rrList.contains(proID))
              rrList.add(proID);
             
        } 
            
        rrProcessInfo +=  String.format("\nProcess    Turnaround Time   Waiting Time");
        
        rrProcessInfo += "\n";
              
        
        for (int i = 0; i < totalProcesses;i ++) {
           rrProcessInfo += String.format("%-11s%-18d%d\n",rrList.get(i).getID(), rrList.get(i).getTurnArroundTime(), rrList.get(i).getWaitTime() );
        }
             
          return rrProcessInfo;
    }
    
    
}

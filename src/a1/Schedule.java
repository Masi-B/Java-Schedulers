/*
 * Masimba Banda c3059877 COMP2240 Assignment 1 Schedule abstract class
 * Has all the implementations needed by algorithm classes
 * Created 20 August 2019, Last modified 6 September 2019
 */
package a1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Masi
 */
abstract class Schedule {
    // protected variables so they can be accessed by classes
    //implementing the abstract class which are the algorithms
    
    protected boolean runSimulation;
    
    protected boolean runDispatcher;
      
    protected double totalWaitTime; // total wait time 
    
    protected double averageWaitTime; // total wait time 
      
    protected double totalTurnArroundTime;
    
    protected double averageTurnArroundTime;
    
    protected LinkedList <ProcessID> completedProcesses;
    
    protected LinkedList <ProcessID> readyQueue;
    
    protected LinkedList <ProcessID> sortedQueue;
    
    protected ProcessID tempMinProcess;
        
    protected int timeCounter;
    
    protected int priorityLevel;
    
    protected double processingTime;
    
    protected int totalProcesses; //totalProcesses 
     
    protected static int TIME_QUANTUM = 4;
    
    protected int quantumRemaining;
    
    protected boolean isFB;
    
    
    Schedule () {
        
         runSimulation = false;
    
         runDispatcher = false;
    
    
         totalWaitTime = 0; 
         
         averageWaitTime = 0;
   
         totalTurnArroundTime = 0;
         
         timeCounter = 0;
         
         averageTurnArroundTime = 0;
         
         processingTime = 0;
         
         tempMinProcess = new ProcessID();
                      
         completedProcesses = new LinkedList();
         
         readyQueue = new LinkedList ();
         
         sortedQueue = new LinkedList ();
         
         totalProcesses  = 0;
         
         quantumRemaining = 0;
         
         priorityLevel = 0;
         
         isFB = false;
         
    }
    

    //keep track of time processing
    public double timeCounter () {
        return timeCounter;
    }
    
     
    
    public double getProcessingTime () {
        return processingTime;
    }
    
    
    public void setPriority(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
     //start simulation method
    public void beginSimulation (ProcessID [] process) {
        runSimulation = true;
                      
        for (ProcessID proces : process) {
            readyQueue.add(proces);
            totalProcesses++;
        }
        
        
    }
    
        
    /*calculate the average wait time, by first
     inserting into list of processes
     */
    

      
      public double getAverageWaitTime () {
                    
        
         ArrayList<ProcessID> list = new ArrayList<>();
       
          Iterator<ProcessID> totalWait = completedProcesses.iterator();
        while (totalWait.hasNext()) {
                ProcessID proID = totalWait.next();
                 if (!list.contains(proID)) {
                    list.add(proID);
                 }
        }
             
          
           for (int i = 0; i < totalProcesses;i ++) {
              totalWaitTime += list.get(i).getWaitTime();
        }
       
                      
        averageWaitTime =   totalWaitTime / totalProcesses;
        
        
        return averageWaitTime;
     }
      
       /*calculate the average turn arround time, by first
     inserting into list of processes
     */
    
      public double getAverageTurnArroundTime () {
          
          ArrayList<ProcessID> list = new ArrayList<>();
       
          Iterator<ProcessID> totalTurnArround = completedProcesses.iterator();
        while (totalTurnArround.hasNext()) {
                ProcessID proID = totalTurnArround.next();
                if (!list.contains(proID))
                 list.add(proID);
           
        }
             
          
           for (int i = 0; i < totalProcesses;i ++) {
              totalTurnArroundTime += list.get(i).getTurnArroundTime();
        }
           
           averageTurnArroundTime = totalTurnArroundTime / totalProcesses;

           return averageTurnArroundTime;
      }
      
      //variable reset method 
      public abstract void reset();
                   
         
      public int getTotalProcesses () {
         
         return totalProcesses;
     }
        
    
    
    //run dispatcher
     void Dispatcher () {
         
         while (runSimulation) {
             
             //dispatcher sorts processes based on Pi assumed to come before pi+1
                        
             //sort queue based on the arrival time and process name
             
             tempMinProcess = readyQueue.peek();
             
             boolean isSorted = false;
             
             
         
    
         sortedQueue.add(readyQueue.remove()) ;
         
         while (!isSorted) {
             
             tempMinProcess = readyQueue.remove();
             
             for (int i = 0; i < sortedQueue.size();i++) {
                 
                 if (sortedQueue.peek().getID().compareTo(tempMinProcess.getID()) < 0) {
                      sortedQueue.add(sortedQueue.remove());
                      
                 } else {
                     sortedQueue.add(tempMinProcess);
                     tempMinProcess = sortedQueue.remove();
                 }
                     
             }
          
            
             sortedQueue.add(tempMinProcess);
             
             //stop when all items added to new queue
             
             if (sortedQueue.size() == totalProcesses)
                 isSorted = true;
             
            
         }
          
          
                 Iterator<ProcessID> sorted = sortedQueue.iterator();
        while (sorted.hasNext()) {
            ProcessID proID = sorted.next(); {
                 
                    readyQueue.add(proID);
        }
          
        } 
             
             
               if (completedProcesses.size() == readyQueue.size())
             
         {
              runDispatcher = false;
              runSimulation = false;
         }
               
           //assign priorities if FB algorithm
           //assign priorities from high to low
           //first time get highest priorities 
           
            while (isFB) {
          
                if (completedProcesses.isEmpty()) 
                
                   priorityLevel = 5;
                
                if (!readyQueue.isEmpty() && priorityLevel != 0) {
                    
                    priorityLevel--;
                }
                
                isFB = false;
            }
       
             
             if ( readyQueue.isEmpty())
             {
                 runDispatcher = false;
                 continue;
             }
             
             runSimulation = false;
         }
             
        
    }
           
         
}

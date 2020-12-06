/*
* Masimba Banda c3059877 COMP2240 Assignment 1 Process class
 * Holds the ID, arrival times and serviceTime of an instance of a process object
 * and open the template in the editor.
 */
package a1;

/**
 *
 * @author Masi
 */
class ProcessID {
    
    //variables for a process
    private String id;
    
    private int arrivalTime;
    
    private int execSize;
    
    private int initialExecSize;
    
    private int initialStartTime;
    
    private String processInfo;
    
    private int exitTime;
    
    private int startTime;
    
    private int turnArroundTime;
    
    private int waitTime;
    
    private final int  initialExitTime;
    
    private final int initialWaitTime;
    
    private final int initialTurnArroundTime;
       
    //default constructor
    ProcessID () {
        
        id = "";
        arrivalTime = 0;
        execSize = -1;
        startTime = -1;
        turnArroundTime = 0;
        waitTime = 0;
        processInfo = "";
        exitTime = -1;
        initialExitTime = 0;
        initialWaitTime = 0;
        initialTurnArroundTime = 0;
        initialExecSize = 0;
        
    }
    
    //mutators to set process information
    public void setID(String id) {
        this.id = id;
    }
    
    public void setArrivalTime (int arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    
    public void setExecSize (int execSize){
       

        if (this.execSize == -1) {
            initialExecSize = execSize;
        }
         this.execSize = execSize;
    }
    
     public  void setStartTime (int startTime){
         
         if (this.startTime == -1) {
             initialStartTime = 0;
         }
        this.startTime = startTime;
    }
     
     public void setExitTime (int exitTime){
        this.exitTime = exitTime;
    }
    
    public void setTurnArroundTime (int turnArround) {
        this.turnArroundTime = turnArround;
    } 
    
    public void setWaitTime (int waitTime) {
        this.waitTime = waitTime;
    } 
     
    //accessors to get process information
    public  String getID() {
        return id;
    }
    
    public int getArrivalTime () {
        return arrivalTime;
    }
    
    public int getExecSize() {
        return execSize;
    }
    
    public final int getInitialExecSize() {
        return initialExecSize;
    }
    
     public final int getInitialStartTime() {
        return initialStartTime;
    }
     
     public final int getInitialExitTime() {
        return initialExitTime;
    }
     
      
    public  int  getStartTime() {
        return startTime;
    }
    
     public int getExitTime() {
        return exitTime;
    }
        
     
     //calculate the turnarround time for each process
    public int  getTurnArroundTime () {   
          
          turnArroundTime = exitTime - arrivalTime;        
          return turnArroundTime;
      }
    
    //calculate the wait time
    public int getWaitTime () {   

         waitTime = turnArroundTime - initialExecSize ;
         
         return waitTime;
     }
    
    //the original initial wait time
     public final int getInitialWaitTime() {
        return initialWaitTime;
    } 
    
     //the turn arround time for previous process
    public final int getInitialTurnArround() {
        return initialTurnArroundTime;
    }
    
    
    
    @Override
    public String toString (){
       processInfo =  String.format("%s          %d "
               + "            %d",id,arrivalTime,execSize);
       return processInfo;
    }
    
}

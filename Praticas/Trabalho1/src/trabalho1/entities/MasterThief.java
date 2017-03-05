/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1.entities;

/**
 *
 * @author Tiago Henriques
 */
public class MasterThief extends Thread {
    
    private MasterThiefState state;

    public MasterThief() {
        this.state = MasterThiefState.PLANNING_THE_HEIST;
    }

    @Override
    public void run() {
        while(!finish){
            switch(this.state){
                case PLANNING_THE_HEIST:
                    // DO something
                    this.state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;
                case DECIDING_WHAT_TO_DO:
                    // DO something
                    //this.state = MasterThiefState.ASSEMBLING_A_GROUP;
                    //this.state = MasterThiefState.WAITING_FOR_ARRIVAL;
                    //this.state = MasterThiefState.PRESENTING_THE_REPORT;
                    //this.state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;    
                case ASSEMBLING_A_GROUP:
                    // DO something
                    this.state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;
                case WAITING_FOR_ARRIVAL:
                    // DO something
                    this.state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;    
            }
        }
    }
    
}

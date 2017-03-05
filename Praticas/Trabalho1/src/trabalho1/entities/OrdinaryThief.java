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
public class OrdinaryThief extends Thread {

    private OrdinaryThiefState state;
    
    public OrdinaryThief() {
        this.state = OrdinaryThiefState.OUTSIDE;
    }

    @Override
    public void run() {
        while(!finish){
            switch(this.state){
                case OUTSIDE:
                    // DO something
                    this.state = OrdinaryThiefState.CRAWLING_INWARDS;
                    break;
                case CRAWLING_INWARDS:
                    // DO something
                    this.state = OrdinaryThiefState.AT_A_ROOM;
                    break;    
                case AT_A_ROOM:
                    // DO something
                    this.state = OrdinaryThiefState.CRAWLING_OUTWARDS;
                    break;
                case CRAWLING_OUTWARDS:
                    // DO something
                    this.state = OrdinaryThiefState.OUTSIDE;
                    break;    
            }
        }
    }
      
}

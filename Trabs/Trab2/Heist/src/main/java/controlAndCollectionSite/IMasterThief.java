/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlAndCollectionSite;

/**
 * Master Thief Interface of ControlAndCollection Site Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IMasterThief {
    
    /** 
    * In the Master Thief life cycle, transition between "deciding what to do" and "deciding what to do".
    * The MasterThief decides what to do. If all rooms are empty and all parties are not
    * deployed then the next state will be presenting the report. If there are enough thieves
    * waiting to assemble a party and if the MasterThief has chosen which room to steal and
    * which party to deploy then the next state will be assembling a group. If not, he has to 
    * wait for the assault parties to return if there are not enough waiting thieves to assemble 
    * a assault party or if there are still deployed assault parties, so the next state will be
    * waiting for group arrival.
    * @return the next state
    */
    public int appraiseSit();
    
    /**
     * In Master Thief life cycle, transition between "deciding what to do" and "waiting for
     * group arrival".
     * CollectCanvas by Master Thief if any ordinary thief has arrived.
     */
    public void takeARest();
    
    /**
     * In Master Thief life cycle, transition between "waiting for group arrival" and "deciding
     * what to do".
     * Decrease the number of canvas to collect.
     */
    public void collectCanvas();     
    
    /**
     * Method that returns the room to steal by the Master Thief
     * @return the room to steal by the Master Thief
     */
    public int getRoomToSteal();

    /**
     * Method that returns the party to deploy by the Master Thief
     * @return the party to deploy by the Master Thief
     */
    public int getPartyToDeploy();
}

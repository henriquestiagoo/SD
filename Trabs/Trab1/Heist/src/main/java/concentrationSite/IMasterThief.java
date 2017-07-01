/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concentrationSite;

/**
 * Master Thief Interface of ConcentrationSite instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */

public interface IMasterThief {
    
    /**
     * In the Master Thief life cycle, transition between "planning the heist" and "deciding what to do".
     * The Ordinary Thieves are notified of the start of Operations by the Master Thief when all
     * thieves are on the waiting thieves Queue.
     */
    public void startOperations();

    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "assembling a group".
     * The Ordinary Thieves that are waiting to be set to a party are notified by the Master Thief that now they 
     * are assembled to a party and no longer are on the waiting thieves Queue. 
     * @param partyId - Assault Party Identifier
     */
    public void prepareAssaultParty(int partyId);
    
    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "presenting the report".
     * The Master Thief notifies the Ordinary Thieves that the results are now ready.
     */
    public void sumUpResults();
    
   /**
     * Method that returns the number of waiting thieves on the Queue
     * @return the number of waiting thieves on the Queue.
     */
    public int getNWaitingThieves();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concentrationSite;

/**
 * Ordinary Thief Interface of ConcentrationSite Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IOrdinaryThief {
    
    /**
     * In the Ordinary Thief life cycle, transition between "outside" and "outside".
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * The ordinary Thief is added to a waitingThieves Queue and notify the Master Thief
     * The Ordinary Thief has to wait while he is not assembled to a assalt party and the results aren't ready.
     * @param thiefId - Ordinary Thief Identifier
     * @return true if thieves are needed; Return false otherwise. (Thieves are needed while results aren't ready).
     */
    public boolean amINeeded(int thiefId);
    
     /**
     * In the Ordinary Thief life cycle, transition between "outside" and "crawling inwards".
     * The Master Thief is notified when there are enough thieves in order to assemble a assault party.
     * The party is set to ready and the number of thieves waiting to be assembled to a party is set to zero.
     * @param thiefId - Ordinary Thief Identifier
     */
    public void prepareExcursion(int thiefId);

   /**
     * Method that returns the Assault Party Id of the Ordinary Thief
     * @param thiefId - Ordinary Thief Identifier
     * @return the party Id of the Ordinary Thief.
     */
    public int getPartyId(int thiefId);
}

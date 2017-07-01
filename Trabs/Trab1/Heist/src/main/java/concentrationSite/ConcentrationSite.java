package concentrationSite;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.Constants;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ConcentrationSite Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConcentrationSite implements IMasterThief, IOrdinaryThief {
    
    private boolean partyReady;
    private final Queue<Integer> waitingThieves;
    private final boolean[] assaultingThieves;
    private boolean resultsReady;
    private int nThievesInParty;
    private final int[] thiefAssaultParty;
    
    /**
     * Creates a new ConcentrationSite.
     */
    public ConcentrationSite() {
        nThievesInParty = 0;
        resultsReady = false;
        partyReady = false;
        waitingThieves = new LinkedList<>();
        assaultingThieves = new boolean[Constants.N_ORD_THIEVES];
        thiefAssaultParty = new int[Constants.N_ORD_THIEVES];
    }
    
    /**
     * In the Master Thief life cycle, transition between "planning the heist" and "deciding what to do".
     * The Ordinary Thieves are notified of the start of Operations by the Master Thief when all
     * thieves are on the waiting thieves Queue.
     */
    @Override
    public synchronized void startOperations(){
        while(getNWaitingThieves() < Constants.N_ORD_THIEVES){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyAll();
    }
    
    /**
     * In the Ordinary Thief life cycle, transition between "outside" and "outside".
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * The ordinary Thief is added to a waitingThieves Queue and notify the Master Thief
     * The Ordinary Thief has to wait while he is not assembled to a assalt party and the results aren't ready.
     * @param thiefId - Ordinary Thief Identifier
     * @return true if thieves are needed; Return false otherwise. (Thieves are needed while results aren't ready).
     */
    @Override
    public synchronized boolean amINeeded(int thiefId){
        waitingThieves.add(thiefId);
        notifyAll();
        
        checkThiefToWait(thiefId);
        
        if (!resultsReady)
            return true;
        
        return false;
    }
    
    /**
     * The Ordinary Thief has to wait while he is not assembled to a assalt party and the results aren't ready.
     * @param thiefId - Ordinary Thief Identifier
     */
    public synchronized void checkThiefToWait(int thiefId) {
        
        while(!assaultingThieves[thiefId] && !resultsReady){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        assaultingThieves[thiefId] = false;
    }
    
    /**
     * In the Ordinary Thief life cycle, transition between "outside" and "crawling inwards".
     * The Master Thief is notified when there are enough thieves in order to assemble a assault party.
     * The party is set to ready and the number of thieves waiting to be assembled to a party is set to zero.
     * @param thiefId - Ordinary Thief Identifier
     */
    @Override
    public synchronized void prepareExcursion(int thiefId){
        if(++nThievesInParty == Constants.N_ASSAULT_PARTY_SIZE) {
            partyReady = true;
            nThievesInParty = 0;
            notifyAll();
        }
    }
    
    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "assembling a group".
     * The Ordinary Thieves that are waiting to be set to a party are notified by the Master Thief that now they 
     * are assembled to a party and no longer are on the waiting thieves Queue. 
     * @param partyId - Assault Party Identifier
     */
    @Override
    public synchronized void prepareAssaultParty(int partyId){
        
        while(waitingThieves.size() < Constants.N_ASSAULT_PARTY_SIZE){
            try{
                wait();
            }catch(InterruptedException ex){
                 Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (int i = 0; i < Constants.N_ASSAULT_PARTY_SIZE; i++) {
            int thiefToWake = waitingThieves.poll();
            thiefAssaultParty[thiefToWake] = partyId;
            assaultingThieves[thiefToWake] = true;
        }
        notifyAll();
        
        checkPartyToWait();
    }
    
    /**
     * The Master Thief has to wait until there are enough thieves able to start a new assault party
     * and wakes them when they are ready.
     */
    public synchronized void checkPartyToWait(){
   
        while(!partyReady){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        partyReady = false;
    }

    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "presenting the report".
     * The Master Thief notifies the Ordinary Thieves that the results are now ready.
     */
    @Override
    public synchronized void sumUpResults(){
        
        while(waitingThieves.size() < Constants.N_ORD_THIEVES){
            try{
                wait();
            }catch(InterruptedException ex){
                 Logger.getLogger(ConcentrationSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        resultsReady = true;
        notifyAll();
    }
      
    /**
     * Method that returns the Assault Party Id of the Ordinary Thief
     * @param thiefId - Ordinary Thief Identifier
     * @return the party Id of the Ordinary Thief.
     */
    @Override
    public synchronized int getPartyId(int thiefId){
        return thiefAssaultParty[thiefId];
    }
    
    /**
     * Method that returns the number of waiting thieves on the Queue
     * @return the number of waiting thieves on the Queue.
     */
    @Override
    public synchronized int getNWaitingThieves(){
        return waitingThieves.size();
    }
}
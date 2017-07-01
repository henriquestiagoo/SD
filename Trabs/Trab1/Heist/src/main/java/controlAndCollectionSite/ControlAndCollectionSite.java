/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlAndCollectionSite;

import states.MasterThiefState;
import states.PartyState;
import states.RoomState;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Constants;
import generalRepository.GeneralRepository;

/**
 * ControlAndCollection Site Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ControlAndCollectionSite implements IMasterThief, IOrdinaryThief {

    private int collectedCanvas;
    private int deliveredCanvas;
    private int roomToSteal;
    private int partyToDeploy;
    private final int[] roomStates;
    private final int[] partyStates;
    private final int[] arrivedPartyThieves;
    private boolean masterThiefResting;
    private boolean thiefArrived;
    private final GeneralRepository genRep;

    /**
     * Creates a new ControlAndCollectionSite
     * @param genRep - General Repository Identifier 
     */
    public ControlAndCollectionSite(GeneralRepository genRep) {
        this.genRep = genRep;
        deliveredCanvas = 0;
        collectedCanvas = 0;
        roomToSteal = -1;
        thiefArrived = false;
        masterThiefResting = false;
        arrivedPartyThieves = new int[Constants.N_ASSAULT_PARTIES];
        roomStates = new int[Constants.N_ROOMS];
        partyStates = new int[Constants.N_ASSAULT_PARTIES];
        
        /* Fill all rooms to NOT_VISITED */
        for (int i = 0; i < Constants.N_ROOMS; i++) {
            roomStates[i] = RoomState.NOT_VISITED;
        }
        /* Fill all parties to NOT_DEPLOYED */
        for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
            partyStates[i] = PartyState.NOT_DEPLOYED;
        }     
    }
    
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
    @Override
    public synchronized int appraiseSit(){
              
        if(allRoomsEmpty() && allPartiesNotDeployed()) {
            genRep.setCollectedCanvas(collectedCanvas);
            return MasterThiefState.PRESENTING_THE_REPORT;
        }
            
        roomToSteal = setRoomToSteal();
        partyToDeploy = setPartyToDeploy();

        if(partyToDeploy != -1 && roomToSteal != -1) {
            roomStates[roomToSteal] = RoomState.BEING_STOLEN;
            partyStates[partyToDeploy] = PartyState.ON_FORMATION;
            return MasterThiefState.ASSEMBLING_A_GROUP;
        }           
        
        return MasterThiefState.WAITING_FOR_GROUP_ARRIVAL;   
    }     
    
    /**
     * In Master Thief life cycle, transition between "deciding what to do" and "waiting for
     * group arrival".
     * CollectCanvas by Masther Thief if any ordinary thief has arrived.
     */
    @Override
    public synchronized void takeARest() {
        masterThiefResting = true;
        notifyAll();

        checkMasterThiefToWait();
    }
    
    /**
     * While Ordinary Thief hasn't arrived, the Master Thief is resting.
     * Master Thief wakes up as long as some thief has arrived.
     */
    public synchronized void checkMasterThiefToWait(){
        while (!thiefArrived) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlAndCollectionSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        thiefArrived = false;
    }

    /**
     * In Master Thief life cycle, transition between "waiting for group arrival" and "deciding
     * what to do".
     * Decrease the number of canvas to collect.
     */
    @Override
    public synchronized void collectCanvas() {
        deliveredCanvas--; 
    }

    /**
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * While Master Thief is resting, the Ordinary Thief that has arrived has to wait until he wakes up.
     * When he does, reset that party. If that ordinary thief returns with a canvas, the number of collected
     * canvas is increased by the Master Thief and that room is set to rob again because there are still paitings 
     * left on that room to retrieve. If the ordinary thief doesn't return a canvas it means that the room is now 
     * empty and the room is set to empty.
     * @param hasCanvas - True if thief has canvas; False otherwise
     * @param roomId - Room Identifier
     * @param partyId - Assault Party Identifier
     * @param thiefId - Thief Identifier
     */
    @Override
    public synchronized void handACanvas(boolean hasCanvas, int roomId, int partyId, int thiefId) {
        
        checkOrdinaryThiefToWait(partyId);
        
        if (arrivedPartyThieves[partyId] == Constants.N_ASSAULT_PARTY_SIZE) {
            partyStates[partyId] = PartyState.NOT_DEPLOYED;
            arrivedPartyThieves[partyId] = 0;   /* Reset party */
        }  
            
        if (hasCanvas){
            collectedCanvas++;
            roomStates[roomId] = RoomState.ROB_AGAIN;
        } else
            roomStates[roomId] = RoomState.EMPTY; 
        
        notifyAll();
    }
    
    /**
     * While Master Thief is not resting, the Ordinary Thief that has arrived has to wait until he wakes up.
     * The number of stored canvas and number of thieves on that party is increased and the flags of
     * the thieves are updated.
     * @param partyId - Assault Party Identifier
     */
    public synchronized void checkOrdinaryThiefToWait(int partyId) {
        
        while (!masterThiefResting) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlAndCollectionSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        masterThiefResting = false;
        thiefArrived = true;
        deliveredCanvas++;                     /* Increase the number of canvas to collect */
        arrivedPartyThieves[partyId]++;        /* Increase the number of thieves that have arrived */
    }

    /**
     * Method that returns the room to steal by the Master Thief
     * @return the room to steal by the Master Thief
     */
    @Override
    public synchronized int getRoomToSteal() {
        return this.roomToSteal;
    }

    /**
     * Method that returns the party to deploy by the Master Thief
     * @return the party to deploy by the Master Thief
     */
    @Override
    public synchronized int getPartyToDeploy() {
        return this.partyToDeploy;
    }
    
    /**
     * Method that returns the number of delivered canvas by the Master Thief
     * @return the number of canvas to collect by the Master Thief
     */
    public int getDeliveredCanvas(){
        return this.deliveredCanvas;
    }
    
    /**
     * Method that returns the number of collected canvas by the Master Thief
     * @return the number of collected canvas by the Master Thief
     */
    public int getCollectedCanvas(){
        return this.collectedCanvas;
    }

    /**
     * Method that sets the room to steal by the Master Thief
     * @return the room to steal by the Master Thief (returns -1 if all rooms are empty 
     * or if there are rooms being stolen)
     */
    private int setRoomToSteal() {
        int room = -1;
        for (int i = 0; i < Constants.N_ROOMS; i++) {
            if (roomStates[i] == RoomState.NOT_VISITED || roomStates[i] == RoomState.ROB_AGAIN){
                room = i;
                return room;
            } 
        }
        return room;
    }

    /**
     * Method that sets the room to steal by the Master Thief
     * @return the party to be deployed by the Master Thief (returns -1 if all parties are not deployed 
     * or if there are parties on formation)
     */
    private int setPartyToDeploy() {
        int party = -1;
        for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
            if (partyStates[i] == PartyState.NOT_DEPLOYED) {
                arrivedPartyThieves[i] = 0;
                party = i;
                return party;
            }
        }
        return party;
    }
    
    /**
     * Method that returns if all rooms are set to empty or not
     * @return true if all rooms are empty; Return false otherwise
     */
    private synchronized boolean allRoomsEmpty(){
        for(int i=0; i<Constants.N_ROOMS; i++){
            if(roomStates[i] != RoomState.EMPTY)
                return false;
        }
        return true;
    }
      
    /**
     * Method that returns if all parties are set to not deployed or not
     * @return true if all parties are not deployed; Return false otherwise
     */
    private boolean allPartiesNotDeployed(){
        for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
            if(partyStates[i] != PartyState.NOT_DEPLOYED)
                return false;
        }
        return true;
    }
}
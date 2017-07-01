/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assaultParty;

import java.util.LinkedList;
import main.Constants;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

/**
 * Assault Party Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class AssaultParty implements IMasterThief, IOrdinaryThief {

    private final int assaultPartyId;
    private int thiefToCrawl = -1;
    private int roomToSteal;
    private int roomDistance;
    private int thiefPartyPosition;
    private int nThievesReadyToCrawlOut;
    private boolean roomReached = false;
    private boolean outsideReached = false;
    private final int[] thievesPositions;
    private final Queue<ThiefInfo> crawlingThieves; 
    private ThiefInfo thiefInfo;
    private final generalRepository.IAssaultParty genRep;
    public HashMap<Integer,Integer> posLogger = new HashMap<>();    

    /**
     * Creates a new Assault Party.
     * @param assaultPartyId - Assault Party Identifier
     * @param genRep - General Repository Interface Identifier 
     */
    public AssaultParty(int assaultPartyId, generalRepository.IAssaultParty genRep) {
        this.assaultPartyId = assaultPartyId;
        this.genRep = genRep;
        nThievesReadyToCrawlOut = 0;
        crawlingThieves = new LinkedList<>();
        thievesPositions = new int[Constants.N_ASSAULT_PARTY_SIZE];
    }

    /**
     * In the Master Thief life cycle, transition between "assembling a group" and "deciding what to do".
     * Signal the first Ordinary Thief that joined the assault party to start crawling inwards.
     */
    @Override
    public synchronized void sendAssaultParty() {
        /* Retrieves the head (first thiefToCrawl) of the crawlingThieves Queue. */
        thiefToCrawl = crawlingThieves.peek().getId();
        notifyAll();
        genRep.setRoomIdAP(assaultPartyId, roomToSteal);
    }
    
    /**
     * Method that assigns a room to steal to a Assault Party
     * @param roomId - Room Identifier
     * @param roomDistance - Room distance
     */
    @Override
    public void setAssaultInfo(int roomId, int roomDistance) {
        this.roomToSteal = roomId;
        this.roomDistance = roomDistance;
        this.roomReached = false;
        this.nThievesReadyToCrawlOut=0;
        this.outsideReached = false;
        this.posLogger = new HashMap<>();
        clearAssaultParty();
    }

    /**
     * In the Ordinary Thief life cycle, transition between "crawling inwards" and "crawling inwards".
     * Method to signal a Thief to crawl inwards a room. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the room.
     * Same process implemented on crawlOut method.
     * @param thiefId - Thief Identifier
     */
    @Override
    public synchronized void crawlIn(int thiefId) {

        while (!roomReached) {
            while (!roomReached && thiefToCrawl != thiefId) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AssaultParty.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (roomReached) {
                return;
            }
            
            thiefInfo = crawlingThieves.poll();
            thievesPositions[thiefInfo.getPartyPosition()]++;
            crawlingThieves.add(thiefInfo);
            thiefToCrawl = (crawlingThieves.peek()).getId();
            /* Check if all thieves have arrived to the room */
            int sum = 0;
            for(int i = 0; i < thievesPositions.length; i++)
                sum += thievesPositions[i];

            if (sum == roomDistance * Constants.N_ASSAULT_PARTY_SIZE) {
                roomReached = true;
                thiefToCrawl = -1;
            }
            genRep.setPositionAP(assaultPartyId, posLogger.get(thiefId),thievesPositions[thiefInfo.getPartyPosition()]);
            notifyAll();
        }
    }
    
    /**
     * In the Ordinary Thief life cycle, transition between "at a room" and "crawling outwards". 
     * Signal the first Ordinary Thief that joined the assault party to start crawling outwards.
     * @param thiefId - Thief Identifier
     */
    @Override
    public synchronized void reverseDirection(int thiefId) {
        //roomReached = false;
        if (++nThievesReadyToCrawlOut == Constants.N_ASSAULT_PARTY_SIZE) {
            thiefToCrawl = crawlingThieves.peek().getId();
            notifyAll();
        }
    }

    /**
     * In the Ordinary Thief life cycle, transition between "crawling outwards" and "crawling outwards".
     * Method to signal a Thief to crawl outwards outside. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the outside.
     * Same process implemented on crawlIn method.
     * @param thiefId - Thief Identifier
     */
    @Override
    public synchronized void crawlOut(int thiefId) {
        
        while (!outsideReached) {
            while (!outsideReached && thiefToCrawl != thiefId) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AssaultParty.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (outsideReached){ 
                return;
            }    

            thiefInfo = crawlingThieves.poll();
            thievesPositions[thiefInfo.getPartyPosition()]--;
            genRep.setPositionAP(assaultPartyId, posLogger.get(thiefId),thievesPositions[thiefInfo.getPartyPosition()]);
            crawlingThieves.add(thiefInfo);
            thiefToCrawl = (crawlingThieves.peek()).getId();

            /* Check if all thieves have arrived to the outside */
            int sum = 0;
            for(int i = 0; i < thievesPositions.length; i++) 
                sum += thievesPositions[i];
            
            if (sum == 0) {
                outsideReached = true;
                thiefToCrawl = -1;
            }    
            notifyAll();
        }
    }

    /**
     * The Master Thief builds a Assault Party and assemble the thieves on the correct position.
     * @param thiefId - Thief Identifier
     * @param thiefSpeed - Thief Speed
     */
    @Override
    public synchronized void buildParty(int thiefId, int thiefSpeed) {
        ThiefInfo ti = new ThiefInfo(thiefId, thiefSpeed, thiefPartyPosition);
        crawlingThieves.add(ti);
        posLogger.put(thiefId,thiefPartyPosition);
        thiefPartyPosition++;
        genRep.setThiefSituation(thiefId, "P");
        genRep.setMemberIdAP(assaultPartyId, posLogger.get(thiefId), thiefId );
    }
    
    /**
     * Method that resets the Assault Party when reaches the outside.
     */
    public synchronized void clearAssaultParty(){
       this.crawlingThieves.clear();
       for (int i = 0; i < thievesPositions.length; i++) {
           thievesPositions[i] = 0;
       }
       thiefPartyPosition = 0;
    }

    /**
     * Method that returns the room to steal by the Assault Party
     * @return the room to steal by the Assault Party.
     */
    @Override
    public synchronized int getRoomToSteal() {
        return this.roomToSteal;
    }
    
    /**
     * Method that returns the position of the thief id on the Assault Party 
     * @param thiefId - Thief Identifier.
     * @return the position of the thief id on the Assault Party 
     */
    @Override
    public synchronized int getPosLogger(int thiefId) {
        /* Finta do Gelson sobre o Eliseu */
        if(posLogger.size() < 3)
            return 0;
        return this.posLogger.get(thiefId);
    }
}

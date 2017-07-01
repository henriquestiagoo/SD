/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import states.OrdinaryThiefState;
import generalRepository.GeneralRepository;

/**
 * Ordinary Thief Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class OrdinaryThief extends Thread {

    private final int id;
    private final int speed;
    private int state;
    private int partyId;
    private int roomId;
    private boolean canvas;
    private int thiefPositionAP;
    private final museum.IOrdinaryThief museum;
    private final concentrationSite.IOrdinaryThief concSite;
    private final controlAndCollectionSite.IOrdinaryThief contCollSite;
    private final assaultParty.IOrdinaryThief[] assaultParty;
     private final GeneralRepository genRep;

    /**
     * It will be passed to the Ordinary Thief the methods of the Concentration Site, 
     * Control And Collection Site and Assault Party that the Ordinary Thief has access.
     * @param id - Ordinary Thief Instance Identifier
     * @param museum - Instance that implements Museum Ordinary Thief methods.
     * @param concSite - Instance that implements ConcentrationSite Ordinary Thief methods.
     * @param contCollSite - Instance that implements ControlAndCollection Site Ordinary Thief methods.
     * @param assaultParty - Instance that implements AssaultParty Ordinary Thief methods.
     * @param genRep - General Repository Instance.
     */
    public OrdinaryThief(int id, museum.IOrdinaryThief museum,
            concentrationSite.IOrdinaryThief concSite,
            controlAndCollectionSite.IOrdinaryThief contCollSite,
            assaultParty.IOrdinaryThief[] assaultParty,GeneralRepository genRep) {
        this.id = id;
        this.speed = Constants.randInt(Constants.MIN_THIEF_SPEED, Constants.MAX_THIEF_SPEED);
        this.museum = museum;
        this.concSite = concSite;
        this.contCollSite = contCollSite;
        this.assaultParty = assaultParty;
        this.state = OrdinaryThiefState.OUTSIDE;
        this.genRep = genRep;
        this.genRep.setThiefSpeed(id, speed);
    }
    
    /**
     * Ordinary Thieves Life Cycle
     */
    @Override
    public void run(){
        while(true){
            genRep.setThiefState(id, state);
            switch(state){
                case OrdinaryThiefState.OUTSIDE:
                    if(!concSite.amINeeded(id))
                        return;
                    
                    partyId = concSite.getPartyId(id);
                    assaultParty[partyId].buildParty(id, speed);
                    concSite.prepareExcursion(this.id);
                    this.state = OrdinaryThiefState.CRAWLING_INWARDS;
                    break;
                case OrdinaryThiefState.CRAWLING_INWARDS:
                    assaultParty[partyId].crawlIn(this.id);
                    roomId = assaultParty[partyId].getRoomToSteal();
                    this.state = OrdinaryThiefState.AT_A_ROOM;
                    break;
                case OrdinaryThiefState.AT_A_ROOM:
                    canvas = museum.rollACanvas(roomId);
                    assaultParty[partyId].reverseDirection(this.id);
                    thiefPositionAP = assaultParty[partyId].getPosLogger(id);
                    genRep.setCanvasAP(partyId, thiefPositionAP, canvas);
                    this.state = OrdinaryThiefState.CRAWLING_OUTWARDS;
                    break;
                case OrdinaryThiefState.CRAWLING_OUTWARDS:
                    assaultParty[partyId].crawlOut(this.id);
                    contCollSite.handACanvas(canvas, roomId, partyId, this.id);
                    genRep.setCanvasAP(partyId, thiefPositionAP, false);
                    genRep.setThiefSituation(this.id , "W");
                    this.state = OrdinaryThiefState.OUTSIDE;
                    break;
            }     
        }
    }
}

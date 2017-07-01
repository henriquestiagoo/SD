/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import states.MasterThiefState;

/**
 * Master Thief Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class MasterThief extends Thread {

    private int state;
    private final museum.IMasterThief museum;
    private final controlAndCollectionSite.IMasterThief contCollSite;
    private final concentrationSite.IMasterThief concSite;
    private final assaultParty.IMasterThief[] assaultParty;
    private final generalRepository.IMasterThief genRep;

    /**
     * It will be passed to the Master Thief the methods of the Concentration Site, 
     * Control And Collection Site and Assault Party that the Master Thief has access.
     * @param museum - Instance that implements Museum Master Thief methods.
     * @param concSite - Instance that implements ConcentrationSite Master Thief methods.
     * @param contCollSite - Instance that implements ControlAndCollection Site Master Thief methods.
     * @param assaultParty - Instance that implements AssaultParty Master Thief methods.
     * @param genRep - General Repository Instance
     */
    public MasterThief(museum.IMasterThief museum,
            concentrationSite.IMasterThief concSite,
            controlAndCollectionSite.IMasterThief contCollSite,
            assaultParty.IMasterThief[] assaultParty, generalRepository.IMasterThief genRep) {
        this.museum = museum;
        this.concSite = concSite;
        this.contCollSite = contCollSite;
        this.assaultParty = assaultParty;
        this.state = MasterThiefState.PLANNING_THE_HEIST;
        this.genRep = genRep;
    }

    /**
     * Master Thief Life Cycle.
     */
    @Override
    public void run() {
        while (true) {
             genRep.setMThiefState(state);
            switch (state) {
                case MasterThiefState.PLANNING_THE_HEIST:
                    concSite.startOperations();
                    state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;
                case MasterThiefState.DECIDING_WHAT_TO_DO:
                    this.state = contCollSite.appraiseSit();
                    break;
                case MasterThiefState.ASSEMBLING_A_GROUP:
                    int roomToSteal = contCollSite.getRoomToSteal();
                    int partyId = contCollSite.getPartyToDeploy();
                    int roomDistance = museum.getRoomDistance(roomToSteal);
                    assaultParty[partyId].setAssaultInfo(roomToSteal, roomDistance);
                    concSite.prepareAssaultParty(partyId);
                    assaultParty[partyId].sendAssaultParty();
                    state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;
                case MasterThiefState.WAITING_FOR_GROUP_ARRIVAL:
                    contCollSite.takeARest();
                    contCollSite.collectCanvas();
                    this.state = MasterThiefState.DECIDING_WHAT_TO_DO;
                    break;
                case MasterThiefState.PRESENTING_THE_REPORT:
                    genRep.writeEnd();
                    concSite.sumUpResults();
                    return;
            }
        }
    }
}

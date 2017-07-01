/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import assaultParty.AssaultParty;
import concentrationSite.ConcentrationSite;
import controlAndCollectionSite.ControlAndCollectionSite;
import generalRepository.GeneralRepository;
import museum.Museum;

/**
 * Heist To The Museum Main.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class Main {
    
    /* P1G3 */
    private static GeneralRepository generalRepo;
    private static Museum museum;
    private static ControlAndCollectionSite contCollSite;
    private static ConcentrationSite concSite;
    private static AssaultParty[] assaultParty;
    private static final int N_RUNS = 1;
    
    public static void main(String[] args) {
        for (int j = 1; j <= N_RUNS; j++) {

            /* Monitors initialization */
            generalRepo = new GeneralRepository();
            museum = new Museum(generalRepo);
            contCollSite = new ControlAndCollectionSite(generalRepo);
            concSite = new ConcentrationSite();
            assaultParty = new AssaultParty[Constants.N_ASSAULT_PARTIES];
            for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
                assaultParty[i] = new AssaultParty(i,generalRepo);
            }

            /* Entities initialization */
            MasterThief masterThief = new MasterThief((museum.IMasterThief) museum, (concentrationSite.IMasterThief) concSite, (controlAndCollectionSite.IMasterThief) contCollSite, (assaultParty.IMasterThief[]) assaultParty, generalRepo);
            OrdinaryThief[] ordinaryThieves = new OrdinaryThief[Constants.N_ORD_THIEVES];
            for (int i = 0; i < Constants.N_ORD_THIEVES; i++) {
                ordinaryThieves[i] = new OrdinaryThief(i, (museum.IOrdinaryThief) museum, (concentrationSite.IOrdinaryThief) concSite, (controlAndCollectionSite.IOrdinaryThief) contCollSite, (assaultParty.IOrdinaryThief[]) assaultParty, generalRepo);
            }

            /* Start of the entities */
            for (int i = 0; i < Constants.N_ORD_THIEVES; i++) {
                ordinaryThieves[i].start();
            }
            masterThief.start();

            try {
                /* Wait for entities to die */
                for (int i = 0; i < Constants.N_ORD_THIEVES; i++) {
                    ordinaryThieves[i].join();
                }
                masterThief.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("\nHeist completed successfuly number: " + j);
        }
    }
}

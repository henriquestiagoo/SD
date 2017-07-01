/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import configuration.ConfProxy;
import java.util.ArrayList;
import java.util.List;

/**
 * OrdinaryThief Run.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class OrdinaryThiefRun {
    
    /* P1G3 */
    private static ConfProxy confProxy;
    private static ConcentrationSiteProxy concSiteProxy;
    private static GeneralRepositoryProxy generalRepoProxy;
    private static ControlAndCollectionSiteProxy contCollSiteProxy;
    private static MuseumProxy museumProxy;
    private static AssaultPartyProxy assaultPartyProxy[];
    
    public static void main(String [] args) {    
        
        confProxy = new ConfProxy();
        int ports[] = {confProxy.getServerPorts().get("AssaultParty0"), confProxy.getServerPorts().get("AssaultParty1")};
        String hosts[] = {confProxy.getServerHosts().get("AssaultParty0"), confProxy.getServerHosts().get("AssaultParty1")};
        concSiteProxy = new ConcentrationSiteProxy();
        generalRepoProxy = new GeneralRepositoryProxy();
        contCollSiteProxy = new ControlAndCollectionSiteProxy(generalRepoProxy);    
        museumProxy = new MuseumProxy(generalRepoProxy);
        assaultPartyProxy = new AssaultPartyProxy[Constants.N_ASSAULT_PARTIES];
        
        for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
            assaultPartyProxy[i] = new AssaultPartyProxy(ports[i], hosts[i], generalRepoProxy);
        }      
        
        /* Create ordThieves and add to the List */
        List<OrdinaryThief> ordinaryThieves = new ArrayList<>(); 
        
        for(int i = 0; i < Constants.N_ORD_THIEVES; i++){
            ordinaryThieves.add(new OrdinaryThief(i, (museum.IOrdinaryThief) museumProxy, (concentrationSite.IOrdinaryThief) concSiteProxy, 
                    (controlAndCollectionSite.IOrdinaryThief) contCollSiteProxy, (assaultParty.IOrdinaryThief[]) assaultPartyProxy, 
                        (generalRepository.IOrdinaryThief) generalRepoProxy));
        }
               
        System.out.println("Number of Ordinary Thieves: " + ordinaryThieves.size());
        
        for(int i = 0; i < ordinaryThieves.size(); i++){
            ordinaryThieves.get(i).start();
            System.out.printf("OrdinaryThief start: %d\n", i);
        }
        
        for(int i = 0; i < ordinaryThieves.size(); i++){
            try {
                ordinaryThieves.get(i).join();
                System.out.printf("OrdinaryThief join: %d\n", i);
            } catch(InterruptedException e) {}
            
        }
        System.out.println("End of Operations!");
    }
}

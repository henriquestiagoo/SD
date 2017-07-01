/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import configuration.ConfProxy;
import comInf.message.Message;
import comInf.message.MessageType;
import comInf.proxy.ClientProxy;

/**
 * MasterThief Run.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class MasterThiefRun {
    
    /* P1G3 */
    private static GeneralRepositoryProxy generalRepoProxy;
    private static ConcentrationSiteProxy concSiteProxy;
    private static ControlAndCollectionSiteProxy contCollSiteProxy;
    private static MuseumProxy museumProxy;
    private static AssaultPartyProxy[] assaultPartyProxy;
    private static ConfProxy confProxy;
    
    public static void main(String [] args) {
        
        confProxy = new ConfProxy();
        int ports[] = {confProxy.getServerPorts().get("AssaultParty0"), confProxy.getServerPorts().get("AssaultParty1")};
        String hosts[] = {confProxy.getServerHosts().get("AssaultParty0"), confProxy.getServerHosts().get("AssaultParty1")};
        generalRepoProxy = new GeneralRepositoryProxy();
        concSiteProxy = new ConcentrationSiteProxy();
        contCollSiteProxy = new ControlAndCollectionSiteProxy(generalRepoProxy);    
        museumProxy = new MuseumProxy(generalRepoProxy);
        assaultPartyProxy = new AssaultPartyProxy[Constants.N_ASSAULT_PARTIES];
        
        for (int i = 0; i < Constants.N_ASSAULT_PARTIES; i++) {
            assaultPartyProxy[i] = new AssaultPartyProxy(ports[i], hosts[i], generalRepoProxy);
        }      
       
        MasterThief masterThief = new MasterThief((museum.IMasterThief) museumProxy, (concentrationSite.IMasterThief) concSiteProxy, 
                (controlAndCollectionSite.IMasterThief) contCollSiteProxy, (assaultParty.IMasterThief[]) assaultPartyProxy,
                    (generalRepository.IMasterThief) generalRepoProxy);
        
        System.out.println("Number of Master Thieves: 1 ");
       
        masterThief.start();
        System.out.printf("MasterThief start: \n");
        
        try { 
            masterThief.join ();
            System.out.printf("MasterThief join: \n");
        } catch (InterruptedException e) {}
        
        System.out.println("Sending END_OPERATIONS Message to the Logger!!");
        
        /* Master Thief orders the end of Operations */
        ClientProxy.connect(confProxy.getServerHosts().get("GeneralRepository"), 
                            confProxy.getServerPorts().get("GeneralRepository"),
                            new Message(MessageType.END_OPERATIONS));
        
        System.out.println("End of Operations!");
    }
}

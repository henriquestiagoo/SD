/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import interfaces.AssaultPartyInterface;
import interfaces.ConcentrationSiteInterface;
import interfaces.ControlAndCollectionSiteInterface;
import interfaces.GeneralRepositoryInterface;
import interfaces.MuseumInterface;
import interfaces.Register;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import structures.Constants;
import static structures.Constants.getNameEntry;

/**
 * @author Ricardo Filipe 72727
 * @author Tiago Henriques 73046
 * @author Miguel Oliveira 72638
 */
public class OrdinaryThievesStart {

    private static int N_ORD_THIEVES;
    private static int N_ASSAULT_PARTY_SIZE;
    private static int MAX_SPEED;
    private static int MIN_SPEED;
    private static String rmiServerHostname;
    private static int rmiServerPort;

    /**
     * Start the Ordinary Thief Life.
     *
     * @param args args
     */
    public static void main(String args[]) {
        
        System.out.println("Starting Ordinary Thieves");
        
        Scanner sc = new Scanner(System.in);
        N_ORD_THIEVES = Constants.N_ORD_THIEVES;
        N_ASSAULT_PARTY_SIZE = Constants.N_ASSAULT_PARTIES;
        MAX_SPEED = Constants.MAX_THIEF_SPEED;
        MIN_SPEED = Constants.MIN_THIEF_SPEED;
        rmiServerHostname = args[0];
        rmiServerPort = Integer.parseInt(args[1]);

        /* Create ordThieves List */
        List<OrdinaryThief> ordThieves = new ArrayList<>();
        /* get location of the generic registry service */
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register reg = getRegister(registry);

        GeneralRepositoryInterface genRepInterface = getGeneralRepository(registry);
        ConcentrationSiteInterface concSiteInterface = getConcentrationSite(registry);;
        ControlAndCollectionSiteInterface contCollSiteInterface = getControlAndCollectionSite(registry);
        MuseumInterface museumInterface = getMuseum(registry);
        AssaultPartyInterface assaultPartyInterface[] = new AssaultPartyInterface[N_ASSAULT_PARTY_SIZE];
        
        for(int i=0; i<N_ASSAULT_PARTY_SIZE; i++)
            assaultPartyInterface[i] = getAssaultParty(registry, "AssaultParty"+i);
        
        for(int i=0; i<N_ORD_THIEVES; i++){
            ordThieves.add(new OrdinaryThief(i, MAX_SPEED, MIN_SPEED, museumInterface, concSiteInterface, 
                    contCollSiteInterface, assaultPartyInterface, genRepInterface));
        }

        System.out.println("\nNumber of ordinary thieves: " +ordThieves.size());  
       
        for(int i = 0; i < ordThieves.size(); i++){
            ordThieves.get(i).start();
            System.out.printf("\nOrdinaryThief start: %d", i);
        }
        
        for(int i = 0; i < ordThieves.size(); i++){
            try {
                ordThieves.get(i).join();
                System.out.printf("\nOrdinaryThief join: %d", i);
            } catch(InterruptedException e) {}
        }
        
        /* print the result */
        System.out.println("Done Ordinary Thieves!!");
    }

    /**
     * This function is used to register it with the local registry service.
     * @param rmiServerHostname Rmi Server Host Name.
     * @param rmiServerPort Rmi Server port.
     * @return registry.
     */
    private static Registry getRegistry(String rmiServerHostname, int rmiServerPort) {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(rmiServerHostname, rmiServerPort);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca getRegistry");
            System.exit(1);
        }
        System.out.println("O registo RMI foi criado!");
        return registry;
    }

    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @return the register reg.
    */
    private static Register getRegister(Registry registry) {
        Register reg = null;
        String xmlFile = Constants.xmlFile;
        try {
            reg = (Register) registry.lookup(getNameEntry("Rmi", xmlFile));
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        }
        return reg;
    }

    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @return the general repository interface.
    */
    private static GeneralRepositoryInterface getGeneralRepository(Registry registry) {
        GeneralRepositoryInterface genRepo = null;
        /* look for the remote object by name in the remote host registry */
        String xmlFile = Constants.xmlFile;
        String nameEntry = getNameEntry("GeneralRepository", xmlFile);

        try {
            /* Locate General Repository */
            genRepo = (GeneralRepositoryInterface) registry.lookup(nameEntry);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca GeneralRepository");
        } catch (NotBoundException ex) {
            System.err.print("Deu bronca GeneralRepository2");
        }
        return genRepo;
    }
    
    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @return the concentration site interface
    */
    private static ConcentrationSiteInterface getConcentrationSite(Registry registry) {
        ConcentrationSiteInterface concSite = null;
        /* look for the remote object by name in the remote host registry */
        String xmlFile = Constants.xmlFile;
        String nameEntry = getNameEntry("ConcentrationSite", xmlFile);

        try {
            /* Locate concentration site */
            concSite = (ConcentrationSiteInterface) registry.lookup(nameEntry);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca ConcentrationSite");
        } catch (NotBoundException ex) {
            System.err.print("Deu bronca ConcentrationSite2");
        }
        return concSite;
    }
    
    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @return the control and collection site interface.
    */
    private static ControlAndCollectionSiteInterface getControlAndCollectionSite(Registry registry) {
        ControlAndCollectionSiteInterface contCollSite = null;
        /* look for the remote object by name in the remote host registry */
        String xmlFile = Constants.xmlFile;
        String nameEntry = getNameEntry("ControlAndCollectionSite", xmlFile);

        try {
            /* Locate control and collection site */
            contCollSite = (ControlAndCollectionSiteInterface) registry.lookup(nameEntry);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca ControlAndCollectionSite");
        } catch (NotBoundException ex) {
            System.err.print("Deu bronca ControlAndCollectionSite2");
        }
        return contCollSite;
    }
    
    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @return the museum interface.
    */
    private static MuseumInterface getMuseum(Registry registry) {
        MuseumInterface museum = null;
        /* look for the remote object by name in the remote host registry */
        String xmlFile = Constants.xmlFile;
        String nameEntry = getNameEntry("Museum", xmlFile);
        
        try {
            /* Locate Museum */
            museum = (MuseumInterface) registry.lookup(nameEntry);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca Museum");
        } catch (NotBoundException ex) {
            System.err.print("Deu bronca Museum2");
        }
        return museum;
    }
    
    /**
    This function us used to return a reference, a stub, for the remote object associated with the specified name.
    * @param registry registry.
    * @param assParty Assault Party String identifier.
    * @return the assault party interface.
    */
    private static AssaultPartyInterface getAssaultParty(Registry registry, String assParty) {
        AssaultPartyInterface assaultParty = null;
        /* look for the remote object by name in the remote host registry */
        String nameEntry = assParty;

        try {
            /* Locate Assault Party */
            assaultParty = (AssaultPartyInterface) registry.lookup(nameEntry);
        } catch (RemoteException ex) {
            System.err.print("Deu bronca AssaultParty");
        } catch (NotBoundException ex) {
            System.err.printf("\nDeu bronca AssaultParty2 - %d", assParty);
        }
        return assaultParty;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitors.GeneralRepository;

import States.MasterThiefStates;
import States.OrdinaryThiefState;
import interfaces.AssaultPartyInterface;
import interfaces.ConcentrationSiteInterface;
import interfaces.ControlAndCollectionSiteInterface;
import interfaces.GeneralRepositoryInterface;
import interfaces.MuseumInterface;
import interfaces.Register;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import monitors.ControlAndCollectionSite.ControlAndCollectionSiteStart;
import structures.Constants;
import static structures.Constants.getHost;
import static structures.Constants.getNameEntry;
import static structures.Constants.getPort;
import structures.VectorClock;

/**
 * @author Ricardo Filipe 72727
 * @author Tiago Henriques 73046
 * @author Miguel Oliveira 72638
 */
public class GeneralRepository implements GeneralRepositoryInterface {

    private static PrintWriter pw;
    private static StringWriter sw;
    private final File log;
    String filename;
    private int masterThiefState;
    private int[] roomId;
    private Room[] rooms;
    private HashMap<Integer, Thief> thiefMap;
    private int[][] partyElement;
    private int canvasCollected = 0;
    private int N_ASSAULT_PARTIES;
    private int N_ROOMS;
    private int ASSAULT_PARTY_SIZE;
    private int N_ORD_THIEVES;
    private boolean DEBUG = false;
    private VectorClock vc;
    private VectorClock clkToSend;
    private HashMap<VectorClock, String> clockLine;
    private final ArrayList<VectorClock> vClocks;

    /**
     * Creates a new General Repository
     * @param logname Name of the File to store the simulation log.
     */
    public GeneralRepository(String logname) {
        N_ASSAULT_PARTIES = Constants.N_ASSAULT_PARTIES;
        N_ROOMS = Constants.N_ROOMS;
        ASSAULT_PARTY_SIZE = Constants.ASSAULT_PARTY_SIZE;
        N_ORD_THIEVES = Constants.N_ORD_THIEVES;
        masterThiefState = MasterThiefStates.PLANNING_THE_HEIST;
        roomId = new int[N_ASSAULT_PARTIES];
        rooms = new Room[N_ROOMS];
        thiefMap = new HashMap<>();
        partyElement = new int[N_ASSAULT_PARTIES][ASSAULT_PARTY_SIZE];
        this.vc = new VectorClock(7, 0); // 1 master + 6 ordinary
        vClocks = new ArrayList<>();
        clockLine = new HashMap<>();

        for (int i = 0; i < roomId.length; i++) {
            roomId[i] = -1;
        }

        for (int i = 0; i < N_ASSAULT_PARTIES; i++) {
            for (int j = 0; j < ASSAULT_PARTY_SIZE; j++) {
                partyElement[i][j] = -1;
            }

        }
        int ts = (int) System.currentTimeMillis();
        filename = logname;
        
        this.log = new File(filename);
        InitializeLog();
    }

    private synchronized void FirstLine(VectorClock vc) {
        Thief currentT;
        sw.write(String.format("\n%4d", masterThiefState));
        for (int i = 0; i < N_ORD_THIEVES; i++) {
            currentT = thiefMap.get(i);
            sw.write(String.format("    %4d %1c %2d", currentT.state, currentT.situation, currentT.speed));
        }
        
        /* Vector Clocks - Assignment 3 */
        String write=  "";
        int[] clocks = vc.getClocks();
        for (int i = 0; i <= Constants.N_ORD_THIEVES; i++) {
            write = write + String.format("   %2d", clocks[i]);
        }
        sw.write(write);
        sw.write("\n");
        
        /* Add to the vector clock array list the clock */
        //VectorClock vcTmp = new VectorClock(write, vc.getClocks());
        //vClocks.add(vcTmp);
    }

    private synchronized void SecondLine() {
        Thief currentT;
        sw.write("   ");
        for (int i = 0; i < N_ASSAULT_PARTIES; i++) {
            if (roomId[i] == -1) {
                sw.write("     ");
            } else {
                sw.write(String.format(" %3d ", roomId[i] + 1));
            }
            for (int j = 0; j < ASSAULT_PARTY_SIZE; j++) {
                if (partyElement[i][j] == -1) {
                    sw.write("           ");
                } else {
                    currentT = thiefMap.get(partyElement[i][j]);
                    sw.write(String.format(" %2d %3d %2d ", currentT.id + 1, currentT.position, currentT.canvas));
                }
            }
        }
        sw.write(" ");
        for (Room room : rooms) {
            sw.write(String.format("    %2d %2d", room.paitings_left, room.distance));
        }
        sw.write("\n");
    }

    /**
     * Update the State of the Ordinary Thief and print the updated Status in
     * the log file.
     * @param thiefId Id of the Ordinary Thief
     * @param state updated State
     * @param vc VectorClock
     * @return VectorClock
     * @throws java.rmi.RemoteException exception
     * @throws java.lang.InterruptedException exception
     */
    @Override
    public synchronized VectorClock updateThiefState(int thiefId, int state, VectorClock vc) throws RemoteException,InterruptedException{
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        Thief t = thiefMap.get(thiefId);
        t.state = state;
        printStatus(vc);
        
        return clkToSend;
    }

    /**
     * Method to update state the Master Thief in the General Repository.
     * @param state Current state the Master Thief.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock updateMThiefState(int state, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        masterThiefState = state;
        printStatus(vc);
        
        return clkToSend;
    }

    /**
     * Method to update state the Thief's situation in the General Repository.
     * @param thiefId Id of the Thief.
     * @param situation Current situation.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock updateThiefSituation(int thiefId, char situation, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        Thief t = thiefMap.get(thiefId);
        t.situation = situation;
        printStatus(vc);
        
        return clkToSend;
    }

    /**
     * Method to set the target room to a specific Assault Party in the General
     * Repository.
     * @param partyId Id of the Party.
     * @param room target room of the Assault Party.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock setRoomIdAP(int partyId, int room, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        roomId[partyId] = room;

        return clkToSend;
    }

    /**
     * Method to set the number of painting stolen in the entire heist in the
     * General Repository.
     * @param toalCanvas Number of canvas "borrowed" from the Museum.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock setCollectedCanvas(int toalCanvas, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        canvasCollected = toalCanvas;

        return clkToSend;
    }

    /**
     * Method to set the Room attributes in the General Repository.
     * @param roomId Id of the Room.
     * @param distance Distance from the outside to the Room.
     * @param paitings Number of paintings present on the room.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock setRoomAtributes(int roomId, int distance, int paitings, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        rooms[roomId] = new Room(paitings, distance);
        
        return clkToSend;
    }

    /**
     * Method to add the Ordinary Thief attributes in the General Repository.
     * @param thiefId Id of the Thief.
     * @param speed Maximum displacement of the Thief.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock addThief(int thiefId, int speed, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        System.out.println("Thief added");
        thiefMap.put(thiefId, new Thief(thiefId, speed));

        return clkToSend;
    }

    /**
     * Method to put a Ordinary Thief a specified Assault Party in the General
     * Repository.
     * @param partyId Id of the Assault Party.
     * @param thiefId Id of the Thief.
     * @param elemId Thief Id in the Assault Party.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock setPartyElement(int partyId, int thiefId, int elemId, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        partyElement[partyId][elemId] = thiefId;
        
        return clkToSend;
    }

    /**
     * Method to clear the elements of a Assault Party in the General
     * Repository.
     * @param partyId Id of the Assault Party.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock clearParty(int partyId, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        for (int i = 0; i < ASSAULT_PARTY_SIZE; i++) {
            partyElement[partyId][i] = -1;
            roomId[partyId] = -1;
        }
        printStatus(vc);

        return clkToSend;
    }

    /**
     * Method to update the position of a Ordinary Thief in the General
     * Repository.
     * @param thiefId Id of the Thief thar Invoked the method.
     * @param position Current position of the Thief.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock updateThiefPosition(int thiefId, int position, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        Thief currentT = thiefMap.get(thiefId);
        currentT.position = position;
        printStatus(vc);

        return clkToSend;
    }

    /**
     * Method to update the contents of a Ordinary Thief cylinder in the General
     * Repository.
     * @param thiefId Id of the thief that invoked the method.
     * @param hasCanvas the state of the thief cylinder.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock updateThiefCylinder(int thiefId, boolean hasCanvas, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        Thief currentT = thiefMap.get(thiefId);
        currentT.canvas = hasCanvas ? 1 : 0;
        printStatus(vc);

        return clkToSend;
    }

    /**
     * Print status.
     * @param vc VectorClock
     */
    private synchronized void printStatus(VectorClock vc) {
        if (DEBUG) {
            return;
        }
        FirstLine(vc);
        SecondLine();
        String s = sw.toString();
        StringBuffer buf = sw.getBuffer();
        System.out.println("PRINTING LINE TO FILE");
        buf.setLength(0);
        clockLine.put(vc, s);
        vClocks.add(vc);
    }

    /**
     * Initialize Log.
     */
    private synchronized void InitializeLog() {
        if (DEBUG) {
            return;
        }
        try {
            pw = new PrintWriter(log);
            sw = new StringWriter();
            pw.println("                               Heist to the Museum - Description of the internal state ");
            pw.println();

            String head = "MstT   ";
            for (int i = 1; i <= 6; i++) {
                head += "Thief " + Integer.toString(i);
                head += "      ";
            }
            head += "               ";
            head += "VCK";
            pw.println(head);

            head = "Stat    ";
            for (int i = 1; i <= N_ORD_THIEVES; i++) {
                head += "Stat S MD";
                head += "    ";
            }
            
            for (int i = 0; i <= N_ORD_THIEVES; i++) {
                head += i;
                head += "    ";
            }
            pw.println(head);

            head = "";
            for (int i = 1; i <= N_ASSAULT_PARTIES; i++) {
                head += "                    Assault party " + Integer.toString(i);
                head += "   ";
            }
            head += "                        Museum";
            pw.println(head);

            head = "           ";
            for (int i = 1; i <= ASSAULT_PARTY_SIZE; i++) {
                head += "Elem " + Integer.toString(i);
                head += "     ";
            }

            head += "     ";
            for (int i = 1; i <= ASSAULT_PARTY_SIZE; i++) {
                head += "Elem " + Integer.toString(i);
                head += "     ";
            }

            head += "  ";
            for (int i = 1; i <= N_ROOMS; i++) {
                head += "Room " + Integer.toString(i);
                head += "   ";
            }

            pw.println(head);

            head = "  ";
            for (int i = 1; i <= N_ASSAULT_PARTIES; i++) {
                head += "  RId";
                for (int j = 1; j <= ASSAULT_PARTY_SIZE; j++) {
                    head += "  Id Pos Cv";
                }
            }
            head += "  ";
            for (int i = 1; i <= N_ROOMS; i++) {
                head += "    NP DT";
            }
            pw.println(head);

            pw.flush();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        System.out.println("Log Initiated");
    }

    /**
     * Method to finalize the log.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock FinalizeLog(VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        if (DEBUG) {
            return clkToSend;
        }

        Collections.sort(vClocks, (VectorClock t, VectorClock t1) -> t.compareTo(t1));
        
        vClocks.forEach((v) -> {
            Object o = clockLine.get(v);
            pw.printf((String)o);
        });
        
        pw.printf("My friends, tonight's effort produced %2d priceless paintings!", canvasCollected);
        pw.println("\nLegend:");
        pw.println("MstT Sta - state of the master thief");
        pw.println("Thief # Stat - state of the ordinary thief # (# - 1 .. 6)");
        pw.println("Thief # S - situation of the ordinary thief  # (# - 1 .. 6) either 'W' (waiting to join a party) or 'P' (in party) ");
        pw.println("Thief # MD - maximum displacement of the ordinary thief # (# - 1 .. 6) a random number between 2 and 6 ");
        pw.println("Assault party # RId - assault party # (# - 1,2) elem # (# - 1 .. 3) room identification (1 .. 5)");
        pw.println("Assault party # Elem # Id - assault party # (# - 1,2) elem # (# - 1 .. 3) member identification (1 .. 6) ");
        pw.println("Assault party # Elem # Pos - assault party # (# - 1,2) elem # (# - 1 .. 3) present position (0 .. DT RId) ");
        pw.println("Assault party # Elem # Cv - assault party # (# - 1,2) elem # (# - 1 .. 3) carrying a canvas (0,1)");
        pw.println("Museum Room # NP - room identification (1 .. 5) number of paintings presently hanging on the walls");
        pw.println("Museum Room # DT - room identification (1 .. 5) distance from outside gathering site, a random number between 15 and 30");
        pw.println("VCK  0 - local clock of the master thief");
        for(int i=1; i<Constants.N_ORD_THIEVES+1; i++){
            pw.printf("VCK  %d - local clock of the ordinary thief %d\n", i, i);
        }
        pw.flush();
        pw.close();
        try {
            sw.close();
        } catch (IOException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("log Completed");
        return clkToSend;
    }

    /**
     * Method to set the number of paintings in the General Repository.
     * @param id Id of the Room
     * @param paitings Number of paintings in the Room.
     * @param vc VectorClock
     * @return VectorClock
     */
    @Override
    public synchronized VectorClock setRoomCanvas(int id, int paitings, VectorClock vc) {
        this.vc.update(vc);
        clkToSend = vc.incrementClock();
        rooms[id].paitings_left = paitings;
        
        return clkToSend;
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
            Logger.getLogger(ControlAndCollectionSiteStart.class.getName()).log(Level.SEVERE, null, ex);
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
     * This function is used for order the General Repository Monitor to shutdown all other Monitors and himself as well.
     * @throws RemoteException may throw during a execution of a remote method call
     */
    @Override
    public void terminateServers() throws RemoteException{
        
        System.out.println("Terminating Monitors...");
        /* Just for test - Put in a file for example */
        String xmlFile = Constants.xmlFile;
        String rmiServerHostname = getHost("Rmi", xmlFile);
        int rmiServerPort = getPort("Rmi", xmlFile);
        String nameEntryObject = getNameEntry("GeneralRepository", xmlFile); 
        
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        
        /* Shutdown Concentration Site */
        try
        {
            ConcentrationSiteInterface concSiteInterface = (ConcentrationSiteInterface) registry.lookup (getNameEntry("ConcentrationSite", xmlFile));
            concSiteInterface.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating concentration site: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Concentration Site is not registered: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        
        /* Shutdown Control And Collection Site */
        try
        {
            ControlAndCollectionSiteInterface contCollSiteInterface = (ControlAndCollectionSiteInterface) registry.lookup (getNameEntry("ControlAndCollectionSite", xmlFile));
            contCollSiteInterface.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating control and collection site: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Control And Collection Site is not registered: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        
        /* Shutdown Assault Parties */
        for(int i=0; i<Constants.N_ASSAULT_PARTIES; i++){
            try{
                AssaultPartyInterface assaultPartyInterface = (AssaultPartyInterface) registry.lookup (getNameEntry("AssaultParty"+i, xmlFile));
                assaultPartyInterface.signalShutdown();
            } catch (RemoteException e) { 
                System.out.println("Exception thrown while locating assault party: " + e.getMessage () + "!");
                Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
            }
            catch (NotBoundException e)
            { 
                System.out.println("AssaultParty"+i+" is not registered: " + e.getMessage () + "!");
                Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
            }
        }        
        
        /* Shutdown Museum */
        try
        {
            MuseumInterface museumInterface = (MuseumInterface) registry.lookup (getNameEntry("Museum", xmlFile));
            museumInterface.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating museum: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Museum is not registered: " + e.getMessage () + "!");
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }        
        
       /* Shutdown General Repository */
        Register reg = getRegister(registry);
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("GeneralRepository registration exception: " + e.getMessage());
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("GeneralRepository not bound exception: " + e.getMessage());
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(GeneralRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("General Repository Log written succesfully!!");
    }

    private class Room {

        private Room(int paitings, int distance) {
            this.distance = distance;
            this.paitings_left = paitings;
        }
        private int paitings_left, distance;
    }

    private class Thief {

        private int id, position, canvas, state, speed;
        private char situation;

        private Thief(int id, int speed) {
            this.id = id;
            this.position = 0;
            this.speed = speed;
            this.situation = 'W';
            this.canvas = 0;
            this.state = OrdinaryThiefState.OUTSIDE;
        }
    }
}

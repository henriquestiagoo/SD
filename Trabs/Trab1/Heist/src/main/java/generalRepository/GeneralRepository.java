/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

import states.MasterThiefState;
import states.OrdinaryThiefState;
import com.sun.javafx.binding.Logging;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Constants;

/**
 * General Repository Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class GeneralRepository {
    
    private static PrintWriter pw;
    private final File log;
    private int masterThiefState;
    private int totalCanvas = 0;
    private final int[] thiefPositions;
    private final int[] thiefStates;
    private final int[] thiefSpeed;
    private final int[] roomId;
    private final int[][] memberId;
    private final int[][] collectedCanvas;
    private final int[][] position;
    private final int[] roomIdAp;
    private final int[] roomDist;
    private final String[] thiefSituation;
    
    /**
     * Creates a new General Repository.
     */
    public GeneralRepository(){
        masterThiefState = MasterThiefState.PLANNING_THE_HEIST;
        thiefPositions = new int[Constants.N_ORD_THIEVES];
        thiefSpeed = new int[Constants.N_ORD_THIEVES];
        thiefStates = new int[Constants.N_ORD_THIEVES];
        thiefSituation = new String[Constants.N_ORD_THIEVES];
        roomId = new int[Constants.N_ASSAULT_PARTIES];
        memberId = new int[Constants.N_ASSAULT_PARTIES][Constants.N_ASSAULT_PARTY_SIZE];
        collectedCanvas = new int[Constants.N_ASSAULT_PARTIES][Constants.N_ASSAULT_PARTY_SIZE];
        position = new int[Constants.N_ASSAULT_PARTIES][Constants.N_ASSAULT_PARTY_SIZE];
        roomIdAp = new int[Constants.N_ROOMS];
        roomDist = new int[Constants.N_ROOMS];  
        
        /* Fill all thieves states to outside */
        for(int i = 0; i < thiefStates.length;i++)
            thiefStates[i] = OrdinaryThiefState.OUTSIDE;
        
        /* Fill all thieves positions to zero */
        for(int i = 0; i < thiefPositions.length; i++){
            thiefPositions[i] = 0;
        }
        
        /* Fill all thieves situation to W (Waiting) */
        for(int i = 0; i < thiefSituation.length; i++){
            thiefSituation[i] = "W";
        }
        
        /* Fill all room Identifiers to zero */
        for(int i = 0; i < roomId.length; i++){
            roomId[i] = 0;
        }
        
        /* Fill all members Identifiers to negative one */
        for(int i = 0; i < Constants.N_ASSAULT_PARTIES; i++){
            for(int j = 0; j <  Constants.N_ASSAULT_PARTY_SIZE;j++)
                memberId[i][j] = -1;
        }
        
        /* Fill all collected canvas to zero */
        for(int i = 0; i < Constants.N_ASSAULT_PARTIES; i++){
            for(int j = 0; j <  Constants.N_ASSAULT_PARTY_SIZE;j++)
                collectedCanvas[i][j] = 0;
        }
        
        /* Fill all positions to zero */
        for(int i = 0; i < Constants.N_ASSAULT_PARTIES; i++){
            for(int j = 0; j <  Constants.N_ASSAULT_PARTY_SIZE;j++)
                position[i][j] = 0;
        }

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename = "HeistMuseum_73046_72638_" + date.format(today) + ".log";

        this.log = new File(filename);
        writeInit();
    }
    
    /**
     * Method that prints the first Log line (Status Master and Ordinary Thieves Information).
     */
    public synchronized void FirstLine(){
        pw.printf("\n%4d", masterThiefState);
        for(int i=0; i< Constants.N_ORD_THIEVES; i++){
            pw.printf("    %4d %1s %2d", thiefStates[i], thiefSituation[i] , thiefSpeed[i]);
        }
        pw.println();
        SecondLine();
    }
    
    /**
     * Method that prints the second Log line (Assault Party elements and Rooms Information).
     */
    public synchronized void SecondLine(){
        pw.println();
        pw.printf("   ");
        for(int i =0; i < Constants.N_ASSAULT_PARTIES; i++){
            pw.printf(" %3d ", roomId[i]);
            for(int j = 0; j < Constants.N_ASSAULT_PARTY_SIZE; j++){
                if(memberId[i][j] == -1)
                     pw.printf("           ");
                else
                    pw.printf(" %2d %3d %2d ", memberId[i][j], position[i][j], collectedCanvas[i][j]);
            }
        }
        pw.printf(" ");
        for (int h = 0; h < Constants.N_ROOMS; h++) {
            pw.printf("    %2d %2d", roomIdAp[h], roomDist[h]);
        }
        
        pw.println();
    }
    
    /**
     * Method that sets the ordinary thief state.
     * @param thiefId - Thief Identifier
     * @param state - State Identifier
     */
    public synchronized void setThiefState(int thiefId, int state ){
        thiefStates[thiefId] = state;
        FirstLine();  
    }
    
    /**
     * Method that sets the master thief state.
     * @param state - State Identifier
     */
    public synchronized void setMThiefState(int state ){
        masterThiefState = state;
        FirstLine();  
    }
    
     /**
     * Method that sets the ordinary thief speed.
     * @param thiefId - Thief Identifier.
     * @param speed - Thief Speed.
     */
    public synchronized void setThiefSpeed(int thiefId, int speed ){
        thiefSpeed[thiefId] = speed;
        //FirstLine();
    }
    
    /**
     * Method that sets the ordinary thief situation.
     * @param thiefId - Thief Identifier.
     * @param situation - Thief Situation.
     */
    public synchronized void setThiefSituation(int thiefId, String situation ){
      thiefSituation[thiefId] = situation;
      FirstLine();   
    }
    
    /**
     * Method that sets the room for the party
     * @param partyId - Party Identifier.
     * @param room - Room Identifier
     */
    public synchronized void setRoomIdAP(int partyId,int room){
        roomId[partyId] = room;
        FirstLine();
    }
    
    /**
     * Method that sets members on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param member - Member Identifier.
     */
    public synchronized void setMemberIdAP(int partyId, int thiefId, int member){
        memberId[partyId][thiefId] = member+1;
        FirstLine();
    }
    
    /**
     * Method that sets the thieves positions on the Assault Party
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param pos - Thief Position on Assault Party
     */
    public synchronized void setPositionAP(int partyId, int thiefId, int pos){
        position[partyId][thiefId] = pos;
        FirstLine();
    }
    
    /**
     * Method that sets the canvas on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param canvas - Canvas (True if has canvas; False otherwise).
     */
    public synchronized void setCanvasAP(int partyId, int thiefId, boolean canvas){
        if(canvas)
            collectedCanvas[partyId][thiefId] = 1;
        else
            collectedCanvas[partyId][thiefId] = 0;
        //FirstLine();
    }
    
    /**
     * Method that sets the number of paitings of a room.
     * @param roomId - Room Identifier.
     * @param paintings - Number of paitings.
     */
    public synchronized void setNPaintings(int roomId, int paintings){
        roomIdAp[roomId] = paintings;
        //FirstLine();
    }
    
    /**
     * Method that sets the rooms distances.
     * @param roomId - Room Identifier.
     * @param dist - Room Distance.
     */
    public synchronized void setDistPaintings(int roomId,int dist){
        roomDist[roomId] = dist;
    }
    
    /**
     * Method that sets the number of canvas.
     * @param nCanvas - Number of canvas.
     */
    public synchronized void setCollectedCanvas(int nCanvas){
        totalCanvas = nCanvas;
    }
    
    /**
     * Method that prints the Log Header.
     */
    private void writeInit(){
        try{
            pw = new PrintWriter(log);
            pw.println("                               Heist to the Museum - Description of the internal state ");
            pw.println();
            
            String head = "MstT   ";
            for(int i=1; i<=6; i++){
                head += "Thief " + Integer.toString(i);
                head += "      ";
            }
            pw.println(head);
            
            head = "Stat  ";
            for(int i=1; i<=Constants.N_ORD_THIEVES; i++){
                head += "Stat S MD";
                head += "    ";
            }
            pw.println(head);
            
            head="";
            for(int i=1; i<=Constants.N_ASSAULT_PARTIES; i++){
                head += "                    Assault party " + Integer.toString(i);
                head += "   ";
            }
            head+= "                        Museum";
            pw.println(head);
            
            
            head= "           "; 
            for(int i=1; i<= Constants.N_ASSAULT_PARTY_SIZE; i++){
                head += "Elem " +Integer.toString(i);
                head += "     ";
            }
            
            head += "     ";
            for(int i=1; i<= Constants.N_ASSAULT_PARTY_SIZE; i++){
                head += "Elem " +Integer.toString(i);
                head += "     ";
            }
            
            head +="  ";
            for(int i=1; i<= Constants.N_ROOMS; i++){
                head += "Room " +Integer.toString(i);
                head += "   ";
            }
            
            pw.println(head);

            head="  ";
            for(int i=1; i<= Constants.N_ASSAULT_PARTIES; i++){
                head += "  RId";
                for(int j=1; j<=Constants.N_ASSAULT_PARTY_SIZE; j++){     
                    head += "  Id Pos Cv";
                }
            }
            head +="  ";
            for(int i=1; i<=Constants.N_ROOMS; i++){
                head += "    NP DT";
            }
            pw.println(head);             
                        
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method that prints the Log footer.
     */
    public synchronized void writeEnd(){
        pw.println();
        pw.printf("My friends, tonight's effort produced %2d priceless paintings!\n", totalCanvas);
        pw.println();
        pw.println("\nLegend:");
        pw.println("MstT Sta – state of the master thief");
        pw.println("Thief # Stat - state of the ordinary thief # (# - 1 .. 6)");
        pw.println("Thief # S – situation of the ordinary thief  # (# - 1 .. 6) either 'W' (waiting to join a party) or 'P' (in party) ");
        pw.println("Thief # MD – maximum displacement of the ordinary thief # (# - 1 .. 6) a random number between 2 and 6 ");
        pw.println("Assault party # RId – assault party # (# - 1,2) elem # (# - 1 .. 3) room identification (1 .. 5)");
        pw.println("Assault party # Elem # Id – assault party # (# - 1,2) elem # (# - 1 .. 3) member identification (1 .. 6) ");
        pw.println("Assault party # Elem # Pos – assault party # (# - 1,2) elem # (# - 1 .. 3) present position (0 .. DT RId) ");
        pw.println("Assault party # Elem # Cv – assault party # (# - 1,2) elem # (# - 1 .. 3) carrying a canvas (0,1)");
        pw.println("Museum Room # NP - room identification (1 .. 5) number of paintings presently hanging on the walls");
        pw.println("Museum Room # DT - room identification (1 .. 5) distance from outside gathering site, a random number between 15 and 30");
        pw.flush();
        pw.close();
    }
}

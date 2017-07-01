/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import configuration.ConfProxy;
import comInf.message.Message;
import comInf.message.MessageType;
import comInf.message.MessageContainer;
import comInf.proxy.ClientProxy;
import generalRepository.IAssaultParty;
import generalRepository.IControlAndCollectionSite;
import generalRepository.IMasterThief;
import generalRepository.IMuseum;
import generalRepository.IOrdinaryThief;

/**
 * General Repository Proxy to make requests to the General Repository Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class GeneralRepositoryProxy implements IMasterThief, IOrdinaryThief, IAssaultParty, IControlAndCollectionSite, IMuseum {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private int[] args;
    
    /**
    * General Repository Proxy Constructor.
    */
    public GeneralRepositoryProxy(){
        ConfProxy confProxy = new ConfProxy();
        this.SERVER_HOST = confProxy.getServerHosts().get("GeneralRepository");
        this.SERVER_PORT = confProxy.getServerPorts().get("GeneralRepository");
        this.args = null;
    }
    
    /**
    * Method that communicates with the Server in order to make a connection.    
    * @param m - Message Identifier
    */
    private MessageContainer sendMessage(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
     * Method that sets the ordinary thief state.
     * @param thiefId - Thief Identifier
     * @param state - State Identifier
     */
    @Override
    public synchronized void setThiefState(int thiefId, int state){
        args = new int[] {thiefId, state};
        sendMessage(new Message(MessageType.SET_THIEF_STATE, args));
    }
    
    /**
     * Method that sets the master thief state.
     * @param state - State Identifier
     */
    @Override
    public synchronized void setMThiefState(int state ){
        args = new int[] { state};
        sendMessage(new Message(MessageType.SET_M_THIEF_STATE, args));
    }
    
     /**
     * Method that sets the ordinary thief speed.
     * @param thiefId - Thief Identifier.
     * @param speed - Thief Speed.
     */
    @Override
    public synchronized void setThiefSpeed(int thiefId, int speed ){
        args = new int[] {thiefId, speed};
        sendMessage(new Message(MessageType.SET_THIEF_SPEED, args));
    }
    
    /**
     * Method that sets the ordinary thief situation.
     * @param thiefId - Thief Identifier.
     * @param situation - Thief Situation.
     */
    @Override
    public synchronized void setThiefSituation(int thiefId, String situation ){
        sendMessage(new Message(MessageType.SET_THIEF_SITUATION, thiefId, situation));
    }
    
    /**
     * Method that sets the room for the party
     * @param partyId - Party Identifier.
     * @param room - Room Identifier
     */
    @Override
    public synchronized void setRoomIdAP(int partyId,int room){
        args = new int[] {partyId, room};
        sendMessage(new Message(MessageType.SET_ROOM_ID_AP, args));
    }
    
    /**
     * Method that sets members on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param member - Member Identifier.
     */
    @Override
    public synchronized void setMemberIdAP(int partyId, int thiefId, int member){
        args = new int[] {partyId, thiefId, member};
        sendMessage(new Message(MessageType.SET_MEMBER_ID_AP, args));
    }
    
    /**
     * Method that sets the thieves positions on the Assault Party
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param pos - Thief Position on Assault Party
     */
    @Override
    public synchronized void setPositionAP(int partyId, int thiefId, int pos){
        args = new int[] {partyId, thiefId, pos};
        sendMessage(new Message(MessageType.SET_POSITION_AP, args));
    }   
    
    /**
     * Method that sets the canvas on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param canvas - Canvas (True if has canvas; False otherwise).
     */
    @Override
    public synchronized void setCanvasAP(int partyId, int thiefId, boolean canvas){
        args = new int[] {partyId, thiefId, canvas ? 1:0};
        sendMessage(new Message(MessageType.SET_CANVAS_AP, args));
    }
    
    /**
     * Method that sets the number of paintings of a room.
     * @param roomId - Room Identifier.
     * @param paintings - Number of paintings.
     */
    @Override
    public synchronized void setNPaintings(int roomId, int paintings){
        args = new int[] {roomId, paintings};
        sendMessage(new Message(MessageType.SET_N_PAINTINGS, args));
    }
    
    /**
     * Method that sets the rooms distances.
     * @param roomId - Room Identifier.
     * @param dist - Room Distance.
     */
    @Override
    public synchronized void setDistPaintings(int roomId, int dist){
        args = new int[] {roomId, dist};
        sendMessage(new Message(MessageType.SET_DIST_PAINTINGS, args));
    }
    
    /**
     * Method that sets the number of canvas.
     * @param nCanvas - Number of canvas.
     */
    @Override
    public synchronized void setCollectedCanvas(int nCanvas){
        args = new int[] {nCanvas};
        sendMessage(new Message(MessageType.SET_COLLECTED_CANVAS, args));
    }
    
    /**
     * Method that prints the Log footer.
     */
    @Override
    public synchronized void writeEnd(){
        sendMessage(new Message(MessageType.WRITE_END));
    }   
}

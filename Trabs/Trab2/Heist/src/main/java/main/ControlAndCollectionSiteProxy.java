/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import comInf.message.Message;
import comInf.message.MessageType;
import comInf.message.MessageContainer;
import comInf.proxy.ClientProxy;
import controlAndCollectionSite.IMasterThief;
import controlAndCollectionSite.IOrdinaryThief;
import configuration.ConfProxy;

/**
 * Control And Collection Site Proxy to make requests to the Control And Collection Site Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ControlAndCollectionSiteProxy implements IMasterThief, IOrdinaryThief {

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private int[] args;
    private final GeneralRepositoryProxy genRepProxy;
    
    /**
     * Control And Collection Site Proxy Constructor.
     * @param genRepProxy - GeneralRepositoryProxy Instance
     */
    public ControlAndCollectionSiteProxy(GeneralRepositoryProxy genRepProxy){
        ConfProxy confProxy = new ConfProxy();
        this.SERVER_HOST = confProxy.getServerHosts().get("ControlAndCollectionSite");
        this.SERVER_PORT = confProxy.getServerPorts().get("ControlAndCollectionSite");
        this.args = null;
        this.genRepProxy = genRepProxy;
    }
    
    /**
    * Method that communicates with the Server in order to make a connection.  
    * @param m - Message Identifier
    */
    private MessageContainer sendMessage(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /** 
    * In the Master Thief life cycle, transition between "deciding what to do" and "deciding what to do".
    * The MasterThief decides what to do. If all rooms are empty and all parties are not
    * deployed then the next state will be presenting the report. If there are enough thieves
    * waiting to assemble a party and if the MasterThief has chosen which room to steal and
    * which party to deploy then the next state will be assembling a group. If not, he has to 
    * wait for the assault parties to return if there are not enough waiting thieves to assemble 
    * a assault party or if there are still deployed assault parties, so the next state will be
    * waiting for group arrival.
    * @return the next state
    */
    @Override
    public int appraiseSit() {
        MessageContainer result = sendMessage(new Message(MessageType.APPRAISE_SIT));
        return result.getMessage().getReply();
    }

    /**
     * In Master Thief life cycle, transition between "deciding what to do" and "waiting for
     * group arrival".
     * CollectCanvas by Master Thief if any ordinary thief has arrived.
     */
    @Override
    public void takeARest() {
        sendMessage(new Message(MessageType.TAKE_A_REST));
    }

    /**
     * In Master Thief life cycle, transition between "waiting for group arrival" and "deciding
     * what to do".
     * Decrease the number of canvas to collect.
     */
    @Override
    public void collectCanvas() {
        sendMessage(new Message(MessageType.COLLECT_CANVAS));
    }
    
    /**
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * While Master Thief is resting, the Ordinary Thief that has arrived has to wait until he wakes up.
     * When he does, reset that party. If that ordinary thief returns with a canvas, the number of collected
     * canvas is increased by the Master Thief and that room is set to rob again because there are still paintings 
     * left on that room to retrieve. If the ordinary thief doesn't return a canvas it means that the room is now 
     * empty and the room is set to empty.
     * @param hasCanvas - True if thief has canvas; False otherwise
     * @param roomId - Room Identifier
     * @param partyId - Assault Party Identifier
     * @param thiefId - Thief Identifier
     */
    @Override
    public void handACanvas(boolean hasCanvas, int roomId, int partyId, int thiefId) {
        args = new int[] { hasCanvas ? 1:0 ,roomId, partyId, thiefId}; 
        sendMessage(new Message(MessageType.HAND_A_CANVAS, args));
    }

    /**
     * Method that returns the room to steal by the Master Thief
     * @return the room to steal by the Master Thief
     */
    @Override
    public int getRoomToSteal() {
        MessageContainer result = sendMessage(new Message(MessageType.GET_ROOM_TO_STEAL));
        return result.getMessage().getReply();
    }

    /**
     * Method that returns the party to deploy by the Master Thief
     * @return the party to deploy by the Master Thief
     */
    @Override
    public int getPartyToDeploy() {
        MessageContainer result = sendMessage(new Message(MessageType.GET_PARTY_TO_DEPLOY));
        return result.getMessage().getReply();
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import configuration.ConfProxy;
import comInf.message.Message;
import comInf.proxy.ClientProxy;
import concentrationSite.IMasterThief;
import concentrationSite.IOrdinaryThief;
import comInf.message.MessageType;
import comInf.message.MessageContainer;

/**
 * Concentration Site Proxy to make requests to the Concentration Site Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConcentrationSiteProxy implements IMasterThief, IOrdinaryThief {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private int[] args;
    
    /**
     * ConcentrationSite Proxy Constructor.
     */
    public ConcentrationSiteProxy(){
        ConfProxy confProxy = new ConfProxy();
        this.SERVER_HOST = confProxy.getServerHosts().get("ConcentrationSite");
        this.SERVER_PORT = confProxy.getServerPorts().get("ConcentrationSite");
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
     * In the Master Thief life cycle, transition between "planning the heist" and "deciding what to do".
     * The Ordinary Thieves are notified of the start of Operations by the Master Thief when all
     * thieves are on the waiting thieves Queue.
     */
    @Override
    public void startOperations() {
        sendMessage(new Message(MessageType.START_OPERATIONS));
    }
    
    /**
     * In the Ordinary Thief life cycle, transition between "outside" and "outside".
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * The ordinary Thief is added to a waitingThieves Queue and notify the Master Thief
     * The Ordinary Thief has to wait while he is not assembled to a assault party and the results aren't ready.
     * @param thiefId - Ordinary Thief Identifier
     * @return true if thieves are needed; Return false otherwise. (Thieves are needed while results aren't ready).
     */
    @Override
    public boolean amINeeded(int thiefId) {
        args = new int[] {thiefId};
        MessageContainer result = sendMessage(new Message(MessageType.AM_I_NEEDED, args));
        return result.getMessage().getReply() != 0;
    }
    
        /**
     * In the Ordinary Thief life cycle, transition between "outside" and "crawling inwards".
     * The Master Thief is notified when there are enough thieves in order to assemble a assault party.
     * The party is set to ready and the number of thieves waiting to be assembled to a party is set to zero.
     * @param thiefId - Ordinary Thief Identifier
     */
    @Override
    public void prepareExcursion(int thiefId) {
        args = new int[] {thiefId};
        sendMessage(new Message(MessageType.PREPARE_EXCURSION, args));
    }
        
    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "assembling a group".
     * The Ordinary Thieves that are waiting to be set to a party are notified by the Master Thief that now they 
     * are assembled to a party and no longer are on the waiting thieves Queue. 
     * @param partyId - Assault Party Identifier
     */
    @Override
    public void prepareAssaultParty(int partyId) {
        args = new int[] {partyId};        
        sendMessage(new Message(MessageType.PREPARE_ASSAULT_PARTY, args));
    }

    /**
     * In the Master Thief life cycle, transition between "deciding what to do" and "presenting the report".
     * The Master Thief notifies the Ordinary Thieves that the results are now ready.
     */
    @Override
    public void sumUpResults() {
        sendMessage(new Message(MessageType.SUM_UP_RESULTS));
    }
    
    /**
     * Method that returns the Assault Party Id of the Ordinary Thief.
     * @param thiefId - Ordinary Thief Identifier
     * @return the party Id of the Ordinary Thief.
     */
    @Override
    public int getPartyId(int thiefId) {
        args = new int[] {thiefId};
        MessageContainer result = sendMessage(new Message(MessageType.GET_PARTY_ID, args));
        return result.getMessage().getReply();
    }

    /**
     * Method that returns the number of waiting thieves on the Queue.
     * @return the number of waiting thieves on the Queue.
     */
    @Override
    public int getNWaitingThieves() {
        MessageContainer result = sendMessage(new Message(MessageType.GET_N_WAITING_THIEVES));
        return result.getMessage().getReply();
    }
}
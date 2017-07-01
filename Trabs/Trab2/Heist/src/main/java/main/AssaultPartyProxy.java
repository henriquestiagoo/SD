/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import assaultParty.IMasterThief;
import assaultParty.IOrdinaryThief;
import comInf.message.Message;
import comInf.message.MessageType;
import comInf.message.MessageContainer;
import comInf.proxy.ClientProxy;

/**
 * Assault Party Proxy to make requests to the Assault Party Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class AssaultPartyProxy implements IMasterThief, IOrdinaryThief{

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private int[] args;
    private final GeneralRepositoryProxy genRepProxy;
    
    /**
     * Assault Party Proxy Constructor.
     * @param port - Port Identifier
     * @param host - Host Identifier
     * @param genRepProxy - GeneralRepositoryProxy Instance
     */
    public AssaultPartyProxy(int port, String host, GeneralRepositoryProxy genRepProxy){
        this.SERVER_HOST = host;
        this.SERVER_PORT = port;
        args = null;
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
     * In the Master Thief life cycle, transition between "assembling a group" and "deciding what to do".
     * Signal the first Ordinary Thief that joined the assault party to start crawling inwards.
     */
    @Override
    public void sendAssaultParty() {
        sendMessage(new Message(MessageType.SEND_ASSAULT_PARTY));
    }

    /**
     * Method that assigns a room to steal to a Assault Party
     * @param roomId - Room Identifier
     * @param roomDistance - Room distance
     */
    @Override
    public void setAssaultInfo(int roomId, int roomDistance) {
        args = new int[] {roomId, roomDistance};
        sendMessage(new Message(MessageType.SET_ASSAULT_INFO, args));
    }

    /**
     * In the Ordinary Thief life cycle, transition between "crawling inwards" and "crawling inwards".
     * Method to signal a Thief to crawl inwards a room. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the room.
     * Same process implemented on crawlOut method.
     * @param thiefId - Thief Identifier
     */
    @Override
    public void crawlIn(int thiefId) {
        args = new int[] {thiefId};
        sendMessage(new Message(MessageType.CRAWL_IN, args));
    }
    
    /**
     * In the Ordinary Thief life cycle, transition between "at a room" and "crawling outwards". 
     * Signal the first Ordinary Thief that joined the assault party to start crawling outwards.
     * @param thiefId - Thief Identifier
     */
    @Override
    public void reverseDirection(int thiefId) {
        args = new int[] {thiefId};
        sendMessage(new Message(MessageType.REVERSE_DIRECTION, args));
    }

    /**
     * In the Ordinary Thief life cycle, transition between "crawling outwards" and "crawling outwards".
     * Method to signal a Thief to crawl outwards outside. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the outside.
     * Same process implemented on crawlIn method.
     * @param thiefId - Thief Identifier
     */
    @Override
    public void crawlOut(int thiefId) {
        args = new int[] {thiefId};
        sendMessage(new Message(MessageType.CRAWL_OUT, args));
    }

    
    /**
     * The Master Thief builds a Assault Party and assemble the thieves on the correct position.
     * @param thiefId - Thief Identifier
     * @param thiefSpeed - Thief Speed
     */
    @Override
    public void buildParty(int thiefId, int thiefSpeed) {
        args = new int[] {thiefId,thiefSpeed };
        sendMessage(new Message(MessageType.BUILD_PARTY, args));
    }

    /**
     * Method that returns the room to steal by the Assault Party
     * @return the room to steal by the Assault Party.
     */
    @Override
    public int getRoomToSteal() {
        MessageContainer result = sendMessage(new Message(MessageType.GET_ROOM_TO_STEAL));
        return result.getMessage().getReply();
    }

    /**
     * Method that returns the position of the thief id on the Assault Party 
     * @param thiefId - Thief Identifier.
     * @return the position of the thief id on the Assault Party 
     */
    @Override
    public int getPosLogger(int thiefId) {
        args = new int[] {thiefId};
        MessageContainer result = sendMessage(new Message(MessageType.GET_POS_LOGGER, args));
        return result.getMessage().getReply();
    } 
}

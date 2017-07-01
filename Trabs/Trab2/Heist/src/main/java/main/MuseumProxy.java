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
import museum.IMasterThief;
import museum.IOrdinaryThief;

/**
 * Museum Proxy to make requests to the Museum Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class MuseumProxy implements IOrdinaryThief, IMasterThief {

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    private int[] args;
    private final GeneralRepositoryProxy genRepProxy;
    
    /**
     * Museum Proxy Constructor.
     * @param genRepProxy - GeneralRepositoryProxy Instance
     */
    public MuseumProxy(GeneralRepositoryProxy genRepProxy){
        ConfProxy confProxy = new ConfProxy();
        this.SERVER_HOST = confProxy.getServerHosts().get("Museum");
        this.SERVER_PORT = confProxy.getServerPorts().get("Museum");
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
     * Method is used to get a canvas from a Museum room.
     * @param roomId - Room Identifier
     * @return true if the room still has canvas to be stolen; False otherwise
     */
    @Override
    public boolean rollACanvas(int roomId) {
        args = new int[] {roomId};
        MessageContainer result = sendMessage(new Message(MessageType.ROLL_A_CANVAS, args));
        return result.getMessage().getReply() != 0;
    }

    /**
     * Method used to get the room distance by the Master Thief
     * @param roomId - Room Identifier
     * @return the distance of a room.
     */
    @Override
    public int getRoomDistance(int roomId) {
        args = new int[] {roomId};
        MessageContainer result = sendMessage(new Message(MessageType.GET_ROOM_DISTANCE, args));
        return result.getMessage().getReply();
    }
}

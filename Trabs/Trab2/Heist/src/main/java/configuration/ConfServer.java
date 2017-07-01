/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * Configuration Server that extends the Configuration Info and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConfServer extends ConfInfo implements IServer {
    
    private boolean serverEnded;

    /**
     * ConfigurationServer Constructor.
     */
    public ConfServer() {
        super();
        this.serverEnded = false;
    }
    
    /**
     * Method that handles the process and reply of a Message.
     * @param inMessage - Message inMessage
     * @return outMessage - Message outMessage
     * @throws MessageException - MessageException value
     */
    @Override
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;
        switch(inMessage.getType()) {
            case END_OPERATIONS:
                this.serverEnded = true;
                outMessage = new Message(MessageType.ACK);
                break;
            case CONF_SERVER_PORT:
                outMessage = new Message(MessageType.SERVER_REPLY, getServerPortsHashMap());
                break;
            case CONF_SERVER_HOST:
                outMessage = new Message(MessageType.SERVER_REPLY, getServerHostsHashMap());
                break;
        }
        return outMessage; 
    }

    /**
     * Method that notifies the end of a server service.
     * @return endServer
     */
    @Override
    public boolean endServer() {
        return serverEnded;
    }
}

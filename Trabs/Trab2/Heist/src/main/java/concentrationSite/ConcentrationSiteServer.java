/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concentrationSite;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * Concentration Site Server that extends the Concentration Site and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConcentrationSiteServer extends ConcentrationSite implements IServer {
    
    public boolean serverEnded;
    
    /**
     * ConcentrationSiteServer Constructor.
     */
    public ConcentrationSiteServer() {
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
        switch(inMessage.getType()){
            case END_OPERATIONS:
                this.serverEnded = true;
                outMessage = new Message(MessageType.ACK);
                break;
            case START_OPERATIONS:
                super.startOperations();
                outMessage = new Message(MessageType.ACK);
                break;
            case AM_I_NEEDED:
                boolean isNeeded = super.amINeeded(inMessage.getArgs(0));
                outMessage = new Message(MessageType.SERVER_REPLY, isNeeded ? 1 : 0);
                break;
            case PREPARE_EXCURSION:
                super.prepareExcursion(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case PREPARE_ASSAULT_PARTY:
                super.prepareAssaultParty(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case SUM_UP_RESULTS:
                super.sumUpResults();
                outMessage = new Message(MessageType.ACK);
                break;
            case GET_PARTY_ID:
                int thiefId = super.getPartyId(inMessage.getArgs(0));
                outMessage = new Message(MessageType.SERVER_REPLY, thiefId);                
                break;
            case GET_N_WAITING_THIEVES:
                int waitingThieves = super.getNWaitingThieves();
                outMessage = new Message(MessageType.SERVER_REPLY, waitingThieves);    
                break;
            default:
                throw new MessageException("Invalid Request", inMessage);    
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

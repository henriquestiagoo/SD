/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assaultParty;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * Assault Party Server that extends the Assault Party and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class AssaultPartyServer extends AssaultParty implements IServer {

    private boolean serverEnded;
    
    /**
     * AssaultPartyServer Instantiation.
     * @param assaultPartyId - Assault Party identifier.
     * @param genRep - Assault Party Interface Identifier of the General Repository.
     */
    public AssaultPartyServer(int assaultPartyId, generalRepository.IAssaultParty genRep) {
        super(assaultPartyId, genRep);
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
            case SEND_ASSAULT_PARTY:
                super.sendAssaultParty();
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_ASSAULT_INFO:
                super.setAssaultInfo(inMessage.getArgs(0),inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;
            case CRAWL_IN:
                super.crawlIn(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case REVERSE_DIRECTION:
                super.reverseDirection(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case CRAWL_OUT:
                super.crawlOut(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case BUILD_PARTY:
                super.buildParty(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;
            case CLEAR_ASSAULT_PARTY:
                super.clearAssaultParty();
                outMessage = new Message(MessageType.ACK);
                break;
            case GET_ROOM_TO_STEAL:
                int roomToSteal = super.getRoomToSteal();
                outMessage = new Message(MessageType.SERVER_REPLY, roomToSteal);
                break; 
            case GET_POS_LOGGER:
                int posLoggerA = super.getPosLogger(inMessage.getArgs(0));
                outMessage = new Message(MessageType.SERVER_REPLY, posLoggerA);
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
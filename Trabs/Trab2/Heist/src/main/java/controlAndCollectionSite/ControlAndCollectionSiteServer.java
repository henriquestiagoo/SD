/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlAndCollectionSite;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * ControlAndCollectionSite Server that extends the ControlAndCollection Site and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ControlAndCollectionSiteServer extends ControlAndCollectionSite implements IServer {

    private boolean serverEnded;
    
    /**
     * ControlAndCollectionSiteServer Constructor.
     * @param genRep - Control and Collection Site Interface Identifier of the General Repository.
     */
    public ControlAndCollectionSiteServer(generalRepository.IControlAndCollectionSite genRep) {
        super(genRep);
        this.serverEnded = false;
    }

    /**
     * Method that handles the process and reply of a Message.
     * @param inMessage - Message inMessage
     * @return outMessage - Message outMessage
     * @throws MessageException - MessageException
     */
    @Override
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;
        switch(inMessage.getType()){
            case END_OPERATIONS:
                this.serverEnded = true;
                outMessage = new Message(MessageType.ACK);
                break;
            case APPRAISE_SIT:
                int value = super.appraiseSit();
                outMessage = new Message(MessageType.SERVER_REPLY, value);
                break;
            case TAKE_A_REST:
                super.takeARest();
                outMessage = new Message(MessageType.ACK);
                break;
            case COLLECT_CANVAS:
                super.collectCanvas();
                outMessage = new Message(MessageType.ACK);
                break;
            case HAND_A_CANVAS:
                super.handACanvas(inMessage.getArgs(0) != 0, inMessage.getArgs(1), inMessage.getArgs(2), inMessage.getArgs(3));
                outMessage = new Message(MessageType.ACK);
                break;
            case GET_ROOM_TO_STEAL:
                int roomId = super.getRoomToSteal();
                outMessage = new Message(MessageType.SERVER_REPLY, roomId);
                break;
            case GET_PARTY_TO_DEPLOY:
                int partyId = super.getPartyToDeploy();
                outMessage = new Message(MessageType.SERVER_REPLY, partyId);
                break;
            case GET_DELIVERED_CANVAS:
                int deliveredCanvas = super.getDeliveredCanvas();
                outMessage = new Message(MessageType.SERVER_REPLY, deliveredCanvas);
                break;
            case GET_COLLECTED_CANVAS:
                int collectedCanvas = super.getCollectedCanvas();
                outMessage = new Message(MessageType.SERVER_REPLY, collectedCanvas);
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
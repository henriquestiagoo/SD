/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museum;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * Museum Server that extends the Museum and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class MuseumServer extends Museum implements IServer {

    private boolean serverEnded;
    
    /**
     * MuseumServer Constructor.
     * @param genRep - Museum Interface Identifier of the General Repository.
     */
    public MuseumServer(generalRepository.IMuseum genRep) {
        super(genRep);
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
            case GET_MUSEUM_N_TOTAL_PAINTINGS:
                super.getMuseumNTotalPaintings();
                outMessage = new Message(MessageType.ACK);
                break;
            case ROLL_A_CANVAS:
                boolean roolCanvas = super.rollACanvas(inMessage.getArgs(0));
                outMessage = new Message(MessageType.SERVER_REPLY, roolCanvas ? 1:0);
                break;
            case GET_ROOM_DISTANCE:
                int roomDistance = super.getRoomDistance(inMessage.getArgs(0));
                outMessage = new Message(MessageType.SERVER_REPLY, roomDistance);
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

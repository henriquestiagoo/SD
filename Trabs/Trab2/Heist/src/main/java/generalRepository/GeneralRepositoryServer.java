/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

import comInf.message.Message;
import comInf.message.MessageException;
import comInf.message.MessageType;
import comInf.proxy.IServer;

/**
 * General Repository Server that extends the General Repository  and will 
 * process the events of the Server.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class GeneralRepositoryServer extends GeneralRepository implements IServer {

    private boolean serverEnded;
    
    /**
     * GeneralRepositoryServer Constructor.
     */
    public GeneralRepositoryServer() {
        super();
        this.serverEnded = false;
    }
    
    /**
     * Method that handles the process and reply of a Message.
     * @param inMessage - Message inMessage
     * @return outMessage - Message outMessage
     * @throws MessageException - MessageException Identifier
     */
    @Override
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;
        switch(inMessage.getType()){
            case SET_THIEF_STATE:
                super.setThiefState(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_M_THIEF_STATE:
                super.setMThiefState(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_THIEF_SPEED:
                super.setThiefSpeed(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_THIEF_SITUATION:
                super.setThiefSituation(inMessage.getThiefId(), inMessage.getSituation());
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_ROOM_ID_AP:
                super.setRoomIdAP(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_MEMBER_ID_AP:
                super.setMemberIdAP(inMessage.getArgs(0), inMessage.getArgs(1), inMessage.getArgs(2));
                outMessage = new Message(MessageType.ACK);
                break;
            case SET_POSITION_AP:
                super.setPositionAP(inMessage.getArgs(0), inMessage.getArgs(1) , inMessage.getArgs(2));
                outMessage = new Message(MessageType.ACK);
                break;   
            case SET_CANVAS_AP:
                super.setCanvasAP(inMessage.getArgs(0), inMessage.getArgs(1), inMessage.getArgs(2) != 0);
                outMessage = new Message(MessageType.ACK);
                break;   
            case SET_N_PAINTINGS:
                super.setNPaintings(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;   
            case SET_DIST_PAINTINGS:
                super.setDistPaintings(inMessage.getArgs(0), inMessage.getArgs(1));
                outMessage = new Message(MessageType.ACK);
                break;   
            case SET_COLLECTED_CANVAS:
                super.setCollectedCanvas(inMessage.getArgs(0));
                outMessage = new Message(MessageType.ACK);
                break;   
            case WRITE_END:
                super.writeEnd();
                outMessage = new Message(MessageType.ACK);
                break;
            case END_OPERATIONS:
                this.serverEnded = true;
                super.endServers();
                outMessage = new Message(MessageType.ACK);
                break;
                /* The End... */
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.proxy;

import comInf.message.Message;
import comInf.message.MessageException;

/**
 * Server Interface.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IServer {
    
    /**
     * Method that handles the process and reply of a Message.
     * @param inMessage - Message inMessage
     * @return outMessage - Message outMessage
     * @throws MessageException - Message Exception
     */
    public Message processAndReply (Message inMessage) throws MessageException;
        
    /**
     * Method that notifies the end of a server service.
     * @return endServer - endServer value.
     */
    public boolean endServer();
}

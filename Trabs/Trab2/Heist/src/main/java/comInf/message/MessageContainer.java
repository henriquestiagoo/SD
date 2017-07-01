/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.message;

/**
 * Message Container Class.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class MessageContainer {
    
    private Message msg;
    
    /**
     * Message Container Constructor.
     */
    public MessageContainer(){}
    
    /**
     * Method that returns a message.
     * @return msg
     */
    public Message getMessage(){
        return this.msg;
    }
    
    /**
     * Method that sets a message.
     * @param msg - Message Identifier 
     */
    public void setMessage(Message msg){
        this.msg = msg;
    }
}

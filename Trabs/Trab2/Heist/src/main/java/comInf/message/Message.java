/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.message;

import java.io.Serializable;
import java.util.HashMap;

 /** Message Class.
  * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
  */
public class Message implements Serializable {
    
  /**
   *  Serialization key.
   *    @serialField serialVersionUID
   */
   private static final long serialVersionUID = 1001L;
   private MessageType msgType;
   private int[] args;
   private int reply;
   private int thiefId;
   private String situation;
   private HashMap<?, ?> hMap;
   
    /**
     * Constructor that creates a Message without any arguments.
     */
    public Message() {
       msgType = null;
       reply = -1;
       thiefId = -1;
       situation = null;
       hMap = null;
   } 
   
   /**
     * Constructor that creates a Message with msgType.
     * @param msgType - Message Type
     */ 
   public Message (MessageType msgType) {
      this.msgType = msgType;
   }
   
    /**
     * Constructor that creates a Message with msgType and reply.
     * @param msgType - Message Type
     * @param reply - Reply Identifier
     */
    public Message(MessageType msgType, int reply) {
        this(msgType);
        this.reply = reply;
    }
   
    /**
     * Constructor that creates a Message with msgType and args.
     * @param msgType - Message Type
     * @param args - Args Identifier
     */
   public Message(MessageType msgType, int[] args){
       this(msgType);
       this.args = args;
   }
   
    /**
     * Constructor that creates a Message with msgType, thiefId and situation.
     * @param msgType - Message Type
     * @param thiefId - Thief Identifier
     * @param situation - Situation Identifier
     */
    public Message(MessageType msgType, int thiefId, String situation){
       this(msgType);
       this.thiefId = thiefId;
       this.situation = situation;
   }
   
    /**
     * Constructor that creates a Message with msgType and hMap (HashMap).
     * @param msgType - Message Type
     * @param hMap - hMap Identifier
     */
    public Message(MessageType msgType, HashMap<?, ?> hMap){
        this(msgType);
        this.hMap = hMap;
    }
    
    /**
     * Method that returns the msgType.
     * @return msgType - Message Type
     */
    public MessageType getType() {
        return this.msgType;
    }
   
    /**
     * Method that returns the reply.
     * @return reply - Reply Identifier
     */
    public int getReply(){
       return this.reply;
   }
   
    /**
     * Method that returns the index of the args array.
     * @param idx - Index Identifier
     * @return args[idx] - Args return value
     */
    public int getArgs(int idx){
       return this.args[idx];
   }
    
    /**
     * Method that returns the thief id.
     * @return thiedId
     */
    public int getThiefId() {
       return this.thiefId;
    }
    
    /**
     * Method that returns the thief situation.
     * @return situation
     */
    public String getSituation(){
       return this.situation;
    }
    
    /**
     * Method that returns the Hosts hMap
     * @return hMap
     */
    public HashMap<String, String> getHostHashMap(){
        return (HashMap<String, String>) this.hMap;
    }
    
    /**
     * Method that returns the Ports hMap
     * @return hMap
     */
    public HashMap<String, Integer> getPortHashMap() {
        return (HashMap<String, Integer>) this.hMap;
    }
    
    /**
     * Method that prints the msgType.
     * @return hMap
     */
   @Override
    public String toString() {
        return ("Tipo = " + this.msgType);
    }  
}
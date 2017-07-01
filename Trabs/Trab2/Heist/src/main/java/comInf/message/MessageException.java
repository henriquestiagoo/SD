/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.message;

/**
 * Message Exception Class.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */

/**
 * Data type that defines a exception that is launched if the message is invalid.
 */
public class MessageException extends Exception {
    
   private final Message msg;

  /**
   * MessageException Instantiation. 
   *    @param errorMessage - errorMessage Error name
   *    @param msg - Message Identifier
   */
   public MessageException (String errorMessage, Message msg) {
     super (errorMessage);
     this.msg = msg;
   }

    /**
     * Method that returns the message that gives a exception.
     * @return msg
     */
    public Message getMessageVal() {
     return (msg);
   }
}
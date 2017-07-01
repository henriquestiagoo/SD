/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assaultParty;

/**
 * Master Thief Interface of Assault Party Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */

public interface IMasterThief {
    
     /**
     * In the Master Thief life cycle, transition between "assembling a group" and "deciding what to do".
     * Signal the first Ordinary Thief that joined the assault party to start crawling inwards.
     */
    public void sendAssaultParty();

    /**
     * Method that assigns a room to steal to a Assault Party
     * @param roomId - Room Identifier
     * @param roomDistance - Room distance
     */
    public void setAssaultInfo(int roomId, int roomDistance);
}

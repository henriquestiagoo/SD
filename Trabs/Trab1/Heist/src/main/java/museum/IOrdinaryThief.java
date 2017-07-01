/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museum;

/**
 * Ordinary Thief Interface of Museum Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IOrdinaryThief {
    
    /**
     * Method is used to get a canvas from a room.
     * @param roomId - Room Identifier
     * @return true if the room still has canvas to be stolen; False otherwise.
     */
    public boolean rollACanvas(int roomId);
}

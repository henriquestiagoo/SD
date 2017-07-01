/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museum;

/**
 * Master Thief Interface of Museum Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IMasterThief {
    
    /**
     * Method used to get the room distance by the Master Thief
     * @param roomId - Room Identifier
     * @return the distance of a room.
     */
    public int getRoomDistance(int roomId);
}

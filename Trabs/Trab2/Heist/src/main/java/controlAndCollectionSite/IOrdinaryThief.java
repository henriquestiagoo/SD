/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlAndCollectionSite;

/**
 * Ordinary Thief Interface of ControlAndCollection Site Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IOrdinaryThief {
    
    /**
     * In Ordinary Thief life cycle, transition between "outside" and "outside"
     * While Master Thief is resting, the Ordinary Thief that has arrived has to wait until he wakes up.
     * When he does, reset that party. If that ordinary thief returns with a canvas, the number of collected
     * canvas is increased by the Master Thief and that room is set to rob again because there are still paitings 
     * left on that room to retrieve. If the ordinary thief doesn't return a canvas it means that the room is now 
     * empty and the room is set to empty.
     * @param hasCanvas - True if thief has canvas; False otherwise
     * @param roomId - Room Identifier
     * @param partyId - Assault Party Identifier
     * @param thiefId - Thief Identifier
     */
    public void handACanvas(boolean hasCanvas, int roomId, int partyId, int thiefId);
}

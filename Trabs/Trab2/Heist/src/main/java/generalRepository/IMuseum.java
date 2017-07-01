/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

/**
 * Museum Interface of General Repository Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IMuseum {
    
    /**
     * Method that sets the number of paintings of a room.
     * @param roomId - Room Identifier.
     * @param paintings - Number of paintings.
     */
    public void setNPaintings(int roomId, int paintings);
    
    /**
     * Method that sets the rooms distances.
     * @param roomId - Room Identifier.
     * @param dist - Room Distance.
     */
    public void setDistPaintings(int roomId,int dist);
}

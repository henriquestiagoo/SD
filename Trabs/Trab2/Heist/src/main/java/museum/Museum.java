package museum;

import main.Constants;

/**
 * Museum Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class Museum implements IOrdinaryThief, IMasterThief {
    
    public Room rooms[] =  new Room[Constants.N_ROOMS];
    private int nTotalPaitings = 0;
    private final generalRepository.IMuseum genRep;
    
    /**
     * Creates a new Museum and creates the Museum rooms.
     * @param genRep - General Repository Instance of Museum Interface
     */
    public Museum(generalRepository.IMuseum genRep){
        this.genRep = genRep;
        for (int i = 0; i < Constants.N_ROOMS; i++) 
            rooms[i] = new Room(this.genRep,i);
    }
    
    /**
     * Method that returns the number of total paintings on the museum. 
     */
    public void getMuseumNTotalPaintings(){
        for (int i = 0; i < Constants.N_ROOMS; i++) {
            nTotalPaitings += rooms[i].getPaintings();
        }
        System.out.printf("The museum has %d paitings", nTotalPaitings);
    }
    
    /**
     * Method is used to get a canvas from a Museum room.
     * @param roomId - Room Identifier
     * @return true if the room still has canvas to be stolen; False otherwise
     */
    @Override
    public boolean rollACanvas(int roomId){
        return rooms[roomId].rollACanvas();
    }
    
    /**
     * Method used to get the room distance by the Master Thief
     * @param roomId - Room Identifier
     * @return the distance of a room.
     */
    @Override
    public int getRoomDistance(int roomId){
        int d = rooms[roomId].getDistance();
        return d;
    }
}

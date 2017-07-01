/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museum;

import main.Constants;
import generalRepository.GeneralRepository;
/**
 * Room Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class Room {
   private final int distance; 
   private int paintings;
   private final int roomId;
   private final GeneralRepository genRep;

   /**
    * Creates a new Room
     * @param genRep - General Repository Instance
     * @param roomId - Room Identifier
    */
   public Room(GeneralRepository genRep, int roomId) {
       this.distance = Constants.randInt(Constants.MIN_ROOM_DISTANCE, Constants.MAX_ROOM_DISTANCE);
       this.paintings = Constants.randInt(Constants.MIN_PAITINGS_ROOM, Constants.MAX_PAITING_ROOM);
       this.genRep = genRep;
       this.roomId = roomId;
       genRep.setNPaintings(roomId, paintings);
       genRep.setDistPaintings(roomId, distance);
   }

   /**
     * Method is used to get a canvas from a room.
     * @return true if the room still has canvas to be stolen; False otherwise.
     */
   public synchronized boolean rollACanvas(){
      if (paintings > 0){
          paintings--;
          genRep.setNPaintings(roomId, paintings);
          return true;
      }
      return false;
   }

   /**
    * Method to get the number of paintings in the room
    * @return the number of paintings of the room.
    */
   public int getPaintings(){
       return paintings;
   }

   /**
    * Method to get the distance to the outside of the room
    * @return the distance of the room.
    */
   public int getDistance() {
       return distance;
   }
}

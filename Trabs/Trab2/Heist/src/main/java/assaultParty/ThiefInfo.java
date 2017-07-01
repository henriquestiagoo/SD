/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assaultParty;

/**
 * Thief Info Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ThiefInfo {

    private final int id;
    private int speed;
    private int distance;
    private final int partyPosition;

    /**
     * Create a new ThiefInfo
     * @param id - Thief Identifier
     * @param speed - Thief Speed
     * @param partyPosition - Thief position on party
     */
    public ThiefInfo(int id, int speed, int partyPosition) {
        this.id = id;
        this.speed = speed;
        this.distance = 0;
        this.partyPosition = partyPosition;
    }

    /**
     * Method that returns the id of the thief
     * @return the id of the thief
     */
    public int getId() {
        return id;
    }
    
    /**
     * Method that returns the speed of the thief
     * @return the speed of the thief 
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Method to set the thief speed.
     * @param speed - Thief Speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Method that returns the maximum distance that a thief is able to crawl
     * @return the maximum distance of the thief
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Method to set the maximum thief distance to crawl.
     * @param distance - Thief distance.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    /**
     * Method that returns the thief position on the party
     * @return thief position on party
     */
    public int getPartyPosition() {
        return partyPosition;
    }
}

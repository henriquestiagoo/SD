/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assaultParty;

/**
 * OrdinaryThief Interface of Assault Party Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IOrdinaryThief {
    
    /**
     * In the Ordinary Thief life cycle, transition between "crawling inwards" and "crawling inwards".
     * Method to signal a Thief to crawl inwards a room. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the room.
     * Same process implemented on crawlOut method.
     * @param thiefId - Thief Identifier
     */
    public void crawlIn(int thiefId);
    
    /**
     * In the Ordinary Thief life cycle, transition between "crawling outwards" and "crawling outwards".
     * Method to signal a Thief to crawl outwards outside. 
     * Each thief has to wait while it is not his time to crawl. Each thief only crawls one position,
     * wakes up the next thief to crawl and then sleeps. This happen until all the thieves have arrived
     * to the outside.
     * Same process implemented on crawlIn method.
     * @param thiefId - Thief Identifier
     */
    public void crawlOut(int thiefId);
    
    /**
     * In the Ordinary Thief life cycle, transition between "at a room" and "crawling outwards". 
     * Signal the first Ordinary Thief that joined the assault party to start crawling outwards.
     * @param thiefId - Thief Identifier
     */
    public void reverseDirection(int thiefId);

    /**
     * The Master Thief builds a Assault Party and assemble the thieves on the correct position.
     * @param thiefId - Thief Identifier
     * @param thiefSpeed - Thief Speed
     */
    public void buildParty(int thiefId, int thiefSpeed);

    /**
     * Method that returns the room to steal by the Assault Party
     * @return the room to steal by the Assault Party.
     */
    public int getRoomToSteal();
    
    /**
     * Method that returns the position of the thief id on the Assault Party 
     * @param thiefId - Thief Identifier.
     * @return the position of the thief id on the Assault Party 
     */
    public int getPosLogger(int thiefId);
}

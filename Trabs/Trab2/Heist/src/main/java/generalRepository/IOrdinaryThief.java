/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

/**
 * Ordinary Thief Interface of General Repository Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IOrdinaryThief {
    
    /**
     * Method that sets the ordinary thief state.
     * @param thiefId - Thief Identifier
     * @param state - State Identifier
     */
    public void setThiefState(int thiefId, int state );
    
    /**
     * Method that sets the ordinary thief speed.
     * @param thiefId - Thief Identifier.
     * @param speed - Thief Speed.
     */
    public void setThiefSpeed(int thiefId, int speed );
    
    /**
     * Method that sets the canvas on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param canvas - Canvas (True if has canvas; False otherwise).
     */
    public void setCanvasAP(int partyId, int thiefId, boolean canvas);
    
    /**
     * Method that sets the ordinary thief situation.
     * @param thiefId - Thief Identifier.
     * @param situation - Thief Situation.
     */
    public void setThiefSituation(int thiefId, String situation );    
}

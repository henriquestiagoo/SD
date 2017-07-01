/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

/**
 * Master Thief Interface of General Repository Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IMasterThief {
    
    /**
     * Method that sets the master thief state.
     * @param state - State Identifier
     */
    public void setMThiefState(int state);
    
    /**
     * Method that prints the Log footer.
     */
    public void writeEnd();    
}

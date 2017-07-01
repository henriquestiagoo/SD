/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generalRepository;

/**
 * Assault Party Interface of General Repository Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public interface IAssaultParty {
    
    /**
     * Method that sets the room for the party
     * @param partyId - Party Identifier.
     * @param room - Room Identifier
     */
    public void setRoomIdAP(int partyId,int room);
    
    /**
     * Method that sets the thieves positions on the Assault Party
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param pos - Thief Position on Assault Party
     */
    public void setPositionAP(int partyId, int thiefId, int pos);  

    /**
     * Method that sets the ordinary thief situation.
     * @param thiefId - Thief Identifier.
     * @param situation - Thief Situation.
     */
    public void setThiefSituation(int thiefId, String situation );
    
    /**
     * Method that sets members on the Assault Party.
     * @param partyId - Party Identifier.
     * @param thiefId - Thief Identifier.
     * @param member - Member Identifier.
     */
    public void setMemberIdAP(int partyId, int thiefId, int member);
}

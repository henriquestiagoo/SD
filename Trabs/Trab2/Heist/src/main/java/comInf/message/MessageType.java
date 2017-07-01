/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.message;

/**
 * Message Types.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public enum MessageType {
    
    START_OPERATIONS,
    APPRAISE_SIT,
    PREPARE_ASSAULT_PARTY,
    SEND_ASSAULT_PARTY,
    SET_ASSAULT_INFO,
    BUILD_PARTY,
    CLEAR_ASSAULT_PARTY,
    GET_ROOM_TO_STEAL,
    GET_POS_LOGGER ,
    TAKE_A_REST,
    COLLECT_CANVAS,
    SUM_UP_RESULTS,
    AM_I_NEEDED,
    NEEDED ,
    HAND_A_CANVAS ,
    GET_PARTY_TO_DEPLOY,
    GET_DELIVERED_CANVAS,
    GET_COLLECTED_CANVAS,
    PREPARE_EXCURSION,
    CRAWL_IN,
    ROLL_A_CANVAS,
    GET_MUSEUM_N_TOTAL_PAINTINGS,
    GET_ROOM_DISTANCE,
    REVERSE_DIRECTION,
    CRAWL_OUT,
    ACK,
    THIEF_ID,
    GET_PARTY_ID,
    GET_N_WAITING_THIEVES,
    SET_THIEF_STATE,
    SET_M_THIEF_STATE,
    SET_THIEF_SPEED,
    SET_THIEF_SITUATION,
    SET_ROOM_ID_AP,
    SET_MEMBER_ID_AP,
    SET_POSITION_AP,
    SET_CANVAS_AP,
    SET_N_PAINTINGS,
    SET_DIST_PAINTINGS,
    SET_COLLECTED_CANVAS, 
    WRITE_END,
    END_OPERATIONS,
    SERVER_REPLY,
    CONF_SERVER_PORT,
    CONF_SERVER_HOST;
}

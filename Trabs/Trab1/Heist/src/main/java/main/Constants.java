/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;

/**
 * Constants file 
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public final class Constants {
    public static final int N_ROOMS = 5;
    public static final int N_ORD_THIEVES = 6;
    public static final int MIN_PAITINGS_ROOM = 8;
    public static final int MAX_PAITING_ROOM = 16;
    public static final int MIN_ROOM_DISTANCE = 15;
    public static final int MAX_ROOM_DISTANCE = 30;
    public static final int N_ASSAULT_PARTY_SIZE = 3;
    public static final int N_ASSAULT_PARTIES = (N_ORD_THIEVES/N_ASSAULT_PARTY_SIZE);
    public static final int MAX_CRAWL_SEPARATION = 3;
    public static final int MIN_THIEF_SPEED = 2;
    public static final int MAX_THIEF_SPEED = 6;
    public static final int MIN_DISPLACEMENT_THIEVES = 2;
    public static final int MAX_DISPLACEMENT_THIEVES = 6;
    
    /**
     * Generate a random numbers between two numbers. 
     * @param min - Minimum value
     * @param max - Maximum value
     * @return a random number between the minimum and maximun numbers
     */
    public static int randInt(int min,int max){
        Random random = new Random();
        return random.nextInt(max-min+1)+min;
    }
}

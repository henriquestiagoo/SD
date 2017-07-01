/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

/**
 * Constants file.
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
    public static final String FILENAME = "conf.xml";
    
    /**
     * Generate a random numbers between two numbers. 
     * @param min - Minimum value
     * @param max - Maximum value
     * @return a random number between the minimum and maximum numbers
     */
    public static int randInt(int min,int max){
        Random random = new Random();
        return random.nextInt(max-min+1)+min;
    }
    
    /**
     * Method that reads the content of a XML file.
     * @param filename - Name of the XML file to read
     * @return nList - NodeList
     */
    public static NodeList readXML(String filename){
        NodeList nList = null;
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = null;
            try {
                doc = dBuilder.parse(fXmlFile);
            } catch (SAXException | IOException ex) {
                Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* optional, but recommended */
            doc.getDocumentElement().normalize();
            
            nList = doc.getElementsByTagName("role");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nList;
    }
    
    /**
     * Method that returns the Host name of a specific Monitor given a XML file.
     * @param role - Monitor name
     * @param filename - XML file to read
     * @return hostR - Returns the Host name
     */
    public static String getHost(String role, String filename){
        String hostR = null;
        int idx = 0;
        
        NodeList nList = readXML(filename);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            if(nList.item(temp).getAttributes().getNamedItem("id").getNodeValue().equals(role)){
                idx = temp;
                break;
            }   
        }

        Node nNode = nList.item(idx);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                hostR = eElement.getElementsByTagName("host").item(0).getTextContent();
        }

        return hostR;
    }
    
    /**
     * Method that returns the Port number of a specific Monitor given a XML file.
     * @param role - Monitor name
     * @param filename - XML file to read
     * @return portR - Returns the Port number
     */
    public static int getPort(String role, String filename){
        int portR = 0;
        int idx = 0;
        
        NodeList nList = readXML(filename);
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            if(nList.item(temp).getAttributes().getNamedItem("id").getNodeValue().equals(role)){
                idx = temp;
                break;
            }
        }

        Node nNode = nList.item(idx);
        
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                portR = Integer.parseInt(eElement.getElementsByTagName("port").item(0).getTextContent());
        }
        return portR;
    }
}

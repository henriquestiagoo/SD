/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf;

import java.io.*;
import java.net.*;

/**
 * Class for all the server communications, this will have the listeningSocket, the commSocket, the serverPortNumb, 
 * the ObjectInputStream in and out.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ServerCom {
    
    private ServerSocket listeningSocket = null;
    private Socket commSocket = null;
    private final int serverPortNumb;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
   
    /** 
     * Constructor to create ClientCom with hostName and portNumb.
     * @param portNumb - Port Number Identifier
     */
    public ServerCom (int portNumb)
    {
      this.serverPortNumb = portNumb;
    }

    /**
     * Constructor to create a ServerCom with portNumb and lSocket.
     * @param portNumb - Port Number Identifier
     * @param lSocket - Server Socket Identifier
     */
    public ServerCom (int portNumb, ServerSocket lSocket)
    {
      this.serverPortNumb = portNumb;
      this.listeningSocket = lSocket;
    }
    
    /**
     * Method that starts the ServerCom.
     * Service establishment. Listening Socket Instantiation and its association to the local machine address
     * and to the port.
     */
    public void start() {
        try {
            this.listeningSocket = new ServerSocket(this.serverPortNumb);
        } catch (BindException e){
            System.out.println(Thread.currentThread().getName() + " port not available: " + this.serverPortNumb + "!\n Error:" + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " undefined error port: " + this.serverPortNumb + "!\n Error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Method that ends the service.
     */
    public void end() {
        try {
            this.listeningSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " is not possible to close the socket");
            System.exit(1);
        }
    }

    /**
     * Listening process.
     * Creation of a communication channel for a waiting request.    
     * Communication socket instantiation and its association the client address.
     * Socket in and out streams opening.  
     * @return scon - ServerCom Class
     */
    public ServerCom accept() {
        ServerCom scon;
        scon = new ServerCom(this.serverPortNumb, this.listeningSocket);
        
        try {
            scon.commSocket = this.listeningSocket.accept();
        } catch (SocketException e) {
            System.out.println(Thread.currentThread().getName() + ", the socket has been closed during the listening");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open a communication channel for a pending request");
            System.exit(1);
        }
        
        try {
            scon.in = new ObjectInputStream(scon.commSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open the socket input channel!");
            System.exit(1);
        }

        try {
            scon.out = new ObjectOutputStream(scon.commSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open the socket output channel!");
            System.exit(1);
        }

        return scon;
    }
    
    /**
     * Communication Channel ending.
     * Socket in and out streams ending.
     * Communication Socket ending.
     */
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the socket input channel!");
            System.exit(1);
        }

        try {
            out.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the output channel socket!");
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the communication socket!");
            System.exit(1);
        }
    }
     
    /**
     * Object reading from a communication channel.
     * @return from_client
     */
    public Object readObject() {
        Object from_client = null; 
        
        try {
            from_client = in.readObject();
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName() + ", the readed object is not capable of deserializer!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", error in the reading of a communication socket input channel object");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println(Thread.currentThread().getName() + ", read the object corresponds to an unknown type of data");
            System.exit(1);
        }

        return from_client;
    }
    
   /**
     * Object writing on the communication channel.
     * @param toClient - Object toClient
     */
    public void writeObject(Object toClient) {
        try {
            out.writeObject(toClient);
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName() + ", the object to be written is not capable of serialization!");
            System.exit(1);
        } catch (NotSerializableException e) {
            System.out.println(Thread.currentThread().getName() + ", the object to be written belongs to a type of non serializable data!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", error in the writing of an output channel of the object of the communication socket!");
            System.exit(1);
        }
    }    
}

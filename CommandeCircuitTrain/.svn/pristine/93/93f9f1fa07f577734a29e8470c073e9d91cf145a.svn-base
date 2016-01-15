/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package upmc.train.model.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import upmc.train.constantes.IConstantes.EDirection;

/**
 *
 * @author brunolesueur
 * 
 * Cette classe permet de cr�er un client reseaux vers un serveur WiThrottle et permet
 * donc de controler les trains
 * 
 */
public class ThreadWiThrottle extends Thread
{
    private static int    _port ;
    private static Socket _socket;
    private String address ;
    private int train = -1 ;
    private boolean isRunning = false ;
    protected ArrayList<String> message = new ArrayList<String>() ;
    private BufferedReader input   = null;
    private PrintWriter output = null ;
    
    synchronized public void  setMessage(String msg)
    {
    	//synchronized(this.message)
    	//{
    		System.err.println("La pile (train "  + train + ") des messages est  " + message) ;
    		this.message.add(msg) ;
    	//}
    }
    
    public void stopWT()
    {
        this.setMessage("Q");
    }
    
    public void emergencyStop()
    {	
    		this.setMessage("TV1") ;
    		this.setMessage("TX");
	    	
    	

    }
 
    private void reconnect()
    {
    	output.println("Q") ;
    	output.flush();
        try
        {
            output.close();
            _socket = new Socket(address, _port);

            // Open stream
            input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
           
            output = new PrintWriter(_socket.getOutputStream());
            output.println("NLesueurTR" + train);
            output.flush() ;
            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            output.println("TS" + train) ;
            output.flush() ;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
        }   	
    }
    public void setDirection(EDirection direction)
    {
    	if (direction == EDirection.FORWARD)
    		this.setMessage("TR1") ;
    	else
    		this.setMessage("TR0");
    }
    
    public void setSpeed(int speed)
    {
    	this.setMessage("TV" + speed) ;
    	this.setMessage("TV" + speed) ;
    }
    public ThreadWiThrottle() 
    {
    	
    }
    public ThreadWiThrottle(String adresse, int port, int adresseLoco)
    {
        _port = port ;
        try
        {
            
            _socket = new Socket(adresse, _port);

            // Open stream
            input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            
            this.train = adresseLoco ;
            this.address = adresse ;
            output = new PrintWriter(_socket.getOutputStream());
            output.println("NLesueurT" + adresseLoco);
            output.flush() ;
            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            output.println("TS" + adresseLoco) ;
            output.flush() ;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
        }   	
    }
    public void startCommand()
    {
    	this.isRunning = true ;
    	this.start() ;
    }
   
    
    public void run()
    {
    	String msg ;
    	int compteur = 0 ;
        while(this.isRunning)
        {
        	
        	//System.err.println("La pile (train "  + train + ") des messages est  " + message) ;
            try {
                while(this.message.size() > 0)
                {
                //	synchronized(this.message)
                //	{
                		msg = this.message.remove(0) ;
               // 	}
                	System.err.println("MESSAGE NON ENVOYE" + msg) ;
                	output.println(msg);
                    output.flush();
                    compteur++;
                    if (compteur > 4)
                    {
                    	compteur = 0; 
                    	this.reconnect() ;
                    }
                    System.err.println("" + this.train + " : MESSAGE ENVOYE" + msg) ;
                    msg = "" ;
                    //Thread.sleep(200);
                    //System.out.println("Je fais reellement la demande pour le message : " + msg) ;
                    String response = "";
                    String texte  = "" ;
                    try {
                        response = input.readLine();
                        while((texte = input.readLine()).length() > 0)
                        {
                            response += texte ;
                        }
                    } catch (Exception ex) {
                        System.err.println("Network error") ;
                    }
                    System.out.println("Reponse : " + response) ;
                 
                
	                if (this.message.equals("Q"))
	                	this.isRunning = false ;
                }
            } catch (Exception ex) {
            	System.err.println("Network error") ;
            }
            
        }
    }

    
}

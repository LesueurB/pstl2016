/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package upmc.train.model.communication.simulator;

import java.util.ArrayList;
import upmc.train.model.communication.ThreadWiThrottle;

/**
 *
 * @author brunolesueur
 * 
 * Cette classe permet de cr�er un client reseaux vers un serveur WiThrottle et permet
 * donc de controler les trains
 * 
 */
public class ThreadWiThrottleSimulator extends ThreadWiThrottle
{
  
    private ArrayList<String> message = new ArrayList<String>() ;
    public ThreadWiThrottleSimulator(String adresse, int port, int adresseLoco)
    {
    	super() ;
 
    }
    synchronized public void  setMessage(String msg)
    {
    	synchronized(this.message)
    	{
    		this.message.add(msg) ;
    		System.out.println("SIMULATOR WITHROTTLE MESSAGE : " + msg) ;
    	}
    }
    /*
    public void stopWT()
    {

    }
    
    public void emergencyStop()
    {	
    		this.setMessage("TV0") ;
    		this.setMessage("TX");
	    	
    	

    }
 
    public void setSpeed(int speed)
    {
    	this.setMessage("TV" + speed) ;
    }
    
 
    public void startCommand()
    {
    	
    }
    public void init(String adresse, int port, int adresseLoco)
    {
    
    }
    
    public void run()
    {
 
    }
*/
    
}

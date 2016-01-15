package upmc.train.model.communication.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import upmc.train.constantes.IConstantes.EColor;
import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.Train;
import upmc.train.model.communication.ICommunication;
import upmc.train.model.communication.NetMonitor;
import upmc.train.model.communication.exception.TrainCommandException;
import upmc.train.model.messages.IMessageListener;
import upmc.train.model.messages.MessageEvent;

public class NetMonitorSimulator extends NetMonitor implements ICommunication, IMessageListener
{
	private ArrayList<CommunicationArduinoSimulator> comUSB = new ArrayList<CommunicationArduinoSimulator>() ;
	private HashMap<String,ThreadWiThrottleSimulator> comCircuit = new HashMap<String,ThreadWiThrottleSimulator>() ;
	private int portWiThrottle = 55555 ;
	private String adresseWiThrottle = "localhost" ;
    private final Collection<IMessageListener> messagesListeners = new ArrayList<IMessageListener>();
	//initialisation
	public NetMonitorSimulator()
	{ 
		
	}

	public NetMonitorSimulator(int port, String adresse)
	{
		this.portWiThrottle = port ;
		this.adresseWiThrottle = adresse ;
	}	
	
	public void simulateMessage(String num, String type)
	{
		for(CommunicationArduinoSimulator com : comUSB)
		{
			com.simulateMessage(num, type);
		}
	}
	
	//mise en place des elements de communication
	public void addNewArduino(String port, boolean protocol)
	{
		CommunicationArduinoSimulator com = new CommunicationArduinoSimulator() ;
		this.comUSB.add(com) ;
		com.addMessageListener(this);
	}
	
	public void removeArduino(String port)
	{
		CommunicationArduinoSimulator toRemove = null ;
		for(CommunicationArduinoSimulator ca : this.comUSB)
		{
			if (ca.getSerialPort().equals(port))
				toRemove = ca ;
		}
		if (toRemove != null)
		{
			toRemove.removeMessageListener(this);
			this.comUSB.remove(toRemove) ;
		}
	}
	
	public void removeTrain(int adresseTrain)
	{
		if (this.comCircuit.containsKey(adresseTrain))
			this.comCircuit.remove(adresseTrain) ;
	}
	
	public void addNewTrain(String adresseTrain)
	{
		int val = -1 ;
		try
		{
			val = Integer.parseInt(adresseTrain) ;
		}
		catch(Exception e)
		{
			
		}
		this.comCircuit.put(adresseTrain, new ThreadWiThrottleSimulator(this.adresseWiThrottle,this.portWiThrottle, val)) ;
	}

	public void setPortWiThrottle(int portWiThrottle) {
		this.portWiThrottle = portWiThrottle;
	}

	public void setAdresseWiThrottle(String adresseWiThrottle) {
		this.adresseWiThrottle = adresseWiThrottle;
	}

	@Override
	public void changeFeu(int numFeu, EColor couleur) 
	{
		for(CommunicationArduinoSimulator ca : this.comUSB)
		{
			ca.changeFeu(numFeu, couleur);
		}
	}
	@Override
	public void changeAiguillage(String id, String pos) 
	{
		for(CommunicationArduinoSimulator ca : this.comUSB)
		{
			ca.changeAiguillage(id,pos);
		}
	}
    //traitement de l'ecoute des messages series
    public void addMessageListener(IMessageListener listener) {
    	messagesListeners.add(listener);
    }
 
    public void removeMessageListener(IMessageListener listener) {
    	messagesListeners.remove(listener);
    }
 
    public IMessageListener[] getMessageListeners() {
        return messagesListeners.toArray(new IMessageListener[0]);
    }
    
    private void firedNewMessageEvent(String msg)
    {
    	MessageEvent evt = new MessageEvent(msg) ;
    	for(IMessageListener listener : this.messagesListeners)
    		listener.newMessage(evt) ;
    }
	public void newMessage(MessageEvent evt) 
	{
		this.firedNewMessageEvent(evt.getMessage());
		
	}
	
	//traitement de l'emission des messages vers les locos
    synchronized public void  setMessage(int train, String msg)
    {
    	this.comCircuit.get(train).setMessage(msg);
    }
    
    public void stopWT(int train)
    {
    	this.comCircuit.get(train).stopWT() ;
    }
    
    public void emergencyStop(Train train)
    {	
    	this.comCircuit.get(train.getAdresse()).emergencyStop();
    }
 
    public void setSpeed(Train train, int speed) throws TrainCommandException
    {
    	if (speed > 126)
    		throw new TrainCommandException("Speed over maximum (126)") ;
    	if (speed < 0)
    		throw new TrainCommandException("Speed under minimum (0)") ;
    	if (!this.comCircuit.containsKey(train.getAdresse()))
    		throw new TrainCommandException("this train is not registered") ;
    	this.comCircuit.get(train.getAdresse()).setSpeed(speed) ;
    }
    
    public void reverse(Train train) throws TrainCommandException
    {
    	if (!this.comCircuit.containsKey(train.getAdresse()))
    		throw new TrainCommandException("this train is not registered") ;
    	this.comCircuit.get(train.getAdresse()).setDirection(EDirection.BACKWARD);
    }
    
    public void forward(Train train) throws TrainCommandException
    {
    	if (!this.comCircuit.containsKey(train.getAdresse()))
    		throw new TrainCommandException("this train is not registered") ;
    	this.comCircuit.get(train.getAdresse()).setDirection(EDirection.FORWARD);
    }
    
	

}

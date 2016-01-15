package upmc.train.controller.model.communication;

import java.util.ArrayList;
import java.util.Collection;

import upmc.train.constantes.IConstantes;
import upmc.train.constantes.IConstantes.EColor;
import upmc.train.controller.PCFEncoder;
import upmc.train.model.communication.ICommunication;
import upmc.train.model.messages.IMessageListener;
import upmc.train.model.messages.MessageEvent;


public class CommunicationArduinoPCF implements ICommunication
{
	
	private final Collection<IMessageListener> messagesListeners = new ArrayList<IMessageListener>();
	private PCFEncoder encoder  ;
	
    public void setEncoder(PCFEncoder encoder) {
		this.encoder = encoder;
	}

	public void changeFeu(int numFeu, IConstantes.EColor couleur)
    {
    	String color = "red" ;
    	if (couleur == EColor.GREEN)
    		color = "green" ;
    	encoder.encodeChangeLight(numFeu, color);
    	
    }
	public void changeAiguillage(String numFeu, String position)
    {
    	
    	encoder.encodeChangeSwitch(numFeu, position);
    	
    }   
    public void simulateMessage(int num)
    {
    	this.firedNewMessageEvent(" Capteur" + num + "...");
    }
    
    //// Methodes concernant les listeners
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
    	System.out.println("SIMULATOR ARDUINO receive " + msg) ;
    }
    
    public String getSerialPort()
    {
    	return "Serial simulator" ;
    }

	
}

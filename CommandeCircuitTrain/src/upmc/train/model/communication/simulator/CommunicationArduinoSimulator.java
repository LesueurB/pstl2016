package upmc.train.model.communication.simulator;

import java.util.ArrayList;
import java.util.Collection;

import upmc.train.constantes.IConstantes;
import upmc.train.model.communication.ICommunication;
import upmc.train.model.messages.IMessageListener;
import upmc.train.model.messages.MessageEvent;


public class CommunicationArduinoSimulator implements ICommunication
{
	
	private final Collection<IMessageListener> messagesListeners = new ArrayList<IMessageListener>();

	public void changeFeu(int numFeu, IConstantes.EColor couleur)
    {
		byte[] message = new byte[4] ;
    	message[0] = (IConstantes.ECodeTypeMateriel.FEU.val) ;// on indique le type de materiel
    	message[1] = (byte) (numFeu) ; // on indique l'adresse du materiel
    	message[2] = (IConstantes.ECodeTypeCommande.SWITCH.val) ; 
    	//Calcul de la valeur
    	if (couleur == IConstantes.EColor.RED)
    	{
    		
    		message[3]  = 0 ;
    	}
    	else
    	{
    		message[3]  = 1 ;
    	}
    	System.out.println("MESSAGE VERS ARDUINO : [" + message[0]
    												+ "," + message[1]
    												+ "," + message[2] + "," 
    												+ message[3] + "]");
    }
    
    public void simulateMessage(String num, String type)
    {
    	this.firedNewMessageEvent("" + num) ;
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

	@Override
	public void changeAiguillage(String numAiguillage, String position) {
		int num = Integer.parseInt(numAiguillage) ;
		int pos = Integer.parseInt(position) ;
		   byte[] message = new byte[4] ;
	        try
	        {
	        	message[0] = IConstantes.ECodeTypeMateriel.AIGUILLAGE.val ;// on indique le type de materiel
	        	message[1] = (byte) (num) ; // on indique l'adresse du materiel
	        	message[2] = IConstantes.ECodeTypeCommande.SWITCH.val ; 
	        	//Calcul de la valeur
		    	message[3]  = (byte)pos ;
		    	System.out.println("MESSAGE VERS ARDUINO : [" + message[0]
						+ "," + message[1]
						+ "," + message[2] + "," 
						+ message[3] + "]");		    	
	        }
	        catch(Exception e)
	        {
	        	System.out.println("ERREUR : pas possible de changer l'aiguillage") ;
	        }
	}
}

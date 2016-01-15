package upmc.train.model.communication;

import java.io.IOException;
import java.util.ArrayList;

import gnu.io.SerialPortEvent;
import upmc.train.constantes.IConstantes;
import upmc.train.model.Train;
import upmc.train.model.communication.exception.TrainCommandException;
import upmc.train.model.messages.IMessageListener;
import upmc.train.model.messages.MessageEvent;

public class CommunicationArduinoTotale extends CommunicationArduino 
{
	ArrayList<Byte> msg = new ArrayList<Byte>() ;
	int count = 0 ;
	@Override
	public void changeAiguillage(String numAiguillage, String position) 
	{
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
		    	output.write(message);
		    	output.flush(); //on envoie effectivement le message
		    	
	        }
	        catch(Exception e)
	        {
	        	System.out.println("ERREUR : pas possible de changer l'aiguillage") ;
	        }
	}
   public void changeFeu(int numFeu, IConstantes.EColor couleur)
    {
	   byte[] message = new byte[4] ;
        try
        {
        	message[0] = IConstantes.ECodeTypeMateriel.FEU.val ;// on indique le type de materiel
	    	message[1] = (byte) (numFeu) ; // on indique l'adresse du materiel
	    	message[2] = IConstantes.ECodeTypeCommande.SWITCH.val ; 
	    	//Calcul de la valeur
	    	if (couleur == IConstantes.EColor.RED)
	    	{
	    		
	    		message[3]  = 0 ;
	    	}
	    	else
	    	{
	    		message[3]  = 1 ;
	    	}
	    	output.write(message);
	    	output.flush(); //on envoie effectivement le message
    	
	    }
	    catch (Exception e)
	    {
	    	System.out.println("ERREUR : pas possible de changer le feu") ;
	    }
}
public void serialEvent(SerialPortEvent evt) 
{
	byte[] message = new byte[4];
    if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
    {
        try
        {
        	int nb = input.read(message) ;
        	for (int i = 0 ; i < nb ; i++)
    		{
        		msg.add(message[i]) ;
    		}
        	if (msg.size() >= 4)
        	{
        		//on ne prend du message que le deuxieme element
        		byte entete = msg.remove(0) ;
        		if (entete == IConstantes.ECodeTypeMateriel.capteur.val)
        		{
        			byte last = msg.remove(0) ;
        			msg.remove(0) ;
        			msg.remove(0) ;
        			this.firedNewMessageEvent("" + (last)) ;
        		}
        		else //on ignore tout simplement le message en lisant les 3 octets suivants
        		{
        			msg.remove(0) ;
        			msg.remove(0) ;
        			System.err.println("Board error : " + IConstantes.erreurs[msg.remove(0)]) ;	
        		}
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   }	
   public void emergencyStop(Train train)
   {	
	   byte[] message = new byte[4] ;
	   message[0] = IConstantes.ECodeTypeMateriel.TRAIN.val ;// on indique le type de materiel
   message[1] = (byte) (Byte.parseByte(train.getAdresse())) ; // on indique l'adresse du materiel
   message[2] = IConstantes.ECodeTypeCommande.EMMERGENCY.val ;
   message[3]  = 0 ;
   try {
		output.write(message);
		output.flush(); //on envoie effectivement le message
   } 
   catch (IOException e) 
   {
			e.printStackTrace();
   }
    	
   }
	 
	public void setSpeed(Train train, int speed) throws TrainCommandException
	{
	   byte[] message = new byte[4] ;
	   message[0] = IConstantes.ECodeTypeMateriel.TRAIN.val ;// on indique le type de materiel
	   message[1] = (byte) (Byte.parseByte(train.getAdresse())) ; // on indique l'adresse du materiel
	   message[2] = IConstantes.ECodeTypeCommande.VITESSE.val ;
	   byte direction ;
	   if (train.getDirection() == IConstantes.EDirection.FORWARD)
		   direction = 0 ;
	   else
		   direction = (byte)0x80 ;
	   message[3]  = (byte)(((byte)speed) | direction) ;
	   try {
			output.write(message);
			output.flush(); //on envoie effectivement le message
	   } catch (IOException e) {
			
			e.printStackTrace();
	   }						    	
	}

	public void reverse(Train train) throws TrainCommandException
	{
		   byte[] message = new byte[4] ;
		   message[0] = IConstantes.ECodeTypeMateriel.TRAIN.val ;// on indique le type de materiel
		   message[1] = (byte) (Byte.parseByte(train.getAdresse())) ; // on indique l'adresse du materiel
		   message[2] = IConstantes.ECodeTypeCommande.VITESSE.val ;
		   byte direction ;
		   if (train.getDirection() == IConstantes.EDirection.FORWARD)
			   direction = 0 ;
		   else
			   direction = (byte)0x80 ;
		   message[3]  = (byte)(((byte)train.getVitesse()) | direction) ;
		   try {
				output.write(message);
				output.flush(); //on envoie effectivement le message
		   } catch (IOException e) {
				
				e.printStackTrace();
		   }					
	}
	
	public void forward(Train train) throws TrainCommandException
	{
		   byte[] message = new byte[4] ;
		   message[0] = IConstantes.ECodeTypeMateriel.TRAIN.val ;// on indique le type de materiel
		   message[1] = (byte) (Byte.parseByte(train.getAdresse())) ; // on indique l'adresse du materiel
		   message[2] = IConstantes.ECodeTypeCommande.VITESSE.val ;
		   byte direction ;
		   if (train.getDirection() == IConstantes.EDirection.FORWARD)
			   direction = 0 ;
		   else
			   direction = (byte)0x80 ;
		   message[3]  = (byte)(((byte)train.getVitesse()) | direction) ;
		   try {
				output.write(message);
				output.flush(); //on envoie effectivement le message
		   } catch (IOException e) {
				
				e.printStackTrace();
		   }				
	}	
	protected void firedNewMessageEvent(String msg)
	{
		//le protocol ayant évolué on fait en sorte de renvoyé la bonne information
		//msg est la chaine contenant 4 octets, celui qui nous inteeresse pour
		//le moment est la valeur du capteur (donc le dernier octet = msg[3])
		//on sait que c'est un capteur
		
		MessageEvent evt = new MessageEvent("" + msg ) ;
		for(IMessageListener listener : this.getMessageListeners())
			listener.newMessage(evt) ;
	}		    
}

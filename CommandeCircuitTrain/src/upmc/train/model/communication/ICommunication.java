package upmc.train.model.communication;

import upmc.train.constantes.IConstantes;
import upmc.train.model.messages.IMessageListener;

public interface ICommunication 
{
	
	public void changeFeu(int numFeu, IConstantes.EColor couleur) ;
	public void changeAiguillage(String numAigguilage, String position);
    //// Methodes concernant les listeners
    public void addMessageListener(IMessageListener listener) ; 
    public void removeMessageListener(IMessageListener listener) ;
    public IMessageListener[] getMessageListeners() ;
	
}

package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFUpElement  extends PCFElement{
	private ArrayList<PCFCapteurElement> capteurs = new ArrayList<PCFCapteurElement>() ;
	public ArrayList<PCFCapteurElement> getCapteurs() {
		return capteurs;
	}
	public void addCapteur(PCFCapteurElement capteur)
	{
		this.capteurs.add(capteur) ;
	}
	public String getFragmentXML()
	{
		String result = "<up>" ;
		for(PCFCapteurElement capteur : capteurs)
		{
			result += capteur.getFragmentXML() ;
		}
		result += "</up>" ;
		return result ;
	}
}

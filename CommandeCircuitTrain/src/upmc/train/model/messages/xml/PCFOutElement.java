package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFOutElement  extends PCFElement{
	private ArrayList<PCFCapteurElement> out = new ArrayList<PCFCapteurElement>() ; 
	public ArrayList<PCFCapteurElement> getOut() {
		return out;
	}
	public String getFragmentXML()
	{
		String result = "<out>" ;
		for(PCFCapteurElement c : out)
		{
			result += c.getFragmentXML() ;
		}
		return result += "</out>" ;
	}
	public void addCapteur(PCFCapteurElement elt)
	{
		out.add(elt) ;
	}
}

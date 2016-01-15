package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFInElement  extends PCFElement
{
	private ArrayList<PCFCapteurElement> in = new ArrayList<PCFCapteurElement>() ; 
	public ArrayList<PCFCapteurElement> getIn() {
		return in;
	}

	public String getFragmentXML()
	{
		String result = "<in>" ;
		for(PCFCapteurElement c : in)
		{
			result += c.getFragmentXML() ;
		}
		return result += "</in>" ;
	}
	
	public void addCapteur(PCFCapteurElement elt)
	{
		in.add(elt) ;
	}
}

package upmc.train.model.messages.xml;

public class PCFEdgeElement  extends PCFElement
{
	
	private PCFInElement in = new PCFInElement();
	public PCFCapteurElement getCapteur() {
		return capteur;
	}
	public void setIn(PCFInElement in) {
		this.in = in;
	}
	public void setOut(PCFOutElement out) {
		this.out = out;
	}
	private PCFOutElement out = new PCFOutElement();
	private PCFCapteurElement capteur ;
	public PCFInElement getIn() {
		return in;
	}
	public PCFOutElement getOut() {
		return out;
	}
	public void setCapteur(PCFCapteurElement capteur) {
		this.capteur = capteur;
	}
	public String getFragmentXML()
	{
		String result = "<sensor-edges>" ;
		
		result += capteur.getFragmentXML() ;
		result += in.getFragmentXML() ;
		result += out.getFragmentXML() ;
		
		return result += "</sensor-edges>";
	}
}

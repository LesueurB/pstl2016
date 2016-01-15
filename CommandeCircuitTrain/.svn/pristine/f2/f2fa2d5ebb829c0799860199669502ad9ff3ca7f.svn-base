package upmc.train.model.messages.xml;

public class PCFHelloElement  extends PCFElement{

	private String id = null;
	public String getId() {
		return id;
	}
	public PCFHelloElement(String id) {
		this.id = id ;
	}
	public PCFHelloElement() {
		
	}
	public String getFragmentXML()
	{
		if (id != null )
			return "<hello id=\"" + this.id + "\"/>";
		else 
			return "<hello/>" ;
	}
}

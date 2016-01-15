package upmc.train.model.messages.xml;

public class PCFOllehElement  extends PCFElement
{
	private String id ;
	public String getId() {
		return id;
	}
	public PCFOllehElement(String id) {
		this.id = id ;
	}
	public PCFOllehElement() {
		
	}
	public String getFragmentXML()
	{
		if (id != null )
			return "<olleh id=\"" + this.id + "\"/>";
		else 
			return "<olleh/>" ;
	}
}

package upmc.train.model.messages.xml;

public class PCFScenarioElement extends PCFElement {
	private String id ;
	public String getId() {
		return id;
	}
	public PCFScenarioElement(String id)
	{
		this.id = id ;
	}
	public PCFScenarioElement()
	{
		
	}
	public String getFragmentXML()
	{
		return "<scenario id=\"" + this.getId() + "\"/>";
	}
}

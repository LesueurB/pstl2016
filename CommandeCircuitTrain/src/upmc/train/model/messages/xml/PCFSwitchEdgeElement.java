package upmc.train.model.messages.xml;

public class PCFSwitchEdgeElement extends PCFEdgeElement
{
		public void setId(String id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setNum_capteur_trunk(String num_capteur_trunk) {
		this.num_capteur_trunk = num_capteur_trunk;
	}
	public void setNum_capteur_branch0(String num_capteur_branch0) {
		this.num_capteur_branch0 = num_capteur_branch0;
	}
	public void setNum_capteur_branch1(String num_capteur_branch1) {
		this.num_capteur_branch1 = num_capteur_branch1;
	}
	private String id = "" ;
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getNum_capteur_trunk() {
		return num_capteur_trunk;
	}
	public String getNum_capteur_branch0() {
		return num_capteur_branch0;
	}
	public String getNum_capteur_branch1() {
		return num_capteur_branch1;
	}
	private String type = "" ;
	private String num_capteur_trunk = "" ;
	private String num_capteur_branch0 = "" ;
	private String num_capteur_branch1 = "" ;
	public PCFSwitchEdgeElement(String id, String type, String num_capteur_trunk, String num_capteur_branch0, String num_capteur_branch1)
	{
		this.id = id ;
		this.type = type ;
		this.num_capteur_trunk = num_capteur_trunk ;
		this.num_capteur_branch0 = num_capteur_branch0 ;
		this.num_capteur_branch1 = num_capteur_branch1 ;
		
	}
	public PCFSwitchEdgeElement()
	{
		
	}
	public String getFragmentXML()
	{
		String result = "<switch-edges" ;
		
		result += " id=\"" + this.id + "\" " ;
		result += " type=\"" + this.type + "\" " ;
		result += " trunk=\"" + this.num_capteur_trunk + "\" " ;
		result += " branch0=\"" + this.num_capteur_branch0 + "\" " ;
		result += " branch1=\"" + this.num_capteur_branch1 + "\"> " ;
		
		
		return result += "</switch-edges>";
	}

}

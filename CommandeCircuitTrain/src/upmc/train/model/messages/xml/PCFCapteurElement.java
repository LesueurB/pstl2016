package upmc.train.model.messages.xml;

import upmc.train.constantes.IConstantes;


public class PCFCapteurElement  extends PCFElement implements IConstantes
{
	
	
	
	private String id ; 
	public String getId() {
		return id;
	}
	public EType getType() {
		return type;
	}
	private EType type ; 
	public PCFCapteurElement(String id, String type) 
	{
		this.id = id ;
		if (type.equalsIgnoreCase("canton"))
			this.type = EType.CANTON ;
		else
			this.type = EType.STATION ;
	}
	public PCFCapteurElement(String id) 
	{
		this.id = id ;
		this.type = null ;
	}
	public String getFragmentXML()
	{	
		String result = "<capteur id=\"" + this.id + "\" type=\"" + this.getTypeAsString() + "\"/>" ;
		return result;
	}
	private String getTypeAsString() {
		if (type == EType.CANTON)
			return "canton" ;
		else
			return "station" ;
	}
}

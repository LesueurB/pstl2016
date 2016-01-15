package upmc.train.model.messages.xml;

import upmc.train.constantes.IConstantes;

public class PCFLightElement  extends PCFElement implements IConstantes{
	private String id ; 
	
	public String getId() {
		return id;
	}
	private EColor color ; 
	public EColor getColor() {
		return color;
	}

	public PCFLightElement(String id, String color) 
	{
		this.id = id ;
		if (color.equalsIgnoreCase("green"))
			this.color = EColor.GREEN ;
		else
			this.color = EColor.RED ;
	}

	public String getFragmentXML()
	{	
		String result = "<light id=\"" + this.id + "\" color=\"" + this.getColorAsString() + "\"/>" ;
		return result;
	}
	private String getColorAsString() {
		if (color == EColor.GREEN)
			return "green" ;
		else
			return "red" ;
	}	
}

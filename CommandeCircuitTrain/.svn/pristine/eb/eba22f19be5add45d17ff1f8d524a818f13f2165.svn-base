package upmc.train.model.messages.xml;

import upmc.train.constantes.IConstantes;


public class PCFTrainElement  extends PCFElement implements IConstantes
{
	
	private String id ; 
	private EAction action ; 
	private EDirection direction = null;
	public EDirection getDirection() {
		return direction;
	}
	public void setDirection(EDirection direction) {
		this.direction = direction;
	}
	public PCFTrainElement(String id, String action, String direction) 
	{
		this.id = id ;
		if (action.equalsIgnoreCase("start"))
			this.action = EAction.START ;
		else if (action.equalsIgnoreCase("ready"))
			this.action = EAction.READY ;
		else
			this.action = EAction.STOP ;
		
		if (direction.equalsIgnoreCase("forward"))
			this.direction = EDirection.FORWARD ;
		else
			this.direction = EDirection.BACKWARD ;		
	}
	public PCFTrainElement(String id, String action) 
	{
		this.id = id ;
		if (action.equalsIgnoreCase("start"))
			this.action = EAction.START ;
		else if (action.equalsIgnoreCase("ready"))
			this.action = EAction.READY ;
		else
			this.action = EAction.STOP ;
				
	}	
	public PCFTrainElement(String id) 
	{
		this.id = id ;
		this.action = null ;
	}
	public String getId() {
		return id;
	}
	public EAction getAction() {
		return action;
	}
	public String getFragmentXML()
	{	
		String result = "<train id=\"" + this.id + "\" action=\"" + this.getActionAsString() + "\" " ;
		result += "direction=\"" + this.getDirectionAsString() + "\"/>" ;
		return result;
	}
	private String getDirectionAsString() {
		
		if (direction == EDirection.BACKWARD)
			return "backward" ;
		else
			return "forward" ;
	}
	private String getActionAsString() {
		if (action == EAction.START)
			return "start" ;
		else if (action == EAction.READY)
			return "ready" ;
		else
			return "stop" ;
	}
}

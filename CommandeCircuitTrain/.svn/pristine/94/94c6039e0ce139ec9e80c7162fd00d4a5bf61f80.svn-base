package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFSetElement 
{
	private ArrayList<PCFLightElement> lights = new ArrayList<PCFLightElement>() ;
	public ArrayList<PCFLightElement> getLights() {
		return lights;
	}
	
	private ArrayList<PCFSwitchElement> switches = new ArrayList<PCFSwitchElement>() ;
	public ArrayList<PCFSwitchElement> getSwitches() {
		return switches;
	}
	
	public ArrayList<PCFTrainElement> getTrains() {
		return trains;
	}
	private ArrayList<PCFTrainElement> trains = new ArrayList<PCFTrainElement>() ;
	
	
	public PCFSetElement()
	{
	
	}
	
	public void addTrain(PCFTrainElement t)
	{
		this.trains.add(t) ;
	}
	
	public void addLight(PCFLightElement l)
	{
		this.lights.add(l) ;
	}

	public void addSwitch(PCFSwitchElement l)
	{
		this.switches.add(l) ;
	}
	public String getFragmentXML()
	{
		String result = "<set>" ;
		for(PCFTrainElement train : trains)
		{
			result += train.getFragmentXML() ;
		}
		for(PCFLightElement light : lights)
		{
			result += light.getFragmentXML() ;
		}		
		for(PCFSwitchElement sw : switches)
		{
			result += sw.getFragmentXML() ;
		}		
		result += "</set>" ;
		
		return result ;		
	}		
}

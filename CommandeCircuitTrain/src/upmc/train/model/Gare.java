package upmc.train.model;

import upmc.train.model.communication.NetMonitor;

public class Gare extends Canton 
{

	
	public Gare(String nom, int numero, NetMonitor com) 
	{
		super(nom, numero, null);
	}
	public Gare(String nom, String numero, NetMonitor com) 
	{
		super(nom, numero, null);
	}
	public String getType() {
		// TODO Auto-generated method stub
		return "gare";
	}	
}

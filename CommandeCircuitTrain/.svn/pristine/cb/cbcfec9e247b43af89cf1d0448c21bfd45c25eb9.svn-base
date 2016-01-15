package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFLightsElement extends PCFElement {

	private ArrayList<PCFLightElement> lights = new ArrayList<PCFLightElement>() ;
	public ArrayList<PCFLightElement> getLights() {
		return lights;
	}
	
	public void addLight(PCFLightElement light)
	{
		this.lights.add(light) ;
	}
	@Override
	public String getFragmentXML() {
		
		String str = "<lights>" ;
		for(PCFLightElement light : lights)
		{
			str += light.getFragmentXML() ;
		}
		return str + "</lights>" ;
	}

}

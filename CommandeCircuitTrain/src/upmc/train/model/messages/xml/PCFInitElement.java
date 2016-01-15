package upmc.train.model.messages.xml;

import java.util.ArrayList;

public class PCFInitElement  extends PCFElement
{
	private ArrayList<PCFPositionElement> positions = new ArrayList<PCFPositionElement>() ;
	public ArrayList<PCFPositionElement> getPositions() {
		return positions;
	}
	public String getFragmentXML()
	{
		String result = "<init>" ;
		for(PCFPositionElement pos : positions)
		{
			result += pos.getFragmentXML() ;
		}
				
		
		result += "</init>" ;
		
		return result ;		
	}
	public void addPosition(PCFPositionElement pos)
	{
		this.positions.add(pos) ;
	}
}

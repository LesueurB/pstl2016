package upmc.train.model.messages.xml;

import java.util.ArrayList;


public class PCFTopographyElement  extends PCFElement
{
	private ArrayList<PCFEdgeElement> edges = new ArrayList<PCFEdgeElement>();
	private ArrayList<PCFSwitchEdgeElement> switchEdges = new ArrayList<PCFSwitchEdgeElement>();
	public ArrayList<PCFEdgeElement> getEdges() {
		return edges;
	}
	public ArrayList<PCFSwitchEdgeElement> getSwitchEdges() {
		return switchEdges;
	}	
	public String getFragmentXML()
	{
		String result = "<topography>" ;
		for(PCFEdgeElement edge : edges)
		{
			result += edge.getFragmentXML() ;
		}
		for(PCFSwitchEdgeElement edge : switchEdges)
		{
			result += edge.getFragmentXML() ;
		}		
		result += "</topography>" ;
		
		return result ;		
	}
	public void addEdge(PCFEdgeElement edge)
	{
		this.edges.add(edge) ;
	}
	public void addSwitchEdge(PCFSwitchEdgeElement edge)
	{
		this.switchEdges.add(edge) ;
	}
}

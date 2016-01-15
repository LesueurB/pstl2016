package upmc.train.model.messages.xml;

import java.util.ArrayList;

import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.Canton;
import upmc.train.model.Light;
import upmc.train.model.Topography;

public class XMLEncoder 
{
	PCFRootElement root = null ;

	public  PCFRootElement getPCFRoot(String reqid, String type)
	{
		if (root == null)
			root = new PCFRootElement(reqid,type) ;
		return root ;

	}	
	public  PCFRootElement getPCFRoot()
	{
		return root ;

	}
	public void setPCFRoot(PCFRootElement root)
	{
		this.root = root ;

	}
	public void addPCFFormatForTopography(Topography t)
	{
		
		PCFTopographyElement topo = new PCFTopographyElement() ;
		root.setTopography(topo);
		ArrayList<Canton> vus = new ArrayList<Canton>() ;
		for(Canton c : t.getCantonsDepart())
		{
			this.populateTopography(topo, c, vus);
		}
		
	}
	
	public void addPCFFormatForLights(Topography t)
	{
		PCFLightsElement lights = new PCFLightsElement() ;
		for(Light l : t.getLights())
		{
			lights.addLight(new PCFLightElement("" + l.getNumero(), l.getColorAsString())) ;
		}
		root.setLights(lights);
	}
	public void addPCFFormatForCapteur(Canton c)
	{
		
	}	
	private void populateTopography(PCFTopographyElement topo, Canton c, ArrayList<Canton> vus)
	{
		
		PCFEdgeElement edge = new PCFEdgeElement() ;
		if (!vus.contains(c))
		{
			vus.add(c) ;
			PCFCapteurElement capteur = new PCFCapteurElement(c.getNumero() + "" , c.getType()) ;
			edge.setCapteur(capteur);
			for(Canton cp : c.getPrevious(EDirection.FORWARD))
			{
				edge.getIn().addCapteur(new PCFCapteurElement(cp.getNumero() + "" , cp.getType())) ;
				this.populateTopography(topo, cp, vus);
			}
			for(Canton cn : c.getNext(EDirection.FORWARD))
			{
				edge.getOut().addCapteur(new PCFCapteurElement(cn.getNumero() + "" , cn.getType())) ;
				this.populateTopography(topo, cn, vus);
			}		
			topo.addEdge(edge);
		}
	}
}

package upmc.train.model;

import upmc.train.constantes.IConstantes.EColor;
import upmc.train.model.communication.NetMonitor;
import upmc.train.model.exception.AlreadySetException;

public class TopographyFactory
{
	public static Topography createTopographyOval(NetMonitor communication)
	{
		Canton c1 = new Canton("C1",1, null) ;
		Canton c2 = new Canton("C2",2,null) ;
		Canton c3 = new Canton("C3",3,null) ;
		
		Light l1  = new Light("1", communication) ;
		try {
			l1.setColor(EColor.RED);
		} catch (AlreadySetException e) {
			
		}
		Light l2  = new Light("2", communication) ;
		try {
			l2.setColor(EColor.RED);
		} catch (AlreadySetException e) {
			
		}
		Light l3  = new Light("3", communication) ;
		try {
			l3.setColor(EColor.RED);
		} catch (AlreadySetException e) {
			
		}
		c1.setLight(l1);
		c2.setLight(l2);
		c3.setLight(l3);
		
		c1.addNextCanton(c2);
		c2.addNextCanton(c3);
		c3.addNextCanton(c1);
		
		c2.addPreviousCanton(c1);
		c3.addPreviousCanton(c2);
		c1.addPreviousCanton(c3);
		
		Topography t = new Topography() ;
		t.addCanton(c1);
		t.addToGlobalList(c1);
		t.addToGlobalList(c2);
		t.addToGlobalList(c3);
		
		t.addLight(l1);
		t.addLight(l2);
		t.addLight(l3);
		return t ;
	}
	public static Topography createTopographyOval2(NetMonitor communication)
	{	
		Canton c1 = new Canton("1",1, null) ;
		Gare g1 = new Gare("4",4, null) ;
		Canton c2 = new Canton("2",2, null) ;
		Gare g2 = new Gare("5",5,null) ;
		Canton c3 = new Canton("3",3,null) ;
		Gare g3 = new Gare("6",6,null) ;
		
		Light l1  = new Light("1", communication) ;
		Light l2  = new Light("2", communication) ;
		Light l3  = new Light("3", communication) ;
		c1.setLight(l1);
		c2.setLight(l2);
		c3.setLight(l3);
		
		c1.addNextCanton(g1);
		g1.addNextCanton(c2);
		c2.addNextCanton(g2);
		g2.addNextCanton(c3);
		c3.addNextCanton(g3);
		g3.addNextCanton(c1);
		
		g1.addPreviousCanton(c1);
		c2.addPreviousCanton(g1);
		g2.addPreviousCanton(c2);
		c3.addPreviousCanton(g2);
		g3.addPreviousCanton(c3);
		c1.addPreviousCanton(g3);
		
		Topography t = new Topography() ;
		t.addCanton(c1);
		t.addToGlobalList(c1);
		t.addToGlobalList(c2);
		t.addToGlobalList(c3);
		t.addToGlobalList(g1);
		t.addToGlobalList(g2);
		t.addToGlobalList(g3);
		
		t.addLight(l1);
		t.addLight(l2);
		t.addLight(l3);
		return t ;
	}
	public static Topography createTopographyOval3(NetMonitor communication)
	{	
		Gare g1 = new Gare("1",1, null) ;
		Gare g2 = new Gare("2",2,null) ;
		Gare g3 = new Gare("3",3, null) ;
		
		Light l1  = new Light("1", communication) ;
		Light l2  = new Light("2", communication) ;
		Light l3  = new Light("3", communication) ;
		g1.setLight(l1);
		g2.setLight(l2);
		g3.setLight(l3);
		
	
		g1.addNextCanton(g2);
		
		g2.addNextCanton(g3);
		
		g3.addNextCanton(g1);
		
		g1.addPreviousCanton(g3);
		g2.addPreviousCanton(g1);
		g3.addPreviousCanton(g2);
		
		
		Topography t = new Topography() ;
		t.addCanton(g1);
		t.addToGlobalList(g1);
		t.addToGlobalList(g2);
		t.addToGlobalList(g3);
		
		t.addLight(l1);
		t.addLight(l2);
		t.addLight(l3);
		return t ;
	}
}

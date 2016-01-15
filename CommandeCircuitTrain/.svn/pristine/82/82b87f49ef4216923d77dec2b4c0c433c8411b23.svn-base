package upmc.train.model;


import java.util.ArrayList;

public class Route 
{
	private ArrayList<PartRoad> portionRoute = new ArrayList<PartRoad>() ;
	private int actuel = 0 ;
	public void addPartRoad(PartRoad p)
	{
		this.portionRoute.add(p) ;
	}
	private int getNumeroCantonSuivant()
	{
		int canton = this.actuel;
		if (this.portionRoute.get(actuel).isFinish())
		{
			if (this.actuel == this.portionRoute.size() )
				actuel = -1 ;
			else 
				canton = this.actuel + 1 ;
			return canton ;
		}
		return actuel ;
	}
	public Canton gotoNextCanton()
	{
		try
		{
			Canton c =  this.portionRoute.get(actuel).gotoNextCanton() ; 
			this.actuel = this.getNumeroCantonSuivant() ;
			return c ;
		}
		catch(Exception e)
		{
			System.err.println("erreur");
			return null ;
		}
	}
	
	public Canton getNextCanton()
	{	
		try
		{
			return this.portionRoute.get(actuel).getNextCanton() ;
		}
		catch(Exception e)
		{
			return null ;
		}
	}
}

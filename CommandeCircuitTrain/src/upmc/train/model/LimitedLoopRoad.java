package upmc.train.model;

public class LimitedLoopRoad extends InfiniteLoopRoad 
{
	private int nb ;
	public LimitedLoopRoad(int nb)
	{
		super() ;
		this.nb = nb ;
		
	}
	protected int getNumeroCantonSuivant()
	{
		int canton;
		if (this.actuel == this.cantons.size() - 1)
		{
			this.nb-- ;
			canton = 0 ;
		}
		else 
			canton = this.actuel + 1 ;
		return canton ;
	}	
	public boolean isFinish()
	{
		return this.nb <= 0 ;
		
	}
}

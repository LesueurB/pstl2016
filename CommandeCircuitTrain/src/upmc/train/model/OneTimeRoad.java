package upmc.train.model;

public class OneTimeRoad extends PartRoad 
{
	protected int getNumeroCantonSuivant()
	{
		int canton;
		canton = this.actuel + 1 ;
		return canton ;
	}
	public boolean isFinish()
	{
		if (this.actuel == this.cantons.size())
			return true ;
		else 
			return false ;
	}
}

package upmc.train.controller.model;

import upmc.train.constantes.IConstantes.EAction;
import upmc.train.constantes.IConstantes.EColor;
import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.Canton;
import upmc.train.model.Light;
import upmc.train.model.Train;
import upmc.train.model.exception.AlreadySetException;


/**
 * @author brunolesueur
 *
 *
 *Cette classe va servir � faire fonctionner le r�seau f�rroviere en mode "metro", les trains se suivent 
 *mais ne doivent pas se collisionner, de plus ils s'arr�te pendant 15s dans une gare
 */
public class StrategieMetroAllerRetour implements IControleurStrategie
{

	private Controller c ;
	
	private EDirection direction = EDirection.FORWARD ;
	
	public void changeFeuMain(int numero)
	{
		
	}
	public StrategieMetroAllerRetour(Controller c)
	{
		this.c = c ;
	}
	
	/**
	 * Dans cette version du controleur, lorsque le feu passe au vert on regarde si une loco n'est pas en attente 
	 * sur le canton pr�c�dent,
	 * si c'est la cas on fait avanc� et on fait r�cursivement la meme chose pour le feux pr�c�dent 
	 * @param feu : numero du feu qui passe au vert
	 */
	public void setFeuxVert(int feu)
	{	
	}
	
	/* (non-Javadoc)
	 * @see clientwithrottle.IControleurStrategie#reaction(int)
	 */
	@Override
	public void reactionGare(String capteur) 
	{
		Canton canton = c.getTopography().getCantonByName(capteur) ;
		Train t = this.c.getTrainOn(canton.getPrevious(this.direction).get(0).getNom()) ;
		if (t == null)
		{
			//t.setAction(EAction.STOP) ;
			System.out.println("Train null non normal") ;
		}
		else
		{
			t.setAction(EAction.ENSTATION);
			try {
				canton.getLight().setColor(EColor.RED);
			} catch (AlreadySetException e) {
			
				e.printStackTrace();
			}
			AttenteTrain attente = new AttenteTrain(t,canton,this) ;
			attente.start();
			c.changed(null);
		}
		
	}
	
	public void redemarreTrain(Train t, Canton nextGare)
	{
		try {
			t.getCanton().getLight().setColor(EColor.GREEN);
		} catch (AlreadySetException e) {
			
			e.printStackTrace();
		}		
		t.setCanton(t.getNextCanton()) ;
		boolean reverse = false ;
		if (this.direction == EDirection.FORWARD)
		{
			try {
				t.getNextCanton() ;
			} catch (Exception e) {
				reverse = true ;
			}
		}
		else
		{
			try {
				t.getNextCanton() ;
			} catch (Exception e) {
				reverse = true ;
			}		
		}
		
		if (!reverse)
			t.setAction(EAction.START);
		else
		{
			if (this.direction == EDirection.FORWARD)
			{
				this.direction = EDirection.BACKWARD ;
				t.reverse();
			}
			else
			{
				this.direction = EDirection.FORWARD ;
				t.forward();
			}
			t.setAction(EAction.START);
		}
			
		//this.propageGreenLight(t.getCanton().getLight(), t.getDirection());
		c.changed(null);
	}



	@Override
	public boolean acceptLightChange(Light light, EColor color) 
	{
		return true ;
	}


	@Override
	public void propageGreenLight(Light light, EDirection direction)  
	{
	
	}
	@Override
	public void reaction(String gare) {
		// TODO Auto-generated method stub
		this.reactionGare(gare);
	}
	
	@Override
	public void setGreenLight() {
		
		
	}	

}

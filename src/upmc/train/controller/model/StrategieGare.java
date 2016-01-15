package upmc.train.controller.model;

import upmc.train.constantes.IConstantes.EAction;
import upmc.train.constantes.IConstantes.EColor;
import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.Canton;
import upmc.train.model.Gare;
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
public class StrategieGare implements IControleurStrategie
{

	protected Controller c ;
	
	public void changeFeuMain(int numero)
	{
		
	}
	public StrategieGare(Controller c)
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
	public void reaction(String id) 
	{

		c.changed(null);
		
	}
	
	public void redemarreTrain(Train t, Canton nextGare)
	{
		if (!(t.getActionsAsString().equals("STOP")))
			t.setAction(EAction.ATTENTE) ;
		this.c.printSituation();
		System.out.println("------------> REDEMARRAGE apres arret en station de " + t.getAdresse() + " depuis le controleur") ;
		System.out.println("------------> REACTION : en precaution on met  " + t.getAdresse() + " en attente") ;
	
		//if (nextGare.getNext(t.getDirection()).get(0).getLight().getColorAsString().equals("green"))
		Train tr = c.getTrainOn(nextGare.getNom()) ;
		if (tr == null)
		{						
				System.out.println("------------> REACTION : condition verifiee, pas de train sur " + nextGare.getNom()) ;
				t.setCanton(nextGare) ;
				if (t.getPreviousCantonWithLight().getLight().getColorAsString().equals("green"))
				{
					for (Train tt : this.c.getTrains())
						tt.setAction(EAction.STOP);
					System.out.println("ERREUR") ;
				}
				System.out.println("------------> REACTION : le train " + t.getAdresse() + " est START sur " + t.getCanton().getNom());
				t.setAction(EAction.START);
				try {
					if (t.getCanton().getLight().getColorAsString().equals("green"))
						t.getCanton().getLight().setColor(EColor.RED);
				} catch (AlreadySetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Train tr1 = this.c.getTrainOn(t.getPreviousCantonWithLight().getNom()) ;
				if (tr1 == null)
				{
					System.out.println("------------> REACTION : condition verifiee, pas de train sur " + t.getPreviousCantonWithLight().getNom()) ;					
					this.propageGreenLight(t.getPreviousCantonWithLight().getLight(), t.getDirection());
				}
				else
				{
					System.out.println("------------> FIN REACTION ----------------------------------------------------------------------------");
					this.c.printSituation();					
				}
				c.changed(null);
		}
		else
		{
			//this.propageGreenLight(t.getCanton().getLight(), t.getDirection());
		}
		c.changed(null);
	}
	
	@Override
	public void reactionGare(String gareId) 
	{
		this.c.printSituation();
		System.out.println("------------> ACTIVATION de " + gareId + " depuis le moniteur") ;
		Gare gare = (Gare)(c.getTopography().getCantonByName(gareId)) ;
		Train t = this.c.getTrainOn(gare.getPrevious(EDirection.FORWARD).get(0).getNom()) ;
		if (t == null)
			System.err.println("I don't find train on this portion, are you really sure this is the good station id : " + gareId);
		Light l = gare.getLight() ;
		try {
			System.out.println("------------> REACTION : feu " + l.getNumero() + " est mis a rouge") ;
			l.setColor(EColor.RED);
		} catch (AlreadySetException e) {
			
		}
		System.out.println("------------> REACTION : " + t.getAdresse() + " est mis en station  (en station " + t.getNextCanton().getNom() + ")") ;
		t.setAction(EAction.ENSTATION);
		
		AttenteTrain att = new AttenteTrain(t,(Gare)(t.getNextCanton(1)),this);
		att.start();
		//this.propageGreenLight(t.getCanton().getLight() ,t.getDirection());
		c.changed(null);
		System.out.println("------------> FIN REACTION -----------------------------------------");  
		this.c.printSituation();
		
		
		
	}


	@Override
	public boolean acceptLightChange(Light light, EColor color) 
	{
		return true ;
	}


	@Override
	public void propageGreenLight(Light light, EDirection direction)  
	{
		boolean b = false ;
		Canton canton = this.c.getTopography().getCantonByName(light.getNumero()) ;
		try {
			System.out.println("------------> REACTION : feu " + light.getNumero() + " est mis a vert") ;
			light.setColor(EColor.GREEN);
		} catch (AlreadySetException e1) {
			for (Train tt : this.c.getTrains())
				tt.setAction(EAction.STOP);
			System.err.println("ERREUR : FEUX DEVRAIT ETRE ROUGE") ;
		}
		for(Canton cpp : canton.getPrevious(EDirection.FORWARD))
		{

			Train tr = this.c.getTrainOn(cpp.getNom()) ;
			if (tr != null)
			{
				b = true ;
				if (tr.getActionsAsString().equals("STOP"))
				{
					System.out.println("------------> REACTION : condition verifiee, train " + tr.getAdresse() + " STOP sur " + cpp.getNom()) ;				
					tr.setAction(EAction.START);
				}
				if (tr.getActionsAsString().equals("ATTENTE"))
				{
					System.out.println("------------> REACTION : condition verifiee, train " + tr.getAdresse() + " en attente sur " + tr.getNextCanton().getNom()) ;
					tr.setCanton(tr.getNextCanton());
					System.out.println("------------> REACTION : le train " + tr.getAdresse() + " est START sur " + tr.getCanton().getNom());
					tr.setAction(EAction.START);
					Train tr1 = this.c.getTrainOn(tr.getPreviousCantonWithLight().getNom()) ;
					try {
						System.out.println("------------> REACTION : feu " + light.getNumero() + " est mis a rouge") ;
						tr.getCanton().getLight().setColor(EColor.RED);
					} catch (AlreadySetException e) {
						
						e.printStackTrace();
					}
					if (tr1 == null)
					{
						this.propageGreenLight(tr.getPreviousCantonWithLight().getLight(), tr.getDirection());
					}
					else
					{
						System.out.println("------------> FIN REACTION -----------------------------------------");  
						this.c.printSituation();						
					}
				}
			}
			if (!b)
			{
				System.out.println("------------> FIN REACTION -----------------------------------------");  
				this.c.printSituation();
			}
		}
		
	}
	
	@Override
	public void setGreenLight() {
		
		
	}	
	

}

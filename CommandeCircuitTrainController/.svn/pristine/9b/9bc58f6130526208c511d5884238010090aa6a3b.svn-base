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
public class StrategieGarePascal extends StrategieGare implements IControleurStrategie
{
	
	public void changeFeuMain(int numero)
	{
		
	}
	public StrategieGarePascal(Controller c)
	{
		super(c) ;
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
		this.c.printSituation();
		System.out.println("------------> REDEMARRAGE apres arret en station de " + t.getAdresse() + " depuis le controleur") ;
		System.out.println("------------> REACTION : en precaution on met  " + t.getAdresse() + " en attente") ;
		if (!(t.getActionsAsString().equals("STOP")))
			t.setAction(EAction.ATTENTE) ;
		//if (nextGare.getNext(t.getDirection()).get(0).getLight().getColorAsString().equals("green"))
		Train tr = c.getTrainOn(nextGare.getNom()) ;
		if (tr == null)
		{
				System.out.println("------------> REACTION : condition verifiee, pas de train sur " + nextGare.getNom()) ;
			
				if (nextGare.getNext(EDirection.FORWARD).get(0).getLight().getColorAsString().equals("green"))
				{
					System.out.println("------------> REACTION : condition verifiee, le feu " + nextGare.getNext(EDirection.FORWARD).get(0).getLight().getNumero() + " est vert"); 
					t.setCanton(nextGare) ;
					/*if (t.getPreviousCantonWithLight().getLight().getColorAsString().equals("green"))
					{
						for (Train tt : this.c.getTrains())
							tt.setAction(EAction.STOP);
						System.out.println("ERREUR") ;
						return ;
					}*/
					
					t.setAction(EAction.START);
					System.out.println("------------> REACTION : le train " + t.getAdresse() + " est START sur " + t.getCanton().getNom());
					try {
						if (t.getCanton().getLight().getColorAsString().equals("green"))
						{
							System.out.println("------------> REACTION : le feu " + t.getCanton().getLight().getNumero() + " est mis � ROUGE");
							t.getCanton().getLight().setColor(EColor.RED);
						}
					} catch (AlreadySetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
/*				
  				Train tr1 = this.c.getTrainOn(t.getPreviousCantonWithLight().getNom()) ;
				if (tr1 == null)
					this.propageGreenLight(t.getPreviousCantonWithLight().getLight(), t.getDirection());
					
*/
				c.changed(null);
		}
		else
		{
			//this.propageGreenLight(t.getCanton().getLight(), t.getDirection());
		}
		c.changed(null);
		
		System.out.println("------------> FIN REACTION ----------------------------------------------------------------------------");
		this.c.printSituation();
	}
	
	@Override
	public void reactionGare(String gareId) 
	{
		this.c.printSituation();
		System.out.println("------------> ACTIVATION de " + gareId + " depuis le moniteur") ;
		Gare gare = (Gare)(c.getTopography().getCantonByName(gareId)) ;
		Train t = this.c.getTrainOn(gare.getPrevious(EDirection.FORWARD).get(0).getNom()) ;
		if (t == null)
		{
			System.err.println("I don't find train on this portion, are you really sure this is the good station id : " + gareId);
			return ;
		}
		Light l = gare.getLight() ;
		try {
			l.setColor(EColor.RED);
		} catch (AlreadySetException e) {
			
		}
		System.out.println("------------> REACTION : " + t.getAdresse() + " est mis en station  (en station " + t.getNextCanton().getNom() + ")") ;
		t.setAction(EAction.ENSTATION);
		//Train tr1 = this.c.getTrainOn(t.getPreviousCantonWithLight().getNom()) ;
		//if (tr1 == null)
		System.out.println("------------> REACTION : feu " + t.getCanton().getLight().getNumero() + " est mis a vert") ;
		this.propageGreenLight(t.getCanton().getLight(), t.getDirection());
		AttenteTrain att = new AttenteTrain(t,(Gare)(t.getNextCanton(1)),this);
		att.start();
		//this.propageGreenLight(t.getCanton().getLight() ,t.getDirection());
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
		Canton canton = this.c.getTopography().getCantonByName(light.getNumero()) ;
		try {
			light.setColor(EColor.GREEN);
		} catch (AlreadySetException e1) {
			//for (Train tt : this.c.getTrains())
			//	tt.setAction(EAction.STOP);
			System.err.println("ERREUR : FEUX DEVRAIT ETRE ROUGE, CONTINUE QUAND MEME") ;
			c.changed(null);
			return ;
		}
		for(Canton cpp : canton.getPrevious(EDirection.FORWARD))
		{
			Train tr = this.c.getTrainOn(cpp.getNom()) ;
			if (tr != null)
			{
				if (tr.getActionsAsString().equals("STOP"))
				{
					System.out.println("------------> REACTION : train qui etait stop " + tr.getAdresse()+ " sur " + tr.getCanton().getNom() + " est mis a start") ; ;
					tr.setAction(EAction.START);
				}
			}
			for(Canton cp : cpp.getPrevious(EDirection.FORWARD))
			{
				Train tr1 = this.c.getTrainOn(cp.getNom()) ;
				if (tr1 != null)
				{
					if (tr1.getActionsAsString().equals("ATTENTE"))
						{
							System.out.println("------------> REACTION : train qui etait attente " + tr1.getAdresse()+ " sur le feu " + tr1.getNextCanton().getNom() + " est mis a start sur le canton "+ tr1.getNextCanton().getNom())  ;
						
							tr1.setCanton(tr1.getNextCanton());
							tr1.setAction(EAction.START);
						}
					}
			}
		}
		System.out.println("------------> FIN REACTION -----------------------------------------");  
		this.c.printSituation();
		/*
		for(Canton cpp : canton.getPrevious(EDirection.FORWARD))
		{

			Train tr = this.c.getTrainOn(cpp.getNom()) ;
			if (tr != null)
			{

				if (tr.getActionsAsString().equals("STOP"))
				{
					tr.setAction(EAction.START);
				}
				if (tr.getActionsAsString().equals("ATTENTE"))
				{
					tr.setCanton(tr.getNextCanton());
					tr.setAction(EAction.START);
					Train tr1 = this.c.getTrainOn(tr.getPreviousCantonWithLight().getNom()) ;
					try {
						tr.getCanton().getLight().setColor(EColor.RED);
					} catch (AlreadySetException e) {
						
						e.printStackTrace();
					}
					if (tr1 == null)
						this.propageGreenLight(tr.getPreviousCantonWithLight().getLight(), tr.getDirection());
				}
			}
			/*for (Canton cp : cpp.getPrevious(EDirection.FORWARD))
			{
				tr = this.c.getTrainOn(cp.getNom()) ;
				if (tr != null)
				{
					if (tr.getActionsAsString().equals("ATTENTE"))
					{
						tr.setCanton(tr.getNextCanton());
						tr.setAction(EAction.START);
						this.propageGreenLight(tr.getPreviousCantonWithLight().getLight(), tr.getDirection());
					}
				}
			}	
		}*/
		
	}
	
	

}

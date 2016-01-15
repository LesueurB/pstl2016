package upmc.train.controller.model;

import java.util.ArrayList;

import upmc.train.constantes.IConstantes.EAction;
import upmc.train.constantes.IConstantes.EColor;
import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.Canton;
import upmc.train.model.Gare;
import upmc.train.model.Light;
import upmc.train.model.Train;
import upmc.train.model.exception.AlreadySetException;
import upmc.train.model.exception.NoSuchElementException;


/**
 * @author brunolesueur
 *
 *
 *Cette classe va servir � faire fonctionner le r�seau f�rroviere en mode "metro", les trains se suivent 
 *mais ne doivent pas se collisionner, de plus ils s'arr�te pendant 15s dans une gare
 */
public class StrategieEntreeCircuit implements IControleurStrategie, Runnable
{

	private Controller c ;
	
	public void changeFeuMain(int numero)
	{
		
	}
	public StrategieEntreeCircuit(Controller c)
	{
		this.c = c ;
		Thread t = new Thread(this) ;
		t.start();
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
	public void reaction(String capteur) 
	{
		this.c.printSituation();
		System.out.println("------------> ACTIVATION de " + capteur + " depuis le moniteur") ;		
		Canton canton = c.getTopography().getCantonByName(capteur) ;
		Train t = null;
		Train t1 = null ;
		for(Canton c : canton.getAllPrevious(EDirection.FORWARD))
		{	
			Train tr ;
			
			tr = this.c.getTrainOn(c.getNom()) ;
			if (tr != null)
				t1 = tr ;
		}
		Train t2 = null;
		for(Canton c : canton.getAllNext(EDirection.FORWARD))
		{	
			Train tr ;
			tr = this.c.getTrainOn(c.getNom()) ;
			if (tr != null)
				t2 = tr ;
		}
		if ((t1 != null) && (t1.getDirection() == EDirection.FORWARD))
			t = t1 ;
		if ((t2 != null) && (t2.getDirection() == EDirection.BACKWARD))
			t = t2 ;		
		Light l;
		try {
			l = c.getTopography().getLightByName(capteur);
			if (l.getColorAsString().equals("red"))
			{
				System.out.println("------------> REACTION : condition verifiee, feu " + l.getNumero() + " rouge") ;				
				System.out.println("------------> REACTION :  on met  " + t.getAdresse() + " en ATTENTE sur " + t.getNextCanton().getNom()) ;				
				
				t.setAction(EAction.ATTENTE);
				this.c.changed(null);
				return ;
			}
		}
		catch (NoSuchElementException e1) {
			e1.printStackTrace();
			this.c.stopAllTrains();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.c.stopAllTrains();
			c.bye();
		}
		if (t != null)
		{	
			
			t.setCanton(canton);
			ArrayList<Canton> prev = t.getCanton().getPrevious(t.getDirection()) ;
			if (prev.size() < 2)
			{
				this.changementFeuNormaux(t, canton);
			}
			else
			{
				this.changementFeuProritaire(t, canton, prev);
			}
		}
		c.changed(null);
		System.out.println("------------> FIN REACTION -----------------------------------------");  
		this.c.printSituation();
	}
	private void changementFeuProritaire(Train t, Canton canton, ArrayList<Canton> previous)
	{
		
		if (!c.isTrainWaitingOn(previous.get(1).getNom()))
		{
			this.changementFeuNormaux(t, canton);
			return ;
		}
		if ((c.getTrainOn(previous.get(0).getNom()) != null) || (c.isTrainWaitingOn(previous.get(0).getNom())))
		{
			this.changementFeuNormaux(t, canton);
			return ;			
		} ;
		Light l2 ;
		try {
			l2 = c.getTopography().getLightByName(canton.getNom()) ;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			l2 = null ;
		}
		if (l2 != null)
		{
			if (t.getDirection() == EDirection.FORWARD)
			{
				c.changeLight(l2, EColor.RED, t.getDirection());
			}
			else
			{
				
			}
		}
		Light lgreen = previous.get(1).getLight() ;
		Light lred = previous.get(0).getLight() ;
		if (lgreen != null)
		{
			System.out.println("------------> REACTION : feu " + lgreen.getNumero() + " est mis a vert") ;
			c.changeLight(lgreen ,EColor.GREEN, t.getDirection());
		}
		if (lred != null)
		{
			System.out.println("------------> REACTION : feu " + lred.getNumero() + " est mis a rouge") ;
			c.changeLight(lred, EColor.RED, t.getDirection());
		}		
		//this.changementFeuNormaux(t, canton);
	}
	private void changementFeuNormaux(Train t, Canton canton)
	{
		Light l1,l2 ;
		try {
		l1 = t.getPreviousCantonWithLight().getLight() ;
		}
		catch (Exception e) {
			e.printStackTrace();
			l1 = null ;
		}
		try {
			l2 = c.getTopography().getLightByName(canton.getNom()) ;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			l2 = null ;
		}
		Light lred ;
		Light lgreen ;
		
		if (t.getDirection() == EDirection.FORWARD)
		{
			lred = l2 ;
			lgreen = l1 ;
		}
		else
		{
			lred = l1 ;
			lgreen = l2 ;
		}
		if (lgreen != null)
		{
			System.out.println("------------> REACTION : feu " + lgreen.getNumero() + " est mis a vert") ;
			c.changeLight(lgreen ,EColor.GREEN, t.getDirection());
		}
		if (lred != null)
		{
			System.out.println("------------> REACTION : feu " + lred.getNumero() + " est mis a rouge") ;
			c.changeLight(lred, EColor.RED, t.getDirection());
		}		
	}
	public void redemarreTrain(Train t, Canton nextGare)
	{
		this.c.printSituation();
		System.out.println("------------> REDEMARRAGE apres arret en station de " + t.getAdresse() + " depuis le controleur") ;
		t.setCanton(t.getNextCanton()) ;
		System.out.println("------------> REACTION :  on met  " + t.getAdresse() + " en START sur " + t.getCanton().getNom()) ;

		t.setAction(EAction.START);
		
		c.changed(null);
		System.out.println("------------> FIN REACTION -----------------------------------------");  
		this.c.printSituation();		
	}
	
	@Override
	public void reactionGare(String gareId) 
	{
		this.c.printSituation();
		System.out.println("------------> ACTIVATION de " + gareId + " depuis le moniteur") ;		
		this.c.printSituation();
		Gare gare = (Gare)(c.getTopography().getCantonByName(gareId)) ;
		Train t = this.c.getTrainOn(gare.getPrevious(EDirection.FORWARD).get(0).getNom()) ;
		System.out.println("------------> REACTION : " + t.getAdresse() + " est mis en station  (en station " + t.getNextCanton().getNom() + ")") ;		
		t.setAction(EAction.ENSTATION);
		AttenteTrain att = new AttenteTrain(t,gare.getNext(t.getDirection()).get(0),this);
		att.start();
		c.changed(null);	
		System.out.println("------------> FIN REACTION -----------------------------------------");  
		this.c.printSituation();		
	}


	@Override
	public boolean acceptLightChange(Light light, EColor color) 
	{
		if (color == EColor.RED)
			return true ;
		Train t = this.c.getTrainOn(light.getNumero()) ;
		if (t == null)
			return true ;
		return false ;
	}


	@Override
	public void propageGreenLight(Light light, EDirection direction)  
	{
		Canton canton = this.c.getTopography().getCantonByName(light.getNumero()) ;
		try {
			System.out.println("------------> REACTION : feu " + light.getNumero() + " est mis a vert") ;
			light.setColor(EColor.GREEN);
		} catch (AlreadySetException e1) {
		}
		boolean b = false ;
		for(Canton cp : canton.getPrevious(direction))
		{
			Train tr = this.c.getTrainOn(cp.getNom()) ;
			if (tr != null)
			{
				if (tr.getActionsAsString().equals("ATTENTE"))
				{
					b = true ;
					tr.setCanton(canton);
					tr.setAction(EAction.START);
					System.out.println("------------> REACTION : condition verifiee, train " + tr.getAdresse() + " EN ATTENTE sur " + tr.getCanton().getNom()) ;				
					System.out.println("------------> REACTION : train " + tr.getAdresse() + " START sur " + tr.getCanton().getNom()) ;				
					
					try {
						System.out.println("------------> REACTION : feu " + light.getNumero() + " est mis a reouge") ;
						light.setColor(EColor.RED);
					} catch (AlreadySetException e1) {
					}
					try
					{
						this.propageGreenLight(tr.getPreviousCantonWithLight().getLight(), direction);
					}
					catch(Exception e)
					{
						//cette exception est la uniquement pour le cas ou le canton precedent
						//n 'existe pas
					}
					
				}
			}//e
		}
		if (!b)
		{
			System.out.println("------------> FIN REACTION -----------------------------------------");  
			this.c.printSituation();				
		}
		
	}
	@Override
	public void setGreenLight() {
		
		
	}
	@Override
	public void run() {
		//on se sert ici d'un nouveau thread pour éventuellement libérer
		//une loco sur une ovie non prioritaire sur un aiguillage avec deux entrées
		//pour cela on repere tout les aiguillages possedant deux entrees
		//si il existe une loco en attente sur le troncon non prioritaire
		//et si le troncon prioritaire est vert alors on passe le troncon
		//prioritaire au rouge et le troncon non prioritaire passe au vert
		while(true)
		{
			for(Canton canton : c.getTopography().getGlobalListCanton())
			{
				ArrayList<Canton> previous = canton.getPrevious(EDirection.FORWARD) ;
				if (previous.size() > 1)
				{
					Canton prioritaire = previous.get(0) ;
					Canton nonPrioritaire = previous.get(1) ;
					if (prioritaire.getLight().getColorAsString().equals("green"))
					{
						Train t = c.getTrainOn(nonPrioritaire.getPrevious(EDirection.FORWARD).get(0).getNom()) ;
						if ((t != null) && (t.getActionsAsString().equals("ATTENTE")))
						{
							c.changeLight(prioritaire.getLight(), EColor.RED, t.getDirection());
							c.changeLight(nonPrioritaire.getLight(), EColor.GREEN, t.getDirection());
						}
					}
				}
			}
		}
	}
}

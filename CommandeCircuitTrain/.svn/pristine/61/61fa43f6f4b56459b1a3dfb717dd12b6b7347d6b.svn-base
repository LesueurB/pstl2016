package upmc.train.model;

import upmc.train.model.Train;

/**
 * @author brunolesueur
 * Date de  dernière modification 2013-11-20 : mise en place de commentaires
 */
public class AttenteTrain extends Thread
{
	/**
	 * Train en attente de redémarrage
	 */
	private Train train ;
	
	/**
	 * délai en seconde de l'attente du train
	 */
	private int delaiEnSeconde = 15 ;
	/**
	 * @param delaiEnSeconde : nouveau delai de l'attente du train
	 */
	public void setDelaiEnSeconde(int delaiEnSeconde) {
		this.delaiEnSeconde = delaiEnSeconde;
	}

	/**
	 * @param t : train devant rester en attente
	 */
	public AttenteTrain(Train t)
	{
		this.train = t ;
		
	}

	/**
	 * @param t : train devant rester en attente
	 * @param delai : délai en seconde de l'attente du train
	 */
	public AttenteTrain(Train t, int delai)
	{
		this.train = t ;
		this.delaiEnSeconde = delai ;
		
	}	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		/*
		 *On attend pendant XX secondes
		 */
		try {
			Thread.sleep(this.delaiEnSeconde * 1000) ;
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		/*
		 * on demande le redémarrage
		 */
		train.stopWaiting();
		
		
	}

}

package upmc.train.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import upmc.train.constantes.IConstantes.EDirection;
import upmc.train.model.communication.NetMonitor;

/**
 * Date de  derni�re modification 2013-11-20 : mise en place de commentaires
 * @author brunolesueur
 * 
 */
public class Canton
{
	
	/**
	 * liste des cantons suivants dans le sens positif (arbitraire)
	 */
	private ArrayList<Canton> next = new ArrayList<Canton>() ;
	/**
	 * 	 * liste des cantons pr�c�dents dans le sens n�gatif (arbitraire)
	 */
	private ArrayList<Canton> previous = new ArrayList<Canton>() ;
	private Switch switche  = null;
	
	private NetMonitor com ;
	
	/**
	 * Feu associ� au canton
	 */
	private Light light ;
	/**
	 * num�ro du canton
	 */
	private int numero ;
	/**
	 * nom du canton
	 */
	private String nom = "" ; 
	
	
	/**
	 * @return
	 */
	public Light getLight() {
		return light;
	}

	/**
	 * @param light
	 */
	public void setLight(Light light) {
		this.light = light;
	}


	/**
	 * @return  nom du canton
	 */
	public String getNom() {
		return nom;
	}

	
	/**
	 * @return : canton qui est d�sign� par l'aiguillage
	 */
	public Canton getPositionAiguillage() {
		if (this.switche == null)
			return null ;
		return this.switche.getPosition();
	}
	

	/**
	 * @param nom  nom du canton
	 * @param num  numero du canton
	 */
	public Canton(String nom, int num, NetMonitor com)
	{
		this.nom = nom ;
		this.numero = num ;
		this.com = com ;
	}
	public Canton(String nom, String numero, NetMonitor com)
	{
		this.nom = nom ;
		try
		{
			this.numero = Integer.parseInt(numero) ;
		}
		catch(Exception e)
		{
			this.numero = 0 ;
		}
		this.com = com ;
		//this.numero = numero ;
	}
	/**
	 * 
	 * @param nom du canton recherch�
	 * @return parmi les cantons pr�c�dents (sens positif arbitraire) prendre celui de nom "nom" sinon null
	 */
	@SuppressWarnings("unused")
	private Canton findPreviousCantonNamed(String nom)
	{
		for(Canton c : previous)
		{
			if (c.getNom().equals(nom))
				return c ;
		}
		return null ;
	}	
	
	/**
	 * @param nom du canton recherch�
	 * @return parmi les cantons suivant (sens positif arbitraire) prendre celui de nom "nom" sinon null
	 */
	private Canton findNextCantonNamed(String nom)
	{
		for(Canton c : next)
		{
			if (c.getNom().equals(nom))
				return c ;
		}
		return null ;
	}
	
	
	/**
	 * ajoute un canton aux suivants (sens positif arbitraire)
	 * @param canton : 
	 */
	public void addNextCanton(Canton canton)
	{
		this.next.add(canton) ;
		
	}
	
	public void setSwitch(Switch s)
	{
		this.switche = s ;
	}
	
	/**
	 * positionne l'aiguillage sur un canton de nom "nomCanton"
	 * @param nomCanton  
	 * cette methode est appelée uniquement du cote serveur
	 */
	public void changeAiguillageTo(String nomCanton)
	{
		this.switche.setBranch(nomCanton, com) ;	
	}
	
	/**
	 * positionne l'aiguillage sur un canton de nom "nomCanton"
	 * @param nomCanton  
	 * cette methode est appelée uniquement du cote serveur
	 */
	public void changeReverseAiguillageTo(String nomCanton)
	{
		this.switche.setBranch(nomCanton, com) ;	
	}
	/**
	 * @param canton: positionne l'aiguillage sur le canton "canton"
	 * si le canton n'existe pas l'aiguillage va sur null 
	 */
	public void changeReverseAiguillageTo(Canton canton)
	{ 
		this.switche.setBranch(canton.getNom(), com) ;	
	}		
	
	/**
	 * @param canton: positionne l'aiguillage sur le canton "canton"
	 * si le canton n'existe pas l'aiguillage va sur null 
	 */
	public void changeAiguillageTo(Canton canton)
	{ 
		this.switche.setBranch(canton.getNom(), com) ;
	}
	
	/**
	 * ajoute un canton aux pr�c�dents (sens positif arbitraire) 
	 * @param canton
	 */
	public void addPreviousCanton(Canton canton)
	{
		this.previous.add(canton) ;
	}

	/**
	 * @return numero du canton
	 */
	public int getNumero() {
	
	return this.numero;
	}
	

	/**
	 * retourne le canton pr�c�dent en fonctio de la direction (FORWARD = sens positif, BACKWARD = sens n�gatif)
	 * @param direction ( @see  upmc.train.constantes.IConstantes )
	 * @return Canton ou null
	 */
	public ArrayList<Canton> getPrevious(EDirection direction) {
		
		if (direction == EDirection.FORWARD)
			return this.previous;
		else 
			return this.next ;
	}
	/**
	 * 
	 * retourne le canton suivant en fonction de la direction (FORWARD = sens positif, BACKWARD = sens n�gatif)
	 * @param direction  ( {@link }  upmc.train.constantes.IConstantes )
	 * @return Canton ou null
	 */
	
	public ArrayList<Canton> getNext(EDirection direction) {
		if (direction == EDirection.FORWARD)
		{
			if (this.next.size() == 1)
				return this.next ;
			else
			{
				ArrayList<Canton> newNext = new ArrayList<Canton>() ;
				newNext.add(this.switche.getPosition());
				return newNext ;
			}
		}
		else
			return this.previous ;
	}
	public ArrayList<Canton> getAllPrevious(EDirection direction) {
		if (direction == EDirection.FORWARD)
		{
				return this.previous ;
		}
		else
			return this.next ;
	}	
	public ArrayList<Canton> getAllNext(EDirection direction) {
		if (direction == EDirection.FORWARD)
		{
				return this.next ;
		}
		else
			return this.previous ;
	}
	/**
	 * @return
	 */
	public String getType() {
		// TODO Auto-generated method stub
		return "canton";
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Capteur " + this.numero ;
	}

	public void switchAiguillage() {
		this.switche.switchAiguillage(com) ;
	}
	public boolean hasSwitch()
	{
		return this.switche != null ;
	}

	public Switch getSwitch() {
		// TODO Auto-generated method stub
		return this.switche;
	}
}

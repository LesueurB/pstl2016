
package upmc.utils.mvc;


import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author brunolesueur
 */
public class Model
{
    /**
     *
     */
    protected ArrayList <IUpdate>  abonnes; //liste des fenetre de notre model
    /**
     * Constructeur de la class Model
     */
	public Model()
    {
		abonnes = new ArrayList<IUpdate>(); //cree une nouvelle liste
    }
	/**
     * methode qui ajoute une fenetre a notre liste abonnes
     * @param a
     */
	public void addAbonne(IUpdate a)
    {
		abonnes.add(a);
	}
    /**
     * methode qui supprime une fenetre a notre liste abonnes
     * @param a
     */
	public void removeAbonne(IUpdate a)
    {
		abonnes.remove(a);
	}
    /**
     * accesseur a la liste d'abonnes
     * @return abonnes
     */
    public ArrayList<IUpdate> getAbonnes()
    {
		return abonnes;
	}
        
        /**  Methode qui parcourt la liste abonnes et appelle
         * la methode update pour chacun de ces abonnés
         * @param o
         */
	public void changed(Object o)
        {
		IUpdate el;
		Iterator <IUpdate> it = abonnes.iterator();
		while (it.hasNext())
                {
			el = it.next();
			el.updateMVC(o);
		}

	}
	

}
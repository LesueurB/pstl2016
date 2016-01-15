
package upmc.utils.mvc;

/** Auteurs : Lesueur
 * Creation : 19 fevrier 2009
 * Date de derniere modification : 19 fevrier 2009
 * Version : 1.00
 * Commentaires : Cette interface definit une methode update, elle
 * permet de s'assurer que l'interface sera mise a jour
 */
public interface IUpdate {
    /**
     * Cette fonction est abstraite. La méthode d'execution sera différente selon les classes implementant IUpdate.
     * Elle devra etre redefinie dans toute les classes l'implementant
     * @param o
     */
    public void updateMVC (Object o);
}

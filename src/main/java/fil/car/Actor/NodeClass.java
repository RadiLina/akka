/**
 * 
 */
package fil.car.Actor;

import java.util.Arrays;

/**
 * @author Lina RADI
 *
 */
public class NodeClass {
	private String nom;
	private String[] noeuds_fils=null;
	
	/**
	 * 
	 * @param nom
	 */
	public NodeClass(String nom) {
		this.nom = nom;
		//this.noeuds_fils.s
	}

	/**
	 * @param nom
	 * @param noeuds_fils
	 */
	public NodeClass(String nom, String[] noeuds_fils) {
		this.nom = nom;
		this.noeuds_fils = noeuds_fils;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String[] getNoeuds_fils() {
		return noeuds_fils;
	}

	public void setNoeuds_fils(String[] noeuds_fils) {
		this.noeuds_fils = noeuds_fils;
	}
	
}

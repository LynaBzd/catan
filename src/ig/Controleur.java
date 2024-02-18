package ig;

import jeu.Jeu;
import joueur.Joueur;

public class Controleur {
	
	private Jeu jeu; // modele
	private JeuIG ig; // vue
	
	protected Joueur[] ordreJeu;
	protected int joueurAJouer;
	
	public Controleur(Jeu jeu, JeuIG ig) {
		this.jeu = jeu;
		this.ig = ig;
		
		this.ordreJeu = jeu.getOrdreJeu();
		this.joueurAJouer = ordreJeu[0].getPlayerId();
	}
	
	/**
	 * Convertit les coordonnées (i, j) en un entier correspondant à un sommet/côté.
	 * @param i Le numéro de ligne de la tuile.
	 * @param j Le numéro de colonne de la tuile.
	 * @return Un entier entre 0 et 3.
	 */
	private int tuileToInt(int i, int j) {
		return 0;
	}
	
	public boolean ressourceSuffisante(int ble, int bois, int mouton, int argile, int minerai) {
		if (ordreJeu[joueurAJouer].ressourcesSuffisantes(ble, bois, mouton, argile, minerai)) return true;
		else return false;
	}
	
	public void construireColonie(int i, int j) {
		int[] coord = new int[2];
		coord[0] = i+2;
		coord[1] = j+2;
		jeu.construireColonie(joueurAJouer, true, coord, tuileToInt(i, j));
	}
	
	public void construireRoute(int i, int j) {
		int[] coord = new int[2];
		coord[0] = i;
		coord[1] = j;
		jeu.construireRoute(joueurAJouer, coord, tuileToInt(i, j));
	}

	public void deplacerVoleur(int i, int j) {
		int[] coord = new int[2];
		coord[0] = i;
		coord[1] = j;
		if (jeu.deplacerVoleur(joueurAJouer, coord)) {
			
		}
	}
	
	public void acheterCarteDev() {
		jeu.acheterCarteDev(joueurAJouer);
	}
	
	public boolean possedeCarte() {
		return ordreJeu[joueurAJouer].getNbCarteDev() > 0;
	}
	
	public void terminerTour() {
		jeu.terminerTour();
	}
}

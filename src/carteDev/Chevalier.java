package carteDev;

import jeu.Jeu;
import joueur.Joueur;

public class Chevalier {

	public void jouerCarte(Joueur j, Jeu jeu, int[] coord) {
		j.setNbChevalier(j.getNbChevalier() + 1);
		jeu.deplacerVoleur(j.getPlayerId(), coord);
		System.out.println("Une carte \"Chevalier\" a ete jouee.");
	}
}

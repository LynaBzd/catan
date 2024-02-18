package carteDev;

import jeu.Jeu;
import joueur.Joueur;

public class RouteX2 {

	public void jouerCarte(Joueur joueur, Jeu jeu) {
		joueur.setArgile(joueur.getArgile()+2);
		joueur.setBois(joueur.getBois()+2);
		System.out.println("Une carte \"Route\" a ete jouee.");
	} 

}

package carteDev;

import joueur.Joueur;

public class Invention {

	public void jouerCarte(Joueur joueur, String r1, String r2) {
		System.out.print("[Premiere ressource] ");
		joueur.ajouterRessource(r1);
		System.out.print("\n[Deuxieme ressource] ");
		joueur.ajouterRessource(r2);
		System.out.println("Vous avez acquis les ressources suivantes: " + r1 + " et " + r2 + "\n");
	}
	
}

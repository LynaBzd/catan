package carteDev;

import joueur.Joueur;

public class Monopole {

	public void jouerCarte(Joueur joueur, Joueur[] listeJoueurs, String ressource) {
		for (Joueur j: listeJoueurs) {
			if (ressource.equals("ble") && j.getBle() > 0 && j.getPlayerId() != joueur.getPlayerId()) {
				joueur.setBle(joueur.getBle() + j.getBle());
				j.setBle(0);
			}
			else if (ressource.equals("bois") && j.getBois() > 0 && j.getPlayerId() != joueur.getPlayerId()) {
				joueur.setBois(joueur.getBois() + j.getBois());
				j.setBois(0);
			}	
			else if (ressource.equals("mouton") && j.getMouton() > 0 && j.getPlayerId() != joueur.getPlayerId()) {
				joueur.setMouton(joueur.getMouton() + j.getMouton());
				j.setMouton(0);
			}	 
			else if (ressource.equals("argile") && j.getArgile() > 0 && j.getPlayerId() != joueur.getPlayerId()) {
				joueur.setArgile(joueur.getArgile() + j.getArgile());
				j.setArgile(0);
			}
			else if (ressource.equals("minerai") && j.getMinerai() > 0 && j.getPlayerId() != joueur.getPlayerId()) {
				joueur.setMinerai(joueur.getMinerai() + j.getMinerai());
				j.setMinerai(0);
			}
		}
		System.out.println("Une carte \"Monopole\" a ete jouee.\n");
	}

}

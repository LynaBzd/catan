package jeu;

import java.util.Scanner;

import joueur.IA;
import joueur.Joueur;

public class Jouer {

	private Jeu jeu;

	public Jouer(Jeu jeu) {
		this.jeu = jeu;
		
		tourUn();
		tourNormal();
	}

	/**
	 * Demande au joueur l'action qu'il souhaite réalisé.
	 * @param playerId Identifiant du joueur qui joue.
	 * @return Une chaîne de caractère contenant l'action à réaliser. 
	 */
	public String demanderAction(int playerId) {
		Joueur j = jeu.joueurs[playerId];
		if (j.ressourcesSuffisantes(0, 1, 0, 1, 0)) {
			System.out.println("- Construire une route (r)");
		}
		if (j.ressourcesSuffisantes(1, 1, 1, 1, 0)) {
			System.out.println("- Construire une colonie (c)");
		}
		if (j.ressourcesSuffisantes(2, 0, 0, 0, 3)) {
			System.out.println("- Construire une ville (v)");
		}
		if (j.ressourcesSuffisantes(1, 0, 1, 0, 1)) {
			if (j instanceof IA) {
				int x = ((IA) j).unSurDeux();
				if (x == 0) return "a";
			}
			else System.out.println("- Acheter une carte de developpement (a)");
		}
		if (j.getNbCarteDev() > 0 && jeu.jouable) {
			System.out.println("- Jouer une carte de developpement (j)");
		}
		System.out.println("- Echanger avec la banque (e)");
		System.out.println("- Consulter vos ressources (s)");
		System.out.println("- Finir le tour (f)");
		
		System.out.print("> Action: ");
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()) {
        	String s = sc.nextLine();
        	if (!s.trim().equals("")) {
	        	if (s.equalsIgnoreCase("r") || s.equalsIgnoreCase("c") || s.equalsIgnoreCase("v") || s.equalsIgnoreCase("a") || 
	        		s.equalsIgnoreCase("j") || s.equalsIgnoreCase("s") || s.equalsIgnoreCase("f") || s.equalsIgnoreCase("e")) {
	        		return s;
	        	}
        	}
        	else {
        		System.out.println("Action non reconnue.\n");
                return demanderAction(playerId);
        	}
        }
        else {
        	System.out.println("Action non reconnue.\n");
            return demanderAction(playerId);
        }
		System.out.println("Action non reconnue.\n");
		return demanderAction(playerId);
	}
	
	/**
	 * Jouer une carte de développement.
	 * @param playerId Identifiant du joueur.
	 * @param x Un entier correspondant au type de carte de développement joué.
	 */
	private void jouerCarte(int playerId, int x) {
		if (x == 3) { // chevalier
			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
			jeu.jouerCarteDev(playerId, x, jeu.jouable, coord, null, null);
		}
		else if (x == 2) { //route
			jeu.jouerCarteDev(playerId, x, jeu.jouable, null, null, null);
			int n = jeu.joueurs[jeu.joueurAJouer].getNbRoutesRestantes();
			int[] coord = null;
			int c = 0;
			while (jeu.joueurs[jeu.joueurAJouer].getNbRoutesRestantes() != n-2) {
				coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
				c = jeu.joueurs[playerId].demanderCoordonneesRoute();
				jeu.construireRoute(playerId, coord, c);
			}
		}
		else if (x == 1) { // invention
			String r1 = jeu.joueurs[playerId].choisirRessource();
			String r2 = jeu.joueurs[playerId].choisirRessource();
			jeu.jouerCarteDev(playerId, x, jeu.jouable, null, r1, r2);
		}
		else if (x == 0) { // monopole
			String r1 = jeu.joueurs[playerId].choisirRessource();
			jeu.jouerCarteDev(playerId, x, jeu.jouable, null, r1, null);
		}
	}
	
	/**
	 * Joue un trour normal.
	 * @param playerId Identifiant du joueur qui joue.
	 */
	public void jouer(int playerId) {
		if (jeu.nouveauTour) {
			jeu.joueurs[playerId].majCarteDev();
			lancerDes(playerId);
			jeu.nouveauTour = false;
		}
		
		String s = demanderAction(playerId);
		if (s.equalsIgnoreCase("r")) {
			System.out.println("[Construction de route]");
			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
			int c = jeu.joueurs[playerId].demanderCoordonneesRoute();
			jeu.construireRoute(playerId, coord, c);
		}
		if (s.equalsIgnoreCase("c")) {
			System.out.println("[Constrution de colonie]");
			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
			int x = jeu.joueurs[playerId].demanderCoordonneesSommet();
			jeu.construireColonie(playerId, false, coord, x);
		}
		if (s.equalsIgnoreCase("v")) {
			System.out.println("[Constrution de ville]");
			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
			int x = jeu.joueurs[playerId].demanderCoordonneesSommet();
			jeu.construireVille(playerId, coord, x);
		}
		if (s.equalsIgnoreCase("a")) jeu.acheterCarteDev(playerId);
		if (s.equalsIgnoreCase("j")) {
			int x = jeu.joueurs[playerId].choixCarteAJouer();
			if (x != -1) {
				this.jouerCarte(playerId, x);
				jeu.jouable = false;
			}
		}
		if (s.equalsIgnoreCase("e")) {
			String besoin = jeu.joueurs[playerId].choisirRessource();
			int taux = jeu.joueurs[playerId].tauxDeChange(besoin);
			String possede = jeu.joueurs[playerId].choisirRessourceDisponible(taux);
			jeu.demanderEchange(playerId, besoin, possede, taux);
		}
		if (s.equalsIgnoreCase("s")) jeu.joueurs[playerId].afficherRessource();
		if (s.equalsIgnoreCase("f")) jeu.terminerTour();
	}
	
	/**
	 * Demande au joueur de lancer les dés. Les dés sont lancés automatiquement si le joueur est une IA.
	 * @param playerId Identifiant du joueur.
	 */
	public void lancerDes(int playerId) {
		if (jeu.joueurs[playerId] instanceof IA) {
			int d = jeu.lancerDes();
			System.out.println("Joueur " + (playerId+1) + " a obtenu " + d + " au lancer de des.");
    		if (d == 7) {
    			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
    			while (!jeu.deplacerVoleur(playerId, coord)) {
    				System.out.println("Les coordonnees d'arrivee doivent etre differentes des coordonnees de depart.\n");
    				coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
    			}
    			jeu.volerRessource();
    		}
    		else jeu.recolterRessource(d);
		}
		else {
			System.out.print("Lancer les des (o): ");
			Scanner sc = new Scanner(System.in);
			if (sc.hasNextLine()) {
	        	String s = sc.nextLine();
	        	if (!s.trim().equals("") && s.equalsIgnoreCase("o")) {
	        		int d = jeu.lancerDes();
	        		System.out.println("Joueur " + (playerId+1) + " a obtenu " + d + " au lancer de des.");
	        		if (d == 7) {
	        			int[] coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
	        			while (!jeu.deplacerVoleur(playerId, coord)) {
	        				System.out.println("Les coordonnees d'arrivee doivent etre differentes des coordonnees de depart.\n");
	        				coord = jeu.joueurs[playerId].demanderCoordonneesTuile();
	        			}
	        			jeu.volerRessource();
	        		}
	        		else jeu.recolterRessource(d);
	        	}
	        	else {
	        		System.out.println("Action non reconnue.\n");
	        		lancerDes(playerId);
	        	}
	        }
	        else {
	        	System.out.println("Action non reconnue.\n");
	            lancerDes(playerId);
	        }
		}
	}
	
	/**
	 * Joue le premier tour.
	 */
	public void tourUn() {
		for (Joueur j: jeu.ordreJeu) {
			// j.setArgile(4);
			// j.setBle(2);
			// j.setBois(4);
			// j.setMinerai(2);
			// j.setMouton(2);
			j.setArgile(4+4);
			j.setBle(2+4);
			j.setBois(4+4);
			j.setMinerai(2+4);
			j.setMouton(2+4);
		}
		
		for (int i = 0; i < jeu.ordreJeu.length; i++) {
			jeu.plateau.afficherTout();
			System.out.println("Au Joueur " +  (jeu.ordreJeu[i].getPlayerId()+1) + " de jouer.");
			System.out.println("[Contruction d'une colonie]");
			while (jeu.ordreJeu[i].getNbColoniesRestantes() != 4) {
				int[] coord = jeu.ordreJeu[i].demanderCoordonneesTuile();
				int s = jeu.ordreJeu[i].demanderCoordonneesSommet();
				jeu.construireColonie(jeu.ordreJeu[i].getPlayerId(), true, coord, s);
			}
			jeu.plateau.afficherTout();
			System.out.println("[Contruction d'une route]");
			while (jeu.ordreJeu[i].getNbRoutesRestantes() != 14) {
				int[] coord = jeu.ordreJeu[i].demanderCoordonneesTuile();
				int c = jeu.ordreJeu[i].demanderCoordonneesRoute();
				jeu.construireRoute(jeu.ordreJeu[i].getPlayerId(), coord, c);
			}
		}
		for (int i = jeu.ordreJeu.length-1; i > -1; i--) {
			jeu.plateau.afficherTout();
			System.out.println("Au Joueur " +  (jeu.ordreJeu[i].getPlayerId()+1) + " de jouer.");
			System.out.println("[Contruction d'une colonie]");
			while (jeu.ordreJeu[i].getNbColoniesRestantes() != 3) {
				int[] coord = jeu.ordreJeu[i].demanderCoordonneesTuile();
				int s = jeu.ordreJeu[i].demanderCoordonneesSommet();
				int x = jeu.construireColonie(jeu.ordreJeu[i].getPlayerId(), true, coord, s);
				jeu.recolterRessource(x);
			}
			jeu.plateau.afficherTout();
			System.out.println("[Contruction d'une route]");
			while (jeu.ordreJeu[i].getNbRoutesRestantes() != 13) {
				int[] coord = jeu.ordreJeu[i].demanderCoordonneesTuile();
				int c = jeu.ordreJeu[i].demanderCoordonneesRoute();
				jeu.construireRoute(jeu.ordreJeu[i].getPlayerId(), coord, c);
			}
		}
	}
	
	/**
	 * Joue des tours normaux tant qu'il n'y a pas de vainqueur.
	 */
	public void tourNormal() {
		while (!jeu.finJeu()) {
			if (jeu.nouveauTour) {
				for (Joueur j: jeu.joueurs) {
					j.infoJoueur();
				}
			}
			jeu.plateau.afficherTout();
			System.out.println("Au Joueur " +  (jeu.joueurAJouer+1) + " de jouer.");
			jouer(jeu.joueurAJouer);
			System.out.println();
		}
		jeu.plateau.afficherTout();
		System.out.print("Fin de la partie. ");
		String s = "";
		for (Joueur j: jeu.joueurs) {
			if (j.getPointVic() >= 3) s = "" + (j.getPlayerId()+1);
		}
		System.out.println("Victoire du Joueur " + s + "\n");
		for (Joueur j: jeu.joueurs) {
			j.infoJoueur();
		}
	}
	
}

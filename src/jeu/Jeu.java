package jeu;

import joueur.IA;
import joueur.Joueur;
import plateau.Plateau;
import plateau.Tuile;
import carteDev.*;

import java.util.Random;

public class Jeu {
	
	protected Plateau plateau = new Plateau();
	
	private int nbJoueur;
    private int nbHumain;
    protected Joueur[] joueurs;
    protected Joueur[] ordreJeu;
    protected int joueurAJouer;
	protected boolean nouveauTour = true;
	protected boolean jouable = true;
    
    private CarteDev carteDev = new CarteDev();
	
	/**
	 * Identifiant du joueur ayant joué le plus de chevalier.
	 */
	private int armee = -1;
	
    public Jeu(int nbJoueur, int nbIA) {
    	this.nbJoueur = nbJoueur;
    	this.nbHumain = nbJoueur - nbIA;
    	this.joueurs = new Joueur[nbJoueur];
        this.joueurs = creerJoueurs();
        
        this.ordreJeu = new Joueur[joueurs.length];
        for (int i = 0; i < joueurs.length; i++) {
			this.ordreJeu[i] = joueurs[i];
		}
		this.ordreJeu = shuffle(this.ordreJeu);
		this.joueurAJouer = ordreJeu[0].getPlayerId();
    }
    
    /**
     * Remplie le tableau joueurs en fonction du nombre de nombre humain et IA. 
     * @return Un tableau contenant tous les joueurs.
     */
    private Joueur[] creerJoueurs() {
    	for (int i = 0; i < nbHumain; i++) {
    		joueurs[i] = new Joueur(i);
    	}
    	if (nbHumain <= nbJoueur) {
    		for(int i = nbHumain; i < nbJoueur; i++) {
    			joueurs[i] = new IA(i);
    		}
    	}
        return joueurs;
    }
    
    /**
	 * Mélange le tableau de Joueur passer en argument.
	 * @param tab Tableau de Joueur à mélanger.
	 * @return Le tableau passer en argument mélangé.
	 */
	private Joueur[] shuffle(Joueur[] tab) {
		Random rd = new Random();
	    for (int i = tab.length-1; i > 0; i--) {
	        int j = rd.nextInt(i+1);            
	        Joueur tmp = tab[i];
	        tab[i] = tab[j];
	        tab[j] = tmp;
	    }
	    return tab;
	}
    
    public Plateau getPlateau() {
    	return this.plateau;
    }
    
    public Joueur[] getJoueurs() {
    	return this.joueurs;
    }
    
    protected Joueur getJoueur(int i) { // test
		return this.joueurs[i];
	}
     
    public Joueur[] getOrdreJeu() {
    	return this.ordreJeu;
    }
    
    //////////////////// lancer de des ///////////////////
    
    /**
     * Lance 2 dés de 6.
     * @return La somme des 2 dés.
     */
    public int lancerDes() {
    	Random r = new Random();
    	int d1 = r.nextInt(6)+1;
    	int d2 = r.nextInt(6)+1;
    	return d1 + d2;
    }
    
    /**
     * Tous les joueurs ayant plus de 7 cartes de ressources doivent défausser la moitié.
     */
    public void volerRessource() {
    	for(Joueur j: joueurs) {
    		if (j.getQuantiteRessource() > 7) {
    			int x = j.getQuantiteRessource();
    			j.ressourceDiv2(x, x/2);
    		}
    	}
    }
    
    //////////////////// voleur ///////////////////
   
    /**
     * Si le joueur à voler à au moins une ressource, il se l'a fait voler sinon il ne se passe rien.
     * @param playerId Identifiant du joueur qui vole la ressource.
     * @param joueurAVoler Identifiant du joueur qui se fait voler la ressource.
     */
    private void volerJoueurRessource(int playerId, int joueurAVoler) {
    	Random r = new Random();
    	int x  = r.nextInt(4)+1;
    	if (this.joueurs[joueurAVoler].getQuantiteRessource() == 0) {
    		return;
    	}
    	else if (x == 1 && this.joueurs[joueurAVoler].getMinerai() >= 1) {
			this.joueurs[playerId].setMinerai(this.joueurs[playerId].getMinerai()+1);
			this.joueurs[joueurAVoler].setMinerai(this.joueurs[joueurAVoler].getMinerai()-1);
    	}
    	else if (x == 2 && this.joueurs[joueurAVoler].getMouton() >= 1) {
			this.joueurs[playerId].setMouton(this.joueurs[playerId].getMouton()+1);
			this.joueurs[joueurAVoler].setMouton(this.joueurs[joueurAVoler].getMouton()-1);
    	}
    	else if (x == 3 && this.joueurs[joueurAVoler].getArgile() >= 1) {
			this.joueurs[playerId].setArgile(this.joueurs[playerId].getArgile()+1);
			this.joueurs[joueurAVoler].setArgile(this.joueurs[joueurAVoler].getArgile()-1);
    	}
    	else if (x == 4 && this.joueurs[joueurAVoler].getBle() >= 1) {
			this.joueurs[playerId].setBle(this.joueurs[playerId].getBle()+1);
			this.joueurs[joueurAVoler].setBle(this.joueurs[joueurAVoler].getBle()-1);
    	}
    	else if (x == 5 && this.joueurs[joueurAVoler].getBois() >= 1) {
			this.joueurs[playerId].setBois(this.joueurs[playerId].getBois()+1);
			this.joueurs[joueurAVoler].setBois(this.joueurs[joueurAVoler].getBois()-1);
    	}
    	else volerJoueurRessource(playerId, joueurAVoler);
    }
    
    /**
     * Vérifie si il y'a des colonies ou villes sur la tuile, si oui le joueur ayant l'identifiant playerId 
     * vole un joueur ayant une colonie ou ville sur la tuile. 
     * @param playerId Identifiant du joueur.
     * @param x Le numéro de ligne de la tuile.
     * @param y Le numéro de collone de la tuile.
     */
    private void volerJoueur(int playerId, int x, int y) {
    	boolean[] j = new boolean[4];
    	int n = 0;
    	for (int i = 0; i < 4; i++) {
    		if (this.plateau.getTuile(x, y).getSommet(i).getPlayerId() != -1 && this.plateau.getTuile(x, y).getSommet(i).getPlayerId() != playerId) {
    			j[this.plateau.getTuile(x, y).getSommet(i).getPlayerId()] = true;
    		}
    	}
    	for (int i = 0; i < 4; i++) {
    		if (j[i]) n++;
    	}
    	if (n == 1) {
    		for (int i = 0; i < j.length; i++) {
    			if (j[i] == true) this.volerJoueurRessource(playerId, i);
    		}
    	}
    	else if (n == 2) {
    		int[] t = new int[n];
    		int p = 0;
    		for (int i = 0; i < j.length; i++) {
    			if (j[i] == true) {
    				t[p] = this.joueurs[i].getPlayerId();
    				p++;
    			}
    		}
    		Random random = new Random();
    		int r = random.nextInt(2);
    		if (r == 0) this.volerJoueurRessource(playerId, joueurs[t[0]].getPlayerId());
    		else this.volerJoueurRessource(playerId, joueurs[t[1]].getPlayerId());
    	}
    }
    
    /**
     * Le joueur ayant l'identifiant playerId déplace le voleur.
     * @param playerId Identifiant du joueur.
     */
    public boolean deplacerVoleur(int playerId, int[] coord) {
    	int[] posInit = this.plateau.getPosVoleur();
    	System.out.println("Veuillez deplacer le voleur.");
    	if (coord[0] == posInit[1] && coord[1] == posInit[0]) return false;
    	this.plateau.setPosVoleur(coord[0], coord[1]);
    	System.out.println("Le voleur a ete deplace.");
    	volerJoueur(playerId, coord[1], coord[0]);
    	return true;
    }
    
    //////////////////// carte de developpement ///////////////////
        
    /**
     * Le joueur ayant l'identifiant playerId achète une carte de développement.
     * @param playerId Identifiant du joueur.
     */
    public void acheterCarteDev(int playerId) {
        if (this.joueurs[playerId].ressourcesSuffisantes(1, 0, 1, 0, 1)) {
        	int carte = this.carteDev.piocheCarteDev();
        	if (carte == -1) {
        		System.out.println("Il n'y a plus de carte de developpement.");
        		return;
        	}
        	else {
        		this.joueurs[playerId].utiliserRessources(1, 0, 1, 0, 1);
	        	switch(carte) {
	        		case 0: joueurs[playerId].ajouteCarteAttente(0); break;
	        		case 1: joueurs[playerId].ajouteCarteAttente(1); break;
	        		case 2: joueurs[playerId].ajouteCarteAttente(2); break;
	        		case 3: joueurs[playerId].ajouteCarteAttente(3); break;
	        		case 4: joueurs[playerId].setPointSupp(1); break;
	        	}
        	}
        }
        else System.out.println("[Ressources insuffisante].");
    }
    
    /**
     * Stocke l'identifiant du joueur ayant joué le plus de carte de développemnt Chevalier dans armee. Donne les 2 points de victoire bonus au joueur ayant la plus grande armée
     * en les retirant au précédent joueur ayant la plus gande armée si nécessaire.
     * @param playerId Identifiant du joueur ayant joué la carte de développement Chevalier.
     */
    private void majArmee(int playerId) {
    	if (this.armee == -1 && this.joueurs[playerId].getNbChevalier() >= 3) {
    		this.armee = playerId;
    		this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic() + 2);
    		System.out.println("Joueur " + (playerId+1) + " a la plus grande armee.");
    	}
    	else {
	    	if (playerId != this.armee && this.armee != -1) {
	    		for (Joueur joueur: this.joueurs) {
	    			if (joueur.getPlayerId() != this.armee && joueur.getNbChevalier() > this.joueurs[this.armee].getNbChevalier()) {
	    				this.joueurs[joueur.getPlayerId()].setPointVic(this.joueurs[joueur.getPlayerId()].getPointVic()+2);
	    				this.joueurs[this.armee].setPointVic(this.joueurs[this.armee].getPointVic()-2);
	    				this.armee = joueur.getPlayerId();
	    				System.out.println("Joueur " + (joueur.getPlayerId()+1) + " a la plus grande armee.");
	    			}
	    		}
	    	}
    	}
    }
    
	/**
	 * Joue une carte de type carte du joueur ayant comme identifiant joueur le playerId passer en argument.
	 * La fonction appel la méthode jouerCarte des cartes de développement en fonction du type de carte à jouer.
	 * @param playerId Identifiant du joueur.
	 * @param carte Type de carte.
	 * @param coord1 Tableau d'entier correspondant à des coordonnées.
     * @param r1 String correspondant à une ressource.
     * @param r2 String correspondant à une ressource.
	 */
    private void jouerCarte(int playerId, int carte, int[] coord1, String r1, String r2) {
		switch (carte) {
			case 0: 
				(new Monopole()).jouerCarte(joueurs[playerId], joueurs, r1);
				this.joueurs[playerId].jouerCarte(0); break;
			case 1: 
				(new Invention()).jouerCarte(joueurs[playerId], r1, r2);
				this.joueurs[playerId].jouerCarte(1); break;
			case 2: 
				(new RouteX2()).jouerCarte(joueurs[playerId], this);
				this.joueurs[playerId].jouerCarte(2); break;
			case 3: 
				(new Chevalier()).jouerCarte(joueurs[playerId], this, coord1); 
				this.joueurs[playerId].jouerCarte(3);
				this.majArmee(playerId); break;
		}
	}
    
    /**
     * Joue une carte de développement.
     * @param playerId Identifiant du joueur.
     * @param type L'entier associé à la carte à jouer.
     * @param jouable true si la crte peut être jouée.
     * @param coord1 Tableau d'entier correspondant à des coordonnées.
     * @param r1 String correspondant à une ressource.
     * @param r2 String correspondant à une ressource.
     */
	public void jouerCarteDev(int playerId, int type, boolean jouable, int[] coord1, String r1, String r2) {
		if (jouable) {
			if (joueurs[playerId].getCarteDev(type) > 0) {  // si le joueur possede une carte de developpement de ce type
				if (type == 4) { // la carte point de victoire ajoute un point en fin de partie elle ne peut donc pas etre jouer 
					System.out.println("Vous ne pouvez pas jouer une carte Point de Victoire.");
				}
				else this.jouerCarte(playerId, type, coord1, r1, r2);  // alors il peut l a jouer
			}
			else {
				if (type == 0) System.out.println("Vous ne possedez pas de carte Monopole / Attendez le prochain tour pour la jouer.");
				else if (type == 1) System.out.println("Vous ne possedez pas de carte Invention / Attendez le prochain tour pour la jouer.");
				else if (type == 2) System.out.println("Vous ne possedez pas de carte Route / Attendez le prochain tour pour la jouer.");
				else if (type == 3) System.out.println("Vous ne possedez pas de carte Chevalier / Attendez le prochain tour pour la jouer.");
				else System.out.println("Action non reconnue.\n");
			}
		}
		else System.out.println("Attendez le prochain tour pour jouer une autre carte de developpement.");
	}
	
	//////////////////// ressource ///////////////////
	
	/**
	 * Donne aux joueurs la ressource de la tuile.
	 * @param i Le numéro de ligne de la tuile.
	 * @param j Le numéro de colonne de la tuile.
	 * @param k Le sommet de la tuile.
	 */
	private void ajouterRessource(int i, int j, int k) {
		Tuile[][] p = this.plateau.getPlateau();
		
		if (p[i][j].getType() == 1) {
			this.joueurs[p[i][j].getSommet(k).getPlayerId()].ajouterRessource("minerai");
		}
		else if (p[i][j].getType() == 2) {
			this.joueurs[p[i][j].getSommet(k).getPlayerId()].ajouterRessource("mouton");
		}
		else if (p[i][j].getType() == 3) {
			this.joueurs[p[i][j].getSommet(k).getPlayerId()].ajouterRessource("argile");
		}
		else if (p[i][j].getType() == 4) {
			this.joueurs[p[i][j].getSommet(k).getPlayerId()].ajouterRessource("ble");
		}
		else if (p[i][j].getType() == 5) {
			this.joueurs[p[i][j].getSommet(k).getPlayerId()].ajouterRessource("bois");
		}
	}
	
	/**
	 * Donne des ressources aux joueurs ayant une colonie ou ville à coté d'une tuile avec comme valeur d.
	 * @param d La valeur de la tuile.
	 */
	public void recolterRessource(int d) {
		Tuile[][] p = this.plateau.getPlateau();
		int[] pos = plateau.getPosVoleur();
		for (int i = 2; i < plateau.getHauteur()+2; i++) {
			for (int j = 2; j < plateau.getLargeur()+2; j++) {
				for (int k = 0; k < 4; k++) {
					if (i != pos[1] && j != pos[0] && p[i][j].getValeur() == d && p[i][j].getSommet(k).getConstruction() > -1) {
						if (p[i][j].getSommet(k).getConstruction() == 0) {
							this.ajouterRessource(i, j, k);
						}
						else if (p[i][j].getSommet(k).getConstruction() == 1) {
							this.ajouterRessource(i, j, k);
							this.ajouterRessource(i, j, k);
						}
					}
				}
			}
		}
	}
	
	//////////////////// construction ///////////////////
	
	/**
	 * Vérifie si il y'a au moins une route appartenant au joueur ayant l'identifiant playerId sur les tuiles adjacentes à la route à construire.
	 * @param playerId Identifiant du joueur.
	 * @param x Le numéro de ligne de la tuile.
	 * @param y Le numéro de colonne de la tuile.
	 * @param i Emplacement du coté sur la tuile.
	 * @return true si il y'a au moins une route adjacente à l'emplacement de la route à construire.
	 */
	private boolean estRelieeAUneRoute(int playerId, int x, int y, int i) {
		if (i == 0) {
			if (this.plateau.getTuile(x, y).getCoteGauche().getPlayerId() == playerId || this.plateau.getTuile(x, y).getCoteDroit().getPlayerId() == playerId ||
				this.plateau.getTuile(x-1, y-1).getCoteDroit().getPlayerId() == playerId || this.plateau.getTuile(x-1, y-1).getCoteBas().getPlayerId() == playerId ||
				this.plateau.getTuile(x-1, y+1).getCoteGauche().getPlayerId() == playerId || this.plateau.getTuile(x-1, y+1).getCoteBas().getPlayerId() == playerId) {
				return true;
			}
		}
		else if (i == 1) {
			if (this.plateau.getTuile(x, y).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x, y).getCoteBas().getPlayerId() == playerId ||
				this.plateau.getTuile(x-1, y+1).getCoteGauche().getPlayerId() == playerId || this.plateau.getTuile(x-1, y+1).getCoteBas().getPlayerId() == playerId ||
				this.plateau.getTuile(x+1, y+1).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x+1, y+1).getCoteGauche().getPlayerId() == playerId) {
				return true;
			}
		}
		else if (i== 2) {
			if (this.plateau.getTuile(x, y).getCoteGauche().getPlayerId() == playerId || this.plateau.getTuile(x, y).getCoteDroit().getPlayerId() == playerId ||
				this.plateau.getTuile(x+1, y-1).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x+1, y-1).getCoteDroit().getPlayerId() == playerId ||
				this.plateau.getTuile(x+1, y+1).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x+1, y+1).getCoteGauche().getPlayerId() == playerId) {
				return true;
			}
		}
		else if (i == 3) {
			if (this.plateau.getTuile(x, y).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x, y).getCoteBas().getPlayerId() == playerId ||
				this.plateau.getTuile(x-1, y-1).getCoteDroit().getPlayerId() == playerId || this.plateau.getTuile(x-1, y-1).getCoteBas().getPlayerId() == playerId||
				this.plateau.getTuile(x+1, y-1).getCoteHaut().getPlayerId() == playerId || this.plateau.getTuile(x+1, y-1).getCoteDroit().getPlayerId() == playerId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vérifie que la route peut être construite sur la tuile sur le coté i.
	 * @param x Le numéro de ligne de la tuile.
	 * @param y Le numéro de colonne de la tuile.
	 * @param i Emplacement du sommet sur la tuile.
	 * @return true si la route peut être construite.
	 */
	public boolean checkConstructionRoute(int playerId, int x, int y, int i) {
		if (i == 0 && !this.plateau.getTuile(x, y).getCoteHaut().aUneRoute()) {
			if (this.plateau.getTuile(x, y).getSommet(0).getPlayerId() == playerId || this.plateau.getTuile(x, y).getSommet(1).getPlayerId() == playerId || 
				this.estRelieeAUneRoute(playerId, x, y, i)) {
				return true;
			}
			else System.out.println("Votre route doit etre connectee a une route, colonie ou ville vous appartenant.");
		}
		else if (i == 1 && !this.plateau.getTuile(x, y).getCoteDroit().aUneRoute()) {
			if (this.plateau.getTuile(x, y).getSommet(1).getPlayerId() == playerId || this.plateau.getTuile(x, y).getSommet(2).getPlayerId() == playerId || 
				this.estRelieeAUneRoute(playerId, x, y, i)) {
				return true;
			}
			else System.out.println("Votre route doit etre connectee a une route, colonie ou ville vous appartenant.");
		}
		else if (i == 2 && !this.plateau.getTuile(x, y).getCoteBas().aUneRoute()) {
			if (this.plateau.getTuile(x, y).getSommet(2).getPlayerId() == playerId || this.plateau.getTuile(x, y).getSommet(3).getPlayerId() == playerId || 
				this.estRelieeAUneRoute(playerId, x, y, i)) {
				return true;
			}
			else System.out.println("Votre route doit etre connectee a une route, colonie ou ville vous appartenant.");
		}
		else if (i == 3 && !this.plateau.getTuile(x, y).getCoteGauche().aUneRoute()) {
			if (this.plateau.getTuile(x, y).getSommet(0).getPlayerId() == playerId || this.plateau.getTuile(x, y).getSommet(3).getPlayerId() == playerId || 
				this.estRelieeAUneRoute(playerId, x, y, i)) {
				return true;
			}
			else System.out.println("Votre route doit etre connectee a une route, colonie ou ville vous appartenant.");
		}
		else System.out.println("Votre route doit etre connectee a une route, colonie ou ville vous appartenant.");
		return false;
	}
	
	/**
	 * Construit une route. La route construite appartient au joueur ayant l'identifiant playerId.
	 * @param playerId Identifiant du joueur.
	 */
	public void construireRoute(int playerId, int[] tuile, int cote) {
		if (this.joueurs[playerId].getNbRoutesRestantes() > 0) {
			if (this.joueurs[playerId].ressourcesSuffisantes(0, 1, 0, 1, 0)) {
				if (cote == 0) {
					if (this.checkConstructionRoute(playerId, tuile[0], tuile[1], 0)) { // si la construction est possible met a jour les tuiles adjacentes
						this.plateau.getTuile(tuile[0], tuile[1]).getCoteHaut().setRoute(playerId);
						this.plateau.getTuile(tuile[0]-1, tuile[1]).getCoteBas().setRoute(playerId);
						
						this.joueurs[playerId].setNbRoutesRestantes(this.joueurs[playerId].getNbRoutesRestantes()-1); // soustrait 1 au nombre de routes restantes
						
						this.joueurs[playerId].utiliserRessources(0, 1, 0, 1, 0);
					}
				}
				else if (cote == 1) {
					if (this.checkConstructionRoute(playerId, tuile[0], tuile[1], 1)) { // si la construction est possible met a jour les tuiles adjacentes
						this.plateau.getTuile(tuile[0], tuile[1]).getCoteDroit().setRoute(playerId);
						this.plateau.getTuile(tuile[0], tuile[1]+1).getCoteGauche().setRoute(playerId);
						
						this.joueurs[playerId].setNbRoutesRestantes(this.joueurs[playerId].getNbRoutesRestantes()-1); // soustrait 1 au nombre de routes restantes
						
						this.joueurs[playerId].utiliserRessources(0, 1, 0, 1, 0);
					}
				}
				else if (cote == 2) {
					if (this.checkConstructionRoute(playerId, tuile[0], tuile[1], 2)) { // si la construction est possible met a jour les tuiles adjacentes
						this.plateau.getTuile(tuile[0], tuile[1]).getCoteBas().setRoute(playerId);
						this.plateau.getTuile(tuile[0]+1, tuile[1]).getCoteHaut().setRoute(playerId);
						
						this.joueurs[playerId].setNbRoutesRestantes(this.joueurs[playerId].getNbRoutesRestantes()-1); // soustrait 1 au nombre de routes restantes
						
						this.joueurs[playerId].utiliserRessources(0, 1, 0, 1, 0);
					}
				}
				else if (cote == 3) {
					if (this.checkConstructionRoute(playerId, tuile[0], tuile[1], 3)) { // si la construction est possible met a jour les tuiles adjacentes
						this.plateau.getTuile(tuile[0], tuile[1]).getCoteGauche().setRoute(playerId);
						this.plateau.getTuile(tuile[0], tuile[1]-1).getCoteDroit().setRoute(playerId);
						
						this.joueurs[playerId].setNbRoutesRestantes(this.joueurs[playerId].getNbRoutesRestantes()-1); // soustrait 1 au nombre de routes restantes
						
						this.joueurs[playerId].utiliserRessources(0, 1, 0, 1, 0);
					}
				}
			}
			else System.out.println("Ressources insuffisantes.");
		}
		else System.out.println("Le nombre de maximum routes a ete atteint.");
	}
	
	/**
	 * Vérifie que la colonie est bien espacée d'au moins 2 routes des autres colonies.
	 * @param x Numéro de ligne de la tuile.
	 * @param y Numéro de colonne de la tuile.
	 * @param i Emplacement du sommet sur la tuile.
	 * @return true si la colonie est espacée d'au moins 2 routes des autres colonies.
	 */
	private boolean checkPositionColonie(int x, int y, int i) {
		if (i == 0 &&
			this.plateau.getTuile(x, y).getSommet(1).getConstruction() == -1 && this.plateau.getTuile(x, y).getSommet(3).getConstruction() == -1 &&
			this.plateau.getTuile(x-1, y-1).getSommet(1).getConstruction() == -1 && this.plateau.getTuile(x-1, y-1).getSommet(3).getConstruction() == -1) {
			return true;
		}
		else if (i == 1 &&
				this.plateau.getTuile(x, y).getSommet(0).getConstruction() == -1 && this.plateau.getTuile(x, y).getSommet(2).getConstruction() == -1 &&
				this.plateau.getTuile(x-1, y+1).getSommet(0).getConstruction() == -1 && this.plateau.getTuile(x-1, y+1).getSommet(2).getConstruction() == -1) {
			return true;
		}
		else if (i == 2 &&
				this.plateau.getTuile(x, y).getSommet(1).getConstruction() == -1 && this.plateau.getTuile(x, y).getSommet(3).getConstruction() == -1 &&
				this.plateau.getTuile(x+1, y+1).getSommet(1).getConstruction() == -1 && this.plateau.getTuile(x+1, y+1).getSommet(3).getConstruction() == -1) {
			return true;
		}
		else if (i == 3 &&
				this.plateau.getTuile(x, y).getSommet(0).getConstruction() == -1 && this.plateau.getTuile(x, y).getSommet(2).getConstruction() == -1 &&
				this.plateau.getTuile(x+1, y-1).getSommet(0).getConstruction() == -1 && this.plateau.getTuile(x+1, y-1).getSommet(2).getConstruction() == -1) {
				return true;
		}
		else System.out.println("Un joueur a deja construit une colonie a proximite de cette emplacement.");
		return false;
	}
	
	/**
	 * Vérifie si une colonie peut être construite par le joueur ayant l'identifiant playerId sur la tuile sur le sommet i.
	 * @param playerId Identifiant du joueur.
	 * @param x Numéro de ligne de la tuile.
	 * @param y Numéro de colonne de la tuile.
	 * @param i Emplacement du sommet sur la tuile.
	 * @return true si la colonie peut être construite.
	 */
	public boolean checkConstructionColonie(int playerId, int x, int y, int i) {
		if (this.plateau.getTuile(x, y).getSommet(i).getConstruction() == -1 && this.plateau.getTuile(x, y).getSommet(i).getPlayerId() == -1) { // si il n y a pas deja une colonie ou ville 
			if ((this.plateau.getTuile(x, y).getCoteHaut().aUneRoute() && this.plateau.getTuile(x, y).getCoteHaut().getPlayerId() == playerId) ||
				(this.plateau.getTuile(x, y).getCoteDroit().aUneRoute() && this.plateau.getTuile(x, y).getCoteDroit().getPlayerId() == playerId) ||
				(this.plateau.getTuile(x, y).getCoteBas().aUneRoute() && this.plateau.getTuile(x, y).getCoteBas().getPlayerId() == playerId) ||
				(this.plateau.getTuile(x, y).getCoteGauche().aUneRoute() && this.plateau.getTuile(x, y).getCoteGauche().getPlayerId() == playerId)) { // et qu il y a au moins une route appartenant au joueur
				
				if (this.checkPositionColonie(x, y, i)) return true;
			}
			else System.out.println("Votre colonie doit etre construite a cote d'une route qui vous appartient.");
		}
		else System.out.println("Un joueur a deja construit une colonie/ville a cette emplacement.");
		return false;
	}
	
	/**
	 * Vérifie si le sommet i de la tuile a un port, si oui l'ajoute au port du joueur.
	 * @param playerId Identifiant du joueur.
	 * @param x Le numéro de ligne de la tuile.
	 * @param y Le numéro de colonne de la tuile.
	 * @param i Le sommet de la tuile.
	 */
	private void checkPort(int playerId, int x, int y, int i) {
		if (this.plateau.getTuile(x, y).getSommet(i).getPort() > -1) {
			this.joueurs[playerId].setPort(plateau.getTuile(x, y).getSommet(i).getPort());
		}
	}
	
	/**
	 * Ajoute la colonie sur toutes les tuiles adjacentes.
	 * @param playerId Identifiant du joueur.
	 * @param x0 Le numéro de ligne de la tuile du sommet du haut à gauche.
	 * @param y0 Le numéro de colonne de la tuile du sommet du haut à gauche.
	 * @param x1 Le numéro de ligne de la tuile du sommet du haut à droite.
	 * @param y1 Le numéro de colonne de la tuile du sommet du haut à droite.
	 * @param x2 Le numéro de ligne de la tuile du sommet du bas à droite.
	 * @param y2 Le numéro de colonne de la tuile du sommet du bas à droite.
	 * @param x3 Le numéro de ligne de la tuile du sommet du bas à gauche.
	 * @param y3 Le numéro de colonne de la tuile du sommet du bas à gauche.
	 */
	private void setColonie(int playerId, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
		this.plateau.getTuile(x0, y0).getSommet(0).setColonie(playerId);
		this.plateau.getTuile(x1, y1).getSommet(1).setColonie(playerId);
		this.plateau.getTuile(x2, y2).getSommet(2).setColonie(playerId);
		this.plateau.getTuile(x3, y3).getSommet(3).setColonie(playerId);
	}
	
	/**
	 * Construit une colonie. La colonie construite appartient au joueur ayant l'identifiant playerId.
	 * Si premierTour est true alors la fonction ne vérifie que la position avant de construire sinon elle vérifie en plus si il y'a une route.
	 * @param playerId Identifiant du joueur.
	 * @return La valeur de la tuile.
	 */
	public int construireColonie(int playerId, boolean premierTour, int[] tuile, int sommet) {
		if (this.joueurs[playerId].getNbColoniesRestantes() > 0) {
			if (this.joueurs[playerId].ressourcesSuffisantes(1, 1, 1, 1, 0)) {
				if (sommet == 0) {
					if (!premierTour && this.checkConstructionColonie(playerId, tuile[0], tuile[1], 0)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setColonie(playerId, tuile[0], tuile[1], tuile[0], tuile[1]-1, tuile[0]-1, tuile[1]-1, tuile[0]-1, tuile[1]);
						this.checkPort(playerId, tuile[0], tuile[1], 0);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
					else if (premierTour && this.checkPositionColonie(tuile[0], tuile[1], 0) && this.plateau.getTuile(tuile[0], tuile[1]).getSommet(0).getConstruction() == -1) {
						this.setColonie(playerId, tuile[0], tuile[1], tuile[0], tuile[1]-1, tuile[0]-1, tuile[1]-1, tuile[0]-1, tuile[1]);
						this.checkPort(playerId, tuile[0], tuile[1], 0);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
				}
				else if (sommet == 1) {
					if (!premierTour && this.checkConstructionColonie(playerId, tuile[0], tuile[1], 1)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setColonie(playerId, tuile[0], tuile[1]+1, tuile[0], tuile[1], tuile[0]-1, tuile[1], tuile[0]-1, tuile[1]+1);
						this.checkPort(playerId, tuile[0], tuile[1], 1);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
					else if (premierTour && this.checkPositionColonie(tuile[0], tuile[1], 1) && this.plateau.getTuile(tuile[0], tuile[1]).getSommet(1).getConstruction() == -1) {
						this.setColonie(playerId, tuile[0], tuile[1]+1, tuile[0], tuile[1], tuile[0]-1, tuile[1], tuile[0]-1, tuile[1]+1);
						this.checkPort(playerId, tuile[0], tuile[1], 1);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
				}
				else if (sommet == 2) {
					if (!premierTour && this.checkConstructionColonie(playerId, tuile[0], tuile[1], 2)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setColonie(playerId, tuile[0]+1, tuile[1]+1, tuile[0]+1, tuile[1], tuile[0], tuile[1], tuile[0], tuile[1]+1);
						this.checkPort(playerId, tuile[0], tuile[1], 2);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
					else if (premierTour && this.checkPositionColonie(tuile[0], tuile[1], 2) && this.plateau.getTuile(tuile[0], tuile[1]).getSommet(2).getConstruction() == -1) {
						this.setColonie(playerId, tuile[0]+1, tuile[1]+1, tuile[0]+1, tuile[1], tuile[0], tuile[1], tuile[0], tuile[1]+1);
						this.checkPort(playerId, tuile[0], tuile[1], 2);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
				}
				else if (sommet == 3) {
					if (!premierTour && this.checkConstructionColonie(playerId, tuile[0], tuile[1], 3)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setColonie(playerId, tuile[0]+1, tuile[1], tuile[0]+1, tuile[1]-1, tuile[0], tuile[1]-1, tuile[0], tuile[1]);						
						this.checkPort(playerId, tuile[0], tuile[1], 3);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
					else if (premierTour && this.checkPositionColonie(tuile[0], tuile[1], 3) && this.plateau.getTuile(tuile[0], tuile[1]).getSommet(3).getConstruction() == -1) {
						this.setColonie(playerId, tuile[0]+1, tuile[1], tuile[0]+1, tuile[1]-1, tuile[0], tuile[1]-1, tuile[0], tuile[1]);						
						this.checkPort(playerId, tuile[0], tuile[1], 3);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+1); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbColoniesRestantes(this.joueurs[playerId].getNbColoniesRestantes()-1); // soustrait 1 au nombre de colonies restantes
						
						this.joueurs[playerId].utiliserRessources(1, 1, 1, 1, 0);
					}
				}
				if (tuile != null) return this.plateau.getTuile(tuile[0], tuile[1]).getValeur();
				else return -1;
			}
			else System.out.println("Ressources insuffisantes.");
			return -1;
		}
		else System.out.println("Le nombre de maximum colonies a ete atteint.");
		return -1;
	}
	
	/**
	 * Vérifie si une ville peut être construite par le joueur ayant l'identifiant playerId sur la tuile sur le sommet i.
	 * @param playerId Identifiant du joueur.
	 * @param x Numéro de ligne de la tuile.
	 * @param y Numéro de colonne de la tuile.
	 * @param i Emplacement du sommet sur la tuile.
	 * @return true si la ville peut être construite.
	 */
	private boolean checkConstructionVille(int playerId, int x, int y, int i) {
		if (this.plateau.getTuile(x, y).getSommet(i).getConstruction() == 0 && this.plateau.getTuile(x, y).getSommet(i).getPlayerId() == playerId) { // si il y a deja une colonie
			return true;
		}
		else System.out.println("Vous devez d'abord construire une colonie.");
		return false;
	}
	
	/**
	 * Ajoute la ville sur toutes les tuiles adjacentes.
	 * @param playerId Identifiant du joueur.
	 * @param x0 Le numéro de ligne de la tuile du sommet du haut à gauche.
	 * @param y0 Le numéro de colonne de la tuile du sommet du haut à gauche.
	 * @param x1 Le numéro de ligne de la tuile du sommet du haut à droite.
	 * @param y1 Le numéro de colonne de la tuile du sommet du haut à droite.
	 * @param x2 Le numéro de ligne de la tuile du sommet du bas à droite.
	 * @param y2 Le numéro de colonne de la tuile du sommet du bas à droite.
	 * @param x3 Le numéro de ligne de la tuile du sommet du bas à gauche.
	 * @param y3 Le numéro de colonne de la tuile du sommet du bas à gauche.
	 */
	private void setVille(int playerId, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
		this.plateau.getTuile(x0, y0).getSommet(0).setVille(playerId);
		this.plateau.getTuile(x1, y1).getSommet(1).setVille(playerId);
		this.plateau.getTuile(x2, y2).getSommet(2).setVille(playerId);
		this.plateau.getTuile(x3, y3).getSommet(3).setVille(playerId);
	}
	
	/**
	 * Construit une ville. La ville construite appartient au joueur ayant l'identifiant playerId.
	 * @param playerId Identifiant du joueur.
	 */
	public void construireVille(int playerId, int[] tuile, int sommet) {
		if (this.joueurs[playerId].getNbColoniesRestantes() > 0) {
			if (this.joueurs[playerId].ressourcesSuffisantes(2, 0, 0, 0, 3)) {
				if (sommet == 0) {
					if (this.checkConstructionVille(playerId, tuile[0], tuile[1], 0)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setVille(playerId, tuile[0], tuile[1], tuile[0], tuile[1]-1, tuile[0]-1, tuile[1]-1, tuile[0]-1, tuile[1]);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+2); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbVillesRestantes(this.joueurs[playerId].getNbVillesRestantes()-1); // soustrait 1 au nombre de villes restantes
						
						this.joueurs[playerId].utiliserRessources(2, 0, 0, 0, 3);
					}
				}
				else if (sommet == 1) {
					if (this.checkConstructionVille(playerId, tuile[0], tuile[1], 1)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setVille(playerId, tuile[0], tuile[1]+1, tuile[0], tuile[1], tuile[0]-1, tuile[1], tuile[0]-1, tuile[1]+1);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+2); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbVillesRestantes(this.joueurs[playerId].getNbVillesRestantes()-1); // soustrait 1 au nombre de villes restantes
						
						this.joueurs[playerId].utiliserRessources(2, 0, 0, 0, 3);
					}
				}
				else if (sommet == 2) {
					if (this.checkConstructionVille(playerId, tuile[0], tuile[1], 2)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setVille(playerId, tuile[0]+1, tuile[1]+1, tuile[0]+1, tuile[1], tuile[0], tuile[1], tuile[0], tuile[1]+1);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+2); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbVillesRestantes(this.joueurs[playerId].getNbVillesRestantes()-1); // soustrait 1 au nombre de villes restantes
						
						this.joueurs[playerId].utiliserRessources(2, 0, 0, 0, 3);
					}
				}
				else if (sommet == 3) {
					if (this.checkConstructionVille(playerId, tuile[0], tuile[1], 3)) { // si la construction est possible met a jour les tuiles adjacentes
						this.setVille(playerId, tuile[0]+1, tuile[1], tuile[0]+1, tuile[1]-1, tuile[0], tuile[1]-1, tuile[0], tuile[1]);
						
						this.joueurs[playerId].setPointVic(this.joueurs[playerId].getPointVic()+2); // ajoute 1 point de victoire
						this.joueurs[playerId].setNbVillesRestantes(this.joueurs[playerId].getNbVillesRestantes()-1); // soustrait 1 au nombre de villes restantes
						
						this.joueurs[playerId].utiliserRessources(2, 0, 0, 0, 3);
					}
				}
			}
			else System.out.println("Ressources insuffisantes.");
		}
		else System.out.println("Le nombre maximum de ville a ete atteint.");
	}
	
	
	//////////////////// echange ///////////////////
	
	/**
	 * Demande au joueur ayant l'identifiant playerId la ressource souhaitée et ce que le joueur veut donner en échange parmi ces ressources dont la quantité est suffisante.
	 * Effectue l'échange si le taux de change est respecté.
	 * @param playerId Identifiant du joueur.
	 */
	public void demanderEchange(int playerId, String besoin, String possede, int taux) {
		Joueur j = this.joueurs[playerId];
		j.afficherRessource();
		System.out.print("[Ressource souhaitee] ");
		if (besoin.trim().equalsIgnoreCase("out")) return;
		if (j.getQuantiteRessource() > taux) {
			System.out.print("[Ressources que vous donner] ");
			if (possede.trim().equalsIgnoreCase("out")) return;
			if (j.peutEchanger(possede)) j.echange(possede, besoin, taux);
			else System.out.println("[Ressources insuffisantes]");
		}
		else System.out.println("[Ressources insuffisantes]");
	}
	
	/**
	 * Termine le tour du joueur.
	 */
	public void terminerTour() {
		this.joueurAJouer = (this.joueurAJouer+1) % this.ordreJeu.length;
		this.nouveauTour = true;
		this.jouable = true;
	}
	
	//////////////////// victoire ///////////////////
	
	/**
	 * Vérifie si il y'a un gagnant.
	 * @return true si un joueur atteint 10 points de victoire.
	 */
	public boolean finJeu() {
		for (Joueur j: joueurs) {
			if (j.getPointSupp() + j.getPointVic() >= 3) {
				for (Joueur x: joueurs) {
					x.setPointVic(x.getPointVic() + x.getPointSupp());
				}
				return true;
			}
		}
		return false;
	}
	
}

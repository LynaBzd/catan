package joueur;

import java.util.Scanner;

public class Joueur {
	
    protected final int playerId;  // identifiant permettant de differencier les joueurs: de 0 a 3 (car 4 joueurs max)
    // quantite de chaque ressource que possede le joueur 
    protected int ble = 0;
    protected int bois = 0;
    protected int mouton = 0;
    protected int argile = 0;
    protected int minerai = 0;
    
    private boolean[] port = new boolean[6];
    
    private int pointVic = 0;
    
    private int nbRoutesRestantes = 15;
    private int nbColoniesRestantes = 5;
    private int nbVillesRestantes = 5;

    private int routeLaPlusLongue = 0;

    // tableau d entier qui stock le nombre de chaque type de cartes de developpement que le joueur possede
    // monopole = 0, invention = 1, route = 2, chevalier = 3; pointVictoire = 4;
    private int[] cartesDev = new int[5];
    private int[] carteAttente = new int[5];
    private int nbChevalier = 0;  // permet de savoir quel joueur a la plus grande armee
    private int pointSupp = 0;  // stocke les points de victoire a ajouter en fin de partie

    public Joueur(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public int getBle() {
        return this.ble;
    }

    public void setBle(int nb) {
        this.ble = nb;
    }

    public int getBois() {
        return this.bois;
    }

    public void setBois(int nb) {
        this.bois = nb;
    }

    public int getMouton() {
        return this.mouton;
    }

    public void setMouton(int nb) {
        this.mouton = nb;
    }

    public int getArgile() {
        return this.argile;
    }

    public void setArgile(int nb) {
        this.argile = nb;
    }

    public int getMinerai() {
        return this.minerai;
    }

    public void setMinerai(int nb) { 
        this.minerai = nb;
    }
    
    public int getQuantiteRessource() {
    	return this.ble + this.bois + this.argile + this.minerai + this.mouton;
    }
    
    public void setPort(int i) {
    	this.port[i] = true;
    }

    public int getPointVic() {
        return this.pointVic;
    }

    public void setPointVic(int nb) { 
        this.pointVic = nb;
    }
    
    public int getNbRoutesRestantes() {
        return this.nbRoutesRestantes;
    }

    public void setNbRoutesRestantes(int nb) { 
        this.nbRoutesRestantes = nb;
    }
    
    public int getNbColoniesRestantes() {
        return this.nbColoniesRestantes;
    }

    public void setNbColoniesRestantes(int nb) { 
        this.nbColoniesRestantes = nb;
    }
    
    public int getNbVillesRestantes() {
        return this.nbVillesRestantes;
    }

    public void setNbVillesRestantes(int nb) { 
        this.nbVillesRestantes = nb;
    }
    
    public int getNbChevalier() {
        return this.nbChevalier;
    }

    public void setNbChevalier(int nb) { 
        this.nbChevalier = nb;
    }

    public int getPointSupp() {
        return this.pointSupp;
    }

    public void setPointSupp(int nb) { 
        this.pointSupp = this.pointSupp + nb;
    }

    public int getCarteDev(int i) {
    	return this.cartesDev[i];
    }
    
    /**
     * Ajoute une carte de développement de type i dans cartesDev.
     * @param i Indice dans le tableau cartesDev.
     */
    public void ajouteCarteDev(int i) {
    	this.cartesDev[i] = cartesDev[i] + 1;
    }
    
    /**
     * Le nombre de carte de développement que possède le joueur.
     * @return Un entier correspondaant au nombre de carte de développement du joueur.
     */
    public int getNbCarteDev() {
    	int n = 0;
    	for (int i = 0; i < this.cartesDev.length; i++) {
    		n += this.cartesDev[i];
    	}
    	return n;
    }
    
    /**
     * Le nombre de carte de développement que possède le joueur, non jouable incluse.
     * @return Un entier correspondaant au nombre de carte de développement du joueur.
     */
    public int getNbCarteDevAll() {
    	int n = 0;
    	for (int i = 0; i < this.cartesDev.length; i++) {
    		n += this.cartesDev[i];
    	}
    	for (int i = 0; i < this.carteAttente.length; i++) {
    		n += this.carteAttente[i];
    	}
    	return n;
    }
    
    /**
     * Incrémente de 1 la valeur stockée dans le tableau carteAttente située à l'indice i.
     * @param i Indice de la case du tableau.
     */
    public void ajouteCarteAttente(int i) {
    	this.carteAttente[i] = carteAttente[i] + 1;
    }
    
    /**
     * Demande le pseudo du joueur.
     * @return Une chaîne de caractère correspondant au pseudo choisi par le joueur. 
     */
//    public String demanderPseudo() {
//        System.out.print("Veuillez entrer le pseudo du joueur " + (this.playerId+1) + ": ");
//        Scanner sc = new Scanner(System.in);
//        if (sc.hasNextLine()) {
//        	String s = sc.nextLine();
//        	if (!s.trim().equals("")) {
//	        	System.out.println("Le pseudo du joueur " + (this.playerId+1) + " est: " + s + "\n");
//	            return s;
//        	}
//        	else {
//        		System.out.println("Le pseudo est incorrect.\n");
//                return demanderPseudo();
//        	}
//        }
//        else {
//            return demanderPseudo();
//        }
//    }
    
    /**
     * Demande les coordonnées d'une tuile et les retournes.
     * @return Un tableau d'entier contenant les coordonnées de la tuile.
     */
    public int[] demanderCoordonneesTuile() {
    	int[] coord = new int[2];
    	System.out.print("Entrer les coordonnees de la tuile (ex: B2): ");
    	Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
        	try {
	        	String s = sc.nextLine();
	        	if (s.equals("out")) return null;
	        	coord[0] = (int) Character.toUpperCase(s.charAt(0)) - 64;
	    		coord[1] = Integer.valueOf(s.substring(1));
	    		if (coord[0] > 0 && coord[0] < 6 && coord[1] > 0 && coord[1] < 5) {
	    			coord[0]++; // +1 pour etre sur les tuiles jouables (qui ne sont ni des ports ni des oceans)
	    			coord[1]++;
	    			return coord;
	    		}
	    		else {
	    			System.out.println("Les coordonnees sont invalides.\n");
	    			return demanderCoordonneesTuile();
	    		}
        	}
        	catch (StringIndexOutOfBoundsException e) {
        		System.out.println("Les coordonnees sont invalides. Veuillez entrer une lettre suivie d'un chiffre.\n");
    			return demanderCoordonneesTuile();
        	}
        	catch (NumberFormatException e) {
        		System.out.println("Les coordonnees sont invalides. Veuillez entrer une lettre suivie d'un chiffre.\n");
    			return demanderCoordonneesTuile();
        	}
        }
        else return demanderCoordonneesTuile();
    }
    
    /**
     * Demande au joueur un entier correspondant à l'emplacement du sommet sur la tuile.
     * @return Un entier correspondant à l'emplacement du sommet sur la tuile.
     */
    public int demanderCoordonneesSommet() {
    	System.out.print("Entrez la valeur correspondant au sommet souhaite\n- 0 -> en haut a gauche\n- 1 -> en haut a droite\n- 2 -> en bas a droite\n- 3 -> en bas a gauche\nValeur: ");
    	Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
        	try {
        		String s = sc.nextLine();
        		if (s.equals("out")) return -1;
 	           	int c = Integer.parseInt(s);
 	           	if (c >= 0 && c < 4) {
 	                return c;
 	            }
 	            else {
 	                System.out.println("Le nombre est incorrect.\n");
 	                return demanderCoordonneesSommet();
 	            }
         	}
 	        catch (NumberFormatException ex) {
 	           System.out.println("La valeur est incorrecte.\n");
 	           return demanderCoordonneesSommet();
 	        }
        }
        else return demanderCoordonneesSommet();
    }
    
    /**
     * Demande au joueur un entier correspondant à l'emplacement de la route sur la tuile.
     * @return Un entier correspondant à l'emplacement de la route sur la tuile.
     */
    public int demanderCoordonneesRoute() {
    	System.out.print("Entrez la valeur correspondant a la route souhaitee\n- 0 -> en haut\n- 1 -> a droite\n- 2 -> en bas\n- 3 -> a gauche\nValeur: ");
    	Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
        	try {
        		String s = sc.nextLine();
        		if (s.equals("out")) return -1;
 	           	int c = Integer.parseInt(s);
 	           	if (c >= 0 && c < 4) {
 	                return c;
 	            }
 	            else {
 	                System.out.println("Le nombre est incorrect.\n");
 	                return demanderCoordonneesRoute();
 	            }
         	}
 	        catch (NumberFormatException ex) {
 	           System.out.println("La valeur est incorrecte.\n");
 	           return demanderCoordonneesRoute();
 	        }
        }
        else return demanderCoordonneesRoute();
    }
    
    //////////////////// ressource ///////////////////
    
    /**
     * Vérifie si le joueur possède les quantités de ressources passer en argument aux ressources du joueur.
     * @param ble Quantité de blé à posséder.
     * @param bois Quantité de bois à posséder.
     * @param mouton Quantité de mouton à posséder.
     * @param argile Quantité d'argile à posséder.
     * @param minerai Quantité de minerai à posséder.
     */
    public boolean ressourcesSuffisantes(int ble, int bois, int mouton, int argile, int minerai) {
		return this.ble >= ble && this.bois >= bois && this.mouton >= mouton && this.argile >= argile && this.minerai >= minerai;
	}
    
    /**
     * Soustrait les quantités passer en argument aux ressources du joueur.
     * @param ble Quantité de blé à soustraire.
     * @param bois Quantité de bois à soustraire.
     * @param mouton Quantité de mouton à soustraire.
     * @param argile Quantité d'argile à soustraire.
     * @param minerai Quantité de minerai à soustraire.
     */
    public void utiliserRessources(int ble, int bois, int mouton, int argile, int minerai) {
    	this.ble -= ble;
    	this.bois -= bois;
    	this.mouton -= mouton;
    	this.argile -= argile;
    	this.minerai -= minerai;
    }
    
    /**
     * Permet au joueur de choisir une ressource.
     * @return Une chaîne de caractère correspondant à la ressource choisie par le joueur.
     */
    public String choisirRessource() {
		System.out.print("Veuillez choisir une ressource parmis: ble, bois, mouton, argile, minerai.\nEntrez votre choix: ");
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()) {
			String ressource = sc.nextLine();
			switch (ressource.trim().toLowerCase()) {  // trim().toLowerCase() permet de supprimer les espaces puis d ignorer la casse
				case "ble": return "ble";
				case "bois": return "bois";
				case "mouton": return "mouton";
				case "argile": return "argile";
				case "minerai": return "minerai";
				case "out": return "out";
				default: 
					System.out.println("Ressource invalide.\n"); 
					return this.choisirRessource();
			}
		}
		System.out.println("Ressource invalide.\n"); 
		return this.choisirRessource();
	}
    
    /**
     * Ajoute la ressource passer en argument aux ressources du joueur.
     * @param ressource Une chaîne de caractère correspondant à la ressource à ajouter.
     * @param n La quantité à ajouter.
     */
    public void ajouterRessource(String ressource) {
    	if (ressource.trim().equalsIgnoreCase("ble")) this.ble = this.ble + 1;
    	else if (ressource.trim().equalsIgnoreCase("bois")) this.bois = this.bois + 1;
    	else if (ressource.trim().equalsIgnoreCase("mouton")) this.mouton = this.mouton + 1;
    	else if (ressource.trim().equalsIgnoreCase("argile")) this.argile = this.argile + 1;
    	else if (ressource.trim().equalsIgnoreCase("minerai")) this.minerai = this.minerai + 1;
    }
    
    /**
     * Permet au joueur de choisir une ressource parmi celles qu'il possède.
     * @return Une chaîne de caratère correspondant à la ressource choisie.
     */
    public String choisirRessourceDisponible(int n) {
    	System.out.println("Veuillez choisir une ressource parmis: ");
    	if (this.ressourcesSuffisantes(n, 0, 0, 0, 0)) System.out.println("- ble");
    	if (this.ressourcesSuffisantes(0, n, 0, 0, 0)) System.out.println("- bois");
    	if (this.ressourcesSuffisantes(0, 0, n, 0, 0)) System.out.println("- mouton");
    	if (this.ressourcesSuffisantes(0, 0, 0, n, 0)) System.out.println("- argile");
    	if (this.ressourcesSuffisantes(0, 0, 0, 0, n)) System.out.println("- minerai");
    	System.out.print("Votre choix: ");
    	Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()) {
			String ressource = sc.nextLine();
			switch (ressource.trim().toLowerCase()) {  // trim().toLowerCase() permet de supprimer les espaces puis d ignorer la casse
				case "ble": return "ble";
				case "bois": return "bois";
				case "mouton": return "mouton";
				case "argile": return "argile";
				case "minerai": return "minerai";
				case "out": return "out";
				default: 
					System.out.println("Ressource invalide.\n"); 
					return this.choisirRessourceDisponible(n);
			}
		}
		System.out.println("Ressource invalide.\n"); 
		return this.choisirRessourceDisponible(n);
    }
    
    /**
     * Divise les ressources du joueur par 2.
     * @param nbDepart Le nombre de cartes ressources que possède le joueur.
     * @param nbArrivee Le nombre de cartes ressources que doit avoir le joueur.
     */
    public void ressourceDiv2(int nbDepart, int nbArrivee) {
		if (nbDepart == nbArrivee) return;
    	
    	int x = nbDepart-nbArrivee;
    	if (x < 2) System.out.println("Joueur " + (this.playerId+1) + " doit defauser " + x + " carte resssource.");
    	else System.out.println("Joueur " + (this.playerId+1) + " doit defausser " + x + " cartes resssources.");
    	
    	this.afficherRessource();
		String s = this.choisirRessourceDisponible(1);
		if (s.trim().equalsIgnoreCase("ble")) {
			if (this.ressourcesSuffisantes(1, 0, 0, 0, 0)) this.ble = this.ble-1;
			else {
				System.out.println("Joueur " + (this.playerId+1) + " n'a pas assez de ble.");
				this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
			}
		}
    	else if (s.trim().equalsIgnoreCase("bois")) {
    		if (this.ressourcesSuffisantes(0, 1, 0, 0, 0)) this.bois = this.bois-1;
    		else {
    			System.out.println("Joueur " + (this.playerId+1) + " n'a pas assez de bois.");
    			this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    		}
    	}
    	else if (s.trim().equalsIgnoreCase("mouton")) {
    		if (this.ressourcesSuffisantes(0, 0, 1, 0, 0)) this.mouton = this.mouton-1;
    		else {
    			System.out.println("Joueur " + (this.playerId+1) + " n'a pas assez de mouton.");
    			this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    		}
    	}
    	else if (s.trim().equalsIgnoreCase("argile")) {
    		if (this.ressourcesSuffisantes(0, 0, 0, 1, 0)) this.argile = this.argile-1;
    		else {
    			System.out.println("Joueur " + (this.playerId+1) + " n'a pas assez d'argile.");
    			this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    		}
    	}
    	else if (s.trim().equalsIgnoreCase("minerai")) {
        	if (this.ressourcesSuffisantes(0, 0, 0, 0, 1)) this.minerai = this.minerai-1;
        	else {
        		System.out.println("Joueur " + (this.playerId+1) + " n'a pas assez de minerai.");
        		this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
        	}
    	}
		this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee); // ligne a ajouter
    }
    
    //////////////////// carte de developpement ///////////////////
    
    /**
     * Soustrait 1 à la valeur à l'indice i dans le tableau contenant les cartes de développement du joueur.
     * @param i indice de la carte à jouer dans le tableau contenant les cartes de développement du joueur.
     */
    public void jouerCarte(int i) {
    	this.cartesDev[i]--;
    }
    
    /**
     * Permet au joueur de choisir la carte de développement à jouer.
     * @return Retourne l'entier associé à la carte de développement choisie.
     */
    public int choixCarteAJouer() {
    	System.out.println("Quelle carte voulez-vous jouer ?");
		if (cartesDev[0] > 0) System.out.println("- Monopole (m) x" + cartesDev[0]);
		if (cartesDev[1] > 0) System.out.println("- Invention (i) x" + cartesDev[1]);
		if (cartesDev[2] > 0) System.out.println("- Route (r) x" + cartesDev[2]);
		if (cartesDev[3] > 0) System.out.println("- Chevalier (c) x" + cartesDev[3]);
    	
    	Scanner sc = new Scanner(System.in);
    	if (sc.hasNextLine()) {
        	String s = sc.nextLine();
        	if (!s.trim().equals("")) {
        		if (s.equalsIgnoreCase("m")) return 0;
        		else if (s.equalsIgnoreCase("i")) return 1;
        		else if (s.equalsIgnoreCase("r")) return 2;
        		else if (s.equalsIgnoreCase("c")) return 3;
        		else if (s.equalsIgnoreCase("out")) return -1;
        		else {
        			System.out.println("Action non reconnue.\n");
                    return choixCarteAJouer();
        		}
        	}
        	else {
        		System.out.println("Action non reconnue.\n");
                return choixCarteAJouer();
        	}
        }
        else {
        	System.out.println("Action non reconnue.\n");
            return choixCarteAJouer();
        }
    }
    
    /**
     * Ajoute les carte de développement se trouvant dans carteAttente (cartes jouables au tour suivant) dans carteDev (les cartes jouables) et les supprimes de carteAttente.
     */
    public void majCarteDev() { 
		for (int i = 0; i < 5; i++) {
			int n = this.carteAttente[i];
			if (n > 0) {
				this.cartesDev[i] = this.cartesDev[i] + n;
				this.carteAttente[i] = 0;
			}
		}
	}
    
	//////////////////// echange ///////////////////
		
    /**
     * Taux de change de la ressource correspondant à possede.
     * @param possede String correspondant à la ressource à donner en échange.
     * @return Un entier correspondant au taux de change.
     */
    public int tauxDeChange(String possede) {
    	int taux = 4;
    	if (this.port[0]==true) taux = 3;
    	if ((possede.equals("ble") && this.port[4]) || (possede.equals("bois") && this.port[5]) || (possede.equals("mouton") && this.port[2]) || 
    		(possede.equals("argile") && this.port[3]) || (possede.equals("minerai") && this.port[1])) {
    		taux = 2;
    	}
    	return taux;
    }
    
    /**
     * Vérifie si le joueur peut faire un échange et à quel taux.
     * @param possede La ressource à échanger.
     * @return true si la quantité de possede est suffisante.
     */
    public boolean peutEchanger(String possede) {
    	int taux = tauxDeChange(possede);
    	
    	if (possede.equals("ble")) return this.ressourcesSuffisantes(taux, 0, 0, 0, 0);
    	else if (possede.equals("bois")) return this.ressourcesSuffisantes(0, taux, 0, 0, 0);
    	else if (possede.equals("mouton")) return this.ressourcesSuffisantes(0, 0, taux, 0, 0);
    	else if (possede.equals("argile")) return this.ressourcesSuffisantes(0, 0, 0, taux, 0);
    	else if (possede.equals("minerai")) return this.ressourcesSuffisantes(0, 0, 0, 0, taux);
    	else return false;
    }
    
    /**
     * Effectue un échange avec la banque avec un taux de change de taux:1.
     * @param possede Les ressources que le joueur posseèdent déjà.
     * @param besoin La ressource que le joueur souhaite acquérir.
     * @param taux Le taux auquel s'effectu l'échange.
     */
	public void echange(String possede, String besoin, int taux) {
		if (peutEchanger(possede)) {
			this.ajouterRessource(besoin);
			if (possede.equals("ble")) this.utiliserRessources(taux, 0, 0, 0, 0);
	    	else if (possede.equals("bois")) this.utiliserRessources(0, taux, 0, 0, 0);
	    	else if (possede.equals("mouton")) this.utiliserRessources(0, 0, taux, 0, 0);
	    	else if (possede.equals("argile")) this.utiliserRessources(0, 0, 0, taux, 0);
	    	else if (possede.equals("minerai")) this.utiliserRessources(0, 0, 0, 0, taux);
		}
		else System.out.println("[Ressources insuffisantes]");
	}
    
    //////////////////// affichage ///////////////////
    
    /**
     * Affiche les ressources du joueur.
     */
    public void afficherRessource() {
    	if (this.getQuantiteRessource() < 2) System.out.println("[Ressource]");
    	else System.out.println("[Ressources]");
    	System.out.println("Ble: " + this.ble);
    	System.out.println("Bois: " + this.bois);
    	System.out.println("Argile: " + this.argile);
    	System.out.println("minerai: " + this.minerai);
    	System.out.println("mouton: " + this.mouton);
    }
    
    /**
     * Affiche les informations du joueur.
     */
    public void infoJoueur() {
    	if (this instanceof IA) System.out.println(" # Joueur " + (this.playerId+1) + " (IA)");
    	else System.out.println(" # Joueur " + (this.playerId+1));
    	String r = "";
    	
    	int q = this.pointVic;
    	if (q == 0) r = "0 point de victoire";
    	else if (q == 1) r = "1 point de victoire";
    	else r = q + " points de victoire";
    	System.out.println(" # " + r);
    	
    	q = this.getQuantiteRessource();
    	if (q == 0 || q == 1) r = q + " carte ressource";
    	else r = q + " cartes ressources";
    	System.out.print(" # " + r);
    	
    	q = this.getNbCarteDevAll();
    	if (q == 0 || q == 1) r = q + " carte de developpement";
    	else r = q + " cartes de developpement";
    	System.out.println(" | " + r);
    	
    	q = this.nbChevalier;
    	if (q == 0 || q == 1) r = q + " armee";
    	else r = q + " armee";
    	System.out.print(" # " + r);
    	
    	q = this.routeLaPlusLongue;
    	if (q == 0 || q == 1) r = q + " route connectee";
    	else r = q + " routes connectees";
    	System.out.println(" | " + r);
    	
    	System.out.println();
    }
    
    /**
     * Affiche l'état des deux tableaux de cartes de développement.
     */
//    public void carteToString() {
//    	System.out.print("carteDev: ");
//    	for (int i = 0; i < cartesDev.length; i++) {
//    		System.out.print(cartesDev[i] + " ");
//    	}
//    	System.out.print("\nen attente: ");
//    	for (int i = 0; i < carteAttente.length; i++) {
//    		System.out.print(carteAttente[i] + " ");
//    	}
//    	System.out.println();
//    }
    
}

package plateau;
import java.util.Random;

public class Plateau {
	
	private int hauteur = 5; 
	private int largeur = 4;
	protected Tuile[][] plateau;
	/**
	 * Position du voleur sur le plateau.
	 */
	private int[] posVoleur = new int[2];

	public Plateau() {	// plateau de 20 tuiles
		this.plateau = new Tuile[this.hauteur+4][this.largeur+4];	// bordure pour faciliter la manipulation du tableau et borbure pour les ports

		for (int i = 0; i < this.plateau.length; i++) {  // initialise toute les Tuiles du plateau a 0
			for (int j = 0; j < this.plateau[i].length; j++) {
				this.plateau[i][j] = new Tuile(0, 0);
			}
		}
		
		ajouteValeursAlea();
		ajouteDesert();
		
		for (int i = 0; i < this.plateau.length; i++) {  // initialise la position du voleur avec les coordonnees du desert
			for (int j = 0; j < this.plateau[i].length; j++) {
				if (this.plateau[i][j].getValeur() == -1) {
					posVoleur[0] = j; 
					posVoleur[1] = i;
				}
			}
		}
		
		ajouteTypesAlea();
		ajoutePorts();
		initPort();
	}

	/**
	 * Mélange le tableau d'entier tab.
	 * @param tab Un tableau d'entier.
	 * @return Le tableau tab mélangé.
	 */
	private int[] shuffle(int[] tab) {
		Random rd = new Random();
	    for (int i = tab.length-1; i > 0; i--) {
	        int j = rd.nextInt(i+1);            
	        int tmp = tab[i];
	        tab[i] = tab[j];
	        tab[j] = tmp;
	    }
	    return tab;
	}
	
	/**
	 * Valeurs possibles du tableau avec deux 2 car le plateau à 20 Tuiles et non 19 
	 */
	private int[] valeursPossibles = {-1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
	
	/**
	 * Remplie le plateau avec le tableau valeursPossibles mélangés
	 */
	private void ajouteValeursAlea() {	
		int k = 0;
		shuffle(this.valeursPossibles);
		for (int i = 2; i <= this.hauteur+1; i++) {
			for (int j = 2; j <= this.largeur+1; j++) {
				this.plateau[i][j].getTuile().setValeur(this.valeursPossibles[k % this.valeursPossibles.length]);
				k++;
			}
		}
	}
	
	private void ajouteDesert() {
		for (int i = 2; i <= this.hauteur+1; i++) {
			for (int j = 2; j <= this.largeur+1; j++) {
				if (this.plateau[i][j].getTuile().getValeur() == -1) this.plateau[i][j].getTuile().setType(6);
			}
		}
	}
	
	private int[] port = {0, 1, 2, 3, 4, 5};	// 1 quelconques, 1 minerai, 1 mouton, 1 argile, 1 ble, 1 bois
	
	/**
	 * Ajoute les ports sur les tuiles.
	 */
	private void ajoutePorts() {
		int k = 0;
		shuffle(this.port);
		for (int i = 2; i <= this.hauteur+1; i += 3) {	// colonne
			this.plateau[i][1].getTuile().setPort(true);
			this.plateau[i][1].getTuile().setType(this.port[k % this.port.length]);	// premiere colonne
			k++;
			
			this.plateau[i][this.largeur+2].getTuile().setPort(true);
			this.plateau[i][this.largeur+2].getTuile().setType(this.port[k % this.port.length]); // derniere colonne
			k++;
		}
		for (int j = 4; j <= this.largeur+1; j += 3) {	// ligne
			this.plateau[1][j].getTuile().setPort(true);
			this.plateau[1][j].getTuile().setType(this.port[k % this.port.length]);	// premiere ligne
			k++;
			
			this.plateau[this.hauteur+2][j-1].getTuile().setPort(true);
			this.plateau[this.hauteur+2][j-1].getTuile().setType(this.port[k % this.port.length]);	// derniere ligne
			k++;
		}
	}
	
	/**
	 * Ajoute les ports sur les sommets.
	 */
	private void initPort() {
		this.plateau[2][4].getSommet(0).setPort(this.plateau[1][4].getTuile().getType());
		this.plateau[2][4].getSommet(1).setPort(this.plateau[1][4].getTuile().getType());
		
		this.plateau[2][2].getSommet(0).setPort(this.plateau[2][1].getTuile().getType());
		this.plateau[2][2].getSommet(3).setPort(this.plateau[2][1].getTuile().getType());
		
		this.plateau[2][5].getSommet(1).setPort(this.plateau[2][6].getTuile().getType());
		this.plateau[2][5].getSommet(2).setPort(this.plateau[2][6].getTuile().getType());
		
		this.plateau[5][2].getSommet(0).setPort(this.plateau[5][1].getTuile().getType());
		this.plateau[5][2].getSommet(3).setPort(this.plateau[5][1].getTuile().getType());
		
		this.plateau[5][5].getSommet(1).setPort(this.plateau[5][6].getTuile().getType());
		this.plateau[5][5].getSommet(2).setPort(this.plateau[5][6].getTuile().getType());
		
		this.plateau[6][3].getSommet(2).setPort(this.plateau[7][3].getTuile().getType());
		this.plateau[6][3].getSommet(3).setPort(this.plateau[7][3].getTuile().getType());
	}
	
	private int[] typesPossibles = {1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5};	// 3 montagnes, 4 pres, 3 collines, 5 champs, 4 forets
	
	/**
	 * Ajoute le type de terrain de chaque tuile aléatoirement.
	 */
	private void ajouteTypesAlea() {	// remplie le plateau avec les typesPossibles melangees
		int k = 0;
		shuffle(this.typesPossibles);
		for (int i = 2; i <= this.hauteur+1; i++) {
			for (int j = 2; j <= this.largeur+1; j++) {
				if (this.plateau[i][j].getTuile().getValeur() == -1) j++; 
				this.plateau[i][j].getTuile().setType(this.typesPossibles[k % this.typesPossibles.length]);
				k++;
			}
		}
	}
	
	public Tuile[][] getPlateau() {
		return this.plateau;
	}
	
	public int getLargeur() {
		return this.largeur;
	}
	
	public int getHauteur() {
		return this.hauteur;
	}
	
	/**
	 * Retourne la tuile de coordonnées (y, x).
	 * @param x Le numéro de ligne de la tuile.
	 * @param y Le numéro de colonne de la tuile.
	 * @return La tuile de coordonnées (y, x) du plateau si les coordonnées sont valides, null sinon.
	 */
	public Tuile getTuile(int x, int y) {
		if (x < 0 || x > plateau.length || y < 0 || y > plateau[0].length) {
			System.out.println("Les coordonnees sont invalides.");
			return null;
		}
		return this.plateau[x][y];
	}
	
	public int[] getPosVoleur() {
		return this.posVoleur;
	}
	
	public void setPosVoleur(int x, int y) {
		this.posVoleur[0] = y;
		this.posVoleur[1] = x;
	}
	
	//////////////////// affichage ///////////////////
	
	public void afficheValeurs() {
		char c = 'A';
		System.out.print("     ");
		for (int i = 1; i <= this.hauteur+1; i++) { // affiche la ligne de chiffres
			System.out.print("   " + i + "   ");
		}
		System.out.println();
		
		for (int i = 1; i <= this.hauteur+2; i++) {
			System.out.print("     ");
			for (int j = 1; j <= 7*(this.largeur+2); j++) {	// affiche la ligne de tirets au dessus des types
		        System.out.print("-");
		    }
		    System.out.println("-");
		    
		    System.out.print("  " + c + "  "); c++; // affiche la colonne de lettres
		    
            for (int j = 1; j <= this.largeur+2; j++) {	// affiche les valeurs
            	if (j == posVoleur[0] && i == posVoleur[1]) System.out.printf("| * %2d ", this.plateau[i][j].getTuile().getValeur());
            	else if (this.plateau[i][j].getTuile().estUnPort()) System.out.printf("|    P ");
            	else System.out.printf("| %4d ", this.plateau[i][j].getTuile().getValeur());
            }
            System.out.println("|");
        }
		
		System.out.print("     ");
		for (int j = 1; j <= 7*(this.largeur+2); j++) {	// affiche la derniere ligne de tirets
	        System.out.print("-");
	    }
	    System.out.println("-");
	}
	
	public void afficheTypes() {
		char c = 'A';
		System.out.print("     ");
		for (int i = 1; i <= this.hauteur+1; i++) { // affiche la ligne de chiffres
			System.out.print("   " + i + "   ");
		}
		System.out.println();
		
		for (int i = 1; i <= this.hauteur+2; i++) {
			System.out.print("     ");
			for (int j = 1; j <= 7*(this.largeur+2); j++) {	// affiche la ligne de tirets au dessus des types
		        System.out.print("-");
		    }
		    System.out.println("-");
		    
		    System.out.print("  " + c + "  "); c++; // affiche la colonne de lettres
            
		    for (int j = 1; j <= this.largeur+2; j++) {	// affiche les types
            	if (j == posVoleur[0] && i == posVoleur[1]) System.out.printf("| * %2d ", this.plateau[i][j].getTuile().getType());
            	else if (this.plateau[i][j].getTuile().estUnPort()) System.out.printf("| P %2d ", this.plateau[i][j].getTuile().getType());
            	else System.out.printf("| %4d ", this.plateau[i][j].getTuile().getType()); 
            }
            System.out.println("|");
        }
		
		System.out.print("     ");
		for (int j = 1; j <= 7*(this.largeur+2); j++) {	// affiche la derniere ligne de tirets
	        System.out.print("-");
	    }
	    System.out.println("-");
	    
	    System.out.println("0: Ocean, 1: Montagne, 2: Pre, 3: Colline, 4: Champs, 5: Foret, 6: Desert, P: Port");
	}
		
	private String typeTuileToString(int x, int y) {
		String str = "";
		if (this.plateau[x][y].getValeur() == -1) str = " Desert ";
		else if (this.plateau[x][y].getType() == 1) str = "Montagne";
		else if (this.plateau[x][y].getType() == 2) str = "  Pres  ";
		else if (this.plateau[x][y].getType() == 3) str = "Colline ";
		else if (this.plateau[x][y].getType() == 4) str = " Champs ";
		else if (this.plateau[x][y].getType() == 5) str = " Foret  ";
		return str;
	}
	
	private String valeurTuileToString(int x, int y) {
		String str = "";
		if (this.plateau[x][y].getValeur() == -1 || this.plateau[x][y].getValeur() > 9) str = " " + this.plateau[x][y].getValeur() + " ";
		else str = "  " + this.plateau[x][y].getValeur() + " ";
		return str;
	}
	
	private String portToString(int x, int y) {
		String str = "[";
    	if (x == 2 && y == 4) str += this.plateau[2][4].portToString();
    	else if (x == 2 && y == 2) str += this.plateau[2][2].portToString();
    	else if (x == 2 && y == 5) str += this.plateau[2][5].portToString();
    	else if (x == 5 && y == 2) str += this.plateau[5][2].portToString();
    	else if (x == 5 && y == 5) str += this.plateau[5][5].portToString();
    	else if (x == 6 && y == 3) str += this.plateau[6][3].portToString();
    	else return "                                  ";
    	str += "]";
    	return str;
	}
	
	private void afficherNumTuile() {
		int n = 1;
		
		System.out.println("Numerotation des tuiles du plateau:");
		
		char c = 'A';
		System.out.print("     ");
		for (int i = 1; i < this.hauteur; i++) { // affiche la ligne de chiffres
			System.out.print("    " + i + "  ");
		}
		System.out.println();
		
		for (int i = 2; i < this.hauteur+2; i++) {
			System.out.print("     ");
			for (int k = 1; k < 5*(this.largeur+2); k++) {	// affiche la ligne de tirets au dessus des numeros
		        System.out.print("-");
		    }
			System.out.println();
			
			System.out.print("  " + c); c++; // affiche la colonne de lettres
			
            for (int j = 2; j < this.largeur+2; j++) {	// affiche les numeros de tuile
            	String num = "";
            	if (n > 9) num = "" + n;
            	else num = " " + n;
            	System.out.print("  |  " + num);
            	n++;
            }
            System.out.println("  |");
        }
		System.out.print("     ");
		for (int k = 1; k < 5*(this.largeur+2); k++) {	// affiche la derniere ligne de tirets
	        System.out.print("-");
	    }
		System.out.println();
	}
	
	public void afficherTout() {
		afficherNumTuile();
		
		int n = 1;
		
		System.out.print(" ");
		for (int j = 1; j <= 28*this.largeur; j++) { // affiche la ligne de tirets au dessus des valeurs
	        System.out.print("-");
	    }
		System.out.println("--");
		System.out.println(" | N° | Valeur |   Type   | Colonies et Villes |       Routes       | Voleur |                Port                |");
		
		System.out.print(" ");
		for (int j = 1; j <= 28*this.largeur; j++) { // affiche la ligne de tirets au dessus des valeurs
	        System.out.print("-");
	    }
		System.out.println("--");
		for (int i = 2; i < this.hauteur+2; i++) {
            for (int j = 2; j < this.largeur+2; j++) { // affiche les informations de la tuile
            	String num = "";
            	if (n > 9) num = "" + n;
            	else num = " " + n;
            	
            	String v = "";
            	if (j == posVoleur[0] && i == posVoleur[1]) v = "  !!  ";
            	else v = "      ";
            	
            	System.out.println(" | " + num + " |  " + this.valeurTuileToString(i, j) + "  | " + this.typeTuileToString(i, j) + " | " + this.plateau[i][j].sommetsToString() + " | " + this.plateau[i][j].cotesToString() + " | " +  v + " | " + this.portToString(i, j) + " |");
            	n++;
            	
            	System.out.print(" ");
            	for (int k = 1; k <= 28*this.largeur; k++) { // affiche la ligne de tirets au dessus des valeurs
    		        System.out.print("-");
    		    }
            	System.out.println("--");
            }
        }
		System.out.println();
	}
	
}

package jeu;

import java.util.Scanner;

public class Parametre {
	
	private int nbJoueur;
    private int nbIA;
	
	public Parametre() {
		this.nbJoueur = demanderNbJoueur();
        this.nbIA = demanderNbIA();
        new Jouer(new Jeu(nbJoueur, nbIA));
	}
    
    /**
     * Demande le nombre de joueur (IA inclus).
     * @return Le nombre de joueur.
     */
    private int demanderNbJoueur() {
        System.out.print("Veuillez entrer le nombre de joueurs total (entre 3 et 4 joueurs): ");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
        	try {
 	           int nb = Integer.parseInt(sc.nextLine());
 	           if (nb > 2 && nb < 5) {
 	                System.out.println(nb + " joueurs\n");
 	                return nb;
 	            }
 	            else {
 	                System.out.println("Le nombre est incorrect.\n");
 	                return demanderNbJoueur();
 	            }
         	}
 	        catch (NumberFormatException ex) {
 	           System.out.println("Le nombre de joueurs est incorrect.\n");
 	           return demanderNbJoueur();
 	        }
        }
        else return demanderNbJoueur();
    }
    
    /**
     * Demande Le nombre de IA souhaité.
     * @return Le nombre de IA.
     */
    private int demanderNbIA() {
    	System.out.print("Veuillez entrer le nombre de IA (jusqu'a " + (this.nbJoueur-1) + " joueurs): ");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
        	try {
	           int nb = Integer.parseInt(sc.nextLine());
	           if (nb >= 0 && nb < this.nbJoueur) {
	                System.out.println(nb + " IA\n");
	                return nb;
	            }
	            else {
	                System.out.println("Le nombre est incorrect.\n");
	                return demanderNbIA();
	            }
        	}
	        catch (NumberFormatException ex) {
	           System.out.println("Le nombre de joueurs IA est incorrect.\n");
	           return demanderNbIA();
	        }
        }
        else return demanderNbIA();
    }
}

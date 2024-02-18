package joueur;

import java.util.Random;

public class IA extends Joueur {
	
	Random random = new Random();
//	private static String[] pseudoPossible = {"Vasco", "Fernand", "Christophe", "Jeanne", "Ida", "Annie", "Amy", "François", "Sarah", "Marco"};
	
	public IA(int playerId) {
		super(playerId);
	}
	
	/**
	 * Choisi un pseudo pour chaque IA créé.
	 * @return Une chaine de caractère correspondant au pseudo du joueur IA.
	 */
//	@Override
//	public String demanderPseudo() {
//		int r = random.nextInt(10);
//		if (pseudoPossible[r].equals("")) return demanderPseudo();
//		else {
//			String s = pseudoPossible[r];
//			pseudoPossible[r] = "";  // supprime le pseudo du tableau pour eviter d avoir different IA avec le meme pseudo
//			System.out.println("Le pseudo du joueur " + (this.playerId+1) + " (IA) est: " + s + "\n");
//			return s;
//		}
//	}
	
	@Override
	public int[] demanderCoordonneesTuile() {
		int[] coord = new int[2];
		int x = random.nextInt(5)+2;
		coord[0] = x;
		int y = random.nextInt(4)+2;
		coord[1] = y;
		return coord;
	}
	
	@Override
	public int demanderCoordonneesSommet() {
		int s = random.nextInt(5);
		return s;
	}
	
	@Override
	public int demanderCoordonneesRoute() {
		int c = random.nextInt(5);
		return c;
	}
	
	public int unSurDeux() {
		int x = random.nextInt(2);
		return x;
	}
	
	@Override
	public void ressourceDiv2(int nbDepart, int nbArrivee) {
		if (nbDepart == nbArrivee) return;
    	
    	int r = random.nextInt(5);
		if (r == 0) {
			if (this.ressourcesSuffisantes(1, 0, 0, 0, 0)) this.ble = this.ble-1;
			else this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
		}
    	else if (r == 1) {
    		if (this.ressourcesSuffisantes(0, 1, 0, 0, 0)) this.bois = this.bois-1;
    		else this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    	}
    	else if (r == 2) {
    		if (this.ressourcesSuffisantes(0, 0, 1, 0, 0)) this.mouton = this.mouton-1;
    		else this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    	}
    	else if (r == 3) {
    		if (this.ressourcesSuffisantes(0, 0, 0, 1, 0)) this.argile = this.argile-1;
    		else this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    	}
    	else if (r == 4) {
        	if (this.ressourcesSuffisantes(0, 0, 0, 0, 1)) this.minerai = this.minerai-1;
        	else this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
    	}
    	this.ressourceDiv2(this.getQuantiteRessource(), nbArrivee);
	}
	
}

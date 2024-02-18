package carteDev;

import java.util.LinkedList;
import java.util.Random;

public class CarteDev {

	// 2 monopole, 2 invention, 2 route, 14 chevalier et 5 pointVictoire
	protected int[] deckInitial = {2, 2, 2, 14, 5};
	protected LinkedList<Integer> deckCartesDev = new LinkedList<Integer>();
	
	// monopole = 0;	
	// invention = 1;
	// route = 2;
	// chevalier = 3;
	// pointVictoire = 4;

	public CarteDev() {
		this.deckInitial = melangeDeckCarteDev();
		this.deckCartesDev = arrayToList();
	}

	public Integer getCarteDev(int i) {
		return this.deckCartesDev.get(i);
	}

	private int[] creerDeckCarteDev() {	// creer le tableau de 25 entiers correspondant aux cartes disponibles dans le deck
		int[] deck = new int[25];
		int k = 0;
		for(int i = 0; i < deckInitial.length; i++) {
			for(int j = 0; j < deckInitial[i]; j++) {
				deck[k] = i;
				k++;
			}
		}
		return deck;
	}

	private int[] melangeDeckCarteDev() {	// melange les 25 entiers
		int[] deck = creerDeckCarteDev();
		return shuffle(deck);
	}
	
	private int[] shuffle(int[] tab) {	// melange le tableau 1d passer en argument
		Random rd = new Random();
        for (int i = tab.length-1; i > 0; i--) {
            int j = rd.nextInt(i + 1);
            int tmp = tab[i];
            tab[i] = tab[j];
            tab[j] = tmp;
        }
        return tab;
	}

	private LinkedList<Integer> arrayToList() {	// ajoute les valeurs du tableau deckInitial a la liste 
		for(int i = 0; i < deckInitial.length; i++) {
			deckCartesDev.add(deckInitial[i]);
		}
		return deckCartesDev;
	}
	
	public int piocheCarteDev() {
		if (deckCartesDev.size() > 0) return deckCartesDev.pop();
		else return -1;
	}

	protected int size() {
		return this.deckCartesDev.size();
	}

    @Override
    public String toString() {
        if (this.deckCartesDev.size() == 1) return this.deckCartesDev.size() + " carte restante.";
        else if (this.deckCartesDev.size() == 0) return "Aucune carte restante";
		else return this.deckCartesDev.size() + " cartes restantes.";
    }
	
}

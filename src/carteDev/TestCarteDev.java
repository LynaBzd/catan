package carteDev;

import java.util.LinkedList;

public class TestCarteDev {

	public static void main(String[] args) {
		CarteDev cartes = new CarteDev();
		for (int i = 0; i < cartes.deckCartesDev.size(); i++) {
			System.out.print(((LinkedList<Integer>) cartes.deckCartesDev).get(i) + " ");
		}
		System.out.println();
		int n = cartes.deckCartesDev.size()-5;
		for (int i = 0; i < n; i++) {  // stocker la taille dans une variable sinon la taille change
			System.out.print(((LinkedList<Integer>) cartes.deckCartesDev).poll() + " ");
		}
		System.out.println();
		System.out.println(cartes.size());
		System.out.println(cartes.toString());
	}
	
}

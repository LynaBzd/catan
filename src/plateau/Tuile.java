package plateau;

public class Tuile {
	
	private int valeur;
	
	private int type;
//	ocean = 0;
//  montagne = 1;
//  pre = 2;
//  colline = 3;
//  champs = 4;
//  foret = 5;
	
	private Sommet[] sommets;
	private Cote[] cotes;
	
	private boolean estUnPort = false;
	
	public Tuile(int valeur, int type) {
		this.valeur = valeur;
        this.type = type;
        this.sommets = new Sommet[4];
        this.cotes = new Cote[4];
        
        for (int i = 0; i < cotes.length; i++) {
        	cotes[i] = new Cote();
        }
        
        for (int i = 0; i < sommets.length; i++) {
        	sommets[i] = new Sommet();
        }
	}
	
	public Tuile getTuile() {
		return this;
	}
	
	public int getValeur() {
		return this.valeur;
	}
	
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Sommet getSommet(int x) {
		return this.sommets[x];
	}
	
	public Cote getCoteHaut() {
		return this.cotes[0];
	}
	
	public Cote getCoteDroit() {
		return this.cotes[1];
	}
	
	public Cote getCoteBas() {
		return this.cotes[2];
	}
	
	public Cote getCoteGauche() {
		return this.cotes[3];
	}
	
	public boolean estUnPort() {
		return this.estUnPort;
	}
	
	public void setPort(boolean b) {
		this.estUnPort = true;
	}
	
	//////////////////// affichage ///////////////////
	
	public String sommetsToString() {
		String str = "[";
		for (Sommet s: sommets) {
			if (s.getConstruction() == -1) str += " -  ";
			else if (s.getConstruction() == 0) str += " C" + (s.getPlayerId()+1) + " ";
			else if (s.getConstruction() == 1) str += " V" + (s.getPlayerId()+1) + " ";
		}
		str += "]";
		return str;
	}
	
	public String cotesToString() {
		String str = "[";
		for (Cote c: cotes) {
			if (c.aUneRoute()) str += " R" + (c.getPlayerId()+1) + " ";
			else str += " -  ";
		}
		str += "]";
		return str;
	}
	
	public String portToString() {
		String str = "";
		int k = 0;
		for (Sommet s: sommets) {
		    if (s.getPort() == -1) str += "   -   ";
			else if (s.getPort() == 0) str += "   Tout  ";
			else if (s.getPort() == 1) str += " Minerai ";
			else if (s.getPort() == 2) str += " Mouton  ";
			else if (s.getPort() == 3) str += " Argile  ";
			else if (s.getPort() == 4) str += "   Ble   ";
			else if (s.getPort() == 5) str += "   Bois  ";
		}
		return str;
	}
	
}

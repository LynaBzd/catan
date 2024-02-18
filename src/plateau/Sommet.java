package plateau;

public class Sommet {

	private int playerId;
	private int construction;
    private int port;
//	quelconque = 0;
//  montagne = 1;
//  pre = 2;
//  colline = 3;
//  champs = 4;
//  foret = 5;
	
	public Sommet() {
		this.playerId = -1;
		this.construction = -1;
		this.port = -1;
	}
	
	public int getPlayerId() {
		return this.playerId;
	} 
	
	public int getConstruction() {
		return this.construction;
	}
	
	public void setColonie(int playerId) {
        this.construction = 0;
        this.playerId = playerId;
    }
	
	public void setVille(int playerId) {
		this.construction = 1;
	}
	
	public int getPort() {
        return port;
    }
	
	public void setPort(int type) {
		this.port = type;
	}
	
}

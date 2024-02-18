package plateau;

public class Cote {
	
	private boolean route;
	private int playerId;
	
	public Cote() {
		this.route = false;
		this.playerId = -1;
	}
	
	public boolean aUneRoute() {
		return this.route;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public void setRoute(int playerId) {
		this.route = true;
		this.playerId = playerId;
	}
	
}

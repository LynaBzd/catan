package ig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jeu.Jeu;
import joueur.Joueur;
import plateau.Plateau;

public class JeuIG extends JFrame implements ActionListener {
	
	private Controleur controleur;
	private Jeu jeu; // modele
	private Plateau p;
	
	private JPanel plateau_panel = new JPanel();
	private JPanel joueurInfo_panel = new JPanel();
	private JPanel menu_panel = new JPanel();
	private JButton[][] plateau = new JButton[13][11];
	
	
	public JeuIG(Jeu jeu) {
		this.controleur = new Controleur(jeu, this);
		this.jeu = jeu;
		this.p = jeu.getPlateau();
		
//		this.setResizable(false);
		this.setSize(960, 700);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setTitle("Catan - Jeu");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		joueurInfo_panel.setBackground(new Color(245, 245, 220));
		Joueur[] joueurs = jeu.getJoueurs();
		for (int i = 0; i < joueurs.length; i++) {
			JPanel infoJoueur = new JPanel();
			infoJoueur.setLayout(new GridLayout(3, 2, 10, 0));
			infoJoueur.setBorder(BorderFactory.createLineBorder(playerColor(joueurs[i])));
			JLabel joueurLabel = new JLabel("Joueur " + (joueurs[i].getPlayerId()+1));
			infoJoueur.add(joueurLabel);
			JLabel pointLabel = new JLabel(joueurs[i].getPointVic() + " pt(s)");
			infoJoueur.add(pointLabel);
			JLabel ressLabel = new JLabel(joueurs[i].getQuantiteRessource() + " ressource(s)");
			infoJoueur.add(ressLabel);
			JLabel devLabel = new JLabel(joueurs[i].getNbCarteDevAll() + " carte(s)");
			infoJoueur.add(devLabel);
			JLabel chevalierLabel = new JLabel(joueurs[i].getNbChevalier() + " arm\u00e9e");
			infoJoueur.add(chevalierLabel);
			JLabel routeLabel = new JLabel(joueurs[i].getNbChevalier() + " chemin");
			infoJoueur.add(routeLabel);
			joueurInfo_panel.add(infoJoueur);
		}
		this.getContentPane().add(joueurInfo_panel, BorderLayout.NORTH);
		
		
		plateau_panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		plateau_panel.setBackground(new Color(225, 206, 154));
		
		JPanel bg = new JPanel();
		bg.setLayout(new BorderLayout());
		bg.setBackground(new Color(225, 206, 154));
		bg.add(plateau_panel, BorderLayout.CENTER);
		this.getContentPane().add(bg, BorderLayout.CENTER);
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 11; j++) {
				plateau[i][j] = new JButton();
				c.gridx = j;
				c.gridy = i;
				c.fill = GridBagConstraints.BOTH;
				
				if (i > 1 && i < 12 && i%2 == 0 && j > 1 && j < 10 && j%2 == 0) { // terrain
					c.ipadx = 15;
					c.ipady = 40;
				}
				else if (i < 1 || i > 11 || j < 1 || j > 9) { // ocean
					c.ipadx = 1;
					c.ipady = 23;
					plateau[i][j].setBackground(new Color(0, 127, 255));
				}
				else if (i > 0 && i < 13 && i%2 != 0 && j > 0 && j < 9 && j%2 == 0) { // route horizontale
					c.ipadx = 45;
					c.ipady = 23;
					plateau[i][j].setBackground(new Color(90, 94, 107));
				}
				else if (i%2 != 0 && j%2 != 0) { // sommet
					c.ipadx = 1;
					c.ipady = 23;
					plateau[i][j].setBackground(new Color(158, 158, 158));
				}
				else if (i > 0 && i < 13 && i%2 == 0 && j > 0 && j < 10 && j%2 != 0) { // route verticale
					c.ipadx = 1;
					c.ipady = 64;
					plateau[i][j].setBackground(new Color(90, 94, 107));
				}
				
				plateau_panel.add(plateau[i][j], c);
				plateau[i][j].setLayout(new BorderLayout());
				plateau[i][j].setFocusable(false);
				plateau[i][j].setBorderPainted(false);
				plateau[i][j].setEnabled(false);
				plateau[i][j].addActionListener(this);
			}
		}
		
		for (int i = 1; i < p.getHauteur()+1; i += 3) {	// colonne
			this.couleur(i+1, 1, i*2, 0);
			this.couleur(i+1, p.getLargeur()+2, i*2, (p.getLargeur()+1)*2);
		}
		for (int j = 4; j < p.getLargeur()+2; j += 3) {	// ligne
			this.couleur(1, j, 0, (j-1)*2);
			this.couleur(p.getHauteur()+2, j-1, (p.getHauteur()+1)*2, j);
		}
		
		for (int i = 2; i < p.getHauteur()+2; i++) {
			for (int j = 2; j < p.getLargeur()+2; j++) {
				JLabel num;
				if (p.getTuile(i, j).getValeur() != -1) {
					if (p.getTuile(i, j).getValeur() < 10) num = new JLabel("  " + p.getTuile(i, j).getValeur() + "  ");
					else num = new JLabel(" " + p.getTuile(i, j).getValeur() + " ");
				}
				else num = new JLabel("      ");
				
				num.setFont(new Font("", Font.PLAIN, 18));
				num.setBorder(null);
				num.setHorizontalAlignment(SwingConstants.CENTER);
				plateau[(i-1)*2][(j-1)*2].add(num, BorderLayout.CENTER);
				this.couleur(i, j, (i-1)*2, (j-1)*2);
			}
		}
		
		JPanel menu = new JPanel();
		menu.setBackground(new Color(245, 245, 220));
		menu.setLayout(new GridLayout(7, 1, 0, 0));
		
		menu_panel.setLayout(new BorderLayout());
		menu_panel.add(menu, BorderLayout.CENTER);
		menu_panel.setBackground(new Color(245, 245, 220));
		this.getContentPane().add(menu_panel, BorderLayout.WEST);
		
		JButton des = new JButton("Lancer les d\u00e9s");
		JButton colonie = new JButton("Construire une colonie");
		colonie.setEnabled(false);
		JButton ville = new JButton("Construire une ville");
		ville.setEnabled(false);
		JButton route = new JButton("Construire une route");
		route.setEnabled(false);
		JButton acheterCarte = new JButton("Acheter une carte de d\u00e9veloppement");
		acheterCarte.setEnabled(false);
		JButton jouerCarte = new JButton("Jouer une carte de d\u00e9veloppement");
		jouerCarte.setEnabled(false);
		JButton finTour = new JButton("Finir le tour");
		finTour.setEnabled(false);
		
		des.addActionListener((e) -> { 
			int x = jeu.lancerDes();
			jeu.recolterRessource(x);
			des.setEnabled(false);
			if (controleur.ressourceSuffisante(1, 0, 1, 0, 1)) colonie.setEnabled(true);
			else colonie.setEnabled(false);
			if (controleur.ressourceSuffisante(2, 0, 0, 0, 3)) ville.setEnabled(true);
			else ville.setEnabled(false);
			if (controleur.ressourceSuffisante(0, 1, 0, 1, 0)) route.setEnabled(true);
			else route.setEnabled(false);
			if (controleur.ressourceSuffisante(1, 0, 1, 0, 1)) acheterCarte.setEnabled(true);
			else acheterCarte.setEnabled(false);
			if (controleur.possedeCarte()) jouerCarte.setEnabled(true);
			else jouerCarte.setEnabled(false);
			finTour.setEnabled(true);
		});
				
		colonie.addActionListener((e) -> {
			for (int i = 2; i < p.getHauteur()+2; i++) {
				for (int j = 2; j < p.getLargeur()+2; j++) {
					plateau[i][j].setEnabled(true);
				}
			}
			
			int[] coord = new int[2];
			coord[0] = 1;
			coord[1] = 1;
			controleur.construireColonie(coord[0], coord[1]);
			plateau[coord[0]][coord[1]].setBackground(playerColor(controleur.ordreJeu[controleur.joueurAJouer]));
			
			for (int i = 2; i < p.getHauteur()+2; i++) {
				for (int j = 2; j < p.getLargeur()+2; j++) {
					plateau[i][j].setEnabled(false);
				}
			}
			if (controleur.ressourceSuffisante(1, 0, 1, 0, 1)) colonie.setEnabled(true);
		});
		
		acheterCarte.addActionListener((e) -> controleur.acheterCarteDev());
		
		finTour.addActionListener((e) -> {
			controleur.terminerTour();
			des.setEnabled(true);
			colonie.setEnabled(false);
			ville.setEnabled(false);
			route.setEnabled(false);
			acheterCarte.setEnabled(false);
			jouerCarte.setEnabled(false);
			finTour.setEnabled(false);
		});
		
		menu.add(des);
		menu.add(colonie);
		menu.add(ville);
		menu.add(route);
		menu.add(acheterCarte);
		menu.add(jouerCarte);
		menu.add(finTour);
	}
	
	private void couleur(int x1, int y1, int x2, int y2) {
		if (p.getTuile(x1, y1).getType() == 0) plateau[x2][y2].setBackground(new Color(255, 255, 255));
		else if (p.getTuile(x1, y1).getType() == 1) plateau[x2][y2].setBackground(new Color(206, 206, 206));
		else if (p.getTuile(x1, y1).getType() == 2) plateau[x2][y2].setBackground(new Color(158, 253, 56));
		else if (p.getTuile(x1, y1).getType() == 3) plateau[x2][y2].setBackground(new Color(239, 155, 15));
		else if (p.getTuile(x1, y1).getType() == 4) plateau[x2][y2].setBackground(new Color(253, 238, 0));
		else if (p.getTuile(x1, y1).getType() == 5) plateau[x2][y2].setBackground(new Color(22, 184, 78));
		else if (p.getTuile(x1, y1).getType() == 6) plateau[x2][y2].setBackground(new Color(254, 254, 226));
	}

	private Color playerColor(Joueur joueur) {
		if (joueur.getPlayerId() == 0) return new Color(233, 56, 63);
		else if (joueur.getPlayerId() == 1) return new Color(223, 115, 255);
		else if (joueur.getPlayerId() == 2) return new Color(179, 103, 0);
		else return new Color(44, 117, 255);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}

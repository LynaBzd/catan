package ig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import jeu.Jeu;

public class LanceurIG extends JFrame {

	public LanceurIG() {
//		this.setResizable(false);
		this.setSize(960, 700);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setTitle("Catan - Menu");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2, 1));
		
		JPanel catan = new JPanel();
		catan.setBackground(new Color(245, 245, 220));
		this.add(catan);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(2, 1, 0, 100));
		content.setBackground(new Color(245, 245, 220));
		this.add(content);
		
		JLabel titre = new JLabel("Catan");
		titre.setFont(new Font("Serif", Font.ITALIC, 100));
		titre.setForeground(new Color(167, 103, 38));
		titre.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));
		catan.add(titre, JLabel.CENTER);
		
		JButton jouer = new JButton("Jouer");
		jouer.setFont(new Font("Serif", Font.PLAIN, 30));
		jouer.setForeground(new Color(225, 100, 100));
		jouer.setBackground(new Color(250, 240, 197));
		jouer.setBorderPainted(false);
		jouer.setFocusable(false);
		
		JButton regle = new JButton("R\u00e8gle du jeu");
		regle.setFont(new Font("Serif", Font.PLAIN, 30));
		regle.setForeground(new Color(225, 100, 100));
		regle.setBackground(new Color(250, 240, 197));
		regle.setBorderPainted(false);
		regle.setFocusable(false);
		
		JMenuBar menu = new JMenuBar();
		menu.setBackground(new Color(250, 240, 197));
		menu.setBorderPainted(false);
		content.add(menu);
		menu.setLayout(new GridLayout(2, 1, 0, 20));
		menu.add(jouer);
		menu.add(regle);
		
		jouer.addActionListener((e) -> {this.dispose(); ParametrePage p = new ParametrePage(); p.setVisible(true);});
		jouer.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jouer.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jouer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
            @Override public void mouseClicked(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
        });
		
		regle.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                regle.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                regle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

			@Override
			public void mouseClicked(MouseEvent e) {
				try { Desktop.getDesktop().browse(new URL("https://www.regledujeu.fr/catane/").toURI()); } 
			    catch (Exception exc) { }
			}
			
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
        });

	}
	
	public class ParametrePage extends JFrame {
		
		private int nombreJoueur;
		private int nombreIA;
		
		public ParametrePage() {
//			this.setResizable(false);
			this.setSize(960, 700);
			this.setExtendedState(MAXIMIZED_BOTH);
			this.setTitle("Catan - Param\u00e8tres");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.getContentPane().setLayout(new BorderLayout());
			
			JPanel content = new JPanel();
			this.getContentPane().add(content);
			content.setBackground(new Color(245, 245, 220));
			content.setLayout(new BorderLayout());
			
			JPanel paramPanel = new JPanel();
			paramPanel.setBackground(new Color(245, 245, 220));
			paramPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
			JLabel param = new JLabel("Param\u00e8tres de la partie");
			param.setFont(new Font("Sans Serif", Font.BOLD, 20));
			paramPanel.add(param, JLabel.CENTER);
			content.add(paramPanel, BorderLayout.NORTH);
			
			JPanel nbJoueurPanel = new JPanel();
			nbJoueurPanel.setLayout(new GridLayout(3, 1));
			nbJoueurPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			nbJoueurPanel.setBackground(new Color(254, 254, 254));
			JLabel nbJ = new JLabel("Nombre de joueur: ");
			nbJ.setFont(new Font("Sans Serif", Font.BOLD, 18));
			nbJ.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			nbJoueurPanel.add(nbJ);
			ButtonGroup nbJoueur = new ButtonGroup();
			JCheckBox j3 = new JCheckBox(" 3 ");
			j3.setFont(new Font("Sans Serif", Font.BOLD, 18));
			j3.setBackground(Color.WHITE);
			j3.setFocusable(false);
			j3.setBorder(BorderFactory.createEmptyBorder(-50, 60, 0, 0));
			JCheckBox j4 = new JCheckBox(" 4 ");
			j4.setFont(new Font("Sans Serif", Font.BOLD, 18));
			j4.setBackground(Color.WHITE);
			j4.setFocusable(false);
			j4.setBorder(BorderFactory.createEmptyBorder(-50, 60, 0, 0));
			nbJoueur.add(j3);
			nbJoueur.add(j4);
			nbJoueurPanel.add(j3);
			nbJoueurPanel.add(j4);
			
			JPanel nbIAPanel = new JPanel();
			nbIAPanel.setLayout(new GridLayout(5, 1));
			nbIAPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			nbIAPanel.setBackground(new Color(254, 254, 254));
			JLabel nbIA = new JLabel("Nombre de joueurs ordinateurs: ");
			nbIA.setFont(new Font("Sans Serif", Font.BOLD, 18));
			nbIA.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			nbIAPanel.add(nbIA);
			ButtonGroup nbIAs = new ButtonGroup();
			JCheckBox ia0 = new JCheckBox(" 0 ");
			ia0.setFont(new Font("Sans Serif", Font.BOLD, 18));
			ia0.setBackground(Color.WHITE);
			ia0.setFocusable(false);
			ia0.setVisible(false);
			ia0.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
			JCheckBox ia1 = new JCheckBox(" 1 ");
			ia1.setFont(new Font("Sans Serif", Font.BOLD, 18));
			ia1.setBackground(Color.WHITE);
			ia1.setFocusable(false);
			ia1.setVisible(false);
			ia1.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
			JCheckBox ia2 = new JCheckBox(" 2 ");
			ia2.setFont(new Font("Sans Serif", Font.BOLD, 18));
			ia2.setBackground(Color.WHITE);
			ia2.setFocusable(false);
			ia2.setVisible(false);
			ia2.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
			JCheckBox ia3 = new JCheckBox(" 3 ");
			ia3.setFont(new Font("Sans Serif", Font.BOLD, 18));
			ia3.setBackground(Color.WHITE);
			ia3.setFocusable(false);
			ia3.setVisible(false);
			ia3.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
			nbIAs.add(ia0);
			nbIAs.add(ia1);
			nbIAs.add(ia2);
			nbIAs.add(ia3);
			nbIAPanel.add(ia0);
			nbIAPanel.add(ia1);
			nbIAPanel.add(ia2);
			nbIAPanel.add(ia3);
			
			j3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent event) {
			        if (((AbstractButton) event.getSource()).isSelected()) {
			        	ia0.setVisible(true);
			            ia1.setVisible(true);
			            ia2.setVisible(true);
			            ia3.setVisible(false);
			            nombreJoueur = 3;
			        }
			    }
			});
			
			j4.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent event) {
			        if (((AbstractButton) event.getSource()).isSelected()) {
			        	ia0.setVisible(true);
			            ia1.setVisible(true);
			            ia2.setVisible(true);
			            ia3.setVisible(true);
			            nombreJoueur = 4;
			        }
			    }
			});
			
			JPanel choix = new JPanel();
			choix.setLayout(new GridLayout(1, 2, 30, 0));
			choix.setBackground(new Color(245, 245, 220));
			choix.add(nbJoueurPanel);
			choix.add(nbIAPanel);
			content.add(choix, BorderLayout.CENTER);
			
			JPanel panelValider = new JPanel();
			panelValider.setBackground(new Color(245, 245, 220));
			panelValider.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
			content.add(panelValider, BorderLayout.SOUTH);
			JButton valider = new JButton("Valider");
			valider.setFont(new Font("Sans Serif", Font.PLAIN, 20));
			valider.setForeground(new Color(255, 255, 255));
			valider.setBackground(new Color(1, 121, 111));
			valider.setBorderPainted(false);
			valider.setFocusable(false);
			valider.setEnabled(false);
			valider.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
			panelValider.add(valider);
			
			valider.addActionListener((e) -> {JeuIG j = new JeuIG(new Jeu(nombreJoueur, nombreIA)); j.setVisible(true); this.dispose();});
			valider.addMouseListener(new MouseListener() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                valider.setCursor(new Cursor(Cursor.HAND_CURSOR));
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                valider.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	            }

				@Override public void mouseClicked(MouseEvent e) {}
				@Override public void mousePressed(MouseEvent e) {}
				@Override public void mouseReleased(MouseEvent e) {}
	        });
			
			ia0.addActionListener((e) -> { nombreIA = 0; valider.setEnabled(true); });
			
			ia1.addActionListener((e) -> { nombreIA = 1; valider.setEnabled(true); });
			
			ia2.addActionListener((e) -> { nombreIA = 2; valider.setEnabled(true); });
			
			ia3.addActionListener((e) -> { nombreIA = 3; valider.setEnabled(true); });
		}
		
	} // end class Parametre
	
}

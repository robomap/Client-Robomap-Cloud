package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayListButton {
	 private String nameList;
	 private JButton button;
	 private JLabel jlPlaylist;
	 private JPanel jpPlaylist;
	
	public PlayListButton (double width){
		this.nameList ="";
		ImageIcon nuevaPlaylist = new ImageIcon("./img/Playlist.png");
		Image auxNuevaPlaylist = nuevaPlaylist.getImage();
	    Image tamanyNuevaPlaylist = auxNuevaPlaylist.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		nuevaPlaylist = new ImageIcon(tamanyNuevaPlaylist);
		button = new JButton(nuevaPlaylist);
		button.setLayout(new BorderLayout()); 
		jlPlaylist = new JLabel("                Nueva Lista");
	    jlPlaylist.setForeground(new Color(200,200,200));
	    jlPlaylist.setFont(new java.awt.Font("Century Gothic",0,12));
	    button.add(jlPlaylist);
	    button.setBackground(null);
	    button.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
	    button.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void setVisibletrue(){
		jpPlaylist.setVisible(true);
	}
	
	public void setVisiblefalse(){
		jpPlaylist.setVisible(false);
	}
	
	public JPanel addPlayList(){
		return this.jpPlaylist;	
	}

	public JButton getButton() {
		return button;
	}
	
	public void setButton(JButton button) {
		this.button = button;
	}
	
	public String getNomllista() {
		return nameList;
	}
	
	public JLabel getJLPlaylist() {
		return jlPlaylist;
	}

	public void setJLPlaylist(String jlNuevaPlaylist) {
		nameList = jlNuevaPlaylist;
		this.jlPlaylist.setText("                "+jlNuevaPlaylist);
	}

	public void setNomllista(String nomllista) {
		this.nameList = nomllista;
	}
}

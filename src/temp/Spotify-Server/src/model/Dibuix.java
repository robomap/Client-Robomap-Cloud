package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Controller.ControllerServer;

@SuppressWarnings("serial")
public class Dibuix extends JPanel {

	private List<Song> songs = new ArrayList<Song> ();
	private ControllerServer ctrl;
	
	
	public Dibuix(ControllerServer ctrl) {
		this.ctrl = ctrl;
		this.setPreferredSize (new Dimension(800,650));
		this.setBackground(Color.GREEN);
		this.setOpaque(true);
		this.setLayout(null);

	}
	
	public void paintComponent (Graphics g ){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		songs = ctrl.getSongsFromDataBase();
		
		// Y Axis
		g2.drawLine(15,290 , 15, 10);		
		g2.drawString("Num. Reproduccions", 20, 15);
		// X Axis
		g2.drawLine(15, 290, 750, 290);
		g2.drawString("Cançons", 735, 285);
		
		int max=0,c=0;
		List<Song> topSongs = new ArrayList<Song>();
		for(Song s : songs){
			if((s.getNumReproducciones()> max) && (c<=10)){
				topSongs.add(s);
				c++;
			}
		}
		for(int i=0;i<topSongs.size()-1;i++){
			for(int j=1;j<topSongs.size();j++){
				if(topSongs.get(j).getNumReproducciones() > topSongs.get(j-1).getNumReproducciones()){
					Song aux = (Song) topSongs.get(j);
					topSongs.set(j,topSongs.get(j-1));
					topSongs.set(j-1,aux);
				}
			}
		}
		int x=50,y=50;
		int z = 1;
		for(int i =0;i<topSongs.size();i++){
			if (i == 10){
				break;
			}
			String nrep = Integer.toString(topSongs.get(i).getNumReproducciones());	
			z = 100/(topSongs.get(0).getNumReproducciones());
			z = z + 10;
			
			g2.fill(new Rectangle(x,y+(z*(topSongs.get(0).getNumReproducciones()-topSongs.get(i).getNumReproducciones())),10,240-(z*(topSongs.get(0).getNumReproducciones()-topSongs.get(i).getNumReproducciones()))));
			if(topSongs.get(i).getSongName().length() > 8){
				String shortName = topSongs.get(i).getSongName().substring(0, 8);				
				topSongs.get(i).setSongName(shortName);
			}
			g2.drawString(nrep, x, -5+y+(z*(topSongs.get(0).getNumReproducciones()-topSongs.get(i).getNumReproducciones())));		
			g2.drawString(topSongs.get(i).getSongName(),x,310);	
			x=x+70;
		}
		g2.setColor(Color.GREEN);		
		}
	
	public void Actualitza(){
		this.repaint();
	}
}

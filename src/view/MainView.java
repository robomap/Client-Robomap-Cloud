package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class MainView extends JFrame {	
	private JMenuBar barraMenu;
	private JMenu menu;
	private JMenuItem menuItemNuevaLista;
	private JMenuItem menuItemSalir;
	private JMenuItem menuItemBuscar;
	private JButton pauseArchivosButton;
	private JButton reproducirArchivosButton;
	private JButton pauseButtonCanciones;
	private JButton reproducirButtonCanciones;
	private JButton nuevaPlaylistButton;
	private JPanel PanellReproduccio;
	private JPanel jpList;
	private JPanel jpCenter;
	private JPanel Center;
	private JPanel jpCenterarchivos;
	private JLabel jlWelcome;
	private JLabel imageAlbumCanciones;
	private JLabel jlLine;
	private JLabel jlWorking;
	private JLabel jlNameArchivos;
	private JLabel imageAlbumArchivos;
	private JLabel jlNameCanciones;
	private JLabel imageLineArchivos;
	private JLabel jlUser;
	private JLabel nombreCancion;
	private Image auxLine1;
	private Image auxLine2;
	private Image auxLine3;
	private Image auxLineArchivos1;
	private Image auxLineArchivos2;
	private Image auxLineArchivos3;
	private Image auxAlbum1;
	private Image auxAlbum2;
	private Image auxAlbumArchivos1;
	private Image auxAlbumArchivos2;
	private DefaultTableModel tableModelarchivos;
	private DefaultTableModel tableModelCanciones;
	private JScrollPane jspList;
	private JTextField jtfBuscador;
	private JTable jTableCanciones;
	private DefaultTableModel tableModelFollowing;
	private JTable jTableFollowers;

	public MainView(){
		leftPanel();
		centerPanel();
		centerPanelInicio();
		centerPanelCanciones();
		centerPanelEnProceso();
		rightPanel();
		centerPanelCancionesVisible();
		centerPanelInicioVisible();	

		this.add(Center, BorderLayout.CENTER);
		this.getContentPane().setBackground(new Color(30,30,30));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.NORMAL);
		this.setMinimumSize(new Dimension(800,600));
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setFont(new java.awt.Font("Century Gothic",0,11));
		this.setTitle("Client Robomap Cloud");
	}
	
	public class TableHeaderRenderer implements TableCellRenderer {
	    private final TableCellRenderer baseRenderer;
	    public TableHeaderRenderer(TableCellRenderer baseRenderer) {
	        this.baseRenderer = baseRenderer;
	    }
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        JComponent c = (JComponent)baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBorder(new EmptyBorder(2,2,2,2));
	        return c;
	    }
	}	
	
	public void centerPanel(){
		Center = new JPanel(new BorderLayout());
		Center.setBackground(Color.white);
	}
	
	public void centerPanelInicio(){
	    ImageIcon welcome = new ImageIcon("./img/Welcome.jpg");
		Image auxWelcome = welcome.getImage();
		auxWelcome = auxWelcome.getScaledInstance((int)(687.0),(int)(450.0),Image.SCALE_SMOOTH);
		jlWelcome = new JLabel(new ImageIcon(auxWelcome));
		Center.add(jlWelcome, BorderLayout.CENTER);
	}

	public void centerPanelEnProceso(){
	    ImageIcon working = new ImageIcon("./img/Working.png");
		Image auxWorking = working.getImage();
		auxWorking = auxWorking.getScaledInstance((int)(787.0),(int)(450.0),Image.SCALE_SMOOTH);
		jlWorking = new JLabel(new ImageIcon(auxWorking));
	}
	
	public void  centerPanelInicioVisible(){
		//jpCenter.setVisible(false);
		//jlWorking.setVisible(false);
		//jpCenterarchivos.setVisible(false);
		startPanel();
		//jlWelcome.setVisible(true);
	}
		
	public void centerPanelCanciones(){
		JPanel jpUPcanciones = new JPanel(new BorderLayout());
		JPanel jpDOWNcanciones = new JPanel(new BorderLayout());
		jpUPcanciones.setBackground(new Color(60,60,60));
		jpDOWNcanciones.setBackground(new Color(60,60,60));
	    
		ImageIcon album = new ImageIcon("./img/Album.png");
		auxAlbum1 = album.getImage();
		auxAlbum1 = auxAlbum1.getScaledInstance((int)(380.0), (int)(300.0), Image.SCALE_SMOOTH);
		auxAlbum2 = auxAlbum1.getScaledInstance((int)(190.0), (int)(150.0), Image.SCALE_SMOOTH);
		imageAlbumCanciones = new JLabel(new ImageIcon (auxAlbum1));
	   
		JPanel jpPageStartCanciones = new JPanel();
	    jpPageStartCanciones.setBackground(new Color(60,60,60));
	    jlNameCanciones = new JLabel();
		ImageIcon pause = new ImageIcon("./img/PauseCanciones.png");
		Image auxPause = pause.getImage();
		pauseButtonCanciones = new JButton(new ImageIcon (auxPause));
		ImageIcon reproducir = new ImageIcon("./img/ReproducirCanciones.png");
		Image auxReproducir = reproducir.getImage();
		auxReproducir = auxReproducir.getScaledInstance((int)(150.0), (int)(30.0), Image.SCALE_SMOOTH);
		reproducirButtonCanciones = new JButton(new ImageIcon (auxReproducir));
		JPanel jpReproducirCanciones = new JPanel();
		jpPageStartCanciones.add(imageAlbumCanciones, BorderLayout.WEST);
		JPanel jpCanciones = new JPanel(new BorderLayout());	
		jpCanciones.add(jlNameCanciones,BorderLayout.PAGE_START);
		jpCanciones.add(jpReproducirCanciones,BorderLayout.WEST);
		jpCanciones.setBackground(new Color(60,60,60));
		jpPageStartCanciones.add(jpCanciones, BorderLayout.CENTER);
		
	    ImageIcon line = new ImageIcon("./img/line-01.png");
		auxLine1 = line.getImage();
		jlLine = new JLabel(new ImageIcon (auxLine1));
		jpPageStartCanciones.add(jlLine, BorderLayout.PAGE_END);
		jpUPcanciones.add(jpPageStartCanciones, BorderLayout.PAGE_START);
		
		String[][] dataCanciones = null;
		
		@SuppressWarnings("rawtypes")
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				JButton.class
		};
		String[] header = {"Cancion", "Artista", "Genero", "Album", "Puntuacion"};;
		tableModelCanciones = new DefaultTableModel(dataCanciones, header) {
		    
		};

		jTableCanciones = new JTable();
		jTableCanciones.getTableHeader().setFont(new Font("Century Gothic",0,18));
		jTableCanciones.setModel(tableModelCanciones);
		TableCellRenderer baseRendererCanciones = jTableCanciones.getTableHeader().getDefaultRenderer();
		jTableCanciones.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRendererCanciones));
		jTableCanciones.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane table = new JScrollPane(jTableCanciones, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.setBackground(new Color(60,60,60));
		table.getVerticalScrollBar().setOpaque(false);
		table.getVerticalScrollBar().setUnitIncrement(16);
		table.getViewport().setBackground(new Color(60,60,60));
		JPanel jpTable = new JPanel();
		jpTable.setBackground(new Color(60,60,60));
		jpTable.add(table);
		jpDOWNcanciones.add(new JLabel("                      "), BorderLayout.LINE_START);
		jpDOWNcanciones.add(new JLabel("                         "), BorderLayout.LINE_END);
		jpDOWNcanciones.add(new JLabel("                      "), BorderLayout.PAGE_END);
		jpDOWNcanciones.add(table,BorderLayout.CENTER);
		jpCenter = new JPanel(new BorderLayout());
		jpCenter.setBackground(new Color(60,60,60));
		jpCenter.add(jpUPcanciones,BorderLayout.PAGE_START);
		jpCenter.add(jpDOWNcanciones,BorderLayout.CENTER);
	}

	public void  centerPanelCancionesVisible(){
		jlWorking.setVisible(false);
		jlWelcome.setVisible(false);
		//jpCenterarchivos.setVisible(false);
		Center.add(jpCenter,BorderLayout.CENTER);
		jpCenter.setVisible(true);
	}

	public void rightPanel(){	
		JPanel jpRight = new JPanel(new BorderLayout());
		jpRight.setBackground(new Color(50,50,50));
		jpRight.setPreferredSize(new Dimension((int)(200.0),0));

		this.add(jpRight, BorderLayout.LINE_END);
	}
	
	public JScrollPane RightPanelFollowers(){
		Object [][] dataUsuariosFollowers = null;
		
		@SuppressWarnings("rawtypes")
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
		};
		String[] headerFollowers = {"Followers"};;
		
		tableModelFollowing = new DefaultTableModel(dataUsuariosFollowers, headerFollowers) {
		    @SuppressWarnings("rawtypes")
			Class[] tipos = tipoColumnas;
		    
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public Class getColumnClass(int ColumnIndex){
		    	return tipos[ColumnIndex];
		    }
		    
			@Override
		    public boolean isCellEditable(int row, int column) {
		 
		       return false;
		    }
		};
		
		jTableFollowers = new JTable();
		jTableFollowers.setModel(tableModelFollowing);
		TableCellRenderer baseRenderer = jTableFollowers.getTableHeader().getDefaultRenderer();
		jTableFollowers.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		jTableFollowers.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane tableFollowers = new JScrollPane(jTableFollowers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		return tableFollowers;
	}
	
	public void leftPanel(){
		jpList = new JPanel(new GridLayout(10,1));
		jpList.setBackground(new Color(50,50,50));
		TitolWest();
		ListWest();
	    
		JPanel jpleft = new JPanel();
		jpleft.setBackground(new Color(50,50,50));
		jpleft.setPreferredSize(new Dimension((int)(300.0),(int)(800.0)));
		jpleft.add(jspList, BorderLayout.NORTH);
	    this.getContentPane().add(jpleft, BorderLayout.WEST);
	}
	
	public void TitolWest(){
		JLabel jlabPrincipal = new JLabel(" Devices"); 
		jlabPrincipal.setFont(new java.awt.Font("Century Gothic",0,12));
		jlabPrincipal.setForeground(new Color(200,200,200));
		jlabPrincipal.setPreferredSize(new Dimension((int)(260.0),(int)(40.0)));
		jpList.add(jlabPrincipal, BorderLayout.NORTH);
	}
				
	public void ListWest(){		
		jspList = new JScrollPane(jpList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspList.setBorder(BorderFactory.createEmptyBorder());
		jspList.getVerticalScrollBar().setOpaque(false);
		jspList.getVerticalScrollBar().setUnitIncrement(16);
		jspList.setPreferredSize(new Dimension((int)(300.0),(int)(400.0)));
	}
	
	public JPanel buscador(){
		JPanel jpBuscador = new JPanel();
		jtfBuscador = new JTextField("Search");
		jtfBuscador.setPreferredSize(new Dimension(140,20));
		jtfBuscador.setBackground(new Color(60,60,60));
		jtfBuscador.setBorder(BorderFactory.createEmptyBorder());
		jtfBuscador.setForeground(new Color(200,200,200));
		jtfBuscador.setFont(new java.awt.Font("Century Gothic",0,11));
		jpBuscador.add(jtfBuscador);
		jpBuscador.setBackground(new Color(60,60,60));
		return jpBuscador;
	}
	
	public void startPanel(){
		JPanel startPanel = new JPanel(new BorderLayout());
		JPanel jpBuscador = new JPanel();
		JPanel jpBuscadorFull = new JPanel();
		jpBuscador = buscador();
		startPanel.setBackground(new Color(60,60,60));
		jpBuscadorFull.setBackground(new Color(60,60,60));
		jpBuscadorFull.add(jpBuscador,BorderLayout.WEST);
		//startPanel.add(jpMenu,BorderLayout.PAGE_START);
		startPanel.add(jpBuscadorFull,BorderLayout.WEST);
		//startPanel.add(jpUser,BorderLayout.EAST);
		this.add(startPanel, BorderLayout.PAGE_START);
	}	

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(() -> {
			MainView mainView = new MainView();
			mainView.setVisible(true); // Make the main view visible
		});
	}
}
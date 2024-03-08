package model;

import java.io.File;
import javax.swing.JFileChooser;

public class FileManager {
    public boolean isMP3(String ruta){ 
    	String s = ""; 
    	boolean found = false;
		int length = ruta.length();
		for(int i = 0; i < length; i++){
			if(ruta.charAt(i) == '.'){
				found = true; 
			}else if(found){
				s+= ruta.toLowerCase().charAt(i);
			}
		}
		return "mp3".equalsIgnoreCase(s);
    }
    
    public String openFile(){
    	JFileChooser fileChooser = new JFileChooser();

        int opcion = fileChooser.showOpenDialog(null); 
        if(opcion == JFileChooser.APPROVE_OPTION){
        	File archivo = fileChooser.getSelectedFile();
        	String file = archivo.getPath();
        	return file;
        }   
        return null;
    }
    
    public String[] openFolder(){ 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(null); 
          if(option == JFileChooser.APPROVE_OPTION){
              String ruta = fileChooser.getSelectedFile().getPath();
              File file = new File(fileChooser.getSelectedFile().getPath());
              String directoryList[] = file.list();
              int length = directoryList.length;
              for(int i = 0; i < length; i++){
                  directoryList[i] = ruta+"\\"+directoryList[i];
              }
              return directoryList;
          }else{
              return null;
          }    
      }
}

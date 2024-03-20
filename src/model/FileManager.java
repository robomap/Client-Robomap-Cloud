package model;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    
    public String[] openFile(String text,String format){
    	JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter(text,format));
        int opcion = fileChooser.showOpenDialog(null); 
        if(opcion == JFileChooser.APPROVE_OPTION){
        	File Archivos[] = fileChooser.getSelectedFiles();
        	String files[] = new String[Archivos.length];
        	int i = 0;
        	for(File aux:Archivos){
        		files[i] = aux.getPath();
        		i++;
        	}
        	return files;
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

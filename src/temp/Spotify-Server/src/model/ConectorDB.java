package model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConectorDB {
	static String userName;
	static String password;
	static String dB_Name;
	static int port;
	static String url = "jdbc:mysql://localhost";
	static Connection conn = null;
	static Statement s;
    
	public ConectorDB(String usr, String pass, String dB_Name, int port) {
		ConectorDB.userName = usr;
		ConectorDB.password = pass;
		ConectorDB.dB_Name = dB_Name;
		ConectorDB.port = port;
		ConectorDB.url += ":"+port+"/";
		ConectorDB.url += dB_Name;
	}

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Conexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }
    
    public boolean insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            return s.executeUpdate(query) != 0;

        } catch (SQLException ex) {
        	ex.printStackTrace();
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
    }
    
    public int updateQuery(String query){
    	int ok = 0;
    	 try {
             s =(Statement) conn.createStatement();
             s.executeUpdate(query);
             ok = 1;

         } catch (SQLException ex) {
             System.out.println("Problema al Modificar --> " + ex.getSQLState());
             ok = 0;
         }
    	 return ok;
    }
    
    public boolean deleteQuery(String query){
    	 try {
             s =(Statement) conn.createStatement();
             s.executeUpdate(query);
             return true;
             
         } catch (SQLException ex) {
             System.out.println("Problema al Eliminar --> " + ex.getSQLState());
             return false;
         }
    }
    
    public ResultSet selectQuery(String query){
    	ResultSet rs = null;
    	 try {
             s =(Statement) conn.createStatement();
             rs = s.executeQuery (query);
             
         } catch (SQLException ex) {
             System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
         }
		return rs;
    }
    
    public void disconnect(){
    	try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
		}
    }
}

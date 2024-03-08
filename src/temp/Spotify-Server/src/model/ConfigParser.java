package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigParser {
	
	private static ServerConfiguration serverConfig;

	public static ServerConfiguration getServerConfig() {
		
		if(serverConfig != null) {
			return serverConfig;
		}
		
		try {
			// Guarda el fitxer .json a la variable reader com a stream.
			InputStreamReader reader = new InputStreamReader(new FileInputStream("./config.json"));
			// Inicialitza gson cridant al constructor.
			Gson gson = new GsonBuilder().create();
			serverConfig = gson.fromJson(reader, ServerConfiguration.class);				
		} catch (FileNotFoundException ex){
			System.out.println("Error al llegir el fitxer configuracio");
		}
		return serverConfig;
	}

	public static void setServerConfig(ServerConfiguration serverConfig) {
		ConfigParser.serverConfig = serverConfig;
	}
}


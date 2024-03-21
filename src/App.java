import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import view.Authentication;

public class App {
    public static void main(String[] args) throws Exception {
        setupUI();
    }

    public static void setupUI() {
        try {
			File lockFile = new File("app.lock");
			if (!lockFile.createNewFile()) {
				System.out.println("Another instance is already running.");
				return;
		    }
	
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if (lockFile.exists()) {
					lockFile.delete();
					System.out.println("Lock file deleted.");
				}
			}));
	
			SwingUtilities.invokeLater(() -> new Authentication());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}


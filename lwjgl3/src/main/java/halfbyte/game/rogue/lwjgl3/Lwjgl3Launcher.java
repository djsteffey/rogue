package halfbyte.game.rogue.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import halfbyte.game.rogue.client.GameRogue;
import halfbyte.game.rogue.client.IPlatformServices;

public class Lwjgl3Launcher implements IPlatformServices {
	public static void main(String[] args) {
		// config
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("Match");
		configuration.setWindowedMode(1280, 720);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		configuration.setWindowPosition(2200, 100);
		configuration.setResizable(false);

		// game
		GameRogue game = new GameRogue(new Lwjgl3Launcher());

		// run it
		new Lwjgl3Application(game, configuration);
	}
}
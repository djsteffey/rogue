package halfbyte.game.rogue;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import halfbyte.game.rogue.client.GameRogue;
import halfbyte.game.rogue.client.IPlatformServices;

public class AndroidLauncher extends AndroidApplication implements IPlatformServices {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// config
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		configuration.useImmersiveMode = true;

		// game
		GameRogue game = new GameRogue(this);

		// run it
		initialize(game, configuration);
	}
}
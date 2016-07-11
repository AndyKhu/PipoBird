package com.andykhu.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.andykhu.game.PipoBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PipoBird.WIDTH;
		config.height = PipoBird.HEIGHT;
		config.title = PipoBird.TITLE;
		new LwjglApplication(new PipoBird(), config);
	}
}

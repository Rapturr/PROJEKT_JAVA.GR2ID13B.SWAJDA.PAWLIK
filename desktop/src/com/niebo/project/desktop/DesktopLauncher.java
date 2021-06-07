package com.niebo.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.niebo.project.MyHeaven;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;

		config.width = 1280;
		config.height = 720;
		config.fullscreen = false;
		config.title = "Odysehja";
		config.resizable = true;

		new LwjglApplication(new MyHeaven(), config);
	}
}

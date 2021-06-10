package com.niebo.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.niebo.project.MyHeaven;

/**
 * Główna klasa projektu.
 */
public class DesktopLauncher {
	/**
	 * Klasa main programu.
	 */
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.forceExit = true;
		config.width = 1280;
		config.height = 720;
		config.fullscreen = false;
		config.title = "Plemiona Kosmiczne";
		config.resizable = true;

		new LwjglApplication(new MyHeaven(), config);
	}
}

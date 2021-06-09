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
		/**
		 * @param config objekt, w którym przechowywana jest konfiguracja okna aplikacji.
		 * @param forceExit jeśli jest false, to program może czasem działać w tle.
		 * @param width ustawia szerokość okna
		 * @param height ustawia wysokość okna
		 * @param fullscreen mówi, czy można włączyć tryb pełnoekranowy
		 * @param title ustawia tytuł okna
		 * @param resizable mówi, czy rozmiar okna może być zmieniany
		 */
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.forceExit = true;

		config.width = 1280;
		config.height = 720;
		config.fullscreen = false;
		config.title = "Plemiona Kosmiczne";
		config.resizable = true;

		/**
		 * Wybiera klasę okna i przekazuje konfiguracje.
		 */
		new LwjglApplication(new MyHeaven(), config);
	}
}

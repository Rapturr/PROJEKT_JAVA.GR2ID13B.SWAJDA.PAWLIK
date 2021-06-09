package com.niebo.project;

import com.badlogic.gdx.Game;

/**
 * Klasa rozszerza klasę Game, i nadpisuje jej metody
 */
public class MyHeaven extends Game {
	/**
	 * @param gameScreen obiekt klasy GameScreen
	 */
	GameScreen gameScreen;

	/**
	 * tworzy nowy obiekt klasy GameScreen i ustawia go jako klasę ekranu
	 */
	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	/**
	 * pozbywa się obiektu gameScreen
	 */
	@Override
	public void dispose () {
		gameScreen.dispose();
	}
}

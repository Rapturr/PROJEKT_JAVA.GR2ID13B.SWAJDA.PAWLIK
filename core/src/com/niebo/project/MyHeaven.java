package com.niebo.project;

import com.badlogic.gdx.Game;

public class MyHeaven extends Game {
	GameScreen gameScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		gameScreen.dispose();
	}
}

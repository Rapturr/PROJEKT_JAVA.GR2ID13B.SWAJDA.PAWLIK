package com.niebo.project;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Klasa, w której zapisany jest tryb gry (nowa gra, kontynuacja, wyjście)
 */
public class Menu {
    /**
     * @param menu
     */
    public Boolean menu;
    private Boolean newGame;
    private Boolean continueGame;
    private Space bg;


    public Menu(){

        menu = true;
        newGame = false;
        continueGame = false;
        System.out.println("menu");
        rend();
    }

    public void changeMenu(String choice){
        switch (choice) {
            case "menu":
                this.menu = true;
                this.newGame = false;
                this.continueGame = false;
                System.out.println("menu");
                break;
            case "newGame":
                this.menu = false;
                this.newGame = true;
                this.continueGame = false;
                System.out.println("new game");
                break;
            case "continueGame":
                this.menu = false;
                this.newGame = false;
                this.continueGame = true;
                System.out.println("continue");
                break;
        }
        rend();
    }

    private void rend(){
        if(menu)
            if(this.bg == null)
                this.bg = new Space(0);
            else{
                this.bg.cleanup();
                this.bg = new Space(0);
            }
        else if(newGame)
            if(this.bg == null){
                this.bg = new Space(1);
                this.bg.generatePlanetPosition();
            }
            else{
                this.bg.cleanup();
                this.bg = new Space(1);
                this.bg.generatePlanetPosition();
            }
        else if(continueGame){
            this.bg.continueGame(1);
            this.bg.loadPlanetPosition();
        }
    }

    public void MenuBackground(SpriteBatch batch){
        this.bg.batchWork(batch);
    }
    public void buttonsPlacement(int newGameX, int newGameY, int continueX, int continueY, int exitX, int exitY, int width, int height, SpriteBatch batch){
        this.bg.spaceButtonsPlacement(newGameX, newGameY, continueX, continueY, exitX, exitY, width, height, batch);
    }
    public void touchPlanet(int screenX, int screenY,int width,int height){
        this.bg.checkIfPlanetClicked(screenX, screenY, width, height);
    }
    public void cleanup(){
        bg.cleanup();
    }

    }

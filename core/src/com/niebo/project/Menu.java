package com.niebo.project;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Klasa, w której zapisany jest tryb gry (nowa gra, kontynuacja, wyjście)
 */
public class Menu {
    /**
     * menu informuje, czy gra jest teraz w trybie menu
     */
    public Boolean menu;
    /**
     * newGame informuje, czy gra jest teraz w trybie newGame
     */
    private Boolean newGame;
    /**
     * continueGame informuje, czy gra jest teraz w trybie continueGame
     */
    private Boolean continueGame;
    /**
     * Tworzy obiekt klasy Space, która przechowuje metody związane z logiką gry
     */
    private Space bg;


    /**
     * Konstruktor klasy main
     */
    public Menu(){
        menu = true;
        newGame = false;
        continueGame = false;
        System.out.println("menu");
        rend();
    }

    /**
     * funkcja służąca do zmiany trybu gry
     * @param choice przechowuje wybór trybu
     */
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

    /**
     * po wybraniu trybu gry, wykonujemy związane z nimi funkcje, w klasie space
     */
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
            if(this.bg == null){
                this.bg = new Space(1);
                this.bg.loadPlanetPosition();
            }
            else{
                this.bg.cleanup();
                this.bg = new Space(1);
                this.bg.loadPlanetPosition();
            }
        }
    }

    /**
     * przekazuje batch do obiektu klasy Space, gdzie umieszczane będzie tło oraz obiekty na mapie
     * @param batch obiekt batch przekazywany z klasy GameScreen
     */
    public void MenuBackground(SpriteBatch batch){
        this.bg.batchWork(batch);
    }

    /**
     * Przekazuje położenie przycisków do klasy Space
     * @param newGameX pozycja X przycisku new game
     * @param newGameY pozycja Y przycisku new game
     * @param continueX pozycja X przycisku continue
     * @param continueY pozycja Y przycisku continue
     * @param exitX pozycja X przycisku exit
     * @param exitY pozycja Y przycisku exit
     * @param width szerokość przycisków
     * @param height wysokość przycisków
     * @param batch obiekt batch
     */
    public void buttonsPlacement(int newGameX, int newGameY, int continueX, int continueY, int exitX, int exitY, int width, int height, SpriteBatch batch){
        this.bg.spaceButtonsPlacement(newGameX, newGameY, continueX, continueY, exitX, exitY, width, height, batch);
    }

    /**
     * przekazywanie pozycji myszy, w celu sprawdzenia, czy najechała ona na obiekt na mapie
     * @param screenX pozycja myszy X
     * @param screenY pozycja myszy Y
     * @param width szerokość ekranu
     * @param height wysokość ekranu
     */
    public void touchPlanet(int screenX, int screenY,int width,int height){
        this.bg.checkIfPlanetClicked(screenX, screenY, width, height);
    }
    /**
     * usuwanie obiektu bg klasy Space
     */
    public void cleanup(){
        bg.cleanup();
    }
}
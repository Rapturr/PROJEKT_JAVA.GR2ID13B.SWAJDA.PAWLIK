package com.niebo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.*;

/**
 * Space to klasa, w której znajdują się akcje związane z obiektami na mapie (zaznaczanie, atakowanie itd.)
 */
public class Space {
    /**
     * różnica w odległości tła, potrzebna do zrobienia animowanego tła
     */
    float backgroundOffset;
    /**
     * zmienna która mówi, które tło powinno być pokazane
     */
    boolean gameBackground;
    /**
     * Inicjowanie atlasu tekstur
     */
    private TextureAtlas textureAtlas;
    /**
     * tablica tekstur tła
     */
    private TextureRegion[] background;
    /**
     * tablica tekstur planet
     */
    private TextureRegion[] planets;
    /**
     * tablica tekstur przycisków
     */
    private TextureRegion[] buttons;
    /**
     * tablica generowanych pozycji X planet
     */
    private int[] tablicaX;
    /**
     * tablica generowanych pozycji Y planet
     */
    private int[] tablicaY;
    /**
     * tablica generowanych typów planet
     */
    private int[] tablicaPlanet;
    /**
     * zmienna, mówiąca czy ma być wykonywany atak
     */
    private static boolean atakowana = false;
    /**
     * pomocnicza zmienna mówiąca, o tym czy jest to gracz.
     */
    boolean isplayer;
    /**
     * obiekt klasy Player, reprezentujący gracza
     */
    Player player;
    /**
     * obiekt klasy Player, reprezentujący przeciwnika
     */
    Player enemy;
    /**
     * tablica indeksów atakujących planet gracza
     */
    int[] indeksAtakujacej = new int[60];
    /**
     * indeks planety atakowanej przeciwnika
     */
    int indeksAtakowanej;
    /**
     * ilość woisk, jaką gracz wpisał
     */
    private int iloscWoiskAtakujacych = 0;
    /**
     * zmienna, przechowuje informacje o ataku(czy przeciwnik ma jeszcze jakieś woiska, czy został podbity)
     */
    private int liczba = -1;


    /**
     * ładowanie tekstur
     */
    private void loadTextures(){
        if(textureAtlas == null)
            textureAtlas = new TextureAtlas("Spaceatlas.atlas");
        if(background == null)
            this.background = new TextureRegion[5];
        this.background[0] = textureAtlas.findRegion("Earth");
        this.background[1] = textureAtlas.findRegion("Space");
        this.background[2] = textureAtlas.findRegion("Stars0");
        this.background[3] = textureAtlas.findRegion("Stars1");
        this.background[4] = textureAtlas.findRegion("Stars2");

        if(buttons == null)
            this.buttons = new TextureRegion[3];
        this.buttons[0] = textureAtlas.findRegion("newGame");
        this.buttons[1] = textureAtlas.findRegion("continue");
        this.buttons[2] = textureAtlas.findRegion("exit");

        if(this.planets == null)
            this.planets = new TextureRegion[18];
        this.planets[0] = textureAtlas.findRegion("playerlv1");
        this.planets[1] = textureAtlas.findRegion("playerlv2");
        this.planets[2] = textureAtlas.findRegion("playerlv3");
        this.planets[3] = textureAtlas.findRegion("playerlv4");

        this.planets[4] = textureAtlas.findRegion("lv1");
        this.planets[5] = textureAtlas.findRegion("lv2");
        this.planets[6] = textureAtlas.findRegion("lv3");
        this.planets[7] = textureAtlas.findRegion("lv4");
        this.planets[8] = textureAtlas.findRegion("planetMenu");
        this.planets[9] = textureAtlas.findRegion("attack");

        this.planets[10] = textureAtlas.findRegion("now1");
        this.planets[11] = textureAtlas.findRegion("now2");
        this.planets[12] = textureAtlas.findRegion("now3");
        this.planets[13] = textureAtlas.findRegion("now4");

        this.planets[14] = textureAtlas.findRegion("attack1");
        this.planets[15] = textureAtlas.findRegion("attack2");
        this.planets[16] = textureAtlas.findRegion("attack3");
        this.planets[17] = textureAtlas.findRegion("attack4");
    }


    /**
     * konstruktor
     * @param i mówi, o tym które tło powinno być generowane
     */
    public Space(int i){
        loadTextures();
        this.gameBackground = i != 0;
        this.tablicaX = new int[50];
        this.tablicaY = new int[50];
        this.tablicaPlanet = new int[50];
        for(int j=0; j<60; j++){
            this.indeksAtakujacej[j] = -1;
        }
    }

    /**
     * przechodzimy w tej funkcji do renderBackground
     * @param batch obiekt batch
     */
    public void batchWork(SpriteBatch batch){
        renderBackground(batch);
    }

    /**
     * funkcja renderująca tło gry
     * @param batch obiekt batch
     */
    private void renderBackground(SpriteBatch batch){
        float spaceX = 0;
        float spaceY = 0;
        if(!gameBackground){
            batch.draw(background[0], spaceX, spaceY);
        }
        else{
            batch.draw(background[1], spaceX, spaceY);
            batch.draw(background[2], spaceX +backgroundOffset/4, spaceY);
            batch.draw(background[3], spaceX +backgroundOffset/2, spaceY);
            batch.draw(background[4], spaceX +backgroundOffset, spaceY);

            batch.draw(background[2],(spaceX +backgroundOffset/4)-3498, spaceY);
            batch.draw(background[3],(spaceX +backgroundOffset/2)-3498, spaceY);
            batch.draw(background[4],(spaceX +backgroundOffset)-3498, spaceY);
            drawPlanets(batch);
        }
        this.backgroundOffset++;
        if(backgroundOffset >=3498)
            this.backgroundOffset = 0;

    }

    /**
     * w menu gry, renderuje przyciski.
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
    public void spaceButtonsPlacement(int newGameX, int newGameY, int continueX, int continueY, int exitX, int exitY, int width, int height, SpriteBatch batch){
        if(!gameBackground){
            batch.draw(buttons[0],newGameX,newGameY,width,height);
            batch.draw(buttons[1],continueX,continueY,width,height);
            batch.draw(buttons[2],exitX,exitY,width,height);
        }
        if(this.indeksAtakowanej >= 0 && this.enemy != null) {
            atakowana = this.enemy.attack(this.indeksAtakowanej);
        }
        if(atakowana){
            akcja();
        }
    }

    /**
     * Generowanie pozycji planet
     */
    public void generatePlanetPosition(){
        for(int i=0; i<50; i++) {
            int x = (int) (Math.random() * background[1].getRegionWidth());
            int y = (int) (Math.random() * background[1].getRegionHeight());
            if (x > 3700 || y > 2000) {
                x = (int) (Math.random() * background[1].getRegionWidth());
                y = (int) (Math.random() * background[1].getRegionHeight());
            }
            tablicaX[i] = x;
            tablicaY[i] = y;
        }
        int tmpX;
        int tmpY;

        for(int i=0; i<50; i++){
            for(int j=0; j<i; j++){
                if((tablicaY[i]+50) - (tablicaY[j]+50) < 0)
                    tmpY = (((tablicaY[i]+50) - (tablicaY[j]+50))*(-1));
                else
                    tmpY = (tablicaY[i]+50) - (tablicaY[j]+50);

                if((tablicaX[i]+50)-(tablicaX[j]+50) < 0)
                    tmpX = (((tablicaX[i]+50) - (tablicaX[j]+50))*(-1));
                else
                    tmpX = (tablicaX[i]+50) - (tablicaX[j]+50);

                if(tmpX < 100 && tmpY < 100){
                    tablicaX[i] = (int) (Math.random() * background[1].getRegionWidth());
                    tablicaY[i] = (int) (Math.random() * background[1].getRegionHeight());
                    if (tablicaX[i] > 3700 || tablicaY[j] > 2000) {
                        tablicaX[i] = (int) (Math.random() * background[1].getRegionWidth());
                        tablicaY[i] = (int) (Math.random() * background[1].getRegionHeight());
                    }
                    i=-1;
                    break;
                }
            }
        }

        for(int i=0; i<50; i++){
            if(i < 25) {
                this.tablicaPlanet[i] = 1;
            }
            else if(i > 25 && i <= 40) {
                this.tablicaPlanet[i] = 2;
            }
            else if(i > 40 && i <= 48) {
                this.tablicaPlanet[i] = 3;
            }
            else {
                this.tablicaPlanet[i] = 4;
            }
        }

        if(this.player == null){
            this.player = new Player(true);
        }
        if(this.enemy == null)
            this.enemy = new Player(false);

        for(int i = 1; i<50; i++){
            if(this.tablicaPlanet[i] == 1)
                this.enemy.addPlanet(tablicaX[i],tablicaY[i],5);
            else if(this.tablicaPlanet[i] == 2)
                this.enemy.addPlanet(tablicaX[i],tablicaY[i],6);
            else if(this.tablicaPlanet[i] == 3)
                this.enemy.addPlanet(tablicaX[i],tablicaY[i],7);
            else if(this.tablicaPlanet[i] == 4)
                this.enemy.addPlanet(tablicaX[i],tablicaY[i],8);
        }
        this.isplayer = true;
        this.player.addPlanet(tablicaX[0],tablicaY[0],1);
    }

    /**
     * przekazujemy obiekt batch klasy SpriteBatch i tekstury planet do funkcji draw graczy
     * @param batch obiekt batch
     */
    private void drawPlanets(SpriteBatch batch){
        if(this.enemy != null)
            this.enemy.draw(batch, this.planets);
        if(this.player != null)
            this.player.draw(batch, this.planets);
    }

    /**
     * Sprawdzanie, czy planeta została naciśnięta
     * @param posX pozycja myszy X
     * @param posY pozycja myszy Y
     * @param width
     * @param height
     */
    public void checkIfPlanetClicked(int posX, int posY,int width,int height){

        this.player.saveGame();
        this.enemy.saveGame();

        int p0, p1;
        p0 = this.player.checkIfClicked(posX, posY,width,height);
        p1 = this.enemy.checkIfClicked(posX, posY,width,height);
        if(p0 == -1 && p1 == -1){
            for(int i = 0; i<49; i++)
                if(this.indeksAtakujacej[i] >= 0)
                    player.changePlanetGraphics(this.indeksAtakujacej[i],1);
                if(this.indeksAtakowanej >= 0)
                    enemy.changePlanetGraphics(this.indeksAtakowanej,1);
        }
        else{
            if(p0 >= 0){
                for(int i = 0; i<49; i++){
                    if(this.indeksAtakujacej[i] != p0){
                        this.indeksAtakujacej[i] = p0;
                        player.changePlanetGraphics(this.indeksAtakujacej[i],2);
                        break;
                    }
                    else{
                        this.indeksAtakujacej[i] = -1;
                        player.changePlanetGraphics(this.indeksAtakujacej[i],1);
                        break;
                    }
                }
            }
            if(this.indeksAtakowanej != p1){
                enemy.changePlanetGraphics(this.indeksAtakowanej,1);
                this.indeksAtakowanej = p1;
            }
            enemy.changePlanetGraphics(this.indeksAtakowanej,2);

            /*if(this.indeksAtakowanej >= 0) {
                atakowana = this.enemy.attack(p1);
            }*/
        }
    }

    private void akcja(){
        boolean check = false;
        for(int i=0; i<49; i++){
            if(this.indeksAtakujacej[i] != -1){
                System.out.println("Indeks atakujacej: "+this.indeksAtakujacej[i]);
                check = true;
                break;
            }
        }
        //Przywracanie armii graczowi!
        if(this.indeksAtakowanej>=0)
        if(check) {
            for(int i=0; i<49; i++){
                if(this.indeksAtakujacej[i]>-1)
                    this.iloscWoiskAtakujacych += this.player.selectPlanet(0, this.indeksAtakujacej[i]);
            }
            this.liczba = this.enemy.selectPlanet(iloscWoiskAtakujacych, indeksAtakowanej);

            if(this.liczba == 0){
                int tech = this.enemy.tech(indeksAtakowanej);
                int x = this.enemy.getx(indeksAtakowanej);
                int y = this.enemy.gety(indeksAtakowanej);
                this.enemy.deletePlanet(indeksAtakowanej);
                if(tech > -1)
                this.player.addPlanet(x, y, tech, 0);
                for(int i = 0; i<50; i++){
                    if(tablicaX[i] == x && tablicaY[i] == y){
                        if(tech <= 3) {
                            tablicaPlanet[i] = 1;
                        }
                        else if(tech <= 7){
                            tablicaPlanet[i] = 2;
                        }
                        else if(tech < 10) {
                            tablicaPlanet[i] = 3;
                        }
                        else{
                            tablicaPlanet[i] = 4;
                        }
                    }
                }
                System.out.println("ilosc planet "+this.player.iloscPlanet);
                //JOptionPane.showMessageDialog(null,"Przejeto planete");
            }
            boolean playerArmyRestored = false;
            for (int j = 0; j < 49; j++) {
                if(this.indeksAtakujacej[j] != -1 && this.liczba > -1){
                    playerArmyRestored = this.player.attackMyPl(indeksAtakujacej[j], this.liczba);
                }
                this.indeksAtakujacej[j] = -1;
            }
            if(playerArmyRestored) {
                this.enemy.resAttack(indeksAtakowanej);
                this.indeksAtakowanej = -1;
                atakowana = false;
            }
            this.iloscWoiskAtakujacych = 0;
        }
    }

    public void loadPlanetPosition(){
        if(this.player == null){
            this.player = new Player(true);
        }
        if(this.enemy == null)
            this.enemy = new Player(false);

        this.player.loadGame();
        this.enemy.loadGame();
    }

    public void cleanup(){
        this.textureAtlas.dispose();
    }
}

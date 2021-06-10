package com.niebo.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Klasa gracza, przechowuje metody takie, jak zapisywanie i wczytywanie gry, dodawanie graczowi planet.
 */
public class Player extends ApplicationAdapter implements TextInputListener{
    private int planetCount;
    Planet planet[];
    public String playerName;
    private boolean myPlayer;
    public String czytam;
    public FileHandle file;
    public int iloscPlanet = 0;

    /**
     * Konstruktor klasy
     * @param myPlayer mówi, czy jest to gracz, czy przeciwnik
     */
    public Player(boolean myPlayer) {
        this.planetCount = 50;
        this.planet = new Planet[50];
        this.myPlayer = myPlayer;
        if (myPlayer) {
            Gdx.input.getTextInput(this, "title", "", "Podaj nick");
            file = Gdx.files.local("playerName.txt");
        }
    }

    /**
     * dodanie planety graczowi
     * @param tabx pozycja X planety
     * @param taby pozycja Y planety
     * @param typ typ planety
     */
    public void addPlanet(int tabx, int taby, int typ){
        int i;
        for(i=0; i<planetCount; i++) {
            if (this.planet[i] == null) {
                this.planet[i] = new Planet(tabx, taby, typ);
                this.iloscPlanet++;
                break;
            }
        }

    }

    /**
     * Dodanie planety, podając technologię zamiast typu(przejmowanie)
     * @param tabx pozycja X
     * @param taby pozycja Y
     * @param tech nr. technologii
     * @param typ typ (=0)
     */
    public void addPlanet(int tabx, int taby, int tech, int typ){
        int i;
        this.planetCount++;
        for(i=0; i<planetCount; i++) {
            if (this.planet[i] == null) {
                if(tech <= 3) {
                    this.planet[i] = new Planet(tabx, taby, 1);
                    this.iloscPlanet++;
                }
                else if(tech <= 7){
                    this.planet[i] = new Planet(tabx, taby, 2);
                    this.iloscPlanet++;
                }
                else if(tech < 10) {
                    this.planet[i] = new Planet(tabx, taby, 3);
                    this.iloscPlanet++;
                }
                else{
                    this.planet[i] = new Planet(tabx, taby, 4);
                    this.iloscPlanet++;
                }
                this.planet[i].technology = tech;
                this.planet[i].totalArmySize = 40;
                break;
            }
            System.out.println("Ilosc planet "+this.iloscPlanet);
        }
    }

    /**
     * przekazanie batch do klasy planet
     * @param batch obiekt batch
     * @param region tekstury
     */
    public void draw(SpriteBatch batch, TextureRegion region[]){
        for(int i = 0; i < this.planetCount-1; i++){
            if(i<50)
            if(this.planet[i] != null)
                this.planet[i].draw(batch, region);
        }
        for (Planet pl: planet) {
            if(pl != null)
            pl.showPlanetInfo(batch, region);
        }
    }

    /**
     * Sprawdzanie, czy planeta została kliknięta
     * @param posX pozycja X myszy
     * @param posY pozycja Y myszy
     * @param width Szerokość okna
     * @param height Wysokość okna
     * @return zwraca indeks planety
     */
    //Sprawdzanie czy gracz nacisnal na planete
    public int checkIfClicked(int posX, int posY,int width,int height){
        for(int j = 0; j < planetCount-1; j++){
            if(j<50)
            if(this.myPlayer && this.planet[j] != null) {
                if(this.planet[j].checkIfClicked(posX, posY, width, height) == 1){
                    //System.out.println("I'm returning: "+j);
                    return j;
                }
            }
            else if(this.planet[j] != null)
                if(this.planet[j].checkIfEnemyClicked(posX,posY, width, height) == 1){
                //System.out.println("I'm returning: "+j);
                return j;
            }
        }
        System.out.println("Im returning -1");
        return -1;
    }

    /**
     * Wybranie planety, oraz zaatakowanie wrogiej
     * @param number ilość woisk
     * @param pos indeks planety
     * @return zwraca liczbę zwróconych woisk lub liczbę atakujących woisk
     */
    public int selectPlanet(int number, int pos){
        if(pos >= 0){
            if(this.myPlayer && this.planet[pos] != null){
                int num;
                num = this.planet[pos].attackPlanet();
                this.planet[pos].attack = 0;
                return num;
            }
            else{
                if(this.planet[pos] != null) {
                    int liczba = this.planet[pos].zaatakowana(number);
                    System.out.println(this.planet[pos].totalArmySize);
                    if (liczba == 0) {
                        this.planet[pos].enemy = false;
                    }
                    return liczba;
                }
                return 0;
            }
        }
        return 0;
    }

    /**
     * sprawdzanie, czy planeta jest atakowana
     * @param pos indeks planety
     * @return zwraca true, jeśli planeta jest atakowana
     */
    public boolean attack(int pos){
        if(this.planet[pos] != null) {
            return this.planet[pos].isAttack;
        }
        return false;
    }

    /**
     * wykonanie ataku
     * @param pos indeks planety
     * @param enemyBarrier wartość bariery planety
     * @return zwraca true, jesli planeta została zaatakowana
     */
    public boolean attackMyPl(int pos, int enemyBarrier){
        if(this.myPlayer && this.planet[pos] != null){
            if(enemyBarrier == 0) enemyBarrier += 1;

            int army = this.planet[pos].attack/enemyBarrier;
            this.planet[pos].totalArmySize += army;
            System.out.println("Armia: "+this.planet[pos].totalArmySize+" zaatakowalo: "+this.planet[pos].attack+"  otrzymano "+army);
            //this.planet[pos].attack = 0;
            return true;
        }
        return false;
    }

    /**
     * resetuje wartość zmiennej attack planety
     * @param pos pos indeks planety
     */
    public void resAttack(int pos){
        if(this.planet[pos] != null && pos >= 0){
            this.planet[pos].isAttack = false;
        }
    }

    /**
     * zwraca parametr planety
     * @param pos indeks planety
     * @return zwraca wartość technologii planety
     */
    public int tech(int pos){
        if(pos >= 0)
        if(this.planet[pos] != null){
        int tech = this.planet[pos].technology;
        return tech;
        }
        return -1;
    }
    /**
     * zwraca parametr planety
     * @param pos indeks planety
     * @return zwraca pozycję X planety
     */
    public int getx(int pos){
        if(this.planet[pos] != null && pos >= 0){
            int x = this.planet[pos].positionx;
            return x;
        }
        return -1;
    }
    /**
     * zwraca parametr planety
     * @param pos indeks planety
     * @return zwraca pozycję Y planety
     */
    public int gety(int pos){
        if(this.planet[pos] != null && pos >= 0){
            int x = this.planet[pos].positiony;
            return x;
        }
        return -1;
    }

    /**
     * usunięcie planety
     * @param pos indeks planety
     */
    public void deletePlanet(int pos){
        if(this.planet[pos] != null){
            this.planet[pos] = null;
        }
    }

    /**
     * Zaznaczenie planety
     * @param pos indeks planety
     * @param res reset zaznaczenia
     */
    public void changePlanetGraphics(int pos, int res){
        if(pos > -1)
            if(this.planet[pos] != null)
            if(this.planet[pos].zaznaczona && res == 1)
                this.planet[pos].zaznaczona = false;
            else if(res == 2){
                this.planet[pos].zaznaczona = true;
            }
    }

    /**
     * Funkcja zapisująca grę
     */
    public void saveGame(){
        if(this.myPlayer)
            file = Gdx.files.local("allyplanets.txt");
        else
            file = Gdx.files.local("enemyplanets.txt");
        file.writeString("", false);
        for(int i=0; i<50; i++){
            if(this.planet[i] != null){
                file.writeString(planet[i].positionx+"\n"+planet[i].positiony+"\n"+planet[i].typ+"\n",true);
            }
        }
        //saving resources in order. technology/kryptonite/spaceStone/roadmilk/spacecoins/army   i%6
        if(this.myPlayer){
            file = Gdx.files.local("allyresources.txt");
            file.writeString("", false);
            for(int i=0; i<50; i++){
                if(this.planet[i] != null){
                    file.writeString(planet[i].technology+"\n"+planet[i].kryptonite+"\n"+
                            planet[i].spaceStone+"\n"+planet[i].roadMilk+"\n"+
                            planet[i].spaceCoins+"\n"+planet[i].totalArmySize+"\n"+planet[i].barrierPower+"\n",true);
                }
            }
        }
    }

    /**
     * Funkcja ładująca grę
     */
    public void loadGame(){
        String read;
        //loading the planets into the game from internal files
        if(this.myPlayer)
            read = Gdx.files.internal("allyplanets.txt").readString();
        else
            read = Gdx.files.internal("enemyplanets.txt").readString();
        String[] readPos = read.split("\\r?\\n");
        int x=-1,y=-1,typ;
        int planetnr = 0;
        for(int i=0; i< readPos.length; i++){
            if(this.planet[planetnr] == null){
                if(i%3 == 0){
                    x = Integer.parseInt(readPos[i]);
                }
                else if(i%3 == 1){
                    y = Integer.parseInt(readPos[i]);
                }
                else{
                    typ = Integer.parseInt(readPos[i]);
                    addPlanet(x,y,typ);
                    planetnr++;
                }
            }
        }
        //loading the resources of the planets
        if(this.myPlayer){
            read = Gdx.files.internal("allyresources.txt").readString();
            readPos = read.split("\\r?\\n");
            int technology=-1;
            float totalArmySize=-1,barrierPower = -1;
            float kryptonite=-1,spaceStone=-1;
            float roadMilk=-1,spaceCoins=-1;

            planetnr = 0;
            for(int i=0; i< readPos.length; i++){
                    if(i%7 == 0){
                        technology = Integer.parseInt(readPos[i]);
                        this.planet[planetnr].technology = technology;
                    }
                    else if(i%7 == 1){
                        kryptonite = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].kryptonite = kryptonite;
                    }
                    else if(i%7 == 2){
                        spaceStone = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].spaceStone = spaceStone;
                    }
                    else if(i%7 == 3){
                        roadMilk = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].roadMilk = roadMilk;
                    }
                    else if(i%7 == 4){
                        spaceCoins = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].spaceCoins = spaceCoins;
                    }
                    else if(i%7 == 5){
                        totalArmySize = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].totalArmySize = (int)totalArmySize;
                    }
                    else{
                        barrierPower = Float.parseFloat(readPos[i]);
                        this.planet[planetnr].barrierPower = (int)barrierPower;

                        System.out.println(planet[planetnr].technology+"\n"+planet[planetnr].kryptonite+"\n"+
                                planet[planetnr].spaceStone+"\n"+planet[planetnr].roadMilk+"\n"+
                                planet[planetnr].spaceCoins+"\n"+planet[planetnr].totalArmySize+"\n");
                        planetnr++;
                    }
            }

        }
    }

    /**
     * Wpisanie nazwy gracza
     * @param text wpisany tekst
     */
    @Override
    public void input(String text) {
        this.playerName = text;
        file = Gdx.files.local("playerName.txt");
        file.writeString(text,false);
    }

    /**
     * Przy naciśnięciu cancel, odczytana jest poprzednia nazwa gracza
     */
    @Override
    public void canceled() {
        czytam = Gdx.files.internal("playerName.txt").readString();
        String[] text = czytam.split("\\r?\\n");
        System.out.println(text[0]);
        this.playerName = text[0];
    }
}
package com.niebo.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends ApplicationAdapter implements TextInputListener{
    private int planetCount;
    Planet planet[];
    public String playerName;
    private boolean myPlayer;
    public String czytam;
    public FileHandle file;
    private boolean select = false;
    public int[] planetXPosition = new int[2];
    public int[] planetYPosition = new int[2];
    public int index;
    public int iloscPlanet = 0;

    public Player(boolean myPlayer) {
        this.planetCount = 50;
        this.planet = new Planet[50];
        this.myPlayer = myPlayer;
        if (myPlayer) {
            Gdx.input.getTextInput(this, "title", "", "Podaj nick");
            file = Gdx.files.local("playerName.txt");
        }
    }

    public void addPlanet(int tabx, int taby, int typ){
        int i = 0;
        for(i=0; i<planetCount; i++) {
            if (this.planet[i] == null) {
                this.planet[i] = new Planet(tabx, taby, typ);
                this.iloscPlanet++;
                break;
            }
        }

    }

    public void addPlanet(int tabx, int taby, int tech, int typ){
        int i = 0;
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
    //Sprawdzanie czy gracz nacisnal na planete
    public int checkIfClicked(int posX, int posY,int width,int height){
        int i = new Integer(1);
        /*for (Planet pl: planet) {
            if(myPlayer) {
                if(pl.checkIfClicked(posX, posY, width, height) == 1){

                }
            }
            else i = pl.checkIfEnemyClicked(posX,posY, width, height);
        }*/

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

    public boolean attack(int pos){
        if(this.planet[pos] != null) {
            return this.planet[pos].isAttack;
        }
        return false;
    }

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
    public void resAttack(int pos){
        if(this.planet[pos] != null && pos >= 0){
            this.planet[pos].isAttack = false;
        }
    }

    public int tech(int pos){
        if(pos >= 0)
        if(this.planet[pos] != null){
        int tech = this.planet[pos].technology;
        return tech;
        }
        return -1;
    }
    public int getx(int pos){
        if(this.planet[pos] != null && pos >= 0){
            int x = this.planet[pos].positionx;
            return x;
        }
        return -1;
    }
    public int gety(int pos){
        if(this.planet[pos] != null && pos >= 0){
            int x = this.planet[pos].positiony;
            return x;
        }
        return -1;
    }
    public void deletePlanet(int pos){
        if(this.planet[pos] != null){
            this.planet[pos] = null;
        }
    }

    public void changePlanetGraphics(int pos, int res){
        if(pos > -1)
            if(this.planet[pos] != null)
            if(this.planet[pos].zaznaczona && res == 1)
                this.planet[pos].zaznaczona = false;
            else if(res == 2){
                this.planet[pos].zaznaczona = true;
            }
    }

    public int type(int pos){
        if(pos >= 0)
            if(this.planet[pos] != null){
                int type = this.planet[pos].typ;
                return type;
            }
        return -1;
    }

    public int checkpos(int x, int y){

        for(int i=0; i<50; i++){
            if(this.planet[i] != null)
                if(this.planet[i].positionx == x && this.planet[i].positiony == y){
                    return i;
                }
        }
        return -1;
    }

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

    public void loadGame(){
        String read;
        //loading the planets into the game from internal files
        if(this.myPlayer)
            read = Gdx.files.internal("allyplanets.txt").readString();
        else
            read = Gdx.files.internal("enemyplanets.txt").readString();
        String[] readPos = read.split("\\r?\\n");
        int x=-1,y=-1,typ=-1;
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

    @Override
    public void input(String text) {
        this.playerName = text;
        file = Gdx.files.local("playerName.txt");
        file.writeString(text,false);
    }

    @Override
    public void canceled() {
        czytam = Gdx.files.internal("playerName.txt").readString();
        String[] text = czytam.split("\\r?\\n");
        System.out.println(text[0]);
    }
}
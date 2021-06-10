package com.niebo.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Klasa planety, przechowuje informacje o planecie
 */
public class Planet implements Input.TextInputListener {
    //Statystyki planety
    /**
     * Bariera planety
     */
    public int barrierPower;
    /**
     * Typ planety
     */
    public int typ;
    //zasoby zbierane na planecie
    /**
     * Ilość surowca kryptonite
     */
    public float kryptonite;
    /**
     * Ilość surowca spaceStone
     */
    public float spaceStone;
    /**
     * Ilość surowca roadMilk
     */
    public float roadMilk;
    /**
     * wartość technologii planety
     */
    public int technology;
    /**
     * ilość surowca spaceCoins
     */
    public float spaceCoins;
    /**
     * Ilość jednostek, jakich posiada planeta
     */
    public int totalArmySize;
    /**
     * pozycja planety X
     */
    public int positionx;
    /**
     * pozycja planety Y
     */
    public int positiony;
    /**
     * pomocnicza zmienna, mówi o tym, czy mają się pokazywać informacje o planecie
     */
    public boolean showInfo;
    /**
     * mówi o tym czy jest to planeta przeciwnika
     */
    public boolean enemy;
    /**
     * pozycja okna X
     */
    private int windowPosX;
    /**
     * pozycja okna Y
     */
    private int windowPosY;
    /**
     * czcionka, służy do wypisywania wartości zasobów planety
     */
    private final BitmapFont font;
    /**
     * czas systemowy
     */
    private long startTime = System.currentTimeMillis();
    /**
     * pomocnicza zmienna, pomaga w dodawaniu zasobów planety
     */
    private long oldTime = 1;
    /**
     * pomocnicza zmienna, pomaga w dodawaniu zasobów planety
     */
    private boolean add = false;
    //System.out.println("Time elapsed in seconds = "+((System.currentTimeMillis() - startTime)/1000));
    /**
     * ilość woisk atakujących
     */
    public int attack;
    /**
     * mówi o tym, czy planeta jest atakowana
     */
    public boolean isAttack = false;
    /**
     * mówi o tym, czy planeta jest zaznaczona
     */
    public boolean zaznaczona = false;

    /**
     * Konstruktor planet
     * @param positionx pozycja X planety
     * @param positiony pozycja Y planety
     * @param typ tup planety
     */
    public Planet(int positionx, int positiony, int typ){
        this.positionx = positionx;
        this.positiony = positiony;
        this.typ = typ;
        giveStats();
        this.showInfo = false;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    /**
     * nadanie statystyk planet
     */
    private void giveStats(){
        Random nazwa = new Random();
        this.kryptonite = nazwa.nextInt(10)+1;
        this.spaceStone = nazwa.nextInt(10)+1;
        this.roadMilk = nazwa.nextInt(10)+1;
        this.spaceCoins = 0;
        giveTech();
        giveBarrier();
        giveArmy();
    }

    /**
     * nadaje poziom technologii
     */
    private void giveTech(){
        if(this.typ == 1)
            this.technology = 1;
        else if(this.typ == 5)
            this.technology = (int) (Math.random() * 3) + 1;
        else if(this.typ == 2 || this.typ == 6)
            this.technology = (int) (Math.random() * 3) + 4;
        else if(this.typ == 3 || this.typ == 7)
            this.technology = (int) (Math.random() * 2) + 8;
        else
            this.technology = 10;
    }

    /**
     * nadaje poziom bariery
     */
    private void giveBarrier(){
        if(this.typ != 8)
            this.barrierPower = this.technology*5;
        else
            this.barrierPower = 60;
    }

    /**
     * daje planecie początkową ilość armii
     */
    private void giveArmy(){
        if (this.typ == 1)
            this.totalArmySize = 100;
        else if(this.typ == 5)
            this.totalArmySize = (int) (Math.random() * 100) + 100;//100 + 100
        else if(this.typ == 2 || this.typ == 6)
            this.totalArmySize = (int) (Math.random() * 300) + 200;//300 + 200
        else if(this.typ == 3 || this.typ == 7)
            this.totalArmySize = (int) (Math.random() * 1000) + 500;
        else
            this.totalArmySize = (int) (Math.random() * 3500) + 2000;
    }


    /**
     * położenie planety na mapie
     * @param batch obiekt batch
     * @param region tekstury
     */
    public void draw(SpriteBatch batch, TextureRegion[] region){
        if(!zaznaczona){
            if(this.typ == 1)batch.draw(region[0],positionx,positiony,100,100);
            else if(this.typ == 2)batch.draw(region[1],positionx,positiony,100,100);
            else if(this.typ == 3)batch.draw(region[2],positionx,positiony,100,100);
            else if(this.typ == 4)batch.draw(region[3],positionx,positiony,100,100);
            else if(this.typ == 5)batch.draw(region[4],positionx,positiony,100,100);
            else if(this.typ == 6)batch.draw(region[5],positionx,positiony,100,100);
            else if(this.typ == 7)batch.draw(region[6],positionx,positiony,100,100);
            else if(this.typ == 8)batch.draw(region[7],positionx,positiony,100,100);
        }
        else{
            if(this.typ == 1)batch.draw(region[10],positionx,positiony,100,100);
            else if(this.typ == 2)batch.draw(region[11],positionx,positiony,100,100);
            else if(this.typ == 3)batch.draw(region[12],positionx,positiony,100,100);
            else if(this.typ == 4)batch.draw(region[13],positionx,positiony,100,100);
            else if(this.typ == 5)batch.draw(region[14],positionx,positiony,100,100);
            else if(this.typ == 6)batch.draw(region[15],positionx,positiony,100,100);
            else if(this.typ == 7)batch.draw(region[16],positionx,positiony,100,100);
            else if(this.typ == 8)batch.draw(region[17],positionx,positiony,100,100);
        }
        checkTimer();
    }

    /**
     * Sprawdzanie czasu, nadawanie surowców
     */
    private void checkTimer() {
        long currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (add){
                if (currentTime % 2 == 0 && this.kryptonite < 1200) {
                    this.kryptonite += ((float) this.technology) / 2;
                    add = false;
                }
                if (currentTime % 4 == 0 && this.spaceStone < 1200) {
                    this.spaceStone += ((float) this.technology) / 2;
                }
                if (currentTime % 6 == 0 && this.roadMilk < 1200) {
                    this.roadMilk += ((float) this.technology) /2;
                }
                if(currentTime % 1000000 == 0){
                    this.spaceCoins++;
                    this.startTime = System.currentTimeMillis();
                }
            }
            if(currentTime==this.oldTime)
                add = true;
            else
                this.oldTime = currentTime+1;
    }

    /**
     * sprawdzanie, czy planeta została nacisnięta, jeśli okienko informacji jest włączone, to przekazuje parametry do
     * odpowiedniej funkcji, enemy = false
     * @param posX pozycja myszy X
     * @param posY pozycja myszy Y
     * @param height szerokość okna
     * @return zwraca 1 jeśli włączy okno, 2 jeśli okno interakcji jest już włączone
     */
    //Sprawdzanie nacisniecia na planete
    public int checkIfClicked(int posX, int posY, int height){
        if(this.showInfo){
            windowInteractions(posX,posY, height);
        }
        else if(posX > positionx && posX <= positionx+100 && posY <  height - positiony && posY >= height - positiony-100){
            showInfo = true;
            enemy = false;
            return 1;
        }
        return 2;
    }
    /**
     * sprawdzanie, czy planeta została nacisnięta, jeśli okienko informacji jest włączone, to przekazuje parametry do
     * odpowiedniej funkcji, enemy = true
     * @param posX pozycja myszy X
     * @param posY pozycja myszy Y
     * @param height szerokość okna
     * @return zwraca 1 jeśli włączy okno, 2 jeśli okno interakcji jest już włączone
     */
    public int checkIfEnemyClicked(int posX, int posY, int height){
        if(this.showInfo){
            windowInteractions(posX,posY, height);
        }
        else if(posX > positionx && posX <= positionx+100 && posY <  height - positiony && posY >= height - positiony-100){
            showInfo = true;
            enemy = true;
            return 1;
        }
        return 2;
    }

    /**
     * Wypisanie informacji o planecie(zasoby, armia)
     * @param batch obiekt batch
     * @param region tekstury
     */
    public void showPlanetInfo(SpriteBatch batch, TextureRegion[] region){
        if(this.showInfo && !this.enemy){
            if(positiony+450 > 2160 && positionx+400 <= 3840){
                batch.draw(region[8],positionx+100,positiony-450,300,450);
                this.windowPosX = positionx+100;
                this.windowPosY = positiony-450;
            }
            else if(positionx+400 > 3840 && positiony+450 > 2160){
                batch.draw(region[8],positionx-300,positiony-450,300,450);
                this.windowPosX = positionx-300;
                this.windowPosY = positiony-450;
            }
            else if(positionx+400 > 3840){
                batch.draw(region[8],positionx-300,positiony,300,450);
                this.windowPosX = positionx-300;
                this.windowPosY = positiony;
            }
            else{
                batch.draw(region[8],positionx+100,positiony,300,450);
                this.windowPosX = positionx+100;
                this.windowPosY = positiony;
            }
        }
        else if(this.showInfo){
            if(positiony+450 > 2160 && positionx+400 <= 3840){
                batch.draw(region[9],positionx+100,positiony-450,300,250);
                this.windowPosX = positionx+100;
                this.windowPosY = positiony-450;
            }
            else if(positionx+400 > 3840 && positiony+450 > 2160){
                batch.draw(region[9],positionx-300,positiony-450,300,250);
                this.windowPosX = positionx-300;
                this.windowPosY = positiony-450;
            }
            else if(positionx+400 > 3840){
                batch.draw(region[9],positionx-300,positiony,300,250);
                this.windowPosX = positionx-300;
                this.windowPosY = positiony;
            }
            else{
                batch.draw(region[9],positionx+100,positiony,300,250);
                this.windowPosX = positionx+100;
                this.windowPosY = positiony;
            }
        }
        if(this.showInfo)
            showStats(batch);
    }

    /**
     * interakcje w oknie informacji
     * @param posX pozycja X myszy
     * @param posY pozycja Y myszy
     * @param height wysokość okna
     */
    private void windowInteractions(int posX, int posY, int height){
        if(this.enemy){
            if(posX > this.windowPosX && posX <= this.windowPosX+300 && posY < height - this.windowPosY && posY >= height - this.windowPosY-250){
                //System.out.println("posx "+posX+"   windowposx  "+this.windowPosX+"  posy "+posY+"   height - windowPosY "+(height - this.windowPosY));
                //Attack
                if(posX > this.windowPosX+32 && posX <= this.windowPosX+272 && posY < height - this.windowPosY - 40 && posY >= height - this.windowPosY - 82){
                    System.out.println("Attack");
                    this.isAttack = true;
                    this.showInfo = false;
                }
            }
            else{
                this.showInfo = false;
            }
        }
        else{
            if(posX > this.windowPosX && posX <= this.windowPosX+300 && posY < height - this.windowPosY && posY >= height - this.windowPosY-450){
                //System.out.println("posx "+posX+"   windowposx  "+this.windowPosX+"  posy "+posY+"   height - windowPosY "+(height - this.windowPosY));
                //Upgrade
                if(posX > this.windowPosX+26 && posX <= this.windowPosX+259 && posY < height - this.windowPosY - 137 && posY >= height - this.windowPosY - 160){
                    if(this.kryptonite>= (50*this.technology) && this.roadMilk >= (10*this.technology) && this.spaceStone >= (8*this.technology) && this.technology < 10 && this.barrierPower < 50){
                        this.kryptonite-=(50*this.technology);
                        this.roadMilk-=(10*this.technology) ;
                        this.spaceStone-=(8*this.technology) ;
                        if(this.technology < 10)
                            this.technology+=1;
                        if(this.barrierPower < 41)
                            this.barrierPower+=10;
                        if(this.technology == 4 && this.typ==1)
                            this.typ = 2;
                        else if(this.technology == 8 && this.typ==2)
                            this.typ = 3;
                        else if(this.technology == 10 && this.typ==3)
                            this.typ = 4;
                    }
                    else
                        System.out.println("Nie masz wystarczajcych zasobow");
                }
                //Increase army size
                else if(posX > this.windowPosX+26 && posX <= this.windowPosX+259 && posY < height - this.windowPosY - 100 && posY >= height - this.windowPosY - 124){
                    if(this.kryptonite >= (10*this.technology) && this.spaceStone >= (20*this.technology) && this.roadMilk >= (50*this.technology)){
                        this.totalArmySize+=(55*this.technology);
                        this.kryptonite-=(10*this.technology);
                        this.spaceStone-=(20*this.technology);
                        this.roadMilk-=(50*this.technology);
                    }
                    else
                        System.out.println("Nie masz wystarczajcych zasobow");
                }
                //Exit
                else if(posX > this.windowPosX+26 && posX <= this.windowPosX+259 && posY < height - this.windowPosY - 24 && posY >= height - this.windowPosY - 47){
                    System.out.println("Exit");
                    this.showInfo = false;
                }
            }
            else{
                this.showInfo = false;
            }
        }
    }

    /**
     * Wypisanie zasobów planety: armia, technologia, zasoby
     * @param batch obiekt batch
     */
    private void showStats(SpriteBatch batch){
        if(this.showInfo){
            if(this.enemy){
                font.getData().setScale(1.5f,1.5f);
                font.draw(batch,""+this.technology,(float)(this.windowPosX+174),(float)(this.windowPosY+192));
                font.draw(batch,""+this.barrierPower,(float)(this.windowPosX+202),(float)(this.windowPosY+131));
            }
            else{
                font.getData().setScale(1,1);
                font.draw(batch,""+this.technology,(float)(this.windowPosX+180),(float)(this.windowPosY+423));
                font.draw(batch,""+this.kryptonite,(float)(this.windowPosX+171),(float)(this.windowPosY+386));
                font.draw(batch,""+this.spaceStone,(float)(this.windowPosX+183),(float)(this.windowPosY+349));
                font.draw(batch,""+this.roadMilk,(float)(this.windowPosX+162),(float)(this.windowPosY+312));
                font.draw(batch,""+this.spaceCoins,(float)(this.windowPosX+176),(float)(this.windowPosY+275));
                font.draw(batch,""+this.totalArmySize,(float)(this.windowPosX+110),(float)(this.windowPosY+238));
                font.draw(batch,""+this.barrierPower,(float)(this.windowPosX+200),(float)(this.windowPosY+201));
            }
        }
    }

    /**
     * odjęcie armii planety, jeśli została zaatakowana
     * @param number ilość wrogich jednostek
     * @return zwraca moc bariery, lub zero jeśli ilość jednostek jest mniejsza od 5
     */
    public int zaatakowana(int number){
        this.totalArmySize = this.totalArmySize - (number - (number/this.barrierPower));
        if(this.totalArmySize > 5)
            return this.barrierPower;
        else
            return 0;
    }

    /**
     * input użytkownika. Użytkownik wpisuje tutaj, iloma jednostkami chce zaatakować
     * @return zwraca wybraną ilość jednostek
     */
    public int attackPlanet(){
        Gdx.input.getTextInput(this, "Attack", "", "Podaj ilosc woiska");
        System.out.println("Liczba woisk = "+this.attack);
        return this.attack;
    }

    /**
     * tu odbywa się wybranie przez użytkownika ilości woisk
     * @param text wpisany tekst
     */
    @Override
    public void input(String text) {
        int a = Integer.parseInt(text);
        if(a > this.totalArmySize)
            System.out.println("Nie masz tak liczebnej armii");
        else if(a > 0){
            this.attack = a;
            this.isAttack = true;
            if(!this.enemy){
                this.totalArmySize -= a;
                System.out.println("aktualny stan woisk: "+totalArmySize+"  wyjechalo: "+a);
            }
        }
        else{
            System.out.println("Wybierz ilosc woisk");
        }
    }

    @Override
    public void canceled() {

    }
}

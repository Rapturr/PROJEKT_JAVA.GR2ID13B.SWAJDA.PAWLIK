package com.niebo.project;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Klasa GameScreen służy jako główna klasa, w której inicjowane są wszystkie ustawienia początkowe okna gry.
 * ta klasa zajmuje się przyciskami w menu, poruszaniem się po ekranie, zoomowanie itp.
 */
public class GameScreen extends InputAdapter implements Screen, InputProcessor {
    //handling graphics
    /**
     * implementuje kamerę do gry
     */
    OrthographicCamera camera;
    /**
     * implementuje kamerę do menu gry
     */
    OrthographicCamera menuCamera;
    /**
     * WORLD_WIDTH szerokość ekranu
     */
    private final int WORLD_WIDTH = 1280;
    /**
     * WORLD_HEIGHT wysokosc ekranu
     */
    private final int WORLD_HEIGHT = 720;
    /**
     * cameraX położenie horyzontalne kamery
     */
    private float cameraX = WORLD_WIDTH;
    /**
     * cameraY położenie wertykalne kamery
     */
    private float cameraY = WORLD_HEIGHT;
    /**
     * zoomAmount zmienna, którą pilnujemy przybliżenie gry
     */
    private float zoomAmount = 10;
    /**
     * goToMenu zmienna, która informuje nas, że nastąpiło przejście do menu
     */
    private boolean goToMenu;
    /**
     * prevX zmienna, w której przy przesuwaniu ekranu, zapisywana jest poprzednia pozycja horyzontalna ekranu
     */
    private int prevX;
    /**
     * prevY zmienna, w której przy przesuwaniu ekranu, zapisywana jest poprzednia pozycja wertykalna ekranu
     */
    private int prevY;
    /**
     * continueButtonX przechowuje pozycję X kamery, do której przechodzimy po naciśnięciu escape
     */
    private float continueButtonX;
    /**
     * continueButtonY przechowuje pozycję Y kamery, do której przechodzimy po naciśnięciu escape
     */
    private float continueButtonY;

    /**
     * batch obiekt klasy SpriteBatch służy do rysowania kształtów na ekranie
     */
    private final SpriteBatch batch;

    //Placement of buttons in the main menu
    /**
     * newGameX pozycja X przycisku new game
     * newGameY pozycja Y przycisku new game
     * continueX pozycja X przycisku continue
     * continueY pozycja Y przycisku continue
     * exitX pozycja X przycisku exit
     * exitY pozycja Y przycisku exit
     * width szerokość przycisków
     * height wysokość przycisków
     */
    int newGameX = 50, newGameY = WORLD_HEIGHT-150 , continueX = 50, continueY = WORLD_HEIGHT-300,
            exitX = 50, exitY = WORLD_HEIGHT-450, width = 250, height = 75;
    //menu
    /**
     * menu tworzy obiekt klasy Menu
     */
    private final Menu menu;
    /**
     * effectiveViewportWidth szerokość ekranu (szerokość ekranu * zoom)
     */
    float effectiveViewportWidth;
    /**
     * effectiveViewportHeight wysokość ekranu (wysokość ekranu * zoom)
     */
    float effectiveViewportHeight;
    /**
     * gameMusic inicjowanie muzyki do gry
     */
    Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));


    // Functions ====================================================


    /**
     * Konstruktor klasy GameScreen, inicjowana jest tutaj kamera, obiekt klasy Menu, muzyka oraz partia.
     */
    GameScreen(){
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        menuCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        menu = new Menu();
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.12f);
        gameMusic.play();
        batch = new SpriteBatch();
    }

    /**
     * nadpisanie metody render z klasy Screen, wykonuje się cały czas w trakcie działania programu,
     * wykonują się tu akcje takie, jak podstawowe ustawienia kamery, oraz odczyt działań użytkownika,
     * takie jak kliknięcie myszką, lub naciśnięcie przycisku na klawiaturze.
     * Odbywa się to za pomocą zaimplementowanej klasy InputProcessor.
     * @param delta różnica czasu.
     */
    @Override
    public void render(float delta) {

        batch.setTransformMatrix(this.camera.view);
        batch.setProjectionMatrix(this.camera.projection);
        if(this.menu.menu){
            cameraX = WORLD_WIDTH/2;
            cameraY = WORLD_HEIGHT/2;
        }
        batch.begin();

        camera.position.set(cameraX,cameraY,10);

        menu.MenuBackground(batch);
        menu.buttonsPlacement(newGameX, newGameY, continueX, continueY, exitX, exitY, width, height, batch);

        if(goToMenu)
            changeToMenu();



        camera.update();
        Gdx.input.setInputProcessor(this);
        batch.end();
    }

    /**
     * Po naciśnięciu esc, przechodzimy do menu, dzieje się to przez wywołanie tej klasy
     * zmienia ona zoom oraz położenie kamery, oraz w obiekcie menu zmienia tryb na menu.
     */
    private void changeToMenu(){
        cameraX = 640;
        cameraY = 360;
        camera.zoom = 1f;
        zoomAmount = 10f;
        camera.position.set(cameraX,cameraY,10);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        menu.changeMenu("menu");
        goToMenu = false;

    }

    /**
     * służy do pozbycia się umieszczonych w niej obiektów
     */
    @Override
    public void dispose() {
        batch.dispose();
        menu.cleanup();
    }

    /**
     * Wywoływana przy zmianie rozmiaru ekranu
     * @param width szerokość ekranu
     * @param height Wysokość ekranu
     */
    //Not used
    @Override
    public void resize(int width, int height) {
        this.camera.viewportWidth = width;
        this.camera.viewportHeight = height;
        this.camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    //Handling input
    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    /**
     * wywołuje się, kiedy jakiś przycisk na klawiaturze zostanie podniesiony
     * służy do rozpoznawania, czy wciśnięty został przycisk ESCAPE, jeśli tak, to wracamy do menu
     * @param keycode przycisk, który został podniesiony
     * @return zwraca podniesiony przycisk
     */
    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.ESCAPE && !menu.menu){
            continueButtonX = cameraX;
            continueButtonY = cameraY;
            goToMenu = true;
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    /**
     * Wywołana kiedy wciśniemy przycisk myszy
     * służy nam do przemieszczania się po ekranie
     * @param screenX pozycja myszy X
     * @param screenY pozycja myszy Y
     * @param pointer wskaźnik do zdarzenia
     * @param button przycisk, który został wciśnięty
     * @return zwraca to, co przyjmuje
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.RIGHT){
            prevX = screenX;
            prevY = screenY;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    /**
     * wywoływana, kiedy przycisk myszy jest podniesiony
     * pozycje klawiszy przekazujemy do metody obiektu menu, a jeśli jesteśmy w menu gry to wybieramy przyciski.
     * @param screenX pozycja myszy X
     * @param screenY pozycja myszy Y
     * @param pointer wskaźnik do zdarzenia
     * @param button przycisk, który został wciśnięty
     * @return zwraca to, co przyjmuje
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*//float xpos = (screenX * camera.zoom) - (screenX / camera.zoom) + screenX;
        //float ypos = (screenY * camera.zoom) - ( screenY / camera.zoom) + screenY;
        float xpos = screenX * (camera.zoom);
        float ypos = screenY * (camera.zoom);
        if(camera.zoom != 1){
            xpos += (screenX * (2-camera.zoom));
            ypos += (screenY * (2-camera.zoom));
        }
        screenY = (int)ypos;
        screenX = (int)xpos;
        System.out.println("pozycja "+screenY+"  camera zoom "+camera.zoom+"  x "+screenX);*/

        if(menu.menu && button == Input.Buttons.LEFT)
            menuButtons(screenX, screenY);
        if(!menu.menu && button == Input.Buttons.LEFT)
            touchPlanet(screenX, screenY);

        return super.touchUp(screenX, screenY, pointer, button);
    }

    /**
     * wywoływana przy przeciąganiu
     * @param screenX pozycja X myszy
     * @param screenY pozycja Y myszy
     * @param pointer wskaźnik do zdarzenia
     * @return zwraca to, co przyjmuje
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !menu.menu)
            dragCameraPosition(screenX, screenY);

        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    /**
     * wywoływana przy poruszeniu kółkiem myszy
     * służy nam do wywołania funkcji zoomującej ekran
     * @param amountX przesunięcie
     * @param amountY przesunięcie
     * @return zwraca to, co przyjmuje
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(!menu.menu)
            scrollingCamera(amountY);

        return super.scrolled(amountX, amountY);
    }


    /**
     * Funkcja zoomująca ekran gry
     * @param amountY przesunięcie kółka myszy
     */
    private void scrollingCamera(float amountY){
        effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        if(amountY < 0 && zoomAmount > 10){
            camera.zoom -= 0.1f;
            zoomAmount -= 0.1f;
        }
        else if(amountY > 0 && zoomAmount < 10.6){
            camera.zoom += 0.1f;
            zoomAmount += 0.1f;
            effectiveViewportWidth = camera.viewportWidth * camera.zoom;
            effectiveViewportHeight = camera.viewportHeight * camera.zoom;

            if(camera.position.x - effectiveViewportWidth/2 < 0)
                cameraX = 0 + effectiveViewportWidth/2;
            else if(camera.position.x + effectiveViewportWidth/2 > 3840)
                cameraX = 3840 - effectiveViewportWidth/2;
            if(camera.position.y + effectiveViewportHeight/2 > 2160)
                cameraY = 2160 - effectiveViewportHeight/2;
            else if(camera.position.y - effectiveViewportHeight/2 < 0)
                cameraY = 0 + effectiveViewportHeight/2;
        }
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    /**
     * Funkcja przesuwająca ekran gry
     * @param screenX pozycja myszy X
     * @param screenY pozycja myszy Y
     */
    private void dragCameraPosition(int screenX, int screenY){
        if(camera.position.x + effectiveViewportWidth/2 <= 3840 && camera.position.x - effectiveViewportWidth/2 >= 0){
            int diffX = screenX - prevX;
            if(cameraX - diffX >= 0 - effectiveViewportWidth/2 && cameraX - diffX <=3840 + effectiveViewportWidth/2)
                cameraX -= diffX;
        }
        if(camera.position.y + effectiveViewportHeight/2 <= 2160 && camera.position.y - effectiveViewportHeight/2 >= 0){
            int diffY = screenY - prevY;
            if(cameraY + diffY >= 0 - effectiveViewportHeight/2 && cameraY + diffY <=2160 + effectiveViewportHeight/2 )
                cameraY += diffY;
        }
        if(cameraY + effectiveViewportHeight/2 > 2160)
            cameraY = 2160 - effectiveViewportHeight/2;
        else if(cameraY - effectiveViewportHeight/2 < 0)
            cameraY = 0 + effectiveViewportHeight/2;
        if(cameraX + effectiveViewportWidth/2 > 3840)
            cameraX = 3840 - effectiveViewportWidth/2;
        else if(cameraX - effectiveViewportWidth/2 < 0)
            cameraX = 0 + effectiveViewportWidth/2;

        effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        prevX = screenX;
        prevY = screenY;
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    /**
     * Funkcja rozpoznaje, który przycisk w menu został naciśnięty.
     * @param screenX pozycja myszy X
     * @param screenY pozycja myszy Y
     */
    private void menuButtons(int screenX, int screenY){
        if((screenX > (newGameX/menuCamera.viewportWidth)*100 && screenX <= ((newGameX/menuCamera.viewportHeight)*100 + width)) && ((screenY > WORLD_HEIGHT - (newGameY + height)) && (screenY <= WORLD_HEIGHT - newGameY))){
            batch.setProjectionMatrix(camera.combined);
            camera.update();
            menu.changeMenu("newGame");
        }
        else if((screenX > continueX && screenX <= continueX + width) && ((screenY > WORLD_HEIGHT - (continueY + height)) && (screenY <= WORLD_HEIGHT - continueY))){
            camera.position.set(continueButtonX,continueButtonY,10);
            cameraX = continueButtonX;
            cameraY = continueButtonY;

            menu.changeMenu("continueGame");
            batch.setProjectionMatrix(camera.combined);
            camera.update();
        }
        else if((screenX > exitX && screenX <= exitX + width) && ((screenY > WORLD_HEIGHT - (exitY + height)) && (screenY <= WORLD_HEIGHT - exitY))){
            Gdx.app.exit();
        }
    }

    /**
     * przekazuje pozycje myszy do metody touchPlanet w obiekcie menu
     * @param screenX pozycja X
     * @param screenY pozycja Y
     */
    private void touchPlanet(int screenX, int screenY){
        menu.touchPlanet((screenX + (int) camera.position.x) - 640, (screenY - (int) camera.position.y) +360, WORLD_HEIGHT);
    }
}
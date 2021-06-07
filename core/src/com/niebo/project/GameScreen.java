package com.niebo.project;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends InputAdapter implements Screen, InputProcessor {
    //handling graphics
    OrthographicCamera camera;
    OrthographicCamera menuCamera;

    private final int WORLD_WIDTH = 1280;
    private final int WORLD_HEIGHT = 720;

    private float cameraX = WORLD_WIDTH;
    private float cameraY = WORLD_HEIGHT;



    private float zoomAmount = 10;

    private boolean goToMenu;

    private int prevX;
    private int prevY;

    private float continueButtonX;
    private float continueButtonY;

    private final SpriteBatch batch;

    //Placement of buttons in the main menu
    int newGameX = 50, newGameY = WORLD_HEIGHT-150 , continueX = 50, continueY = WORLD_HEIGHT-300,
            exitX = 50, exitY = WORLD_HEIGHT-450, width = 250, height = 75;
    //menu
    private final Menu menu;

    float effectiveViewportWidth;
    float effectiveViewportHeight;


    // Functions ====================================================
    GameScreen(){
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        menuCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        menu = new Menu();

        batch = new SpriteBatch();
    }

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

    @Override
    public void dispose() {
        batch.dispose();
        menu.cleanup();
    }
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

    //Tutej zapisywanie
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.RIGHT){
            prevX = screenX;
            prevY = screenY;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*float xpos = (screenX * camera.zoom) - (screenX / camera.zoom) + screenX;
        float ypos = (screenY * camera.zoom) - ( screenY / camera.zoom) + screenY;
        screenY = (int)ypos;
        screenX = (int)xpos;
        System.out.println("pozycja "+ypos+"  camera zoom "+camera.zoom+"  x "+screenY);*/

        if(menu.menu && button == Input.Buttons.LEFT)
            menuButtons(screenX, screenY);
        if(!menu.menu && button == Input.Buttons.LEFT)
            touchPlanet(screenX, screenY);

        return super.touchUp(screenX, screenY, pointer, button);
    }

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

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(!menu.menu)
            scrollingCamera(amountY);

        return super.scrolled(amountX, amountY);
    }


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

    /*private void touchPlanet(int screenX, int screenY){
        effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        System.out.println( "width"+effectiveViewportWidth+"    height"+effectiveViewportHeight);
        menu.touchPlanet((screenX + (int) (camera.position.x)) - (int)effectiveViewportWidth/2 , (screenY - (int) camera.position.y) +(int)effectiveViewportHeight/2,(int)camera.position.y,WORLD_HEIGHT);
    }*/

    private void touchPlanet(int screenX, int screenY){
        menu.touchPlanet((screenX + (int) camera.position.x) - 640, (screenY - (int) camera.position.y) +360,(int)camera.position.y,WORLD_HEIGHT);
    }
}